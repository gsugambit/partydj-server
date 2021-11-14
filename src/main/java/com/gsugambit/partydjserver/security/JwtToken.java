package com.gsugambit.partydjserver.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtToken extends UsernamePasswordAuthenticationToken {

	/**
	 * UUID 
	 */
	private static final long serialVersionUID = 836355448718186757L;
	
	private String token;

	public JwtToken(String token) {
		super(null, null);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

}
