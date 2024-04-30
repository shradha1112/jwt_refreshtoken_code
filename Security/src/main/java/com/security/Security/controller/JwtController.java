package com.security.Security.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.Security.entity.RefreshToken;
import com.security.Security.filter.JwtAuthFilter;
import com.security.Security.service.JwtService;
import com.security.Security.service.RefreshTokenService;
import com.security.Security.service.TokenHandler;

import jakarta.servlet.ServletException;

@RestController
@RequestMapping("/token")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody String userName) {
    	System.out.println("Entered in geentrate token");
        String token = jwtService.generateToken(userName);
        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestHeader("Authorization") String authHeader) throws Exception {
    	System.out.println("chkTok enter");
//    	String decryptedToken = TokenHandler.decrypt(authHeader.substring(7));
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtAuthFilter.validateToken(token)) {
                return ResponseEntity.ok("Token is valid");
            }
        }
        // Return error response if token is invalid or missing
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
    }
    // You may expose other methods similarly
    
    
    @PostMapping("/create-refresh-token")
    public RefreshToken createRefreshToken(@RequestParam String username) {
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);
        // Map RefreshToken to RefreshTokenDTO if needed
        return refreshToken;
    }

}
