package com.libertymutual.goforcode.communityShed.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.User;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*")
public class SessionApiController {
	
	private UserDetailsService userDetails;
	private AuthenticationManager authManager;
	
	public SessionApiController(UserDetailsService userDetails, AuthenticationManager authManager)	{
		this.userDetails = userDetails;
		this.authManager = authManager;
	}
	
	@PutMapping("/mine")
	public Boolean login(@RequestBody User user)	{
		UserDetails details = userDetails.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, user.getPassword(), details.getAuthorities());
		
		authManager.authenticate(token);
		
		if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        return token.isAuthenticated();
	}
	
	@DeleteMapping("/mine")
	public Boolean logout(Authentication auth, HttpServletRequest request, HttpServletResponse response)	{
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return true;
	}

}