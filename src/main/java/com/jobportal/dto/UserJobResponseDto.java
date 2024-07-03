package com.jobportal.dto;

import java.util.List;

import com.jobportal.entities.JobDetails;
import com.jobportal.entities.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJobResponseDto {

	private UserDetails userDetails;

	private List<JobDetails> jobDetails;
}
