package com.oracle.surveys.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
	
	private String errorCode;
	private String errorMessage;
	private int status;
	
	@JsonInclude(Include. NON_NULL)
	private String field;
}
