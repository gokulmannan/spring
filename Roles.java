package com.gokul.entity;

 
public enum Roles {
 	ADMIN(Authority.admin),
	USER(Authority.user);
	
String[] authority;

private Roles(String...  admin2) {
	this.authority = admin2;
}

public String[] getAuthority() {
	return authority;
}

 



 	
	
	
}
