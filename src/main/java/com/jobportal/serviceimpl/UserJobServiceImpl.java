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

import com.jobportal.dto.JobApplyDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobUserResponseDto;
import com.jobportal.dto.UserJobResponseDto;
import com.jobportal.entities.JobDetails;
import com.jobportal.entities.JobEntity;
import com.jobportal.entities.UserDetails;
import com.jobportal.entities.UserEntity;
import com.jobportal.entities.UserJobEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.ilistdto.IUserJobsListDto;
import com.jobportal.page.Pegination;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserJobRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.EmailService;
import com.jobportal.service.UserJobService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class UserJobServiceImpl implements UserJobService {

	@Autowired
	private UserJobRepository userJobRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Override
	public JobDto applyForJob(Long userId, JobApplyDto jobApplyDto) throws Exception {
		JobEntity job = this.jobRepository.findById(jobApplyDto.getJobId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.JOB_E031401,
						ErrorMessageConstants.JOB_NOT_FOUND));

		UserEntity user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
						ErrorMessageConstants.USER_NOT_FOUND));
		JobDto jobDto = new JobDto();
		if (!this.userJobRepository.existsByJobAndUser(job, user)) {
			UserJobEntity userJobEntity = new UserJobEntity();
			userJobEntity.setUser(user);
			userJobEntity.setJob(job);
			try {
				emailService.sendEmail(user.getEmail(), "Job application successfull",
						"Dear User, \\nYou have successfully applied for the " + job.getTitle() + " job.");
				UserEntity recruiter = this.userRepository.findById(job.getCreatedBy())
						.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
								ErrorMessageConstants.USER_NOT_FOUND));

				emailService.sendEmail(recruiter.getEmail(), "Job application received", "Dear Recruiter,\\n"
						+ user.getName() + " has applied for the " + job.getTitle() + " job that you have posted");
			} catch (Exception e) {
				return null;
			}
			this.userJobRepository.save(userJobEntity);
			jobDto.setTitle(job.getTitle());
			jobDto.setDescription(job.getDescription());
			jobDto.setId(job.getId());
			return jobDto;
		} else {
			throw new Exception("You have already applied for this job");
		}
	}

	@Override
	public void cancelJobApplication(Long userId, Long jobId) {
		JobEntity job = this.jobRepository.findById(jobId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.JOB_E031401,
						ErrorMessageConstants.JOB_NOT_FOUND));

		UserEntity user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
						ErrorMessageConstants.USER_NOT_FOUND));

		UserJobEntity userJob = this.userJobRepository.findByJobAndUser(job, user);

		this.userJobRepository.delete(userJob);
	}

	@Override
	public Page<UserJobResponseDto> getAllJobsByUser(Long userId, String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserJobsListDto> userJobs = this.userJobRepository.getAllJobsByUser(userId, pagination,
				IUserJobsListDto.class);

		if (userJobs.getContent().isEmpty()) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_E031101, "You have not applied for any job");
		}
		UserDetails details = new UserDetails();
		List<JobDetails> jobList = new ArrayList<>();

		userJobs.forEach(u -> {
			details.setId(u.getId());
			details.setName(u.getName());
			details.setEmail(u.getEmail());
			JobDetails jobDetails = new JobDetails();
			jobDetails.setJobId(u.getJobId());
			jobDetails.setJobTitle(u.getJobTitle());
			jobList.add(jobDetails);
		});
		UserJobResponseDto userJobResponse = new UserJobResponseDto(details, jobList);

		return new PageImpl<>(Collections.singletonList(userJobResponse), pagination, userJobs.getTotalElements());
	}

	@Override
	public Page<JobUserResponseDto> getAllUsersByJob(Long jobId, String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		JobEntity job = this.jobRepository.findById(jobId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.JOB_E031401,
						ErrorMessageConstants.JOB_NOT_FOUND));
		Page<IUserJobsListDto> userJobs = this.userJobRepository.getAllUsersByJob(job.getId(), pagination,
				IUserJobsListDto.class);
		if (userJobs == null || userJobs.getContent().isEmpty()) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_JOB_E031701,
					"Not any user applied to job " + job.getTitle());
		}
		List<UserDetails> userList = new ArrayList<>();
		JobDetails jobDetails = new JobDetails();

		userJobs.forEach(u -> {
			UserDetails user = new UserDetails();
			user.setId(u.getId());
			user.setName(u.getName());
			user.setEmail(u.getEmail());

			jobDetails.setJobId(u.getJobId());
			jobDetails.setJobTitle(u.getJobTitle());
			userList.add(user);
		});

		JobUserResponseDto jobUserResponse = new JobUserResponseDto(jobDetails, userList);

		return new PageImpl<>(Collections.singletonList(jobUserResponse), pagination, userJobs.getTotalElements());
	}

	@Override
	public Page<JobUserResponseDto> getAddedJobs(Long userId, String pageNumber, String pageSize) {

		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserJobsListDto> allCreatedJobs = this.userJobRepository.getAllCreatedJobs(userId, pagination,
				IUserJobsListDto.class);

		if (allCreatedJobs == null || allCreatedJobs.getContent().isEmpty()) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_JOB_E031701,
					"Not any user applied to any of the listed job");
		}

		Map<Long, JobUserResponseDto> jobUserResponseMap = new LinkedHashMap<>(); // Map to store jobUserResponse by
																					// jobId

		for (IUserJobsListDto u : allCreatedJobs) {
			JobDetails jobDetails = new JobDetails();
			UserDetails userDetails = new UserDetails();

			jobDetails.setJobId(u.getJobId());
			jobDetails.setJobTitle(u.getJobTitle());
			userDetails.setId(u.getId());
			userDetails.setName(u.getName());
			userDetails.setEmail(u.getEmail());

			if (jobUserResponseMap.containsKey(u.getJobId())) {
				// If jobUserResponse already exists for the jobId, add userDetails to the
				// existing jobUserResponse
				JobUserResponseDto jobUserResponse = jobUserResponseMap.get(u.getJobId());
				jobUserResponse.getUserDetails().add(userDetails);
			} else {
				// If jobUserResponse does not exist for the jobId, create a new jobUserResponse
				JobUserResponseDto jobUserResponse = new JobUserResponseDto();
				jobUserResponse.setJobDetails(jobDetails);
				jobUserResponse.setUserDetails(new ArrayList<>(Collections.singletonList(userDetails)));
				jobUserResponseMap.put(u.getJobId(), jobUserResponse);
			}
		}

		List<JobUserResponseDto> jobUserResponseList = new ArrayList<>(jobUserResponseMap.values());

		return new PageImpl<>(jobUserResponseList, pagination, allCreatedJobs.getTotalElements());

	}

	@Override
	public Page<JobUserResponseDto> getAllUserJob(String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserJobsListDto> userJobs = this.userJobRepository.getAllUserJobs(pagination, IUserJobsListDto.class);
		if (userJobs == null || userJobs.getContent().isEmpty()) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_JOB_E031701,
					"Not any user applied to any of the listed job");
		}

		Map<Long, JobUserResponseDto> jobUserResponseMap = new LinkedHashMap<>(); // Map to store jobUserResponse by
																					// jobId

		for (IUserJobsListDto u : userJobs) {
			JobDetails jobDetails = new JobDetails();
			UserDetails userDetails = new UserDetails();

			jobDetails.setJobId(u.getJobId());
			jobDetails.setJobTitle(u.getJobTitle());
			userDetails.setId(u.getId());
			userDetails.setName(u.getName());
			userDetails.setEmail(u.getEmail());

			if (jobUserResponseMap.containsKey(u.getJobId())) {
				// If jobUserResponse already exists for the jobId, add userDetails to the
				// existing jobUserResponse
				JobUserResponseDto jobUserResponse = jobUserResponseMap.get(u.getJobId());
				jobUserResponse.getUserDetails().add(userDetails);
			} else {
				// If jobUserResponse does not exist for the jobId, create a new jobUserResponse
				JobUserResponseDto jobUserResponse = new JobUserResponseDto();
				jobUserResponse.setJobDetails(jobDetails);
				jobUserResponse.setUserDetails(new ArrayList<>(Collections.singletonList(userDetails)));
				jobUserResponseMap.put(u.getJobId(), jobUserResponse);
			}
		}

		List<JobUserResponseDto> jobUserResponseList = new ArrayList<>(jobUserResponseMap.values());

		return new PageImpl<>(jobUserResponseList, pagination, userJobs.getTotalElements());
	}

	public void getAllJobs() {

	}

}
