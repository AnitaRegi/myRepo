package com.oracle.surveys.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
	
	private String errorCode;
	private String errorMessage;
	private int status;
	private String field;
}
