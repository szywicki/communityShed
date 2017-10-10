package com.libertymutual.goforcode.communityShed.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

@Service
public class ShedUserDetailsService implements UserDetailsService {
	
	private UserRepo users;
	
	public ShedUserDetailsService(UserRepo users) {
		this.users = users;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = users.findByEmail(email);
		if (user == null)	{
			throw new UsernameNotFoundException("No user found with that email address");
		}
		return user;
	}

}
