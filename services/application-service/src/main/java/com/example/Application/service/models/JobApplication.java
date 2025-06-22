package com.example.Application.service.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="job_applications")
public class JobApplication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long jobId;
	private String applicantUsername;
	private String resumeUrl;
	
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status=  ApplicationStatus.APPLIED;
	private LocalDateTime appliedAt = LocalDateTime.now();
	
	public JobApplication() {
		super();
	}

	public JobApplication(Long id, Long jobId, String applicantUsername, String resumeUrl, ApplicationStatus status,
			LocalDateTime appliedAt) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.applicantUsername = applicantUsername;
		this.resumeUrl = resumeUrl;
		this.status = status;
		this.appliedAt = appliedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getApplicantUsername() {
		return applicantUsername;
	}

	public void setApplicantUsername(String applicantUsername) {
		this.applicantUsername = applicantUsername;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public LocalDateTime getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDateTime appliedAt) {
		this.appliedAt = appliedAt;
	}
	
	
	
	
	
}
