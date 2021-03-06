package com.libertymutual.goforcode.communityShed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.User;

@Repository
public interface ConfirmedUserRepo extends JpaRepository<User, Long> {
		
	public ConfirmedUser findByEmail(String email);
	
}
