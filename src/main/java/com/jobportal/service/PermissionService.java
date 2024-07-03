package com.jobportal.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.PermissionDto;
import com.jobportal.ilistdto.IPermissionListDto;

public interface PermissionService {

	PermissionDto addPermission(PermissionDto permissionDto, Long userId);

	PermissionDto updatePermission(PermissionDto permissionDto, Long id, Long userId);

	void deletePermission(Long id);

	List<IPermissionListDto> getAllPermissions();

	Map<Object, Object> uploadPermissions(MultipartFile file, Long userId) throws Exception;
}
