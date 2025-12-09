package com.splitmate.user_service.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.splitmate.user_service.exception.InvalidTokenException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${security.secret}")
	private String secret;
	
	@Value("${security.expiration-ms}")
	private Long expiration;
	
	private Key getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(String email) {
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getKey())
				.compact();
	}
	
	public String extractEmail(String token) {
		return Jwts.parser()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean isValid(String token) {
		
		try {
			Jwts.parser()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token);
			return true;
		}catch(Exception ex) {
			throw new InvalidTokenException("Token is not valid!");
		}
	}
	
	
}
