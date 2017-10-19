package com.libertymutual.goforcode.communityShed.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.dtos.PendingGroupDto;
import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/groups")


public class GroupApiController {
	
	private GroupRepo groupRepo;
	private UserRepo userRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private InvitedUserRepo invitedUserRepo;
	
	public GroupApiController (GroupRepo groupRepo, UserRepo userRepo, ConfirmedUserRepo confirmedUserRepo, InvitedUserRepo invitedUserRepo) {
		this.groupRepo = groupRepo;
		this.userRepo = userRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		this.invitedUserRepo = invitedUserRepo;
	}
		
	
	@ApiOperation("Get one Group by Id")
	@GetMapping("{groupId}")
	public Group getOneGroup(@PathVariable long id) {
		return groupRepo.findOne(id); 
	}	

	
	@ApiOperation("Create a Group")
	@PostMapping("")
	public Group createGroup(@RequestBody Group group, Authentication auth ) {
		User currentUser = (User) auth.getPrincipal();
		group.addUserToGroup(currentUser);	
		group = groupRepo.save(group);
		return group;
	}
		
	
	@ApiOperation("Get list of users in that group")
	@GetMapping("{groupId}/users")
	public List<User> getUsers(@PathVariable long groupId) {
		Group group = groupRepo.findOne(groupId);
		return group.getUsers();
	}
	
	// Methods added for invitation handling. 
	@ApiOperation("Gets list of pendingGroup invites for the logged in user.")
	@GetMapping("pendingInvites")
	public List<PendingGroupDto> getPendingGroups(Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		user = (ConfirmedUser) confirmedUserRepo.findOne(user.getId());
		return user.getPendingGroups()
				.stream()
				.map(group -> new PendingGroupDto(group))
				.collect(Collectors.toList());
	}
	
	@ApiOperation("Remove the user from the pending invite table into the group member table.")
	@PutMapping("{groupId}/user/accept")
	public List<Group> acceptInvite(@PathVariable long groupId, Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		Group group = groupRepo.findOne(groupId);
		group.removePendingUserFromGroup(user);
		group.addUserToGroup(user);
		groupRepo.save(group);
		user.addGroup(group);
		return user.getGroups();
	}
	
	@ApiOperation("remove the user from the pending relationship table.")
	@PutMapping("{groupId}/user/deny")
	public List<Group> denyInvite(@PathVariable long groupId, Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		Group group = groupRepo.findOne(groupId);
		group.removePendingUserFromGroup(user);
		groupRepo.save(group);
		user = (ConfirmedUser) confirmedUserRepo.findOne(user.getId());
		return user.getGroups();
	}
	
	
	@ApiOperation("Get list of tools owned by that group")
	@GetMapping("{groupId}/tools")
	public List<Tool> getTools(@PathVariable long groupId) {
		Group group = groupRepo.findOne(groupId);
		List<Tool> tools = new ArrayList<Tool>();
        for (User user : group.getUsers()) {
            tools.addAll(user.getTools());
        }
        return tools;
	}
	
	
	@ApiOperation("Gets list of groups that user is a member of.")
	@GetMapping("")
	public List<Group> getGroups(Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		user = (ConfirmedUser) confirmedUserRepo.findOne(user.getId());
		return user.getGroups();
	}
	
}
