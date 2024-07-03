package com.jobportal.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.constants.CacheConstants;
import com.jobportal.constants.Constants;
import com.jobportal.dto.UserDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.entities.RoleEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.entities.UserRoleEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.ilistdto.IUserListDto;
import com.jobportal.ilistdto.IUserRoleListDto;
import com.jobportal.page.Pegination;
import com.jobportal.repository.RoleRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.repository.UserRoleRepository;
import com.jobportal.service.UserService;
import com.jobportal.util.CacheOperations;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private CacheOperations cacheOperations;

	@Override
	public UserResponseDto updateUser(Long id, UserDto userDto, Long adminId) {
		UserEntity entity = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
						ErrorMessageConstants.USER_NOT_FOUND));
		entity.setEmail(userDto.getEmail());
		entity.setName(userDto.getName());
		entity.setGender(userDto.getGender());
		entity.setPhoneNumber(userDto.getPhoneNumber());
		entity.setUpdatedBy(adminId);
		RoleEntity role = this.roleRepository.findById(userDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201,
						ErrorMessageConstants.ROLE_NOT_FOUND));

		this.repository.save(entity);
		if (this.userRoleRepository.existsByUser(entity)) {
			UserRoleEntity user = this.userRoleRepository.findByUser(entity);
			user.setRole(role);
			this.userRoleRepository.save(user);
		} else {
			UserRoleEntity userRoleEntity = new UserRoleEntity();
			userRoleEntity.setUser(entity);
			userRoleEntity.setRole(role);
			this.userRoleRepository.save(userRoleEntity);
		}

		String userId = entity.getId().toString();
		String key = CacheConstants.PERMISSIONS.concat(userId);
		cacheOperations.removeKeyFromCache(key);
		cacheOperations.removeKeyFromCache(entity.getEmail());
		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(entity.getId());
		responseDto.setName(entity.getName());
		responseDto.setEmail(entity.getEmail());
		responseDto.setRoleName(role.getRoleName());
		responseDto.setGender(entity.getGender());
		responseDto.setPhonenumber(entity.getPhoneNumber());
		return responseDto;

	}

	@Override
	public void deleteUser(Long id) {
		UserEntity user = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101,
						ErrorMessageConstants.USER_NOT_FOUND));
		this.repository.delete(user);
	}

	@Override
	public Page<IUserListDto> getUserById(Long id, String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserListDto> users = this.repository.findById(id, pagination, IUserListDto.class);
		return users;
	}

//	@Override
//	public Page<IUserListDto> getUserByName(String name, String pageNumber, String pageSize) {
//		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
//		Page<IUserListDto> users = this.repository.findByNameContainingIgnoreCase(name, pagination, IUserListDto.class);
//		return users;
//	}

	@Override
	public Page<IUserRoleListDto> getAllUsersBySerach(String search, String pageNumber, String pageSize) {
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserRoleListDto> users = this.userRoleRepository.getAllUsersBySearch(search, pagination,
				IUserRoleListDto.class);
		return users;
	}

	@Override
	public Page<IUserRoleListDto> getAllUsers(String pageNumber, String pageSize) {
		RoleEntity role = this.roleRepository.findByRoleName(Constants.USER);
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserRoleListDto> users = this.userRoleRepository.getAllUsersByRole(role.getId(), pagination,
				IUserRoleListDto.class);

		return users;
	}

	@Override
	public Page<IUserRoleListDto> getAllRecruiters(String pageNumber, String pageSize) {
		RoleEntity role = this.roleRepository.findByRoleName(Constants.RECRUITER);
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserRoleListDto> users = this.userRoleRepository.getAllUsersByRole(role.getId(), pagination,
				IUserRoleListDto.class);

		return users;
	}

	@Override
	public Page<IUserRoleListDto> getAllAdmin(String pageNumber, String pageSize) {
		RoleEntity role = this.roleRepository.findByRoleName(Constants.ADMIN);
		Pageable pagination = Pegination.getPagination(pageNumber, pageSize);
		Page<IUserRoleListDto> users = this.userRoleRepository.getAllUsersByRole(role.getId(), pagination,
				IUserRoleListDto.class);

		return users;
	}

}
