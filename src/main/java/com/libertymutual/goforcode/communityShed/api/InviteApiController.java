package com.libertymutual.goforcode.communityShed.api;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;
import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/invites")
@CrossOrigin(origins = "*")

public class InviteApiController {
	
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private InvitedUserRepo invitedUserRepo;
	private MailGunEmailService emailer;
	private PasswordEncoder encoder;
	private ShedUserDetailsService userDetails;
	
	public InviteApiController(UserRepo userRepo, GroupRepo groupRepo, ConfirmedUserRepo confirmedUserRepo, InvitedUserRepo invitedUserRepo, MailGunEmailService emailer, PasswordEncoder encoder, ShedUserDetailsService userDetails) {
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		this.invitedUserRepo = invitedUserRepo;
		this.emailer = emailer;
		this.encoder = encoder;
		this.userDetails = userDetails;
	}
	
	@ApiOperation("Return user details for an invite")
	@GetMapping("{inviteKey}")
	public InvitedUser getUserFromInvite(@PathVariable UUID inviteKey)	{
		return invitedUserRepo.findByInvitationKey(inviteKey);
	}
	
	@ApiOperation("Convert InvitedUser into a ConfirmedUser")
	@PostMapping("{inviteKey}")
	public ConfirmedUser convertInvitedUserAndLogin(@RequestBody ConfirmedUser user, @PathVariable UUID inviteKey)	{
		InvitedUser invited = invitedUserRepo.findByInvitationKey(inviteKey);
		if (user.getEmail().equals(invited.getEmail()))	{
			//remove invited from groups and save -- cascade would be better but can't get it working
			for (Group group : invited.getPendingGroups())	{
				group.removePendingUserFromGroup(invited);
				groupRepo.save(group);
			}
			//delete invited now that it no longer is tied to any pendingGroups
			invitedUserRepo.delete(invited);
			
			//save user now that the email address is available
			user.setPassword(encoder.encode(user.getPassword()));
			user = confirmedUserRepo.save(user);
			//add user to groups and save
			for (Group group : invited.getPendingGroups())	{
				group.addUserToGroup(user);
				groupRepo.save(group);
			}
			
			//Authenticate user
			UserDetails details = userDetails.loadUserByUsername(user.getEmail());
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, user.getPassword(), details.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(token);
			return user;
		}
		return null;
	}
	
	@ApiOperation("Generate invite for a group")
	@PostMapping("group/{groupId}")
	public void	inviteUser(Authentication auth, @RequestBody inviteEmail inviteEmail, @PathVariable long groupId)	{
		//get session user
		ConfirmedUser authUser = (ConfirmedUser) auth.getPrincipal();
		authUser = (ConfirmedUser) confirmedUserRepo.findOne(authUser.getId());
		//get invited user by submitted email address
		User existingUser = userRepo.findByEmail(inviteEmail.getEmail());
		Group group = groupRepo.findOne(groupId);
		
		//verify that session user is a member of group they are inviting a user to
		if (authUser.getGroups().contains(group)) {
			//invite a new user
			if (null == existingUser) {
				InvitedUser invited = new InvitedUser();
				invited.setEmail(inviteEmail.getEmail());
				group.addPendingUserToGroup(invited);
				invitedUserRepo.save(invited);
				groupRepo.save(group);
				invited.inviteToGroup(group, emailer);
			} 
			//invite an existing user if they aren't already in the group or invited to the group
			else if (!existingUser.getGroups().contains(group) && !existingUser.getPendingGroups().contains(group)) {
				group.addPendingUserToGroup(existingUser);
				groupRepo.save(group);
				existingUser.inviteToGroup(group, emailer);
			} 
		}
	}
	
	static class inviteEmail	{
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
