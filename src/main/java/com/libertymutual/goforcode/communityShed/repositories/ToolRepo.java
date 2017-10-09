package com.libertymutual.goforcode.communityShed.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.communityShed.models.Tool;


@Repository
public interface ToolRepo extends JpaRepository<Tool, Long>{

	List<Tool> findByBrandEquals(String brand);
	
	List<Tool> findByToolNameContaining(String partialToolName);
	
	List<Tool> findByBrandEqualsAndToolNameContaining(String brand, String partialToolName);
	
}
