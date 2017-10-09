package com.libertymutual.goforcode.communityShed.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Stuff")  // 404

public class StuffNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
}