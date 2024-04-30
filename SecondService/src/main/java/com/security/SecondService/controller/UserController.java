package com.security.SecondService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.security.SecondService.dto.AuthRequest;
import com.security.SecondService.dto.JwtResponse;
import com.security.SecondService.dto.RefreshTokenRequest;
import com.security.SecondService.entity.RefreshToken;
import com.security.SecondService.entity.UserInfo;
import com.security.SecondService.service.RefreshTokenService;
import com.security.SecondService.service.TokenHandler;
import com.security.SecondService.service.UserService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    private WebClient webClient;

    // Constructor to initialize WebClient
    public UserController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }
    
    
    @GetMapping("/message")
    public String getMessage(@RequestHeader("Authorization") String authHeader) throws Exception {
        // Make the HTTP request to the /checkToken endpoint with the token
    	String token = authHeader.substring(7);
//        String encryptedToken = TokenHandler.encrypt(authHeader.substring(7));

    	System.out.println("into getM");
        String result = webClient.get()
                .uri("/token/checkToken")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Return the response from the /checkToken endpoint
        return "Working";
    }
//    @GetMapping("/message")
//    public String getMessage(@RequestHeader("Authorization") String authHeader,@RequestBody RefreshTokenRequest request) throws Exception {
//        String token = authHeader.substring(7);
//        System.out.println("Into getMessage");
//
//        try {
//            String result = webClient.get()
//                    .uri("/token/checkToken")
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//            // Return the response from the /checkToken endpoint
//            return "Working";
//        } catch (WebClientResponseException.Forbidden ex) {
//            // Token has expired, so refresh it
//        	System.out.println(request.getToken());
//            RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
//            refreshTokenRequest.setToken(request.getToken());
//            System.out.print("Entered in catch");
//            System.out.println(refreshTokenRequest.getToken());
//            JwtResponse jwtResponse = webClient.post()
//                    .uri("/products/refreshToken")
//                    .body(BodyInserters.fromValue(refreshTokenRequest))
//                    .retrieve()
//                    .bodyToMono(JwtResponse.class)
//                    .block();
//
//            String accessToken = jwtResponse.getAccessToken();
//           
//            // Now, you can use the new access token to retry the original request
//            // Example: Call getMessage again with the new access token
//            // Be cautious of infinite loops or excessive token refreshes
//            // return getMessage("Bearer " + jwtResponse.getAccessToken());
//            // For now, let's just return a message indicating success
//            return getMessage("Bearer " + accessToken, request);
//        }
//    }
    
    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtResponse jwtResponse = new JwtResponse();

        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
//                    String accessToken = jwtService.generateToken(userInfo.getName());
                    String accessToken = webClient.post()
    	                    .uri("/token/generateToken")
    	                    .body(BodyInserters.fromValue(userInfo.getName()))
    	                    .retrieve()
    	                    .bodyToMono(String.class)
    	                    .block();
                    jwtResponse.setAccessToken(accessToken);
                    jwtResponse.setToken(refreshTokenRequest.getToken());
                    return jwtResponse; // Return JwtResponse here
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

	
	 @PostMapping("/new")
	    public String addNewUser(@RequestBody UserInfo userInfo){
	        return service.addUser(userInfo);
	    }

	 @PostMapping("/login")
	    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//	        if (authentication.isAuthenticated()) {
		 System.out.println("Entered In Call");
		 		if (service.validateLogin(authRequest))
		 		{
		 		System.out.println(authRequest);
	        	System.out.println("ENtered IN IF St");
//	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
	            // Call the /refresh-token endpoint to create a refresh token
	            RefreshToken refreshToken = webClient.post()
	                    .uri("/token/create-refresh-token?username={username}", authRequest.getUsername())
	                    .retrieve()
	                    .bodyToMono(RefreshToken.class)
	                    .block();
	            JwtResponse jwtResponse = new JwtResponse();
	            String token = webClient.post()
	                    .uri("/token/generateToken")
	                    .body(BodyInserters.fromValue(authRequest.getUsername()))
	                    .retrieve()
	                    .bodyToMono(String.class)
	                    .block();

//	            jwtResponse.setAccessToken(jwtService.generateToken(authRequest.getUsername()));
	            jwtResponse.setAccessToken(token);
	            jwtResponse.setToken(refreshToken.getToken());
//	            return JwtResponse.builder()
//	                    .accessToken(jwtService.generateToken(aut hRequest.getUsername()))
//	                    .token(refreshToken.getToken()).build();
	            return jwtResponse;
	        } else {
	            throw new UsernameNotFoundException("invalid user request !");
	        }
	    }
	 
	 public Mono<Boolean> checkToken(String token) {
	        return webClient
	                .get()
	                .uri("/token/checkToken")
	                .header("Authorization", "Bearer " + token)
	                .retrieve()
	                .bodyToMono(Boolean.class);
	    }
}
