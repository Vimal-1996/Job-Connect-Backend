package com.example.Application.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Application.service.models.ApplicationStatus;
import com.example.Application.service.models.JobApplication;
import com.example.Application.service.services.JobApplicationService;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {
	
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@PostMapping
	public ResponseEntity<?> applyToJob(@RequestBody JobApplication jobApplication,@RequestParam String username){
		return ResponseEntity.ok(jobApplicationService.applyToJob(jobApplication, username));
	}
	
    @GetMapping("/my")
    public ResponseEntity<?> getMyApplications(@RequestParam String username) {
        return ResponseEntity.ok(jobApplicationService.getMyApplications(username));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(jobApplicationService.updateStatus(id, status));
    }
}
