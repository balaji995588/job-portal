package com.jobportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

	@NotNull(message = "User id is required")
	private Long userId;

	@NotNull(message = "Role id is required")
	private Long roleId;
}
