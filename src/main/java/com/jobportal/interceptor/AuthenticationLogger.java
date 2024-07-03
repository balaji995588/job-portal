package com.jobportal.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jobportal.configuration.JwtTokenUtil;
import com.jobportal.entities.UserEntity;
import com.jobportal.service.AuthService;
import com.jobportal.util.ApiUrls;
import com.jobportal.util.GlobalFunctions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationLogger implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authHeader = request.getHeader("Authorization");
		String token = (authHeader != null) ? authHeader.split("\\s")[1] : null;

		List<String> urlsWithoutHeader = new ArrayList<String>(Arrays.asList(ApiUrls.BYPASSEDURLS));

		final String requestUrl = request.getRequestURI();
		if (!urlsWithoutHeader.contains(requestUrl) && token != null) {
			final String email = this.jwtTokenUtil.getUsernameFromToken(token);
			UserEntity user = this.authService.userInformation(email);

			if (user != null) {
				List<String> permissions = this.authService.permissions(user.getId());
				request.setAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_PERMISSIONS, permissions);
				request.setAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID, user.getId());
			}
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);

	}
}
