package com.libertymutual.goforcode.communityShed.api;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

@RestController
@RequestMapping("/api/users")

public class UserApiController {
	
	private PasswordEncoder encoder;
	private UserRepo userRepo;

	public UserApiController(PasswordEncoder encoder, UserRepo userRepo) {
		this.encoder = encoder; 
		this.userRepo = userRepo;
		
	}
	
	@PostMapping("")
	public User signUp(@RequestBody User user) {
	user.setPassword(encoder.encode(user.getPassword()));
	userRepo.save(user);
	return user;
}
}
