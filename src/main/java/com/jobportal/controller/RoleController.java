package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.RoleDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.service.RoleService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

//	@PreAuthorize("hasRole(AddRole)")
	@PostMapping("/add-role")
	public ResponseEntity<?> addRole(@RequestBody RoleDto dto) {

		try {
			RoleDto addedRole = this.roleService.addRole(dto);

			ResponseEntity<SuccessResponseDto> successResponse = new ResponseEntity<>(
					new SuccessResponseDto(SuccessKeyConstants.ROLE_S031201, SuccessMessageConstants.ROLE_ADDED,
							addedRole),
					HttpStatus.CREATED);

			return successResponse;
		} catch (Exception e) {
			ResponseEntity<ErrorResponseDto> errorResponse = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.ROLE_E031201, e.getMessage()), HttpStatus.BAD_REQUEST);
			return errorResponse;
		}
	}

	@PreAuthorize("hasRole(UpdateRole)")
	@PutMapping("/update-role")
	public ResponseEntity<?> updateRole(@RequestBody RoleDto dto) {

		try {
			RoleDto updatedRole = this.roleService.addRole(dto);

			ResponseEntity<SuccessResponseDto> successResponse = new ResponseEntity<>(
					new SuccessResponseDto(SuccessKeyConstants.ROLE_S031202, SuccessMessageConstants.ROLE_UPDATED,
							updatedRole),
					HttpStatus.CREATED);

			return successResponse;
		} catch (Exception e) {
			ResponseEntity<ErrorResponseDto> errorResponse = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.ROLE_E031201, e.getMessage()), HttpStatus.BAD_REQUEST);
			return errorResponse;
		}
	}

}
