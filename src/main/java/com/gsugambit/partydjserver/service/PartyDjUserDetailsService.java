package com.gsugambit.partydjserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gsugambit.partydjserver.dao.UserRepository;
import com.gsugambit.partydjserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PartyDjUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public PartyDjUserDetailsService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("cannot find user by username: " + username));
		
		LOGGER.trace("User {} has been found and loaded. ID: {}", username, user.getUserId());
		return user;
	}

}
