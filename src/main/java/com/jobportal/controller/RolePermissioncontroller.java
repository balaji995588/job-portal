package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.RolePermissionDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.ilistdto.IRolePermissionListDto;
import com.jobportal.service.RolePermissionService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rolepermission")
public class RolePermissioncontroller {

	@Autowired
	private RolePermissionService rolePermissionService;

//	@PreAuthorize("hasRole('AddRolePermission')")
	@PostMapping("/assign-permissions")
	public ResponseEntity<?> assignPermissionsToRole(@Valid @RequestBody RolePermissionDto rolePermissionDto) {

		try {
			List<IRolePermissionListDto> rolePermissions = this.rolePermissionService
					.assignPermissionsToRole(rolePermissionDto);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.ROLE_PERMISSION_S031601,
							SuccessMessageConstants.ROLE_PERMISSION_ADDED, rolePermissions),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);

		}
	}

//	@PreAuthorize("hasRole('UpdateRolePermission')")
	@PutMapping("/update-role-permissions/{id}")
	public ResponseEntity<?> updateRolePermission(@Valid @RequestBody RolePermissionDto rolePermissionDto,
			@PathVariable("id") Long id) {

		try {
			List<IRolePermissionListDto> rolePermissions = this.rolePermissionService
					.updatePermissionsOfRole(rolePermissionDto, id);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.ROLE_PERMISSION_S031601,
							SuccessMessageConstants.ROLE_PERMISSION_ADDED, rolePermissions),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('RolePermissionList')")
	@GetMapping("/role-permissions/{roleId}")
	public ResponseEntity<?> getPermissionsOfRole(@PathVariable Long roleId) {
		try {
			List<IRolePermissionListDto> permissions = this.rolePermissionService.getAllPermissionsByRoleId(roleId);
			return new ResponseEntity<SuccessResponseDto>(
					new SuccessResponseDto(SuccessKeyConstants.ROLE_PERMISSION_S031604,
							SuccessMessageConstants.ROLE_PERMISSION_FETCHED, permissions),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
