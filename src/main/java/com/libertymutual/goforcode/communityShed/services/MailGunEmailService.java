package com.libertymutual.goforcode.communityShed.services;

import org.springframework.beans.factory.annotation.Value;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailGunEmailService {

	@Value("${MAIL_GUN_API_KEY}")
	private static String key;

	public static void sendSimpleMessage(String recipient, String subject, String html) throws UnirestException {

		HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox94dc8b9546d34223b40efef2175b22bf.mailgun.org"	+ "/messages")
				.basicAuth("api", key)
				.queryString("from", "CommunityShed Admin <admin@communityshed.online>")
				.queryString("to", "brandvig@gmail.com")
				.queryString("subject", subject)
				.queryString("html", html)
				.asString();
		System.out.println(request.getStatus());
//		return request.getBody();
	}
}
