package com.example.Apartment.controlleradvice;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.common.ResponsMessage;
import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// wrong Password exception handling
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoSuchElementFoundException(BadCredentialsException exception) {
		ResponseEntity<?> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
		return response;
	}

	// wrong UserName exception handling
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoSuchElementFoundException(InternalAuthenticationServiceException exception) {
		ResponseEntity<?> response = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
		return response;
	}

	// constraint exception handling
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoSuchElementFoundException(ConstraintViolationException exception) {
		ResponsMessage res = new ResponsMessage(exception.getConstraintViolations().stream()
				.map(p -> p.getMessageTemplate()).collect(Collectors.toList()).toString());
		ResponseEntity<?> response = new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
		return response;
	}

	@ExceptionHandler(JsonParseException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoSuchElementFoundException(JsonParseException exception) {
		ResponsMessage res = new ResponsMessage(exception.getMessage().toString());
		ResponseEntity<?> response = new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
		return response;
	}
	
	@ExceptionHandler(SignatureException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public ResponseEntity<?> handleNoSuchElementFoundException(SignatureException exception) {
		ResponsMessage res = new ResponsMessage(exception.getMessage().toString());
		ResponseEntity<?> response = new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
		return response;
	}
}
