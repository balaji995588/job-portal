package com.jobportal.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.ilistdto.IUserJobsListDto;
import com.jobportal.ilistdto.IUserRoleListDto;
import com.jobportal.repository.UserJobRepository;
import com.jobportal.repository.UserRoleRepository;
import com.jobportal.service.ExcelExportService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserJobRepository userJobRepository;

	@Override
	public List<IUserRoleListDto> exportUsersToExcel(HttpServletResponse response) throws IOException {

//		RoleEntity role = this.roleRepository.findByRoleName(Constants.USER);
		List<IUserRoleListDto> iListUsersRoleDto = this.userRoleRepository.getAllUsers(response,
				IUserRoleListDto.class);
		UserExportServiceImpl exportUtils = new UserExportServiceImpl(iListUsersRoleDto);
		exportUtils.exportDataToExcel(response);
		return iListUsersRoleDto;
	}

	@Override
	public List<IUserRoleListDto> exportRecruitersToExcel(HttpServletResponse response) throws IOException {

//		RoleEntity role = this.roleRepository.findByRoleName(Constants.RECRUITER);
		List<IUserRoleListDto> iListUsersRoleDto = this.userRoleRepository.getAllRecruiters(response,
				IUserRoleListDto.class);
		UserExportServiceImpl exportUtils = new UserExportServiceImpl(iListUsersRoleDto);
		exportUtils.exportDataToExcel(response);
		return iListUsersRoleDto;
	}

	@Override
	public List<IUserJobsListDto> exportUserAppliedJobListToExcelFile(HttpServletResponse response) throws IOException {
		List<IUserJobsListDto> iListUsersJobDto = this.userJobRepository.getAllUserJobsList(response,
				IUserJobsListDto.class);
		UserJobExportServiceImpl userJob = new UserJobExportServiceImpl(iListUsersJobDto);
		userJob.exportDataToExcel(response);
		return iListUsersJobDto;

	}
}
