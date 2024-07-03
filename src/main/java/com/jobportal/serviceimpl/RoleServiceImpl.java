package com.jobportal.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.RoleDto;
import com.jobportal.entities.RoleEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.RoleRepository;
import com.jobportal.service.RoleService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

//	@Autowired
//	private RolePermissionRepository rolePermissionRepository;
//
//	@Autowired
//	private PermissionRepository permissionRepository;

	@Override
	public RoleDto addRole(RoleDto roleDto) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleName(roleDto.getRoleName());

		this.repository.save(roleEntity);
//		for (Long id : roleDto.getPermissionIds()) {
//			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
//			rolePermissionEntity.setRole(roleEntity);
//			PermissionEntity permission = this.permissionRepository.findById(id)
//					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
//							"Permission not found with id " + id));
//			rolePermissionEntity.setPermission(permission);
//			rolePermissionRepository.save(rolePermissionEntity);
//		}
		roleDto.setId(roleEntity.getId());
		return roleDto;
	}

	@Override
	public RoleDto updateRole(RoleDto roleDto) {
		RoleEntity roleEntity = this.repository.findById(roleDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));
		roleEntity.setRoleName(roleDto.getRoleName());
		this.repository.save(roleEntity);
//		for (Long id : roleDto.getPermissionIds()) {
//			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
//			rolePermissionEntity.setRole(roleEntity);
//			PermissionEntity permission = this.permissionRepository.findById(id)
//					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
//							"Permission not found with id " + id));
//			rolePermissionEntity.setPermission(permission);
//			rolePermissionRepository.save(rolePermissionEntity);
//		}
		roleDto.setId(roleEntity.getId());
		return roleDto;

	}

	@Override
	public void deleteRole(Long id) {
		RoleEntity roleEntity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));
		this.repository.delete(roleEntity);
	}

}
