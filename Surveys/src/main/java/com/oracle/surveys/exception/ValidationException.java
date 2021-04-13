package com.oracle.surveys.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8363315749392512290L;

	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatusCode;
	private final String field;

	public ValidationException(String errorCode, String errorMessage, String field, HttpStatus httpStatusCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatusCode = httpStatusCode;
		this.field = field;
	}

}
