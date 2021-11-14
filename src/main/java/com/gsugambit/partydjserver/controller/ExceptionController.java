package com.gsugambit.partydjserver.controller;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gsugambit.partydjserver.dto.ExceptionResponse;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception,
			HttpServletRequest request) {
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().exceptionMessage(exception.getMessage())
				.exceptionType(exception.getClass().getSimpleName()).path(request.getRequestURI())
				.exceptionTrace(ExceptionUtils.getStackTrace(exception))
				.timestamp(Instant.now()).build();
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);

	}
}
