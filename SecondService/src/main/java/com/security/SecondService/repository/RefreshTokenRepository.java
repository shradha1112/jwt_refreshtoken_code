package com.security.SecondService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.SecondService.entity.RefreshToken;



public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
