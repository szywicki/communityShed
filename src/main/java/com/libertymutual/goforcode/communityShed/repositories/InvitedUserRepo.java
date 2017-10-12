package com.libertymutual.goforcode.communityShed.repositories;

import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.InvitedUser;

@Repository
public interface InvitedUserRepo {

	public InvitedUser findByUUID(String invitationKey);
}
