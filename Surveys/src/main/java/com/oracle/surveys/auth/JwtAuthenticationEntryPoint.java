package com.oracle.surveys.auth;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 6686235861930686930L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		log.debug("Inside JwtAuthenticationEntryPoint.commence() {}...", authException);

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}
