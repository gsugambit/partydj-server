package com.gsugambit.partydjserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "cannot persist")
public class ConstraintException extends RuntimeException{
	/**
	 * UUID
	 */
	private static final long serialVersionUID = -8825229183792668910L;

	public ConstraintException(String message) {
		super(message);
	}
}
