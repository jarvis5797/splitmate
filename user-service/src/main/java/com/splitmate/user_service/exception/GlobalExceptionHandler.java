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
		
		ErrorResponse response = ErrorResponse.builder()
									.timestamp(LocalDateTime.now())
									.message(ex.getMessage())
									.path(request.getDescription(false))
									.errorCode("NOT_FOUND")
									.build();
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex, WebRequest request){
		
		ErrorResponse response = ErrorResponse.builder()
									.timestamp(LocalDateTime.now())
									.message(ex.getMessage())
									.path(request.getDescription(false))
									.errorCode("DUPLICATE")
									.build();
		
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request){
		
		ErrorResponse response = ErrorResponse.builder()
									.timestamp(LocalDateTime.now())
									.message(ex.getMessage())
									.path(request.getDescription(false))
									.errorCode("INTERNAL_SERVER_ERROR")
									.build();
									
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
