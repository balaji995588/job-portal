package com.jobportal.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

	@Value("${spring.mail.host}")
	private String smtpHost;

	@Value("${spring.mail.port}")
	private int smtpPort;

	@Value("${spring.mail.username}")
	private String smtpUsername;

	@Value("${spring.mail.password}")
	private String smtpPassword;

//	@Autowired
//	private JobRepository jobRepository;

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(smtpHost.trim());
		mailSender.setPort(smtpPort);
		mailSender.setUsername(smtpUsername);
		mailSender.setPassword(smtpPassword);
//		String name = jobRepository.getClass().getName();
//		System.err.println("JOBREPO " + name);
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
