package com.libertymutual.goforcode.communityShed.services;

import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;

@Service
public class ConfirmedUserService {
	
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private InvitedUserRepo invitedUserRepo;
	
	public ConfirmedUserService(GroupRepo groupRepo, ConfirmedUserRepo confirmedUserRepo, InvitedUserRepo invitedUserRepo)	{
		this.groupRepo = groupRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		this.invitedUserRepo = invitedUserRepo;
	}
	
	public ConfirmedUser convertInvitedUserToConfirmedUser(ConfirmedUser user, InvitedUser invited)	{
		if (user.getEmail().equals(invited.getEmail()))	{
			//remove invited from groups and save -- cascade on user delete would be better but can't get it working
			for (Group group : invited.getPendingGroups())	{
				group.removePendingUserFromGroup(invited);
				groupRepo.save(group);
			}
			//delete invited now that it no longer is tied to any pendingGroups
			invitedUserRepo.delete(invited);
			
			//save user now that the email address is available
			user = confirmedUserRepo.save(user);
			//add user to groups and save
			for (Group group : invited.getPendingGroups())	{
				group.addUserToGroup(user);
				groupRepo.save(group);
			}
			
			return user;
		}
		return null;
	}

}
