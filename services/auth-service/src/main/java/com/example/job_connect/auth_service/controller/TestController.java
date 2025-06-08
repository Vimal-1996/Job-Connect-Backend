package com.example.job_connect.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/api/v1/auth/hello")
	public String Hello() {
		return "Hello From Auth Service";
	}

}
