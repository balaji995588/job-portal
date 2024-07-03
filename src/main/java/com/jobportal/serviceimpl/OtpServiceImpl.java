package com.jobportal.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.constants.Constants;
import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.OtpDto;
import com.jobportal.entities.OtpEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.OtpRepository;
import com.jobportal.service.AuthService;
import com.jobportal.service.EmailService;
import com.jobportal.service.OtpService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.OtpUtil;

import jakarta.mail.MessagingException;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private AuthService authService;

	@Override
	public String sendOtp(OtpDto otpDto) throws MessagingException {

		OtpEntity otp = new OtpEntity();
		otp = this.otpRepository.findByEmailIgnoreCase(otpDto.getEmail().trim());

		if (otp != null) {
			String otp1 = new String(OtpUtil.otp());
			otp.setOtp(otp1);
			otp.setGeneratedAt(LocalDateTime.now());
			otpRepository.save(otp);
			emailService.sendEmail(otpDto.getEmail(), Constants.LOGIN_OTP, OtpUtil.message(otp1));

			return otp1;

		} else {
			UserEntity user = null;
			OtpEntity otpEntity = new OtpEntity();
			user = this.authService.userInformation(otpDto.getEmail().trim());
			if (user != null) {
				otpEntity.setEmail(otpDto.getEmail());
				String otp1 = new String(OtpUtil.otp());
				otpEntity.setOtp(otp1);
				otpEntity.setGeneratedAt(LocalDateTime.now());
				otpRepository.save(otpEntity);
				emailService.sendEmail(otpDto.getEmail(), Constants.LOGIN_OTP, OtpUtil.message(otp1));
				return otp1;
			}

		}

		return null;
	}

	@Override
	public String passwordResetOtp(EmailOtpDto emailOtpDto) throws MessagingException {

		OtpEntity otp = new OtpEntity();
		otp = this.otpRepository.findByEmailIgnoreCase(emailOtpDto.getEmail().trim());

		if (otp != null) {
			String otp1 = new String(OtpUtil.otp());
			otp.setOtp(otp1);
			otp.setGeneratedAt(LocalDateTime.now());
			otpRepository.save(otp);
			emailService.sendEmail(emailOtpDto.getEmail(), Constants.LOGIN_OTP, OtpUtil.resetPasswordMessage(otp1));

			return otp1;

		} else {
			UserEntity user = null;
			OtpEntity otpEntity = new OtpEntity();
			user = this.authService.userInformation(emailOtpDto.getEmail().trim());
			if (user != null) {
				otpEntity.setEmail(emailOtpDto.getEmail());
				String otp1 = new String(OtpUtil.otp());
				otpEntity.setOtp(otp1);
				otpEntity.setGeneratedAt(LocalDateTime.now());
				otpRepository.save(otpEntity);
				emailService.sendEmail(emailOtpDto.getEmail(), Constants.LOGIN_OTP, OtpUtil.message(otp1));
				return otp1;
			}

		}

		return null;
	}

	@Override
	public boolean validateOtp(String email, String otp) {

		OtpEntity otpEntity = this.otpRepository.findByEmailIgnoreCase(email);

		if (otpEntity == null) {
			throw new ResourceNotFoundException(ErrorKeyConstants.OTP_E032201, ErrorMessageConstants.OTP_NOT_FOUND);
		}
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime otpExpirationTime = otpEntity.getGeneratedAt().plusMinutes(15); // OTP expiration time
		if (currentTime.isAfter(otpExpirationTime)) {
			// OTP has expired
			otpEntity.setOtp(""); // Clear the OTP
			this.otpRepository.save(otpEntity); // Delete the OTP entity from the database
			return false;
		}

		else if (otp.equalsIgnoreCase(otpEntity.getOtp())) {
			otpEntity.setOtp("");
			this.otpRepository.save(otpEntity);
			return true;

		}
		return false;
	}
}
