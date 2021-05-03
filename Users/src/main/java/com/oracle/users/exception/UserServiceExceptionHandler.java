package com.oracle.users.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler {

	@ExceptionHandler(value = UserServiceException.class)
	public ResponseEntity<Object> exception(UserServiceException exc){
		Error error = new Error();
		error.setErrorCode(exc.getErrorCode());
		error.setErrorMessage(exc.getErrorMessage());
		error.setStatus(exc.getHttpStatusCode().value());
		
		return new ResponseEntity<>(error,exc.getHttpStatusCode());
	}
}
