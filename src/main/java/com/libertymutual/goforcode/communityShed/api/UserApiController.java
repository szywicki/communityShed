package com.libertymutual.goforcode.communityShed.api;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/users")

public class UserApiController {

	private ShedUserDetailsService userDetails;
	private PasswordEncoder encoder;
	private UserRepo userRepo;
	private ConfirmedUserRepo confirmedUserRepo;

	public UserApiController(PasswordEncoder encoder, UserRepo userRepo, ConfirmedUserRepo confirmedUserRepo, ShedUserDetailsService userDetails) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.userDetails = userDetails;
		this.confirmedUserRepo = confirmedUserRepo;

	}

	@ApiOperation("creates a user")
	@PostMapping("")
	public User signUp(@RequestBody User user) {
		User existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser != null) {
			return null;
		}
		user.setPassword(encoder.encode(user.getPassword()));
		user = userRepo.save(user);
		UserDetails details = userDetails.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, user.getPassword(), details.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		return user;
	}
}
