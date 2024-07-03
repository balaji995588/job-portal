package com.jobportal.configuration;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		if (response.getHeader("Error") != null && response.getHeader("Error").equals("JWT Expired")) {
			response.getOutputStream().println(
					"{\r\n" + "	\"message\": \"Jwt Expired\",\r\n" + "	\"msgKey\": \"Jwt Expired\"\r\n" + "}");
		} else {
			response.getOutputStream().println("{\r\n" + "	\"message\": \"Unauthorized user\",\r\n"
					+ "	\"msgKey\": \"Unauthorized\"\r\n" + "}");
		}
	}

}
