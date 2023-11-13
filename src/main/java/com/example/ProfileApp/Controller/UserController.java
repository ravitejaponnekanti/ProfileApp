package com.example.ProfileApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ProfileApp.Model.UserProfile;
import com.example.ProfileApp.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@GetMapping("/showall")
	public List<UserProfile> getall(){
	  	return userservice.getall();
	}
	
	@PostMapping(path="/{userid}/image",
			consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public void uploadimage(@PathVariable long userid,
			@RequestParam("file") MultipartFile file) {
		userservice.uploadimage(userid,file);
		
	}
	
	@GetMapping(path="/{userid}/image/download")
	public byte[] downloadimage(@PathVariable long userid) {
		return userservice.downloadimage(userid);
	}
	
	@PostMapping(path="/adduser")
	   public void adduser(@RequestParam String name, @RequestParam(value = "image", required = false) MultipartFile imageFile) {
		userservice.newuser(name,imageFile);
	}
	
	
	

}
