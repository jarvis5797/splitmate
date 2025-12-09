package com.splitmate.user_service.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest request){
		
		ErrorResponse response = errorResponseBuilder(ex, request);
		response.setErrorCode("NOT_FOUND");
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex, WebRequest request){
		
		ErrorResponse response = errorResponseBuilder(ex, request);
		response.setErrorCode("DUPLICATE");
		
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request){
		
		ErrorResponse response = errorResponseBuilder(ex, request);
		response.setErrorCode("INTERNAL_SERVER_ERROR");
									
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex, WebRequest request){
		
		ErrorResponse response = errorResponseBuilder(ex, request);
		response.setErrorCode("INVALID_TOKEN");
		
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex, WebRequest request){
		
		ErrorResponse response = errorResponseBuilder(ex, request);
		response.setErrorCode("INVALID_EMAIL_OR_PASSWORD");
		
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	private ErrorResponse errorResponseBuilder(Exception ex, WebRequest request) {
		
		return ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.path(request.getDescription(false))
				.build();
	}

}
