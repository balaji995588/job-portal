package com.jobportal.serviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.constants.CacheConstants;
import com.jobportal.dto.RolePermissionDto;
import com.jobportal.entities.PermissionEntity;
import com.jobportal.entities.RoleEntity;
import com.jobportal.entities.RolePermissionEntity;
import com.jobportal.entities.UserRoleEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.ilistdto.IRolePermissionListDto;
import com.jobportal.repository.PermissionRepository;
import com.jobportal.repository.RolePermissionRepository;
import com.jobportal.repository.RoleRepository;
import com.jobportal.repository.UserRoleRepository;
import com.jobportal.service.RolePermissionService;
import com.jobportal.util.CacheOperations;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
//	implements RolePermissionService
//	{

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private CacheOperations cacheOperations;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<IRolePermissionListDto> assignPermissionsToRole(RolePermissionDto rolePermissionDto) {

		RoleEntity role = this.roleRepository.findById(rolePermissionDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));
		List<RolePermissionEntity> list = new LinkedList<>();

		int i;
		for (i = 0; i < rolePermissionDto.getPermissionId().size(); i++) {
			Long id = rolePermissionDto.getPermissionId().get(i);
			PermissionEntity permission = this.permissionRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
							"Permission not found with id " + id));

			if (!rolePermissionRepository.existsByRoleAndPermission(role, permission)) {

				RolePermissionEntity rolePermission = new RolePermissionEntity();
				rolePermission.setRole(role);
				rolePermission.setPermission(permission);
				list.add(rolePermission);
			}
		}

		List<UserRoleEntity> userRole = this.userRoleRepository.findByRole(role);

		userRole.forEach(ur -> {

			String userId = ur.getId().toString();
			String key = CacheConstants.PERMISSIONS.concat(userId);
			cacheOperations.removeKeyFromCache(key);
		});
		boolean flag = true;
		if (list.size() == rolePermissionDto.getPermissionId().size()) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getPermission() == null) {
					flag = false;
				}
			}
			if (flag) {
				this.rolePermissionRepository.saveAll(list);
			}
//			this.rolePermissionRepository.save(rolePermission);
//			System.out.println(rolePermission.getRole().getId() + "\t" + rolePermission.getPermission().getId());

			List<IRolePermissionListDto> rolePermissionName = this.rolePermissionRepository
					.getAllPermissionsByRoleId(role.getId(), IRolePermissionListDto.class);

			return rolePermissionName;
		}
		return null;
	}

	@Override
	public List<IRolePermissionListDto> updatePermissionsOfRole(RolePermissionDto rolePermissionDto, Long id) {
		RoleEntity role = this.roleRepository.findById(rolePermissionDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));
		List<RolePermissionEntity> list = new LinkedList<>();

		int i;
		for (i = 0; i < rolePermissionDto.getPermissionId().size(); i++) {
			Long permissionId = rolePermissionDto.getPermissionId().get(i);
			PermissionEntity permission = this.permissionRepository.findById(permissionId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
							"Permission not found with id " + permissionId));

			if (!rolePermissionRepository.existsByRoleAndPermission(role, permission)) {

				RolePermissionEntity rolePermission = new RolePermissionEntity();
				rolePermission.setRole(role);
				rolePermission.setPermission(permission);
				list.add(rolePermission);
			}
		}

		List<RolePermissionEntity> rolePermissionList = this.rolePermissionRepository.findByRole(role);// List<UserRoleEntity>
																										// userRole =
																										// this.userRoleRepository.findUserIdsByRoleId(role.getId());

		for (i = 0; i < rolePermissionList.size(); i++) {
			if (!rolePermissionList.get(i).getPermission().getId().equals(rolePermissionDto.getPermissionId().get(i))) {
				this.rolePermissionRepository.delete(rolePermissionList.get(i));
			}
		}

		List<UserRoleEntity> users = this.userRoleRepository.findByRole(role);
		users.forEach(ur -> {

			String userId = ur.getId().toString();
			String key = CacheConstants.PERMISSIONS.concat(userId);
			cacheOperations.removeKeyFromCache(key);
		});
		boolean flag = true;
		if (list.size() == rolePermissionDto.getPermissionId().size()) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getPermission() == null) {
					flag = false;
				}
			}
			if (flag) {
				this.rolePermissionRepository.saveAll(list);
			}
//			this.rolePermissionRepository.save(rolePermission);
//			System.out.println(rolePermission.getRole().getId() + "\t" + rolePermission.getPermission().getId());

			List<IRolePermissionListDto> rolePermissionName = this.rolePermissionRepository
					.getAllPermissionsByRoleId(role.getId(), IRolePermissionListDto.class);

			return rolePermissionName;
		}
		return null;
	}

	@Override
	public List<String> getPermissionsOfUser(Long userId) {
		List<String> permissions = this.rolePermissionRepository.getAllPermissionsOfUsers(userId);
		return permissions;
	}

	@Override
	public List<IRolePermissionListDto> getAllPermissionsByRoleId(Long roleId) {

		RoleEntity role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));
		List<IRolePermissionListDto> rolePermissions = this.rolePermissionRepository
				.getAllPermissionsByRoleId(role.getId(), IRolePermissionListDto.class);
		return rolePermissions;
	}

//	public void deletePermissionOfRole(Long id) {
//		RolePermissionEntity rolePermission = this.rolePermissionRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_PERMISSION_E031601,
//						ErrorMessageConstants.ROLE_PERMISSION_NOT_FOUND));
//		this.rolePermissionRepository.delete(rolePermission);
//	}
//
//	@Override
//	public List<IRolePermissionListDto> getAllPermissionsOfRole() {
//		List<IRolePermissionListDto> rolePermissions = this.rolePermissionRepository
//				.getAllPermissionsOfRoles(IRolePermissionListDto.class);
//		System.out.println(rolePermissions);
//		return rolePermissions;
//	}
//

}
