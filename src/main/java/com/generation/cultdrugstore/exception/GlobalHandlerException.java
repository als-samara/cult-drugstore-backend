package com.generation.cultdrugstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.generation.cultdrugstore.exception.user.UserException;

@RestControllerAdvice
public class GlobalHandlerException {

	// Handle validation errors {@link MethodArgumentNotValidException} and return the
	// default message of the first error at the response body
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest()
				.body("Erro de validação: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	// Handle all exceptions related to {@link UserException} and return the message in the response body.
	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handleUserException(UserException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
