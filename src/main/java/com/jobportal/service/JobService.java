package com.jobportal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jobportal.dto.JobDto;
import com.jobportal.dto.RecruiterJobResponseDto;
import com.jobportal.ilistdto.IJobListDto;

public interface JobService {

	JobDto addJob(Long userId, JobDto jobDto);

	JobDto updateJob(Long userId, JobDto dto, Long jobId);

	Page<IJobListDto> getAddedJobs(Long userId, String pageNumber, String pageSize);

	Page<IJobListDto> getAllJobs(String pageNumber, String pageSize);

//	JobDto getJobApplicants(Long userId);

	void DeleteJob(Long jobId, Long userId, List<String> permissions);

	Page<RecruiterJobResponseDto> getAlljobsWithRecruiter(String pageNumber, String pageSize);

	Page<IJobListDto> getJobsBySearch(String pageNumber, String pageSize, String search);

}
