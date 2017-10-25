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
import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;
import com.libertymutual.goforcode.communityShed.services.NotificationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class RequestApiController {

	private RequestRepo requestRepo;
	private ToolRepo toolRepo;
	private MailGunEmailService emailer;
	private NotificationService notifier;

	public RequestApiController(RequestRepo requestRepo, ToolRepo toolRepo, NotificationService notifier) {
		this.requestRepo = requestRepo;
		this.toolRepo = toolRepo;
		this.notifier = notifier;

	}

	@ApiOperation("creates a new request.  Date is expected in yyyy-mm-dd format.")
	@PostMapping("/tool/{toolId}")
	public Request create(Authentication auth, @RequestBody Request request, @PathVariable long toolId) {
		ConfirmedUser borrower = (ConfirmedUser) auth.getPrincipal();
		Tool tool = toolRepo.findOne(toolId);
		// to determine whether the tool is available or not (based on requests)
		tool.setStatus("Requested");
		// setting start date to current date/time.
		Date loanStartDate = new Date();
		// SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		request.setLoanStartDate(loanStartDate);
		request.setStatus("Pending");
		request.setTool(tool);
		request.setBorrower(borrower);
		request.setLoaner(tool.getOwner());
		toolRepo.save(tool);
		requestRepo.save(request);
		
		// Send Notification request to Tool Loaner
		String emailAddress = tool.getOwner().getEmail();
		String borrowerName = borrower.getFirstName() + " " + borrower.getLastName();
		String toolName = tool.getToolName();
		notifier.notifyToolOwner(emailAddress, toolName, borrowerName);
		return request;
	}

	@ApiOperation("approve a request")
	@PutMapping("{id}/approve")
	public Request approve(@PathVariable Long id) {
		Request request = requestRepo.findOne(id);
		Tool tool = request.getTool();
		request.setStatus("Approved");
		tool.setStatus("On Loan");
		requestRepo.save(request);
		toolRepo.save(tool);
		
		// Send Notification acceptance to Tool Borrower
		String emailAddress = request.getBorrower().getEmail();
		String toolName = tool.getToolName();
		String ownerName = tool.getOwner().getFirstName() + " " + tool.getOwner().getLastName();
		notifier.notifyToolBorrower(emailAddress, toolName, ownerName, request.getStatus());
		return request;
	}

	@ApiOperation("deny a request")
	@PutMapping("{id}/deny")
	public Request deny(@PathVariable Long id) {
		Request request = requestRepo.findOne(id);
		Tool tool = request.getTool();
		request.setStatus("Denied");
		tool.setStatus("Available");
		requestRepo.save(request);
		toolRepo.save(tool);
		
		// Send Notification denial to Tool Borrower
		String emailAddress = request.getBorrower().getEmail();
		String toolName = tool.getToolName();
		String ownerName = tool.getOwner().getFirstName() + " " + tool.getOwner().getLastName();
		notifier.notifyToolBorrower(emailAddress, toolName, ownerName, request.getStatus());
		return request;
	}

}
