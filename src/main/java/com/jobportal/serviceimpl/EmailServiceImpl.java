package com.jobportal.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jobportal.constants.Constants;
import com.jobportal.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

//	@Override
//	public void sendRegistrationSuccessEmail(String recipientAddress, String subject) throws MessagingException {
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, true);
//		helper.setFrom(Constants.EMAIL_SENDER);
//		helper.setTo(recipientAddress);
//		helper.setSubject(Constants.REGISTRATION_SUBJECT);
//		helper.setText(subject);
//
//		mailSender.send(message);
//	}

	@Override
	public void sendEmail(String recipientAddress, String subject, String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(Constants.EMAIL_SENDER);
		helper.setTo(recipientAddress);
		helper.setSubject(subject);
		helper.setText(body);

		mailSender.send(message);
	}

//	public void sendSimpleMessage(String emailTo, String subject, String text) throws MessagingException {
//
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//
//		helper.setFrom("noreplyntts@gmail.com");
//		helper.setTo(emailTo);
//		helper.setSubject(subject);
//		helper.setText(text, true);
//		mailSender.send(message);
//	}

}
