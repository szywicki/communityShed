package com.libertymutual.goforcode.communityShed.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.Request;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class RequestApiController {
	
	private RequestRepo requestRepo;
	private ToolRepo toolRepo;
	
	public RequestApiController(RequestRepo requestRepo, ToolRepo toolRepo)	{
		this.requestRepo = requestRepo;
		this.toolRepo = toolRepo;
	}
	
	@ApiOperation("creates a new request.  Date is expected in yyyy-dd-mm format.")
	@PostMapping("")
	public Request create(Authentication auth, String endDateString, String description, String toolId)	{
		User borrower = (User) auth.getPrincipal();
		Tool tool = toolRepo.findOne(Long.parseLong(toolId));
		//we should consider not storing the status on a tool and just having a method to determine whether the tool is available or not (based on requests)
		tool.setStatus("Requested");
		//setting startdate to current date/time right now.  We should refactor and have it set to the first available time for the requested tool.
		Date loanStartDate = new Date();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date loanEndDate = parser.parse(endDateString);
			Request request = new Request(loanStartDate, loanEndDate, description, "Pending", tool, borrower, tool.getOwner());
			toolRepo.save(tool);
			requestRepo.save(request);
			return request;
		} catch (ParseException e) {
			System.out.println("Cannot parse loan end date");
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}
