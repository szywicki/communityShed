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
		
	
	// Get all Groups of a User
	
}
