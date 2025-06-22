package com.example.job_connect.auth_service.Util;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtility {
	
	private static final String  SECRET_KEY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEF"; // 32 chars;
	private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	
	public static String generateToken(String username, String role) {
		return Jwts.builder()
				.setSubject(username)
				.claim("role",role)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
				.signWith(key)
				.compact();
	}
	
	public static String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public static boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername());
	}

}
