package com.libertymutual.goforcode.communityShed.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.dtos.GroupDto;
import com.libertymutual.goforcode.communityShed.dtos.PendingGroupDto;
import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/groups")


public class GroupApiController {
	
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	
	public GroupApiController (GroupRepo groupRepo, ConfirmedUserRepo confirmedUserRepo) {
		this.groupRepo = groupRepo;
		this.confirmedUserRepo = confirmedUserRepo;
	}
		
	
	@ApiOperation("Get one Group by Id")
	@GetMapping("{groupId}")
	public Group getOneGroup(@PathVariable long groupId) {
		return groupRepo.findOne(groupId); 
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
	public Set<User> getUsers(@PathVariable long groupId) {
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
	public Set<GroupDto> acceptInvite(@PathVariable long groupId, Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		Group group = groupRepo.findOne(groupId);
		group.removePendingUserFromGroup(user);
		group.addUserToGroup(user);
		groupRepo.save(group);
		user.addGroup(group);
		return user.getGroups()
				.stream()
				.map(g -> new GroupDto(g))
				.collect(Collectors.toSet());
	}
	
	@ApiOperation("remove the user from the pending relationship table.")
	@PutMapping("{groupId}/user/deny")
	public Set<GroupDto> denyInvite(@PathVariable long groupId, Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		Group group = groupRepo.findOne(groupId);
		group.removePendingUserFromGroup(user);
		groupRepo.save(group);
		user = (ConfirmedUser) confirmedUserRepo.findOne(user.getId());
		return user.getGroups()
				.stream()
				.map(g -> new GroupDto(g))
				.collect(Collectors.toSet());
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
	public Set<GroupDto> getGroups(Authentication auth) {
		ConfirmedUser user = (ConfirmedUser) auth.getPrincipal();
		user = (ConfirmedUser) confirmedUserRepo.findOne(user.getId());
		return user.getGroups()
				.stream()
				.map(group -> new GroupDto(group))
				.collect(Collectors.toSet());
	}
	
}
