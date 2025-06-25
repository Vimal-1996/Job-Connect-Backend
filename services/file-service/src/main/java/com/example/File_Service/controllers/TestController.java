package com.example.File_Service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filestest")
public class TestController {
	
	@PostMapping("/hi")
	public String returndataPost() {
		return "hello from test";
	}
	
	@GetMapping("/hi")
	public String returndataGet() {
		return "hello from test get";
	}

}
