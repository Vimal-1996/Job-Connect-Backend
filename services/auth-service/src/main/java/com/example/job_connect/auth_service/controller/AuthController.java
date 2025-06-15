package com.example.job_connect.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_connect.auth_service.Repository.UserRepository;
import com.example.job_connect.auth_service.Util.JwtUtility;
import com.example.job_connect.auth_service.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }
    

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
    	
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    	System.out.println("User registration implemented");
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User request) {
		System.out.println(request.getUsername() + " " + request.getPassword());
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = JwtUtility.generateToken(user.getUsername());
            return ResponseEntity.ok(user.getRole());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
