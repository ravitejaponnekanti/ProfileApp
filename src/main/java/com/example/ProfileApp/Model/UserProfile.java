package com.example.ProfileApp.Model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
    
	 private String name;
	 private String ProfilePiclink;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfilePiclink() {
		return ProfilePiclink;
	}
	public void setProfilePiclink(String profilePiclink) {
		ProfilePiclink = profilePiclink;
	}
	public UserProfile(Long id, String name, String profilePiclink) {
		super();
		this.id = id;
		this.name = name;
		ProfilePiclink = profilePiclink;
	}
	public UserProfile() {
		super();
	}
	 
	
}
