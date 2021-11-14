package com.gsugambit.partydjserver.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class PartyDJAuthToken extends AbstractAuthenticationToken {
	
	/**
	 * UUID
	 */
	private static final long serialVersionUID = 2015818613156117819L;

	private String credentials;
	
	private UserDetails userDetails;

	public PartyDJAuthToken(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

}
