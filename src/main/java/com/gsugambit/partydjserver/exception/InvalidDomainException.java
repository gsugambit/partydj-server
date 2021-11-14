package com.gsugambit.partydjserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "cannot persist")
public class InvalidDomainException extends RuntimeException{

	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = 505290146113735306L;

	public InvalidDomainException(String message) {
		super(message);
	}
}
