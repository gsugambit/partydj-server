package com.gsugambit.partydjserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsugambit.partydjserver.model.User;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
public class UserDto {

	private String email;
	private String username;
	private String password;
	
	public boolean valid() {
		return (StringUtils.isNotBlank(email) || StringUtils.isNotBlank(username)) && StringUtils.isNotBlank(password);
	}
	
	public static User convert(UserDto userDto) {
		return User.builder()
				.email(userDto.getEmail())
				.username(userDto.getUsername())
				.password(userDto.getPassword()).build();
	}
}
