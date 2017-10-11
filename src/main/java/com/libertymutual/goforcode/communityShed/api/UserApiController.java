package com.libertymutual.goforcode.communityShed.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/users")

public class UserApiController {
	
	private PasswordEncoder encoder;
	private UserRepo userRepo;

	public UserApiController(PasswordEncoder encoder, UserRepo userRepo) {
		this.encoder = encoder; 
		this.userRepo = userRepo;
		
	}
	
	@ApiOperation("creates a user")
	@PostMapping("")
	public User signUp(@RequestBody User user) {
	user.setPassword(encoder.encode(user.getPassword()));
	userRepo.save(user);
	return user;
}
	
	@ApiOperation("Gets list of groups that user is a member of.")
	@GetMapping("{userId}/groups")
	public List<Group> getGroups(@PathVariable long userId) {
		User user = userRepo.findOne(userId);
		return user.getGroups();
	}
	
	@ApiOperation("Gets list of tools that user owns.")
	@GetMapping("{userId}/tools/mine")
	public List<Tool> getTools(@PathVariable long userId) {
		User user = userRepo.findOne(userId);
		return user.getTools();
	}
	
	@ApiOperation("Gets list of all tools for all users in all groups which user is a member of")
    @GetMapping("{userId}/tools")
    public List<Tool> getUserGroupTools(@PathVariable long userId) {
        System.out.println("Full List UserId: " + userId);
        List<Tool> tools = new ArrayList<Tool>();
        User user = userRepo.findOne(userId);
        for (Group group : user.getGroups()) {
            System.out.println("Full List UserId group: " + group);
              for (User user1 : group.getUsers()) {
                  System.out.println("Full List UserId group User: " + user1);
                  tools.addAll(user1.getTools());
              }
        }
        return tools;
	}
}
