package com.jobportal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

	private Long id;

	@NotEmpty(message = "job title is required")
	@NotBlank(message = "job title is required")
	@NotNull(message = "job title is required")
	@Min(value = 3, message = "Job title must be of minimum 3 characters")
	@Max(value = 100, message = "Job title should not exceed 100 characters")
	private String title;

	@NotEmpty(message = "Job description is required")
	@NotBlank(message = "Job description is required")
	@NotNull(message = "Job description is required")
	@Min(value = 10, message = "Job description must be of minimum 3 characters")
	@Max(value = 1000, message = "Job description should not exceed 1000 characters")
	private String description;

}
