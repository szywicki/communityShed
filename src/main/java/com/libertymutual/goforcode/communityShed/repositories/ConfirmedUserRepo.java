package com.libertymutual.goforcode.communityShed.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;

@Repository
public interface ConfirmedUserRepo extends JpaRepository<User, Long> {
		
	public User findByEmail(String email);
	
}
