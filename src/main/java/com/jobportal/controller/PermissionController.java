package com.jobportal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.PermissionDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.ilistdto.IPermissionListDto;
import com.jobportal.service.PermissionService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.GlobalFunctions;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@PreAuthorize("hasRole('AddPermission')")
	@PostMapping("/add-permission")
	public ResponseEntity<?> addPermission(@Valid @RequestBody PermissionDto permissionDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			PermissionDto permission = this.permissionService.addPermission(permissionDto, userId);
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.PERMISSION_S031301,
					SuccessMessageConstants.PERMISSION_ADDED, permission), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UpdatePermission')")
	@PutMapping("/update-permission/{id}")
	public ResponseEntity<?> updatePermission(@Valid @RequestBody PermissionDto permissionDto,
			@PathVariable("id") Long id, @RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			PermissionDto permission = this.permissionService.updatePermission(permissionDto, id, userId);
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.PERMISSION_S031301,
					SuccessMessageConstants.PERMISSION_ADDED, permission), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('DeletePermission')")
	@DeleteMapping("/delete-permission/{id}")
	public ResponseEntity<?> deletePermission(@PathVariable("id") Long id) {
		try {
			this.permissionService.deletePermission(id);
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.PERMISSION_S031303,
					SuccessMessageConstants.PERMISSION_DELETED, null), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UploadPermission')")
	@PostMapping("/upload-permissions")
	public ResponseEntity<?> uploadPermissions(@RequestParam("file") MultipartFile file,

			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {
			if (GlobalFunctions.checkExcelFormat(file)) {
				Map<Object, Object> map = this.permissionService.uploadPermissions(file, userId);
				return new ResponseEntity<SuccessResponseDto>(
						new SuccessResponseDto(SuccessKeyConstants.PERMISSION_S031305,
								SuccessMessageConstants.PERMISSION_UPLOADED, map),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ErrorResponseDto>(
						new ErrorResponseDto("Invalid file format", "Please upload excel file"),
						HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

//	@PreAuthorize("hasRole('PermissionList')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllPermissions() {
		try {
			List<IPermissionListDto> permissions = this.permissionService.getAllPermissions();
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.PERMISSION_S031304,
					SuccessMessageConstants.PERMISSION_FETCHED, permissions), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, ErrorMessageConstants.SOMETHING_WENT_WRONG),
					HttpStatus.BAD_REQUEST);
		}
	}
}
