package com.jobportal.util;

public class ApiUrls {

	public static final String DOWNLOAD_FILE = "files/download-file";
	public static final String AUTH = "/auth";
	public static final String LOGIN = AUTH + "/login";
	public static final String REGISTER = AUTH + "/register";
	public static final String GENERATE_ACCESS_TOKEN = "/refreshToken";
	public static String[] BYPASSEDURLS = { "/gen/start", "/auth/register-user", "roles/add-role", "/auth/login",
			"/gen/allschemes", "/auth/refreshToken", "/auth/generateOtp", "/auth/validateOtp", "/auth/forgot-password",
			"/auth/confirm-password", "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**",
			"/webjars/**", "/api/swagger-ui/index.html" };
}
