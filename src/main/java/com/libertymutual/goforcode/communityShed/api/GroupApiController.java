package com.libertymutual.goforcode.communityShed.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")

public class GroupApiController {
	private GroupRepo groupRepo;
	private UserRepo userRepo;
	private ToolRepo toolRepo;
	
	public GroupApiController (GroupRepo groupRepo) {
		this.groupRepo = groupRepo;
	}
		
	
	@ApiOperation("Get one Group by Id")
	@GetMapping("{id}")
	public Group getOneGroup(@PathVariable long id) {
		return groupRepo.findOne(id); 
	}	

	
	@ApiOperation("Create a Group")
	@PostMapping("")
	public Group createGroup(@RequestBody Group group) {	
	group = groupRepo.save(group);
	return group;
	
	}
		
	
	@ApiOperation("Get list of users in that group")
	@GetMapping("{groupId}/users")
	public List<User> getUsers(@PathVariable long groupId) {
		Group group = groupRepo.findOne(groupId);

		return group.getUsers();
	}
	
	
	@ApiOperation("Get list of tools owned by that group")
	@GetMapping("{groupId}/tools")
	public List<Tool> getTools(@PathVariable long groupId) {
		Group group = groupRepo.findOne(groupId);

		return group.getTools();
	}
	
	@ApiOperation("Add a tool to the group")
	@PostMapping("{groupId}/tools")
	public Group addAnTool(@PathVariable long groupId, @RequestBody Tool tool) {
		Group group = groupRepo.findOne(groupId);
		tool = toolRepo.findOne(tool.getId());
		group.addTool(tool);
		groupRepo.save(group);
		
		return group;
	}

	
}
