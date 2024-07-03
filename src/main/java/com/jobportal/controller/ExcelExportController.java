package com.jobportal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.service.ExcelExportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/excel")
public class ExcelExportController {

	@Autowired
	private ExcelExportService excelExportService;

	@PreAuthorize("hasRole('ExcelUsersList')")
	@GetMapping("/all-users")
	public void exportUsersToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment,filename=User_Information.xlsx";
		response.setHeader(headerkey, headerValue);
		excelExportService.exportUsersToExcel(response);
	}

	@PreAuthorize("hasRole('ExcelRecruitersList')")
	@GetMapping("/all-recruiters")
	public void exportRecruitersToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment,filename=Recruiter_Information.xlsx";
		response.setHeader(headerkey, headerValue);
		excelExportService.exportRecruitersToExcel(response);
	}

	@PreAuthorize("hasRole('ExcelUserJobList')")
	@GetMapping("/user-applied-jobs")
	public void exportUserAppliedJobList(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment,filename=Users And Aplied Jobs";
		response.setHeader(headerkey, headerValue);
		excelExportService.exportUserAppliedJobListToExcelFile(response);
	}
}
