package com.libertymutual.goforcode.communityShed.api;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
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
	
	@ApiOperation("creates a new request.  Date is expected in yyyy-mm-dd format.")
	@PostMapping("/tool/{toolId}")
	public Request create(Authentication auth, @RequestBody Request request, @PathVariable long toolId)	{
		ConfirmedUser borrower = (ConfirmedUser) auth.getPrincipal();
		Tool tool = toolRepo.findOne(toolId);
		//we should consider not storing the status on a tool and just having a method to determine whether the tool is available or not (based on requests)
		tool.setStatus("Requested");
		//setting start date to current date/time right now.  We should refactor and have it set to the first available time for the requested tool.
		Date loanStartDate = new Date();
//		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		request.setLoanStartDate(loanStartDate);
		request.setStatus("Pending");
		request.setTool(tool);
		request.setBorrower(borrower);
		request.setLoaner(tool.getOwner());
		System.out.println(request.toString());
		toolRepo.save(tool);
		requestRepo.save(request);
		return request;
	}
	
	@ApiOperation("approve a request")
	@PutMapping("{id}/approve")
	public void approve(@PathVariable Long id)	{
		Request request = requestRepo.findOne(id);
		Tool tool = request.getTool();
		request.setStatus("Approved");
		tool.setStatus("On Loan");
		requestRepo.save(request);
		toolRepo.save(tool);
	}
	
	@ApiOperation("deny a request")
	@PutMapping("{id}/deny")
	public void deny(@PathVariable Long id)	{
		Request request = requestRepo.findOne(id);
		Tool tool = request.getTool();
		request.setStatus("Denied");
		tool.setStatus("Available");
		requestRepo.save(request);
		toolRepo.save(tool);
	}

}
