package com.example.File_Service.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.File_Service.services.s3Services;

@RestController
@RequestMapping("/files")
public class FileController {
	
	
	private final s3Services s3services;
	
    public FileController(s3Services s3services) {
		super();
		this.s3services = s3services;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		try {
			String key = s3services.putObjectIntoBucket(file);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("File "+key+" successfully uploaded");
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    
	}
	

    @GetMapping("/download/{key}")
    public ResponseEntity<?> download(@PathVariable String key) {
        try {
			String downloadedObj = s3services.getObjectFromBucket(key);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("File "+downloadedObj+" successfully downloaded");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
        
    }
}
