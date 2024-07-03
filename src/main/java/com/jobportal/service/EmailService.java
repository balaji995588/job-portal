package com.jobportal.service;

import jakarta.mail.MessagingException;

public interface EmailService {

//	void sendRegistrationSuccessEmail(String recipientAddress, String subject) throws MessagingException;
//
//	void sendOtpEmail(String recipientAddress, String subject) throws MessagingException;

	void sendEmail(String recipientAddress, String subject, String body) throws MessagingException;
}
