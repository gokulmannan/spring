package com.gokul.service;

import static java.util.Arrays.stream;
 
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gokul.entity.Student;

public class UserPrincipal implements UserDetails {

	
	private static final long serialVersionUID = 1L;
	Student s;
	public UserPrincipal(Student s) {
		super();
		this.s = s;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return stream(this.s.getAuthority()).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
 	}

	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return s.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return s.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return s.getIsActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return s.getIsEnabled();
	}

}
