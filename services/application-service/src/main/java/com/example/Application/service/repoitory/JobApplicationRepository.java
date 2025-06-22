package com.example.Application.service.repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Application.service.models.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{
	List<JobApplication> findByApplicantUsername(String username);
}
