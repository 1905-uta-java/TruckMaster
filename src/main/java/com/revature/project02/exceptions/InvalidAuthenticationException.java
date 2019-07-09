package com.revature.project02.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAuthenticationException(String message) {
		super(message);
	}
	
}
