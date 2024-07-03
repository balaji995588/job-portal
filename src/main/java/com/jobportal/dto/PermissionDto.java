package com.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

	private Long id;

	@NotEmpty(message = "Permission name is required")
	@NotNull(message = "Permission name is required")
	@NotBlank(message = "Permission name is required")
	private String permissionName;

	private String controller;

	private String url;

	private String method;

}
