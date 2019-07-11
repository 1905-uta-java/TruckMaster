package com.revature.project02.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//somewhat ironically, this only occurs when FORBIDDEN should occur.
@ResponseStatus(HttpStatus.FORBIDDEN) 
public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String msg) {
		super(msg);
	}
}
