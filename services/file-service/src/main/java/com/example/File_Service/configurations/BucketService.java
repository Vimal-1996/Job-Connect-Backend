package com.example.File_Service.configurations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;

public interface BucketService {
	
	List<Bucket> getBucketList();
	boolean validateBucket(String bucketName);
	String getObjectFromBucket(String key) throws IOException;
	String putObjectIntoBucket(MultipartFile file);
	void createBucket(String bucket);
	

}
