package com.example.job_connect.auth_service.Util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.job_connect.auth_service.Repository.UserRepository;
import com.example.job_connect.auth_service.model.User;

public class CustomUserDetailsService implements UserDetailsService{
	
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		
	}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(CustomUserDetails::new)
                   .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }




	
}
