package com.example.File_Service.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.File_Service.configurations.BucketService;

@Service
public class s3Services implements BucketService{
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	
	private final AmazonS3 amazonS3;
	
	public s3Services(AmazonS3 amazonS3) {
		super();
		this.amazonS3 = amazonS3;
	}

	@Override
	public List<Bucket> getBucketList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateBucket(String bucketName) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void createBucket(String bucket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getObjectFromBucket(String key) throws IOException {
		try {
		    S3Object o = amazonS3.getObject(bucketName, key);
		    S3ObjectInputStream s3is = o.getObjectContent();
		    FileOutputStream fos = new FileOutputStream(new File(key));
		    byte[] read_buf = new byte[1024];
		    int read_len = 0;
		    while ((read_len = s3is.read(read_buf)) > 0) {
		        fos.write(read_buf, 0, read_len);
		    }
		    s3is.close();
		    fos.close();
		    return o.getKey();
		} catch (IOException | AmazonServiceException e) {
			throw new RuntimeException("Failed to download file "+ e.getMessage(), e);
		}
		 
	}

	@Override
	public String putObjectIntoBucket(MultipartFile file) {		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());	
		try {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
				PutObjectResult res = amazonS3.putObject(bucketName, filename,file.getInputStream(), metadata );
				return filename;
		} catch (IOException | SdkClientException e) {
			throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
		} 
	}}
