package com.jobportal.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jobportal.dto.AuthDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.entities.UserEntity;
import com.jobportal.util.JwtAuthenticationRequest;
import com.jobportal.util.JwtAuthenticationResponse;

public interface AuthService extends UserDetailsService {

	JwtAuthenticationResponse login(JwtAuthenticationRequest request);

	String refreshAndGetAuthenticationToken(String refreshToken) throws Exception;

	List<String> permissions(Long userId);

	UserEntity userInformation(String email);

	JwtAuthenticationResponse loginWithOtp(String email);

	UserResponseDto registerUser(AuthDto AuthDto);

	UserResponseDto registerRecruiter(AuthDto authDto, Long adminId);

//	boolean confirmPassword(ForgotPasswordDto forgotPasswordDto);

}
