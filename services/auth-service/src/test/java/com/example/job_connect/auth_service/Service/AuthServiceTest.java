package com.example.job_connect.auth_service.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.job_connect.auth_service.Repository.UserRepository;
import com.example.job_connect.auth_service.model.Role;
import com.example.job_connect.auth_service.model.User;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private AuthService authService;
	@InjectMocks
	private User mockUser;
	
	@BeforeEach
	void setup() {
		mockUser = new User(1L, "testUser", "testPass", Role.ADMIN);
	}
	
	@Test
	void testGetUserByUsername_found() {
		when(userRepository.findByUsername("newuser")).thenReturn(Optional.of(mockUser));
		Optional<User> result = authService.getUserByUsername("newuser");
		
		assertTrue(result.isPresent());
		assertEquals("testUser", result.get().getUsername());
	}
	
	@Test
	void testGetUserByUsername_notFound() {
		when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class,()-> authService.getUserByUsername("nouser"));
	}
	
//	@Test
//	void testValidatePasswordRequests_success() {
//		when(passwordEncoder.matches("rawpass", mockUser.getPassword())).thenReturn(true);
//		String token = authService.validatePasswordRequests("rawpass", Optional.of(mockUser));
//		assertNotNull(token);
//	}
	
	
	@Test
	void testValidatePasswordRequests_success() {
		when(passwordEncoder.matches("userpass",mockUser.getPassword())).thenReturn(false);
		String token = authService.validatePasswordRequests("userpass",Optional.of(mockUser));
		assertNotNull(token);
	}
	@Test
	void testValidatePasswordRequests_failure() {
		when(passwordEncoder.matches("nopass", mockUser.getPassword())).thenReturn(true);
		assertThrows(BadCredentialsException.class ,()->authService.validatePasswordRequests("nopass", Optional.of(mockUser)));
	}
	
	@Test
	void testRegisterUser_success() {
	    // Arrange
	    User mockUser = new User(); 
	    mockUser.setUsername("testUser");
	    mockUser.setPassword("testPass");
	    mockUser.setRole(Role.ADMIN);

	    when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
	    when(passwordEncoder.encode("testPass")).thenReturn("encodedpass");

	    // Act
	    String result = authService.registerUser(mockUser);

	    // Assert
	    assertEquals("User Successfully registered", result);
	    verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void testRegisterUser_duplicateUsername() {
		//Arrange
		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
		
		//Act
		//Assert
		assertThrows(IllegalArgumentException.class,()->{
			authService.registerUser(mockUser);
		});
	}

}
