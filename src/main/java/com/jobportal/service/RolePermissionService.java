package com.jobportal.service;

import java.util.List;

import com.jobportal.dto.RolePermissionDto;
import com.jobportal.ilistdto.IRolePermissionListDto;

public interface RolePermissionService {

	List<IRolePermissionListDto> assignPermissionsToRole(RolePermissionDto rolePermissionDto);

	List<IRolePermissionListDto> updatePermissionsOfRole(RolePermissionDto rolePermissionDto, Long id);

	List<String> getPermissionsOfUser(Long userId);

	List<IRolePermissionListDto> getAllPermissionsByRoleId(Long roleId);
//	IRolePermissionListDto updatePermissionsOfRole(RolePermissionDto rolePermissionDto, Long id);
//
//	void deletePermissionOfRole(Long id);
//
//	List<IRolePermissionListDto> getAllPermissionsOfRole();
//
//	IRolePermissionListDto getAllPermissionsByRoleId(Long roleId);
}
