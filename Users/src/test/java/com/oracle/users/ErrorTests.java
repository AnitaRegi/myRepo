package com.oracle.users;

import org.junit.Assert;
import org.junit.Test;

public class ErrorTests {

	
	@Test
	public void testErrorModel() {
		com.oracle.users.exception.Error error = new com.oracle.users.exception.Error();
		error.setErrorCode("errorode");
		error.setErrorMessage("errormessage");
		error.setStatus(404);
		
		Assert.assertNotNull(error.getErrorCode());
		Assert.assertNotNull(error.getErrorMessage());
		Assert.assertNotNull(error.getStatus());
	}
}
