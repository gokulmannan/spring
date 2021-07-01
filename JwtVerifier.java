package com.gokul.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtVerifier extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token=request.getHeader("Authorization");
		
		if(token ==null ||!token.startsWith("Bearer ") )
		{
			filterChain.doFilter(request, response);
		}
		else {
			String text="seceretseceretseceretseceretseceretseceretseceret";
			SecretKey key=Keys.hmacShaKeyFor(text.getBytes(StandardCharsets.UTF_8));
			String tok=token.substring(7);
			Jws<Claims> jws=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tok);
			 Claims body=jws.getBody();
			  String name=body.getSubject();
			 
			 @SuppressWarnings("unchecked")
			List<Map<String,String>> role=(List<Map<String, String>>) body.get("authorities");
			 System.out.println(role);
			 Set<SimpleGrantedAuthority> grant=role.stream().map(data-> new SimpleGrantedAuthority(data.get("authority"))).collect(Collectors.toSet());
			 System.out.println(grant);
			 UsernamePasswordAuthenticationToken au=new UsernamePasswordAuthenticationToken(name,null,grant);
 			 	Authentication a=au;
			 SecurityContextHolder.getContext().setAuthentication(a);
			 filterChain.doFilter(request,response);
		}
 		
	}

}
