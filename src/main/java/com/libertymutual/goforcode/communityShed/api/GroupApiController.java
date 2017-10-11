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
		
//	@GetMapping("")
//	public List<Group> getAll() {
//		return groupRepo.findAll();
//	}
	
	
	// Get one Group by Id
	@GetMapping("{id}")
	public Group getOneGroup(@PathVariable long id) {
		return groupRepo.findOne(id); 
	}	

	
	// Create a Group
	@PostMapping("")
	public Group createGroup(@RequestBody Group group) {	
	group = groupRepo.save(group);
	return group;
	
	}
		
	
	// Get all Users Of this Group


	@PostMapping("{groupId}/users")
	public Group addAnUser(@PathVariable long groupId, @RequestBody User user) {
		Group group = groupRepo.findOne(groupId);
		user = userRepo.findOne(user.getId());

		group.addUser(user);
		groupRepo.save(group);

		return group;
	}
	
	@PostMapping("{groupId}/tools")
	public Group addAnTool(@PathVariable long groupId, @RequestBody Tool tool) {
		Group group = groupRepo.findOne(groupId);
		tool = toolRepo.findOne(tool.getId());

		group.addTool(tool);
		groupRepo.save(group);

		return group;
	}

	
}
