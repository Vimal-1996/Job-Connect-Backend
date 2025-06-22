package com.example.Application.service.services;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Application.service.models.ApplicationStatus;
import com.example.Application.service.models.JobApplication;
import com.example.Application.service.repoitory.JobApplicationRepository;

@Service
public class JobApplicationService {
	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	public ResponseEntity<?> applyToJob(JobApplication jobApplication, String username) {
		try {
			jobApplication.setApplicantUsername(username);
			JobApplication jobApplicationnew = jobApplicationRepository.save(jobApplication);
			return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationnew);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Failed to craete new job "+e.getMessage());
		}
	}
	
	
	public ResponseEntity<?> getMyApplications(String username){
		List<JobApplication> jobApplicationsByUsername = jobApplicationRepository.findByApplicantUsername(username);
		if(jobApplicationsByUsername.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.EMPTY_LIST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationsByUsername);
	}
	
	
	public ResponseEntity<?> updateStatus(Long id, ApplicationStatus status) {
		try {
			Optional<JobApplication> application  = jobApplicationRepository.findById(id);
			if(application.isPresent()) {
				application.get().setStatus(status);
				JobApplication jobApplicationnew = jobApplicationRepository.save(application.get());
				return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationnew);
			}
			else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.EMPTY_LIST);
			}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Failed to craete new job "+e.getMessage());
		}
		
	}
}
