package com.libertymutual.goforcode.communityShed.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Invite;


@Repository
public interface InviteRepo extends JpaRepository<Invite, Long>{

}
