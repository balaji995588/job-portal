package com.jobportal.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDto {

	private Long id;

	@NotNull(message = "Role id is required")
	private Long roleId;

	@NotNull(message = "Permission id list is required")
	@NotEmpty(message = "Permission id list is required")
	private List<Long> permissionId;
}
