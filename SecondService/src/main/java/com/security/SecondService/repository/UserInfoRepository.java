package com.security.SecondService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.SecondService.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{
	Optional<UserInfo> findByName(String username);
	
	  @Query("SELECT u FROM UserInfo u WHERE u.name = ?1")
	  UserInfo findByName1(String username);
	  
	 
}
