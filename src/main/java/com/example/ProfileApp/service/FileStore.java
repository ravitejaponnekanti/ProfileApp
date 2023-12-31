package com.example.ProfileApp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class FileStore {
	
	private final AmazonS3 s3;
	@Autowired
	public FileStore(AmazonS3 s3) {
		this.s3 = s3;
	}
	 
     
	 public void save(String path, String filename,
			 Optional<Map<String,String>> optinalMetadata,
			 InputStream inputstream) {
		 ObjectMetadata metadata=new ObjectMetadata();
		 optinalMetadata.ifPresent(map->
		 {if(!map.isEmpty()) {
			 map.forEach(metadata::addUserMetadata);
		 }});
		 try {
			 s3.putObject(path,filename,inputstream,metadata);
		 }catch(AmazonServiceException e) {
			throw new IllegalStateException("Failed to store",e); 
		 }
	 }


	public byte[] download(String path, String key) {
		try {
			S3Object object=s3.getObject(path, key);
			S3ObjectInputStream inputstream=object.getObjectContent();
		    return IOUtils.toByteArray(inputstream);
		}catch(AmazonServiceException|IOException e) {
			throw new IllegalStateException("filed to download file");
		}
			
		
	}
	 
	 
	 

}
