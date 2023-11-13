package com.example.ProfileApp.service;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ProfileApp.Dao.UserDao;
import com.example.ProfileApp.Model.UserProfile;

@Service
public class UserService {

	
	@Autowired
	UserDao userdao;
	@Autowired
	FileStore filestore;
	public String bucketName = "mycoolappphotosbucket";

	public List<UserProfile> getall() {
		// TODO Auto-generated method stub
		return userdao.findAll();
	}

	public void uploadimage(long userid, MultipartFile file) {
		if(file.isEmpty()) {
			throw new IllegalStateException("cannot upload empty file");
		}
		
		if (!(file.getContentType().equalsIgnoreCase("image/jpeg") || file.getContentType().equalsIgnoreCase("image/png"))) {
		    throw new IllegalStateException("File must be either image/jpeg or image/png. Actual content type: " + file.getContentType());
		}
		Optional<UserProfile> user=userdao.findById(userid);
		if(user.isPresent()) {
			UserProfile user1=user.get();
			Map<String,String> metadata=new HashMap<>();
			metadata.put("Content-Type", file.getContentType());
			metadata.put("content-Length",String.valueOf(file.getSize()));
			
			String path=String.format("%s/%s",bucketName,user1.getId());
			String filename=String.format("%s-%s",file.getName(),UUID.randomUUID());
			try {
				filestore.save(path, filename,Optional.of(metadata), file.getInputStream());
				user1.setProfilePiclink(filename);
				userdao.save(user1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public byte[] downloadimage(long userid) {
		// TODO Auto-generated method stub
		Optional<UserProfile> user=userdao.findById(userid);
		if(user.isPresent()) {
			UserProfile user1=user.get();
			String path=String.format("%s/%s",bucketName,user1.getId());
			String key=user1.getProfilePiclink();
			return filestore.download(path,key);
		}
		
		return null;
	}

	public void newuser(String name,MultipartFile file) {
		// TODO Auto-generated method stub
		
		UserProfile newuser=new UserProfile();
		newuser.setName(name);
		userdao.save(newuser);
          long id=newuser.getId();
		
		if(file!=null&& !file.isEmpty()) {
			if (!(file.getContentType().equalsIgnoreCase("image/jpeg") || file.getContentType().equalsIgnoreCase("image/png"))) {
			    throw new IllegalStateException("File must be either image/jpeg or image/png. Actual content type: " + file.getContentType());
			}
			Optional<UserProfile> user=userdao.findById(id);
			if(user.isPresent()) {
				UserProfile user1=user.get();
				Map<String,String> metadata=new HashMap<>();
				metadata.put("Content-Type", file.getContentType());
				metadata.put("content-Length",String.valueOf(file.getSize()));
				
				String path=String.format("%s/%s",bucketName,user1.getId());
				String filename=String.format("%s-%s",file.getName(),UUID.randomUUID());
				try {
					filestore.save(path, filename,Optional.of(metadata), file.getInputStream());
					user1.setProfilePiclink(filename);
					userdao.save(user1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
}
