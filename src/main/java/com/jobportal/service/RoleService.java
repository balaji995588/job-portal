package com.jobportal.service;

import com.jobportal.dto.RoleDto;

public interface RoleService {

	RoleDto addRole(RoleDto roleDto);

	RoleDto updateRole(RoleDto roleDto);

	void deleteRole(Long id);

}
