package com.example.job.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class TestController {
	
	@GetMapping("/test")
	public String helloTest() {
		return "Hello from job service";
	}
}
