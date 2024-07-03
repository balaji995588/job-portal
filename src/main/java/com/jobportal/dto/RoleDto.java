package com.jobportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class RoleDto {

	private Long id;

	@NotEmpty(message = "Role name cannot be null")
	@NotNull(message = "Role name cannot be null")
	@NotBlank(message = "Role name cannot be null")
	@Min(value = 3, message = "Role name must contain at least 3 characters")
	@Max(value = 25, message = "Role name should not exceed 25 characters")
	@Pattern(regexp = "\\w+", message = "Special characters and spaces are not allowed in role name")
	private String roleName;
//
//	@NotEmpty(message = "Permission Id list must not be empty")
//	@Pattern(regexp = "\\d+", message = "Only digits are allowed in role list")
//	private List<Long> permissionIds;
}
