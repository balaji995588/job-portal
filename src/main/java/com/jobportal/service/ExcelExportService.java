package com.jobportal.service;

import java.io.IOException;
import java.util.List;

import com.jobportal.ilistdto.IUserJobsListDto;
import com.jobportal.ilistdto.IUserRoleListDto;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExportService {

	List<IUserRoleListDto> exportUsersToExcel(HttpServletResponse response) throws IOException;

	List<IUserRoleListDto> exportRecruitersToExcel(HttpServletResponse response) throws IOException;

	List<IUserJobsListDto> exportUserAppliedJobListToExcelFile(HttpServletResponse response) throws IOException;
}
