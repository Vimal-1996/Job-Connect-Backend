package com.example.job.service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.job.service.models.Job;
import com.example.job.service.services.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	@Autowired
	private JobService jobService;
	
	@PostMapping
	public ResponseEntity<?> createJob(@RequestBody Job job, @RequestParam String username){
		logger.info("Inside createjob post making");
		return ResponseEntity.ok(jobService.createJob(job, username));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllJobs(){
		return ResponseEntity.ok(jobService.getAllJobs());
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<?> searchJobs(@RequestParam String keyword){	
		return ResponseEntity.ok(jobService.searchJobs(keyword));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getJobById(@PathVariable Long id){
		return ResponseEntity.ok(jobService.getJobById(id));
		
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
         return jobService.deleteJob(id);
        
    }
	
	
	
}
