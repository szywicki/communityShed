package com.libertymutual.goforcode.communityShed.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.InvitedUser;

@Repository
public interface InvitedUserRepo extends JpaRepository<InvitedUser, Long> {

	public InvitedUser findByInvitationKey(UUID invitationKey);
}
