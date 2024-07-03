package com.jobportal.dto;

import com.jobportal.constants.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidateDto {

	@NotEmpty(message = "Email address is required")
	@NotBlank(message = "Email address is required")
	@NotNull(message = "Email address is required")
	@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
	private String email;

	@NotEmpty(message = "Please enter the otp")
	@NotBlank(message = "Please enter the otp")
	@NotNull(message = "Please enter the otp")
	@Pattern(regexp = Constants.OTP_REGEX, message = "Invalid otp")
	private String otp;
}
