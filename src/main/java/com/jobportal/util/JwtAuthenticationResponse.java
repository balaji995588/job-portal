package com.jobportal.util;

import java.util.List;

public class JwtAuthenticationResponse {

	private String accessToken;

	private String refreshToken;

	private List<String> permissions;

	public JwtAuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtAuthenticationResponse(String accessToken, String refreshToken, List<String> permissions) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.permissions = permissions;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}
