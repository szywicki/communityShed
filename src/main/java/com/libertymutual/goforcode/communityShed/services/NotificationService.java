package com.libertymutual.goforcode.communityShed.services;

import org.springframework.stereotype.Service;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.mashape.unirest.http.exceptions.UnirestException;

	@Service
	public class NotificationService {
		
		private MailGunEmailService emailer;
		String subject = "";
		String html = "";
		
		public NotificationService(GroupRepo groupRepo, InvitedUserRepo invitedUserRepo, MailGunEmailService emailer) {
			this.emailer = emailer;
		}
		
		public void notifyToolOwner(String emailAddress, String toolName, String borrowerName)	{
			subject = borrowerName + " has requested to borrow your tool: " + toolName;
			html = 	"<html>"
					+ "<h3>You've received a request from " + borrowerName + " to borrow your tool: " + toolName + "</h3>"
					+ "<br>Please <a href=\"https://community-shed.herokuapp.com\">login</a> and visit My Shed to view the pending request."
					+ "</html>";

			try {

				System.out.println("notification sending email to owner email-subject-html: " + emailAddress + " " + subject + " " + html);
				emailer.sendSimpleMessage(emailAddress, subject, html);
			} catch (UnirestException e) {
				System.out.println("notification email failed");
				e.printStackTrace();
			}
		}
		
		public void notifyToolBorrower(String emailAddress, String toolName, String ownerName, String status)	{
			subject = "Your request to borrow " + toolName + " from " + ownerName + " has been " + status;
			html = 	"<html>"
					+ "<h3>Your request to borrow the tool: " + toolName + " was " + status + " by " + ownerName + "</h3>";
					
			if (status.equals("Approved"))	{
				html = html + "<br>Go pick up your tool!</html>";
			} else	{
				html = html + "<br>Sorry!</html>";
			}
					

			try {
				emailer.sendSimpleMessage(emailAddress, subject, html);
				System.out.println("notification sending email to owner email-subject-html: " + emailAddress + " " + subject + " " + html);
			} catch (UnirestException e) {
				System.out.println("notification email failed");
				e.printStackTrace();
			}
		}

}
