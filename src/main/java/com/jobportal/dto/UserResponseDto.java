package com.jobportal.dto;

import com.jobportal.enums.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponseDto {

	private Long id;

	private String name;

	private GenderEnum gender;

	private String phonenumber;

	private String email;

	private String roleName;
}
