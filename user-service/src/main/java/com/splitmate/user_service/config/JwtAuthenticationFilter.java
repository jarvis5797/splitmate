package com.splitmate.user_service.config;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.splitmate.user_service.exception.ResourceNotFoundException;
import com.splitmate.user_service.repository.UserRepository;
import com.splitmate.user_service.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);
		String email = jwtUtil.extractEmail(token);
		
		if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 var user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Invalis user!"));
			 
			 if(jwtUtil.isValid(token)) {
				 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						 user.getEmail(), null, null
						 );
				 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				 SecurityContextHolder.getContext().setAuthentication(authToken);
			 }
		}
		
		filterChain.doFilter(request, response);
	}

}
