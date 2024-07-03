package com.jobportal.service;

import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.OtpDto;

import jakarta.mail.MessagingException;

public interface OtpService {

	String sendOtp(OtpDto otpDto) throws MessagingException;

	boolean validateOtp(String email, String otp);

	String passwordResetOtp(EmailOtpDto emailOtpDto) throws MessagingException;
}
