package com.libertymutual.goforcode.communityShed.api;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Request;
import com.libertymutual.goforcode.communityShed.models.SimpleTool;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin(origins = "*")
public class ToolApiController {

	private ToolRepo toolRepo;
	private RequestRepo requestRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	
	public ToolApiController(ToolRepo toolRepo, RequestRepo requestRepo, ConfirmedUserRepo confirmedUserRepo) {
		this.toolRepo = toolRepo;
		this.requestRepo = requestRepo;
		this.confirmedUserRepo = confirmedUserRepo;
		
	}

	@ApiOperation("Get all Tools of all users for all groups that the current user is in")
	@GetMapping("")
	public Collection<Tool> getAllTools(Authentication auth) {
		ConfirmedUser authUser = (ConfirmedUser) auth.getPrincipal();
		final ConfirmedUser realAuthUser = (ConfirmedUser) confirmedUserRepo.findOne(authUser.getId());
		
		return realAuthUser.getGroups()
			.stream()
			.flatMap(group -> group.getUsers().stream())
			.flatMap(user -> user.getTools().stream())
			.filter(tool -> !tool.getOwner().equals(realAuthUser))
			.collect(Collectors.toMap(tool -> tool.getId(), tool -> tool))
			.values();
	}
	

	@ApiOperation("Get all tools that are owned by the current user")
	@GetMapping("mine")
	public List<Tool> getMyTools(Authentication auth) {
		ConfirmedUser authUser = (ConfirmedUser) auth.getPrincipal();
		authUser = (ConfirmedUser) confirmedUserRepo.findOne(authUser.getId());
		return authUser.getTools();
	}

	@ApiOperation("Get one Tool by Id")
	@GetMapping("{id}")
	public Tool getOneTool(@PathVariable long id) {
		return toolRepo.findOne(id);
	}

	@ApiOperation("Create a Tool")
	@PostMapping("")
	public Tool createTool(Authentication auth, @RequestBody SimpleTool simple) {
		Tool tool = new Tool();
		tool.copyFromSimpleTool(simple);
		tool.setOwner((ConfirmedUser) auth.getPrincipal());
		tool.setStatus("Available");
		tool = toolRepo.save(tool);
		return tool;
	}

	@ApiOperation("Update a Tool")
	@PutMapping("{id}")
	public Tool updateTool(@RequestBody SimpleTool simple, @PathVariable long id) {
		Tool tool = toolRepo.findOne(id);
		tool.copyFromSimpleTool(simple);
		return toolRepo.save(tool);
	}

	@ApiOperation("Disable a Tool")
	@PutMapping("{id}/disable")
	public Tool disableTool(@PathVariable long id) {
		// disable tool and deny all pending requests
		Tool tool = toolRepo.findOne(id);
		tool.setStatus("Disabled");
		List<Request> requests = tool.getRequests();
		for (Request request : requests)	{
			if(request.getStatus().equals("Pending")) {
				request.setStatus("Denied");
			}
		}
		requestRepo.save(requests);
		toolRepo.save(tool);
		return tool;
	}
	
	@ApiOperation("Enable a Tool")
	@PutMapping("{id}/enable")
	public Tool enableTool(@PathVariable long id)	{
		// enable tool
		Tool tool = toolRepo.findOne(id);
		tool.setStatus("Available");
		toolRepo.save(tool);
		return tool;
	}

}
