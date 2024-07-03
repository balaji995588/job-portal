package com.jobportal.dto;

import com.jobportal.constants.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDto {

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

	@NotEmpty(message = "Password is required")
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password is required")
	@Size(min = 8, max = 25, message = "Password must contain at least 8 characters")
	@Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must contain 1 uppercase 1 lowercase 1 numeric and 1 special character")
	private String password;

	@NotEmpty(message = "Password is required")
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password is required")
	@Size(min = 8, max = 25, message = "Password must contain at least 8 characters")
	@Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must contain 1 uppercase 1 lowercase 1 numeric and 1 special character")
	private String confirmPassword;
}
