package com.libertymutual.goforcode.communityShed.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepo extends JpaRepository<RequestRepo, Long>{

}
