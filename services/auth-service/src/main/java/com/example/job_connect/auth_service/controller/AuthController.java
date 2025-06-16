package com.example.job_connect.auth_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_connect.auth_service.Repository.UserRepository;
import com.example.job_connect.auth_service.Service.AuthService;
import com.example.job_connect.auth_service.Util.JwtUtility;
import com.example.job_connect.auth_service.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthService authService;

    public AuthController(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }
    

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
    	try {
    		String response = authService.registerUser(user);
    		return ResponseEntity.ok(response);
    	}
    	catch(IllegalArgumentException ex) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    	}
    	catch(Exception ex) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
    	}
    	 
    }

	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User request) {
		Optional<User> user = authService.getUserByUsername(request.getUsername());
		try {
			String token = authService.validatePasswordRequests(request.getPassword(), user);
			return ResponseEntity.ok(token);
		} catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}		
    }
}
