package com.libertymutual.goforcode.communityShed.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/invites")
@CrossOrigin(origins = "*")

public class InviteApiController {
	
	private UserRepo userRepo;
	
	public InviteApiController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@ApiOperation("Generate invite for a group")
	@PostMapping("group/{groupId}")
	public User	inviteUser(@RequestBody User user, @PathVariable long groupId)	{
		User existingUser = userRepo.findByEmail(user.getEmail());
		if(null != existingUser)	{
			
		}
		
	}

}
