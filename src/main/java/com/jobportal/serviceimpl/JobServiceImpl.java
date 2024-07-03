package com.jobportal.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.constants.Constants;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.RecruiterJobResponseDto;
import com.jobportal.entities.JobDetails;
import com.jobportal.entities.JobEntity;
import com.jobportal.entities.RecruiterDetails;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.ilistdto.IJobListDto;
import com.jobportal.ilistdto.IUserJobsListDto;
import com.jobportal.page.Pegination;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.JobService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Override
	public JobDto addJob(Long userId, JobDto jobDto) {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setTitle(jobDto.getTitle());
		jobEntity.setCreatedBy(userId);
		jobEntity.setDescription(jobDto.getDescription());
		this.jobRepository.save(jobEntity);
		jobDto.setId(jobEntity.getId());
		return jobDto;
	}

	@Override
	public JobDto updateJob(Long userId, JobDto jobDto, Long jobId) {
		JobEntity jobEntity = this.jobRepository.findById(jobId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.JOB_E031401,
						ErrorMessageConstants.JOB_NOT_FOUND));

		jobEntity.setTitle(jobDto.getTitle());
		jobEntity.setDescription(jobDto.getDescription());
		this.jobRepository.save(jobEntity);
		jobDto.setId(jobEntity.getId());
		return jobDto;

	}

	@Override
	public Page<IJobListDto> getAddedJobs(Long userId, String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IJobListDto> jobs = this.jobRepository.findByCreatedBy(userId, pagination, IJobListDto.class);
		return jobs;
	}

	@Override
	public Page<IJobListDto> getAllJobs(String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IJobListDto> jobs = this.jobRepository.findByOrderByIdDesc(pagination, IJobListDto.class);
		return jobs;
	}

	@Override
	public Page<RecruiterJobResponseDto> getAlljobsWithRecruiter(String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserJobsListDto> userJobs = this.jobRepository.getAllJobs(pagination, IUserJobsListDto.class);
//		Page<IUserJobsListDto> userJobs = this.userJobRepository.getAllUserJobs(pagination, IUserJobsListDto.class);
		if (userJobs == null || userJobs.getContent().isEmpty()) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_JOB_E031701, "Not any job is listed");
		}

		Map<Long, RecruiterJobResponseDto> userJobResponseMap = new LinkedHashMap<>(); // Map to store jobUserResponse
																						// by
																						// jobId

		for (IUserJobsListDto u : userJobs) {
			JobDetails jobDetails = new JobDetails();
			RecruiterDetails recruiterDetails = new RecruiterDetails();

			jobDetails.setJobId(u.getJobId());
			jobDetails.setJobTitle(u.getJobTitle());
			recruiterDetails.setId(u.getId());
			recruiterDetails.setName(u.getName());

			if (userJobResponseMap.containsKey(u.getId())) {

				RecruiterJobResponseDto userJobResponse = userJobResponseMap.get(u.getId());
				userJobResponse.getJobDetails().add(jobDetails);
			} else {

				RecruiterJobResponseDto userJobResponse = new RecruiterJobResponseDto();
				userJobResponse.setRecruiterDetails(recruiterDetails);
				userJobResponse.setJobDetails(new ArrayList<>(Collections.singletonList(jobDetails)));
				userJobResponseMap.put(u.getId(), userJobResponse);
			}
		}

		List<RecruiterJobResponseDto> userJobResponseList = new ArrayList<>(userJobResponseMap.values());

		return new PageImpl<>(userJobResponseList, pagination, userJobs.getTotalElements());
	}

	@Override
	public Page<IJobListDto> getJobsBySearch(String pageNumber, String pageSize, String search) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IJobListDto> jobs = this.jobRepository
				.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByIdDesc(search, search,
						pagination, IJobListDto.class);
		return jobs;
	}

	@Override
	public void DeleteJob(Long jobId, Long userId, List<String> permissions) {
		JobEntity job = this.jobRepository.findById(jobId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.JOB_E031401,
						ErrorMessageConstants.JOB_NOT_FOUND));
		if ((permissions.contains(Constants.IS_RECRUITER) && job.getCreatedBy().equals(userId))
				|| (permissions.contains(Constants.IS_ADMIN))) {
			this.jobRepository.delete(job);
		}
	}

}
