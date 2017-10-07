package com.libertymutual.goforcode.communityShed.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Session;


@Repository
public interface SessionRepo extends JpaRepository<Session, Long>{

}
