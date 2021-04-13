package com.oracle.surveys.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = SurveyException.class)
	public ResponseEntity<Object> exception(SurveyException exc){
		
		Error error = new Error();
		error.setErrorCode(exc.getErrorCode());
		error.setErrorMessage(exc.getErrorMessage());
		error.setStatus(exc.getHttpStatusCode().value());		
		return new ResponseEntity<>(error, exc.getHttpStatusCode());
	}
	
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Object> exception(ValidationException exc){
		
		Error error = new Error();
		error.setErrorCode(exc.getErrorCode());
		error.setErrorMessage(exc.getErrorMessage());
		error.setStatus(exc.getHttpStatusCode().value());
		error.setField(exc.getField());
		return new ResponseEntity<>(error, exc.getHttpStatusCode());
	}
	

}
