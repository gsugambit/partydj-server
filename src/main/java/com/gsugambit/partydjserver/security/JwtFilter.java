package com.gsugambit.partydjserver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	
	private final TokenHelper tokenHelper;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public JwtFilter(final TokenHelper tokenHelper,
			final UserDetailsService userDetailsService) {
		this.tokenHelper = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username;
		String authToken = tokenHelper.getToken(request);

		if (authToken != null) {
			// get username from token
			username = tokenHelper.getUsernameFromToken(authToken);
			if (username != null) {
				// get user
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (tokenHelper.validateToken(authToken, userDetails)) {
					PartyDJAuthToken authentication = new PartyDJAuthToken(userDetails);
					authentication.setAuthenticated(true);
					authentication.setCredentials(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					LOGGER.info("Yo we authentication: {}", username);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
