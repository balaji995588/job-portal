package com.jobportal.dto;

import com.jobportal.annotation.ValidEnum;
import com.jobportal.constants.Constants;
import com.jobportal.enums.GenderEnum;

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
public class AuthDto {

	private Long id;

	@NotEmpty(message = "Name is required")
	@NotBlank(message = "Name is required")
	@NotNull(message = "Name is required")
	private String name;

	@NotEmpty(message = "Email address is required")
	@NotBlank(message = "Email address is required")
	@NotNull(message = "Email address is required")
	@Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
	private String email;

	@NotEmpty(message = "Password is required")
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password is required")
	@Size(min = 8, max = 25, message = "Password must contain at least 8 characters")
	@Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must contain 1 uppercase 1 lowercase 1 numeric and 1 special character")
	private String password;

//	@NotNull(message = "Gender cannot be null")
	@ValidEnum(enumClass = GenderEnum.class, message = "Invalid gender")
	private GenderEnum gender;

	@NotBlank(message = "Phone Number is Required")
	@NotEmpty(message = "Phone Number is Required")
	@NotNull(message = "Phone Number is Required")
	@Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
	private String phoneNumber;

}
