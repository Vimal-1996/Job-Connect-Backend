package com.example.job.service.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.job.service.models.Job;
import com.example.job.service.repository.JobRepository;

@Service
public class JobService {
	
	@Autowired
	private JobRepository jobRepository;
	
	public ResponseEntity<?> createJob(Job job, String username) {
		try {
			job.setCreatedBy(username);
			Job savedJob  = jobRepository.save(job);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create job "+e.getMessage());
		}
		
	}
	
	public ResponseEntity<?> getAllJobs(){
		List<Job> jobs = jobRepository.findAll();
		return jobs.isEmpty()
				? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No jobs found")
			    : ResponseEntity.status(HttpStatus.OK).body(jobs);			
	}
	
	public ResponseEntity<?> searchJobs(String keyword){
		List<Job> results = jobRepository.findByTitleContainingIgnoreCase(keyword);
		return results.isEmpty()
				? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No jobs found")
					    : ResponseEntity.status(HttpStatus.OK).body(results);	
	}
	
	public ResponseEntity<?> getJobById(Long id) {
	    Optional<Job> job = jobRepository.findById(id);
	    return job.isEmpty()
				? ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.EMPTY_LIST)
					    : ResponseEntity.status(HttpStatus.OK).body(job.get());
	}

	
	public ResponseEntity<?> updateJob(Long id, Job job) {		
		return jobRepository.findById(id)
				.map((existingJob)->{
					existingJob.setTitle(job.getTitle());
					existingJob.setDescription(job.getDescription());
					existingJob.setLocation(job.getLocation());
					existingJob.setCompany(job.getCompany());
					return  ResponseEntity.status(HttpStatus.OK).body(jobRepository.save(existingJob));
		})
		.orElseThrow(()->new RuntimeException("Job not found with id"+id));
	}
	
	public ResponseEntity<?> deleteJob(Long id) {
	    if (!jobRepository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("Job not found with id: " + id);
	    }

	    jobRepository.deleteById(id);
	    return ResponseEntity.ok("Job deleted successfully");
	}

	
}
