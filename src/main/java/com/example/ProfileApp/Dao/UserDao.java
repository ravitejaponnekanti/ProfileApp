package com.example.ProfileApp.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ProfileApp.Model.UserProfile;

@Repository
public interface UserDao extends JpaRepository<UserProfile,Long> {

}
