package com.gsugambit.partydjserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "cannot find")
public class NotFoundException extends RuntimeException {

	/**
	 * UUID
	 */
	private static final long serialVersionUID = 1408387747179460282L;

	public NotFoundException(String message) {
		super(message);
	}
}
