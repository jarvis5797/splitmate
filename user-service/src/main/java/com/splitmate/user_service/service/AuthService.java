package com.splitmate.user_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitmate.user_service.dto.AuthResponse;
import com.splitmate.user_service.dto.CreateUserRequest;
import com.splitmate.user_service.dto.LoginRequest;
import com.splitmate.user_service.entity.User;
import com.splitmate.user_service.exception.AuthenticationException;
import com.splitmate.user_service.repository.UserRepository;
import com.splitmate.user_service.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public String addUser(CreateUserRequest request) {
		
		User user = User.builder()
						.name(request.getName())
						.email(request.getEmail())
						.phone(request.getPhone())
						.password(passwordEncoder.encode(request.getPassword()))
						.build();
		
		userRepository.save(user);
		
		return "User Created!";
	}
	
	public AuthResponse login(LoginRequest request) {
		
		User user  = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new AuthenticationException("Invalid email or password!"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new AuthenticationException("Invalid email or password!");
		}
		
		String token = jwtUtil.generateToken(user.getEmail());
		return AuthResponse.builder().token(token).build();
	}

}
