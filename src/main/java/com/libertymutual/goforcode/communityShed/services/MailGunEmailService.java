package com.libertymutual.goforcode.communityShed.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class MailGunEmailService {

	@Value("${MAIL_GUN_API_KEY}")
	private String key;

	public void sendSimpleMessage(String recipient, String subject, String html) throws UnirestException {
		HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox94dc8b9546d34223b40efef2175b22bf.mailgun.org"	+ "/messages")
				.basicAuth("api", key)
				.queryString("from", "CommunityShed Admin <admin@communityshed.online>")
//				.queryString("to", "brandvig@gmail.com")
//				.queryString("to", "erinleigh2000@gmail.com")
				.queryString("to", "jeans771@hotmail.com")
				.queryString("subject", subject)
				.queryString("html", html)
				.asString();
		System.out.println(request.getStatus());
	}
}
