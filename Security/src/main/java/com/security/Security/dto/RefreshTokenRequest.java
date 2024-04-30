package com.security.Security.dto;

public class RefreshTokenRequest {


    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshTokenRequest(String token) {
		super();
		this.token = token;
	}

	public RefreshTokenRequest() {
		super();
	}
    
	
}
