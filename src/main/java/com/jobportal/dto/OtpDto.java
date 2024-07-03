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
public class OtpDto {

	@NotEmpty(message = "Email address is required")
	@NotBlank(message = "Email address is required")
	@NotNull(message = "Email address is required")
	@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
	private String email;

}
