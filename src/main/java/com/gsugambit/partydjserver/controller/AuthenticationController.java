package com.gsugambit.partydjserver.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.model.User;
import com.gsugambit.partydjserver.security.AuthSuccess;
import com.gsugambit.partydjserver.security.JWTRequest;
import com.gsugambit.partydjserver.security.TokenHelper;
import com.gsugambit.partydjserver.security.UserJwtTokenState;
import com.gsugambit.partydjserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/login")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final TokenHelper tokenHelper;
	private final UserService userService;
	private final UserDetailsService userDetailsService;

	@Autowired
	public AuthenticationController(final AuthenticationManager authenticationManager, final TokenHelper tokenHelper,
			final UserService userService, final UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.tokenHelper = tokenHelper;
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping()
	public AuthSuccess login(@RequestBody JWTRequest jwtRequest) {
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// token creation
		final User user = (User) authentication.getPrincipal();
		final String jwsToken = tokenHelper.generateToken(user.getUsername());
		final int expiresIn = tokenHelper.getExpiredIn();
		// Return the token
		final UserJwtTokenState token = new UserJwtTokenState(jwsToken, expiresIn);
		
		return AuthSuccess.builder().token(token).user(user).build();
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<UserJwtTokenState> refresh(HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		final String jwsToken = tokenHelper.getToken(request);
		String username = tokenHelper.getUsernameFromToken(jwsToken);


		LOGGER.trace("jwtToken == null --> {}, principal == null --> {}", jwsToken == null, principal == null);
		LOGGER.trace("username--> {}", username);

		if (jwsToken != null) {
			final String refreshedToken = tokenHelper.refreshToken(jwsToken);
			final int expiresIn = tokenHelper.getExpiredIn();

			return ResponseEntity.ok(new UserJwtTokenState(refreshedToken, expiresIn));
		}else {
			final UserJwtTokenState userTokenState = new UserJwtTokenState();

			// we return accepted with an empty JWT if nothing was provided.
			return ResponseEntity.accepted().body(userTokenState);

		}
	}
}
