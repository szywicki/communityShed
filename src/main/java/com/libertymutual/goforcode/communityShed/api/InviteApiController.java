package com.libertymutual.goforcode.communityShed.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/invites")
@CrossOrigin(origins = "*")

public class InviteApiController {
	
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	
	public InviteApiController(UserRepo userRepo, GroupRepo groupRepo) {
		this.userRepo = userRepo;
		this.groupRepo = groupRepo;
	}
	
	@ApiOperation("Generate invite for a group")
	@PostMapping("group/{groupId}")
	public void	inviteUser(@RequestBody inviteEmail inviteEmail, @PathVariable long groupId)	{
		User existingUser = userRepo.findByEmail(inviteEmail.getEmail());
		Group group = groupRepo.findOne(groupId);
		if(null != existingUser && !existingUser.getGroups().contains(group) && !existingUser.getPendingGroups().contains(group))	{
			group.addPendingUserToGroup(existingUser);
			groupRepo.save(group);
			existingUser.inviteToGroup(group);
		} else	{
			//do stuff for non-user
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
