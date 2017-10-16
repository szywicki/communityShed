package com.libertymutual.goforcode.communityShed.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	public InviteApiController(UserRepo userRepo, GroupRepo groupRepo, ConfirmedUserRepo confirmedUserRepo, InvitedUserRepo invitedUserRepo, MailGunEmailService emailer) {
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		this.invitedUserRepo = invitedUserRepo;
		this.emailer = emailer;
		
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
