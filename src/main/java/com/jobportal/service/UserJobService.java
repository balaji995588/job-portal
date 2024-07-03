package com.jobportal.service;

import org.springframework.data.domain.Page;

import com.jobportal.dto.JobApplyDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobUserResponseDto;
import com.jobportal.dto.UserJobResponseDto;

public interface UserJobService {

	JobDto applyForJob(Long userId, JobApplyDto jobApplyDto) throws Exception;

	void cancelJobApplication(Long userId, Long jobId);

	Page<UserJobResponseDto> getAllJobsByUser(Long userId, String pageNumber, String pageSize);

	Page<JobUserResponseDto> getAllUsersByJob(Long jobId, String pageNumber, String pageSize);

//	Page<IUserJobsListDto> getAllUserJob(String pageNumber, String pageSize);

	Page<JobUserResponseDto> getAllUserJob(String pageNumber, String pageSize);

	Page<JobUserResponseDto> getAddedJobs(Long userId, String pageNumber, String pageSize);
}
