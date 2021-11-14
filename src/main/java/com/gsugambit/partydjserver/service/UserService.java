package com.gsugambit.partydjserver.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.gsugambit.partydjserver.dao.RoleRepository;
import com.gsugambit.partydjserver.dao.UserRepository;
import com.gsugambit.partydjserver.dto.UserDto;
import com.gsugambit.partydjserver.exception.ConstraintException;
import com.gsugambit.partydjserver.exception.InvalidDomainException;
import com.gsugambit.partydjserver.model.Role;
import com.gsugambit.partydjserver.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserService {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	
	@Autowired
	public UserService(final RoleRepository roleRepository,
			final UserRepository userRepository,
			final PasswordEncoder encoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public User create(UserDto userDto) {
		if(!userDto.valid()) {
			throw new InvalidDomainException("all needed fields are not present");
		}
		
		User newUser = UserDto.convert(userDto);
		
		boolean emailPresent = StringUtils.isBlank(newUser.getEmail())
				? false : userRepository.findByEmail(newUser.getEmail()).isPresent();
		boolean userNamePresent = StringUtils.isBlank(newUser.getUsername())
				? false : userRepository.findByUsername(newUser.getUsername()).isPresent();
		
		if(emailPresent || userNamePresent) {
			throw new ConstraintException("email or username already in use");
		}
		
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		newUser.setEnabled(true);
		
		Role role = Role.builder().name(UUID.randomUUID().toString()).build();
		roleRepository.save(role);
		newUser.setRoles(Sets.newHashSet(role));
		LOGGER.trace("Attempting to create user: {}", newUser);
		
		userRepository.save(newUser);
		LOGGER.trace("Create user: {}", newUser);
		
		return newUser;
	}
}
