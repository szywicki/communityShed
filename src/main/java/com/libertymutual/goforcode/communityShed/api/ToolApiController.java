package com.libertymutual.goforcode.communityShed.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.services.ToolRepo;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "*")

public class ToolApiController {
	private ToolRepo toolRepo;
	
	public ToolApiController (ToolRepo toolRepo) {
		this.toolRepo = toolRepo;
		
	}
	
	// Get all Tools
	@GetMapping("")
	public List<Tool> getAllTools() {
		return toolRepo.findAll();
	
	}
	
	// Get one Tool by Id
	@GetMapping("{id}")
	public Tool getOneTool(@PathVariable long id) {
		return toolRepo.findOne(id); 
	
	}	
	
	// Create a Tool
	@PostMapping("")
	public Tool create(@RequestBody Tool tool) {

	tool = toolRepo.save(tool);

	return tool;
}
		
	// Update a Tool
	@PutMapping("{id}")
	public Tool UpdateTool(@RequestBody Tool tool, @PathVariable long id) {
		tool.setId(id);
		return toolRepo.save(tool); 
		
	}
	
	// Delete a Tool
	@DeleteMapping("{id}")
	public Tool deleteTool(@PathVariable long id) {
		Tool tool = toolRepo.findOne(id);
//		userRepo.delete(tool.getUser());
//		groupRepo.delete(tool.getGroup());
		toolRepo.delete(id);
		return tool;
		
	}
	
	// Get all Users of a Group tools
	
	// Get all Users of all Groups Tools
	
	// Get Tool Detail
		


	
}
