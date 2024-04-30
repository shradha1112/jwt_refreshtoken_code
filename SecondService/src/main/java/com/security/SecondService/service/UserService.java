package com.security.SecondService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.SecondService.dto.AuthRequest;
import com.security.SecondService.entity.UserInfo;
import com.security.SecondService.repository.UserInfoRepository;



@Service
public class UserService {
	
	
	  @Autowired
	    private UserInfoRepository repository;

	  @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	 public String addUser(UserInfo userInfo) {
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        repository.save(userInfo);
	        return "user added to system ";
	    }

	 
	 public boolean validateLogin(AuthRequest authRequest)
	 {
		 String username = authRequest.getUsername();
		 String password = authRequest.getPassword();
		 System.out.println(password);
		 System.out.println("Entered in validate func");
		 UserInfo userInfoOptional =  repository.findByName1(username);
	
		 // Check if userInfoOptional is not null
		    if (userInfoOptional != null) {
		        System.out.println("Entered in if loop");

		        System.out.println(userInfoOptional);
		        // Compare passwords (assuming you have a getPassword() method in UserInfo)
		        // Retrieve the hashed password from the user info
		        String hashedPassword = userInfoOptional.getPassword();

		        // Compare the hashed password with the provided password after encoding
		        if (passwordEncoder.matches(password, hashedPassword)) {
		            System.out.println("Correct Password");
		            return true; // Passwords match, login is valid
		        } else {
		            System.out.println("Incorrect Password");
		        }
		    } else {
		        System.out.println("User not found");
		    }
		 
		return false;
		 
	 }
}
