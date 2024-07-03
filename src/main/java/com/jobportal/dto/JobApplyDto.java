package com.jobportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplyDto {

	@NotNull(message = "Job id is required to apply for the job")
//	@NotBlank(message = "Job id is required to apply for the job")
//	@NotEmpty(message = "Job id is required to apply for the job")
	private Long jobId;
}
