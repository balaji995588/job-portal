package com.jobportal.dto;

import com.jobportal.constants.Constants;
import com.jobportal.enums.GenderEnum;

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
public class UserDto {

	private Long id;

	private Long roleId;

	@NotEmpty(message = "Name is required")
	@NotBlank(message = "Name is required")
	@NotNull(message = "Name is required")
	private String name;

	@NotEmpty(message = "Email address is required")
	@NotBlank(message = "Email address is required")
	@NotNull(message = "Email address is required")
	@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
	private String email;

	private GenderEnum gender;

	@NotBlank(message = "Phone Number is Required")
	@NotEmpty(message = "Phone Number is Required")
	@NotNull(message = "Phone Number is Required")
	@Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
	private String phoneNumber;

}
