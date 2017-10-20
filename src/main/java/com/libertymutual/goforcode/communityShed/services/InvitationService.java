package com.libertymutual.goforcode.communityShed.services;

import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;

@Service
public class InvitationService {
	
	private GroupRepo groupRepo;
	private InvitedUserRepo invitedUserRepo;
	private MailGunEmailService emailer;
	
	public InvitationService(GroupRepo groupRepo, InvitedUserRepo invitedUserRepo, MailGunEmailService emailer) {
		this.groupRepo = groupRepo;
		this.invitedUserRepo = invitedUserRepo;
		this.emailer = emailer;
	}
	
	public Boolean sendInvitation(ConfirmedUser authUser, User existingUser, Group group, String email)	{
		//verify that session user is a member of group they are inviting a user to
		if (authUser.getGroups().contains(group)) {
			//invite a new user
			if (null == existingUser) {
				InvitedUser invited = new InvitedUser();
				invited.setEmail(email);
				group.addPendingUserToGroup(invited);
				invitedUserRepo.save(invited);
				groupRepo.save(group);
				invited.inviteToGroup(group, emailer);
				return true;
			} 
			//invite an existing user if they aren't already in the group or invited to the group
			else if (!existingUser.getGroups().contains(group) && !existingUser.getPendingGroups().contains(group)) {
				group.addPendingUserToGroup(existingUser);
				groupRepo.save(group);
				existingUser.inviteToGroup(group, emailer);
				return true;
			}
		}
		return false;
	}

}
