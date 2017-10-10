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
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;


@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "*")

public class GroupApiController {
	private GroupRepo groupRepo;
	
	public GroupApiController (GroupRepo groupRepo) {
		this.groupRepo = groupRepo;
	}
		
	// Get all Groups
	@GetMapping("")
	public List<Group> getAllGroups(String groupName, String groupDescription) {

		List<Group> returnList = groupRepo.findAll();
		return returnList;	
		
	}
	
	
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
		
	// Update a Group
	@PutMapping("{id}")
	public Group updateGroup(@RequestBody Group group, @PathVariable long id) {
		System.out.println("Update id:" + id);
		group.setId(id);
		return groupRepo.save(group); 
		
	}
	
	// Delete a Group
	@DeleteMapping("{id}")
	public Group deleteGroup(@PathVariable long id) {
		System.out.println("Deleted id:" + id);
		Group group = groupRepo.findOne(id);
//		userRepo.delete(tool.getUser());
//		groupRepo.delete(tool.getGroup());
		groupRepo.delete(id);
		return group;
		
	}
	
	// Get all Users of a Group tools
	
}
