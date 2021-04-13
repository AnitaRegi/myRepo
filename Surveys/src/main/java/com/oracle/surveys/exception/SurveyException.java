package com.oracle.surveys.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyException extends RuntimeException {

	private static final long serialVersionUID = 8363315749392512290L;

	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatusCode;
	private String field;

	public SurveyException(String errorCode, String errorMessage, HttpStatus httpStatusCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatusCode = httpStatusCode;
	}

}
