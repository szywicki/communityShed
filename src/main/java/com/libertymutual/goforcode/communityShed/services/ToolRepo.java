package com.libertymutual.goforcode.communityShed.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Tool;


@Repository
public interface ToolRepo extends JpaRepository<Tool, Long>{

}
