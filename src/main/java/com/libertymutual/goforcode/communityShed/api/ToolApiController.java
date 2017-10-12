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

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "*")

public class ToolApiController {
	private ToolRepo toolRepo;

	public ToolApiController(ToolRepo toolRepo) {
		this.toolRepo = toolRepo;

	}

	@ApiOperation("Get all Tools of all users in all groups")
	@GetMapping("")
	public List<Tool> getAllTools(String brand, String toolName) {

		List<Tool> returnList;
		if (toolName != null) {
			returnList = toolRepo.findByToolNameContainingAllIgnoreCase(toolName);
		} else {
			returnList = toolRepo.findAll();
		}
		return returnList;

	}

	@ApiOperation("Get one Tool by Id")
	@GetMapping("{id}")
	public Tool getOneTool(@PathVariable long id) {

		return toolRepo.findOne(id);
	}

	@ApiOperation("Create a Tool")
	@PostMapping("")
	public Tool createTool(@RequestBody Tool tool) {

		tool = toolRepo.save(tool);

		return tool;
	}

	@ApiOperation("Update a Tool")
	@PutMapping("{id}")
	public Tool updateTool(@RequestBody Tool tool, @PathVariable long id) {

		tool.setId(id);

		return toolRepo.save(tool);

	}

	@ApiOperation("Delete a Tool")
	@DeleteMapping("{id}")
	public Tool deleteTool(@PathVariable long id) {
		System.out.println("Deleted id:" + id);
		Tool tool = toolRepo.findOne(id);
		toolRepo.delete(id);
		return tool;

	}

}
