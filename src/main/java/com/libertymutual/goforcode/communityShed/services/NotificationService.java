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
			subject = "Notification of a request message for tool owner";
			html = 	"<html>You've received a request from " + borrowerName + " to borrow your: " + toolName + "</html>";

			try {

				System.out.println("notification sending email to owner email-subject-html: " + emailAddress + " " + subject + " " + html);
				emailer.sendSimpleMessage(emailAddress, subject, html);
			} catch (UnirestException e) {
				System.out.println("notification email failed");
				e.printStackTrace();
			}
		}
		
		public void notifyToolBorrower(String emailAddress, String toolName, String ownerName, String status)	{
			subject = "Notification of acceptance/denial message for tool borrower";
			html = 	"<html>Your request to borrow the tool: " + toolName + " was " + status + " by " + ownerName + "</html>";

			try {
				emailer.sendSimpleMessage(emailAddress, subject, html);
				System.out.println("notification sending email to owner email-subject-html: " + emailAddress + " " + subject + " " + html);
			} catch (UnirestException e) {
				System.out.println("notification email failed");
				e.printStackTrace();
			}
		}

}
