package com.gokul.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
 
public class DenailOfService implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		HttpResponse res=new HttpResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Unauthorised", "You do not have permission to access this page");
			response.setContentType("APPLICATION_JSON_VALUE");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			new ObjectMapper().writeValue(response.getOutputStream(), res);
 		
	}

} 
