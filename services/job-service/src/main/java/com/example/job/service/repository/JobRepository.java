package com.example.job.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.job.service.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{
	
	List<Job> findByTitleContainingIgnoreCase(String keyword);

	
}
