package com.splitmate.user_service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
	
	private LocalDateTime timestamp;
	
	private String message;
	
	private String path;
	
	private String errorCode;

}
