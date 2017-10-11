package com.libertymutual.goforcode.communityShed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Request;

@Repository
public interface RequestRepo extends JpaRepository<Request, Long>{

}
