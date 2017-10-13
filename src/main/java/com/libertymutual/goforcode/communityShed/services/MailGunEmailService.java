package com.libertymutual.goforcode.communityShed.services;

import org.springframework.beans.factory.annotation.Value;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailGunEmailService {

	@Value("${MAIL_GUN_API_KEY}")
	private static String key;
	
	public static JsonNode sendSimpleMessage() throws UnirestException {

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "communityshed.online.com" + "/messages")
                        .basicAuth("api", key)
                    .queryString("from", "Excited User <USER@YOURDOMAIN.COM>")
                    .queryString("to", "brandvig@gmail.com")
                    .queryString("subject", "hello")
                    .queryString("text", "testing")
                    .asJson();

           return request.getBody();
    }
}
