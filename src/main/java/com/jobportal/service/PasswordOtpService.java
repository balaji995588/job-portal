package com.jobportal.service;

import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.ForgotPasswordDto;

import jakarta.mail.MessagingException;

public interface PasswordOtpService {

	String passwordResetOtp(EmailOtpDto emailOtpDto) throws MessagingException;

	boolean validateOtp(ForgotPasswordDto forgotPasswordDto);

}
