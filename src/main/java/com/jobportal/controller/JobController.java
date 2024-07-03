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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.constants.Constants;
import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.ListResponseDto;
import com.jobportal.dto.RecruiterJobResponseDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.ilistdto.IJobListDto;
import com.jobportal.page.PeginationResponse;
import com.jobportal.service.JobService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.GlobalFunctions;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	private JobService jobService;

	@PreAuthorize("hasRole('AddJob')")
	@PostMapping("/add-job")
	public ResponseEntity<?> addJob(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestBody JobDto jobDto) {
		try {
			JobDto job = this.jobService.addJob(userId, jobDto);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.JOB_S031401, SuccessMessageConstants.JOB_ADDED, job),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UpdateJob')")
	@PutMapping("/update-job/{jobId}")
	public ResponseEntity<?> updateJob(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestBody JobDto jobDto, @PathVariable("jobId") Long jobId) {
		try {
			JobDto job = this.jobService.updateJob(userId, jobDto, jobId);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.JOB_S031402, SuccessMessageConstants.JOB_UPDATED, job),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ViewAddedJobs')")
	@GetMapping("/added-jobs")
	public ResponseEntity<?> getAddedJobs(@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {
		try {
			Page<IJobListDto> jobs = this.jobService.getAddedJobs(userId, pageNumber, pageSize);
			List<IJobListDto> content = jobs.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(jobs.getNumber() + 1, jobs.getSize(),
					jobs.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.JOB_S031404,
					SuccessMessageConstants.JOB_FETCHED, content, peginationResponse), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

//	@PreAuthorize("hasRole('JobList')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllJobs(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize,
			@RequestParam(value = "search", defaultValue = "", required = false) String search) {
		try {

			Page<IJobListDto> jobs;
			if (search != null && !search.isBlank() && !search.isEmpty()) {
				jobs = this.jobService.getJobsBySearch(pageNumber, pageSize, search);
			} else {
				jobs = this.jobService.getAllJobs(pageNumber, pageSize);
			}

			List<IJobListDto> content = jobs.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(jobs.getNumber() + 1, jobs.getSize(),
					jobs.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.JOB_S031404,
					SuccessMessageConstants.JOB_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('JobWithRecruiterList')")
	@GetMapping("/all-jobs")
	public ResponseEntity<?> getAllJobsWithRecruiter(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize,
			@RequestParam(value = "search", defaultValue = "", required = false) String search) {
		try {

			Page<RecruiterJobResponseDto> jobs = this.jobService.getAlljobsWithRecruiter(pageNumber, pageSize);
//			if (search != null && !search.isBlank() && !search.isEmpty()) {
//				jobs = this.jobService.getJobsBySearch(pageNumber, pageSize, search);
//			} else {
//				jobs = this.jobService.getAllJobs(pageNumber, pageSize);
//			}

			List<RecruiterJobResponseDto> content = jobs.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(jobs.getNumber() + 1, jobs.getSize(),
					jobs.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.JOB_S031404,
					SuccessMessageConstants.JOB_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('DeleteJob')")
	@DeleteMapping("/delete-job/{jobId}")
	public ResponseEntity<?> deleteJob(@PathVariable("jobId") Long jobId,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_PERMISSIONS) List<String> permissions) {
		try {
			this.jobService.DeleteJob(jobId, userId, permissions);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.JOB_S031403, SuccessMessageConstants.JOB_DELETED, null),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.JOB_E031401, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
