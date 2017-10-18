package com.libertymutual.goforcode.communityShed.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")


public class SessionApiController {
	
	private ConfirmedUserRepo confirmedUserRepo;
	
	private ShedUserDetailsService userDetails;
	private AuthenticationManager authManager;
		
	public SessionApiController(ShedUserDetailsService userDetails, AuthenticationManager authManager, ConfirmedUserRepo confirmedUserRepo)	{
		this.userDetails = userDetails;
		this.authManager = authManager;
		this.confirmedUserRepo = confirmedUserRepo;
		
	}
	
	@PutMapping("/mine")
	public User login(@RequestBody Credentials credentials)	{
			UserDetails details = userDetails.loadUserByUsername(credentials.getEmail());
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, credentials.getPassword(), details.getAuthorities());
			authManager.authenticate(token);
			if (token.isAuthenticated()) {
	            SecurityContextHolder.getContext().setAuthentication(token);
	    		return confirmedUserRepo.findByEmail(credentials.getEmail());
	        }
			return null;
	}
		
	
	@DeleteMapping("/mine")
	public Boolean logout(Authentication auth, HttpServletRequest request, HttpServletResponse response)	{
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return true;
	}
	
	static class Credentials {
		private String email;
		private String password;
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

}