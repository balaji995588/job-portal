package com.jobportal.serviceimpl;

import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl {
//implements UserRoleService {

//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;

//	@Override
//	public List<IUserRoleListDto> assignRoleToUser(UserRoleDto userRoleDto) {
//
//		if (!userRoleRepository.existsByUserIdAndRoleId(userRoleDto.getUserId(), userRoleDto.getRoleId())) {
//
//			UserEntity user = this.userRepository.findById(userRoleDto.getUserId())
//					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
//							ErrorMessageConstants.USER_NOT_FOUND));
//
//			RoleEntity role = this.roleRepository.findById(userRoleDto.getRoleId())
//					.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
//							ErrorMessageConstants.ROLE_NOT_FOUND));
//
//			UserRoleEntity userRole = new UserRoleEntity();
//
//			userRole.setUser(user);
//			userRole.setRole(role);
//			this.userRoleRepository.save(userRole);
//
//			List<IUserRoleListDto> userRoleById = this.userRoleRepository.getUserRoleById(userRole.getId(),
//					IUserRoleListDto.class);
//			return userRoleById;
//		}
//		return null;
//	}
//
//	@Override
//	public List<IUserRoleListDto> updateUserRole(UserRoleDto userRoleDto, Long id) {
//		UserRoleEntity userRole = this.userRoleRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_ROLE_E031501,
//						ErrorMessageConstants.USER_ROLE_NOT_FOUND));
//
//		UserEntity user = this.userRepository.findById(userRoleDto.getUserId())
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
//						ErrorMessageConstants.USER_NOT_FOUND));
//
//		RoleEntity role = this.roleRepository.findById(userRoleDto.getRoleId())
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
//						ErrorMessageConstants.ROLE_NOT_FOUND));
//
//		userRole.setUser(user);
//		userRole.setRole(role);
//		this.userRoleRepository.save(userRole);
//
//		List<IUserRoleListDto> userRoleById = this.userRoleRepository.getUserRoleById(userRole.getId(),
//				IUserRoleListDto.class);
//		return userRoleById;
//	}
//
//	@Override
//	public void deleteUserRole(Long id) {
//		UserRoleEntity userRole = this.userRoleRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_ROLE_E031501,
//						ErrorMessageConstants.USER_ROLE_NOT_FOUND));
//
//		this.userRoleRepository.delete(userRole);
//	}
//
//	@Override
//	public List<IUserRoleListDto> getAllUserRoles() {
//
//		List<IUserRoleListDto> rolesOfAllUsers = this.userRoleRepository.getRolesOfAllUsers(IUserRoleListDto.class);
//		return rolesOfAllUsers;
//	}
}
