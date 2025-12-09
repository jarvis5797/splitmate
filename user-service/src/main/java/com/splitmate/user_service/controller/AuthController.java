package com.splitmate.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitmate.user_service.dto.CreateUserRequest;
import com.splitmate.user_service.dto.LoginRequest;
import com.splitmate.user_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody CreateUserRequest request){
		return new ResponseEntity<>(authService.addUser(request), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		return new ResponseEntity<>(authService.login(request), HttpStatus.ACCEPTED);
	}

}
