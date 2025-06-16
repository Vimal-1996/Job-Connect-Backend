package com.example.job_connect.auth_service.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.job_connect.auth_service.Repository.UserRepository;
import com.example.job_connect.auth_service.Util.JwtUtility;
import com.example.job_connect.auth_service.model.User;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Optional<User> getUserByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found") ));		
	}
	
	
	public String validatePasswordRequests(String requestPassword, Optional<User> user){
		
		if (passwordEncoder.matches(requestPassword, user.get().getPassword()))
		{
            String token = JwtUtility.generateToken(user.get().getUsername());
            return token;
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
	}
	
	
	public String registerUser(User user) {
		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username already exists");
		}
		else{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return "User Successfully registered";		
		}

	}
	
	
	
}
