package com.gokul.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokul.entity.Student;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtToken extends UsernamePasswordAuthenticationFilter {
	
AuthenticationManager authentication;
	public JwtToken(AuthenticationManager authentication) {
	super();
	this.authentication = authentication;
}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Student s = null;
		try {
			 s=new ObjectMapper().readValue(request.getInputStream(), Student.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authentication.authenticate(new UsernamePasswordAuthenticationToken(s.getName(),s.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String text="seceretseceretseceretseceretseceretseceretseceret";
		SecretKey key=Keys.hmacShaKeyFor(text.getBytes(StandardCharsets.UTF_8));
		 String token=Jwts.builder().setSubject(auth.getName()).claim("authorities", auth.getAuthorities())
				 .setIssuedAt(new Date()).setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(2))).signWith(key).compact();
 		 String au=auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		 response.setHeader("Authorization","Bearer "+token);
		 	response.setHeader("name",auth.getName());
		 	response.setHeader("role",au );
		 
	}
	

}
