package com.gokul.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static com.gokul.entity.Roles.*;

import com.gokul.error.DenailOfService;
import com.gokul.error.JwtAuthenticationEntryPoint;
import com.gokul.filter.JwtToken;
import com.gokul.filter.JwtVerifier;
import com.gokul.service.StudentServiceImp;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class Security extends WebSecurityConfigurerAdapter{
	@Autowired
	StudentServiceImp service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

 	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(STATELESS).and().

		addFilter(new JwtToken(authenticationManager())).addFilterAfter(new JwtVerifier(), JwtToken.class).exceptionHandling()
		.accessDeniedHandler(new DenailOfService()).authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and().
		authorizeRequests()
		.antMatchers(HttpMethod.GET,"/all").permitAll().
		antMatchers(HttpMethod.POST,"/reg").permitAll().
		antMatchers(HttpMethod.GET,"/log").permitAll()

 		.antMatchers(HttpMethod.GET,"/single").hasAnyAuthority("view")
		.antMatchers(HttpMethod.PUT,"/update").hasAnyAuthority("create")
		.antMatchers(HttpMethod.DELETE,"/delete").hasAnyAuthority("delete")
		 
		.anyRequest().authenticated();
	
	}
	

}
