package com.oracle.users.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 8363315749392512290L;

	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatusCode;

	public UserServiceException(String errorCode, String errorMessage, HttpStatus httpStatusCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatusCode = httpStatusCode;
	}

}
