package com.gsugambit.partydjserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.dto.UserDto;
import com.gsugambit.partydjserver.model.User;
import com.gsugambit.partydjserver.service.UserService;

@RestController
@RequestMapping("/api/v1/register")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping
	public User createUser(@RequestBody UserDto userDto) {
		return userService.create(userDto);
	}
}
