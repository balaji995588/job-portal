package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.constants.Constants;
import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.JobApplyDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobUserResponseDto;
import com.jobportal.dto.ListResponseDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserJobResponseDto;
import com.jobportal.entities.UserEntity;
import com.jobportal.ilistdto.ForNesting;
import com.jobportal.page.PeginationResponse;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserJobRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.UserJobService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.GlobalFunctions;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/userjob")
public class UserJobController {

	@Autowired
	private UserJobService userJobService;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserJobRepository userJobRepository;

	@Autowired
	private UserRepository userRepository;

	@PreAuthorize("hasRole('ApplyForJob')")
	@PostMapping("/apply-for-job")
	public ResponseEntity<?> applyForJob(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@Valid @RequestBody JobApplyDto jobApplyDto) {
		try {
			JobDto jobDto = this.userJobService.applyForJob(userId, jobApplyDto);
			if (jobDto != null) {
				return new ResponseEntity<SuccessResponseDto>(
						new SuccessResponseDto(SuccessKeyConstants.USER_JOB_S031701,
								SuccessMessageConstants.APPLIED_FOR_JOB, jobDto),
						HttpStatus.CREATED);
			}
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, ErrorMessageConstants.SOMETHING_WENT_WRONG),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('CancelJobapplication')")
	@DeleteMapping("/delete-job-application/{jobId}")
	public ResponseEntity<?> cancelJobapplication(
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@PathVariable("jobId") Long jobId) {
		try {
			this.userJobService.cancelJobApplication(userId, jobId);
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.USER_JOB_S031703,
					SuccessMessageConstants.JOB_APPLICATION_DELETED, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('MyAppliedJobList')")
	@GetMapping("/my-applied-jobs")
	public ResponseEntity<?> getAllJobsByUser(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {

		try {
			Page<UserJobResponseDto> userJobs = this.userJobService.getAllJobsByUser(userId, pageNumber, pageSize);
			PeginationResponse peginationResponse = new PeginationResponse(userJobs.getNumber() + 1, userJobs.getSize(),
					userJobs.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_JOB_S031704,
					SuccessMessageConstants.USER_JOB_FETCHED, userJobs.getContent(), peginationResponse),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UserAppliedJobView')")
	@GetMapping("/applied-users")
	public ResponseEntity<?> getAllUsersByJob(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(value = "jobId", required = false) Long jobId,
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {

		try {
			if (jobId != null) {
				if (this.jobRepository.existsByIdAndCreatedBy(jobId, userId)) {
					Page<JobUserResponseDto> userJobs = this.userJobService.getAllUsersByJob(jobId, pageNumber,
							pageSize);
					PeginationResponse peginationResponse = new PeginationResponse(userJobs.getNumber() + 1,
							userJobs.getSize(), userJobs.getTotalElements());
					return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_JOB_S031704,
							SuccessMessageConstants.USER_JOB_FETCHED, userJobs.getContent(), peginationResponse),
							HttpStatus.OK);
				} else {

					return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(ErrorKeyConstants.JOB_E031401,
							"You have not added any job with id " + jobId), HttpStatus.BAD_REQUEST);
				}
			} else {
				Page<JobUserResponseDto> addedJobs = this.userJobService.getAddedJobs(userId, pageNumber, pageSize);
//				Page<JobUserResponseDto> addedJobs = this.userJobService.getAllUserJob1(pageNumber, pageSize);
				PeginationResponse peginationResponse = new PeginationResponse(addedJobs.getNumber() + 1,
						addedJobs.getSize(), addedJobs.getTotalElements());
				return new ResponseEntity<ListResponseDto>(
						new ListResponseDto(SuccessKeyConstants.USER_JOB_S031704,
								SuccessMessageConstants.USER_JOB_FETCHED, addedJobs.getContent(), peginationResponse),
						HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UserAppliedJobList')")
	@GetMapping("/all")
	public ResponseEntity<?> getUserJob(@RequestParam(value = "jobId", required = false) Long jobId,
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {

		try {
			if (jobId != null) {
				Page<JobUserResponseDto> userJobs = this.userJobService.getAllUsersByJob(jobId, pageNumber, pageSize);
				PeginationResponse peginationResponse = new PeginationResponse(userJobs.getNumber() + 1,
						userJobs.getSize(), userJobs.getTotalElements());
				return new ResponseEntity<ListResponseDto>(
						new ListResponseDto(SuccessKeyConstants.USER_JOB_S031704,
								SuccessMessageConstants.USER_JOB_FETCHED, userJobs.getContent(), peginationResponse),
						HttpStatus.OK);
			} else {
//				Page<IUserJobsListDto> userJobs = this.userJobService.getAllUserJob(pageNumber, pageSize);
				Page<JobUserResponseDto> userJobs = this.userJobService.getAllUserJob(pageNumber, pageSize);

				PeginationResponse peginationResponse = new PeginationResponse(userJobs.getNumber() + 1,
						userJobs.getSize(), userJobs.getTotalElements());

				return new ResponseEntity<ListResponseDto>(
						new ListResponseDto(SuccessKeyConstants.USER_JOB_S031704,
								SuccessMessageConstants.USER_JOB_FETCHED, userJobs.getContent(), peginationResponse),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/system")
	public ResponseEntity<?> userJobs(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			UserEntity user = this.userRepository.findById(3l).orElseThrow();
			List<ForNesting> userJobs = this.userJobRepository.findAllJobByUser(user, ForNesting.class);
//			System.err.println("NAME " + userJobs.get(0).getName());
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto("Success", "", userJobs),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
