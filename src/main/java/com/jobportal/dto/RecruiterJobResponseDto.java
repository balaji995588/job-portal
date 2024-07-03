package com.jobportal.dto;

import java.util.List;

import com.jobportal.entities.JobDetails;
import com.jobportal.entities.RecruiterDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterJobResponseDto {

	private RecruiterDetails recruiterDetails;

	private List<JobDetails> jobDetails;
}
