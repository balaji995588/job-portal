package com.jobportal.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.constants.Constants;
import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.ForgotPasswordDto;
import com.jobportal.entities.PasswordOtpEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.PasswordOtpRepository;
import com.jobportal.service.AuthService;
import com.jobportal.service.EmailService;
import com.jobportal.service.PasswordOtpService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.OtpUtil;

import jakarta.mail.MessagingException;

@Service
public class PasswordOtpServiceImpl implements PasswordOtpService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordOtpRepository repository;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String passwordResetOtp(EmailOtpDto emailOtpDto) throws MessagingException {

		UserEntity user = null;

		user = this.authService.userInformation(emailOtpDto.getEmail().trim());
		if (user != null) {
			PasswordOtpEntity entity = this.repository.findByEmailIgnoreCase(emailOtpDto.getEmail().trim());
			if (entity != null) {
				repository.delete(entity);
			}
			PasswordOtpEntity otpEntity = new PasswordOtpEntity();
			otpEntity.setEmail(emailOtpDto.getEmail());
			String otp1 = new String(OtpUtil.otp());
			otpEntity.setOtp(otp1);
			otpEntity.setGeneratedAt(LocalDateTime.now());
			otpEntity.setExpireAt(LocalDateTime.now().plusMinutes(15));
			repository.save(otpEntity);
			emailService.sendEmail(emailOtpDto.getEmail(), Constants.PASSWORD_RESET_OTP, OtpUtil.message(otp1));
			return otp1;
		}

		return null;
	}

	@Override
	public boolean validateOtp(ForgotPasswordDto forgotPasswordDto) {

		PasswordOtpEntity otpEntity = this.repository.findByEmailIgnoreCase(forgotPasswordDto.getEmail());

		if (otpEntity == null) {
			throw new ResourceNotFoundException(ErrorKeyConstants.OTP_E032201, ErrorMessageConstants.OTP_NOT_FOUND);
		}
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime otpExpirationTime = otpEntity.getExpireAt(); // OTP expiration time

		UserEntity user = this.authService.userInformation(forgotPasswordDto.getEmail());

		if (user != null && otpEntity.getOtp().equals(forgotPasswordDto.getOtp())
				&& forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
			user.setPassword(this.passwordEncoder.encode(forgotPasswordDto.getPassword()));
			this.repository.delete(otpEntity);
			return true;
		}

		else if (currentTime.isAfter(otpExpirationTime)) {
			// OTP has expired
			this.repository.delete(otpEntity); // Delete the OTP entity from the database
			return false;
		}
//		UserEntity user = this.userInformation(forgotPasswordDto.getEmail());
//		System.out.println();
//		if (user != null && this.otpService.validateOtp(forgotPasswordDto.getEmail(), forgotPasswordDto.getOtp())
//				&& forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
//			System.err.println("IF Condition");        
//			user.setPassword(this.passwordEncoder.encode(forgotPasswordDto.getPassword()));
//			return true;
//		}
		else if (user == null) {

			throw new ResourceNotFoundException(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND);
		}

		else if (!otpEntity.getOtp().equals(forgotPasswordDto.getOtp())) {

			throw new ResourceNotFoundException(ErrorKeyConstants.OTP_E032202, ErrorMessageConstants.INVALID_OTP);
		}

		else if (!forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {

			throw new ResourceNotFoundException("Invalid password", ErrorMessageConstants.INVALID_PASSWORD);
		}

		return false;
	}
}
