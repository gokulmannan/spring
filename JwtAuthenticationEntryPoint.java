package com.gokul.error;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		 HttpResponse res= new HttpResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "You need to log in to access this page","forbidden");
		 response.setContentType("APPLICATION_JSON_VALUE");
		 response.setStatus(HttpStatus.FORBIDDEN.value());
		 new ObjectMapper().writeValue(response.getOutputStream(), res);
	}
	

}
