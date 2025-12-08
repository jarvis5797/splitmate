package com.splitmate.user_service.service;

import org.springframework.stereotype.Service;

import com.splitmate.user_service.dto.CreateUserRequest;
import com.splitmate.user_service.dto.UserResponse;
import com.splitmate.user_service.entity.User;
import com.splitmate.user_service.exception.ResourceNotFoundException;
import com.splitmate.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserResponse createUser(CreateUserRequest userRequest) {
		User user = User.builder()
					.name(userRequest.getName())
					.email(userRequest.getEmail())
					.phone(userRequest.getPhone())
					.build();
		
		User savedUser = userRepository.save(user);
		
		return UserResponse.builder()
				.id(savedUser.getId())
				.name(savedUser.getName())
				.email(savedUser.getEmail())
				.phone(savedUser.getPhone())
				.build();
	}
	
	public UserResponse getUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Unable to find user!"));
		
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.build();
	}
	
	public UserResponse updateUser(Long id, CreateUserRequest request) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Unable to find user!"));
		
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		userRepository.save(user);
		
		return UserResponse.builder()
				.id(id)
				.name(request.getName())
				.email(request.getEmail())
				.phone(request.getPhone())
				.build();
	}
	
	public String deleteUser(Long id) throws Exception {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find user!"));
		try {
			userRepository.delete(user);
			return "User deleted!";
		}catch (Exception e) {
			throw new Exception("Error while deleting user!");
		}
	}

}
