package com.jobportal.service;

import org.springframework.data.domain.Page;

import com.jobportal.dto.UserDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.ilistdto.IUserListDto;
import com.jobportal.ilistdto.IUserRoleListDto;

public interface UserService {

	Page<IUserRoleListDto> getAllUsersBySerach(String search, String pageNumber, String pageSize);

	Page<IUserRoleListDto> getAllUsers(String pageNumber, String pageSize);

	Page<IUserRoleListDto> getAllRecruiters(String pageNumber, String pageSize);

	Page<IUserRoleListDto> getAllAdmin(String pageNumber, String pageSize);

	Page<IUserListDto> getUserById(Long id, String pageNumber, String pageSize);

//	Page<IUserListDto> getUserByName(String name, String pageNumber, String pageSize);

	void deleteUser(Long id);

	UserResponseDto updateUser(Long id, UserDto userDto, Long adminId);

}
