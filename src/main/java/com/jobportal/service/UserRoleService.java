package com.jobportal.service;

import java.util.List;

import com.jobportal.dto.UserRoleDto;
import com.jobportal.ilistdto.IUserRoleListDto;

public interface UserRoleService {

	List<IUserRoleListDto> assignRoleToUser(UserRoleDto userRoleDto);

	List<IUserRoleListDto> updateUserRole(UserRoleDto userRoleDto, Long id);

	void deleteUserRole(Long id);

	List<IUserRoleListDto> getAllUserRoles();
}
