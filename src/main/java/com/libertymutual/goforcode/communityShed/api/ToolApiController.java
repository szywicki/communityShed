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
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;

@RestController
@RequestMapping("/tools")
@CrossOrigin(origins = "*")

public class ToolApiController {
	private ToolRepo toolRepo;
	
	public ToolApiController (ToolRepo toolRepo) {
		this.toolRepo = toolRepo;
		
	}
	
	// Get all Tools (of all users in all groups)
	@GetMapping("")
	public List<Tool> getAllTools(String brand, String toolName) {

			List<Tool> returnList;
			if (toolName != null) {
				returnList = toolRepo.findByToolNameContainingAllIgnoreCase(toolName);
			}
			else {
				returnList = toolRepo.findAll();
			}
			return returnList;	
		
	}
	
	// Get one Tool by Id
	@GetMapping("{id}")
	public Tool getOneTool(@PathVariable long id) {
		return toolRepo.findOne(id); 
	
	}	
	
	// Create a Tool
	@PostMapping("")
	public Tool createTool(@RequestBody Tool tool) {

	tool = toolRepo.save(tool);

	return tool;
	}
		
	// Update a Tool
	@PutMapping("{id}")
	public Tool updateTool(@RequestBody Tool tool, @PathVariable long id) {
		System.out.println("Update id:" + id);
		tool.setId(id);
		return toolRepo.save(tool); 
		
	}
	
	// Delete a Tool
	@DeleteMapping("{id}")
	public Tool deleteTool(@PathVariable long id) {
		System.out.println("Deleted id:" + id);
		Tool tool = toolRepo.findOne(id);
//		userRepo.delete(tool.getUser());
//		groupRepo.delete(tool.getGroup());
		toolRepo.delete(id);
		return tool;
		
	}
	
	// Get tools of all Users of a Group
	
	// Get all Users of all Groups Tools
	
	// Get Tool Detail
		


	
}
