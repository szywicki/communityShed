package com.libertymutual.goforcode.communityShed.api;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
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
import com.libertymutual.goforcode.communityShed.services.ConfirmedUserService;
import com.libertymutual.goforcode.communityShed.services.InvitationService;
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
	private PasswordEncoder encoder;
	private ShedUserDetailsService userDetails;
	private ConfirmedUserService cus;
	private InvitationService inviteService;
	
	public InviteApiController(UserRepo userRepo, GroupRepo groupRepo, ConfirmedUserRepo confirmedUserRepo, InvitedUserRepo invitedUserRepo, PasswordEncoder encoder, ShedUserDetailsService userDetails, ConfirmedUserService cus, InvitationService inviteService) {
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		this.invitedUserRepo = invitedUserRepo;
		this.encoder = encoder;
		this.userDetails = userDetails;
		this.cus = cus;
		this.inviteService = inviteService;
	}
	
	@ApiOperation("Return user details for an invite")
	@GetMapping("{inviteKey}")
	public InvitedUser getUserFromInvite(@PathVariable UUID inviteKey)	{
		return invitedUserRepo.findByInvitationKey(inviteKey);
	}
	
	@ApiOperation("Convert InvitedUser into a ConfirmedUser")
	@PostMapping("{inviteKey}")
	public ConfirmedUser convertInvitedUserAndLogin(@RequestBody ConfirmedUser user, @PathVariable UUID inviteKey, HttpServletResponse response)	{
		InvitedUser invited = invitedUserRepo.findByInvitationKey(inviteKey);
		user.setPassword(encoder.encode(user.getPassword()));
		user = cus.convertInvitedUserToConfirmedUser(user, invited);
		if (null != user)	{
			//Authenticate user
			UserDetails details = userDetails.loadUserByUsername(user.getEmail());
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, user.getPassword(), details.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(token);
			response.setStatus(200);
			return user;
		}
		response.setStatus(403);
		return null;
	}
	
	@ApiOperation("Generate invite to a group")
	@PostMapping("group/{groupId}")
	public Boolean	inviteUser(Authentication auth, @RequestBody inviteEmail inviteEmail, @PathVariable long groupId, HttpServletResponse response)	{
		//get session user
		ConfirmedUser authUser = (ConfirmedUser) auth.getPrincipal();
		authUser = (ConfirmedUser) confirmedUserRepo.findOne(authUser.getId());
		//get invited user by submitted email address
		User existingUser = userRepo.findByEmail(inviteEmail.getEmail());
		Group group = groupRepo.findOne(groupId);
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, inviteEmail.getEmail());
		if(invitationSent == false)	{
			response.setStatus(403);
			return false;
		}
		return true;
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
