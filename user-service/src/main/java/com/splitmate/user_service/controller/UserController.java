package com.splitmate.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitmate.user_service.dto.CreateUserRequest;
import com.splitmate.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id){
		return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request){
		return new ResponseEntity<>(userService.updateUser(id, request), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestParam(name = "id") Long id) throws Exception{
		return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
	}

}
