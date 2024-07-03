package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.constants.Constants;
import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.ListResponseDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.ilistdto.IUserListDto;
import com.jobportal.ilistdto.IUserRoleListDto;
import com.jobportal.page.PeginationResponse;
import com.jobportal.service.UserService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.GlobalFunctions;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('UpdateUser')")
	@PutMapping("/update-user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long adminId) {
		try {
			UserResponseDto updatedUser = this.userService.updateUser(id, userDto, adminId);
			ResponseEntity<SuccessResponseDto> successResponse = new ResponseEntity<>(
					new SuccessResponseDto(SuccessKeyConstants.USER_S031102, SuccessMessageConstants.USER_ROLE_UPDATED,
							updatedUser),
					HttpStatus.OK);
			return successResponse;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<ErrorResponseDto> errorResponse = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031101, e.getMessage()), HttpStatus.BAD_REQUEST);
			return errorResponse;
		}
	}

	@PreAuthorize("hasRole('UserList')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize,
			@RequestParam(value = "search", defaultValue = "", required = false) String search) {
		try {
			Page<IUserRoleListDto> users;
			if (search != null && !search.isBlank() && !search.isEmpty()) {
				users = this.userService.getAllUsersBySerach(search, pageNumber, pageSize);
			} else {
				users = this.userService.getAllUsers(pageNumber, pageSize);
			}
			List<IUserRoleListDto> content = users.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(users.getNumber() + 1, users.getSize(),
					users.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_S031104,
					SuccessMessageConstants.USER_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('RecruiterList')")
	@GetMapping("/all-recruiters")
	public ResponseEntity<?> getAllRecruiters(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {
		try {
			Page<IUserRoleListDto> recruiters;

			recruiters = this.userService.getAllRecruiters(pageNumber, pageSize);

			List<IUserRoleListDto> content = recruiters.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(recruiters.getNumber() + 1,
					recruiters.getSize(), recruiters.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_S031104,
					SuccessMessageConstants.RECRUITER_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('AdminList')")
	@GetMapping("/all-admins")
	public ResponseEntity<?> getAllAdmins(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize) {
		try {
			Page<IUserRoleListDto> admin;

			admin = this.userService.getAllAdmin(pageNumber, pageSize);

			List<IUserRoleListDto> content = admin.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(admin.getNumber() + 1, admin.getSize(),
					admin.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_S031104,
					SuccessMessageConstants.ADMIN_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UserView')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(
			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize,
			@PathVariable("id") Long id) {
		try {
			Page<IUserListDto> users = this.userService.getUserById(id, pageNumber, pageSize);
			List<IUserListDto> content = users.getContent();
			PeginationResponse peginationResponse = new PeginationResponse(users.getNumber() + 1, users.getSize(),
					users.getTotalElements());
			return new ResponseEntity<ListResponseDto>(new ListResponseDto(SuccessKeyConstants.USER_S031104,
					SuccessMessageConstants.USER_FETCHED, content, peginationResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('DeleteUser')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			this.userService.deleteUser(id);
			return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.USER_S031103,
					SuccessMessageConstants.USER_DELETED, null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031101, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
//	@GetMapping()
//	public ResponseEntity<?> getUserByName(
//			@RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) String pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) String pageSize,
//			@RequestParam(value = "search") String search) {
//		try {
//			Page<IUserListDto> users = this.userService.getUserByName(search, pageNumber, pageSize);
//			List<IUserListDto> content = users.getContent();
//			PeginationResponse peginationResponse = new PeginationResponse(users.getNumber(), users.getSize(),
//					users.getTotalElements());
//			return new ResponseEntity<ListResponseDto>(new ListResponseDto(content, peginationResponse), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<ErrorResponseDto>(
//					new ErrorResponseDto(ErrorKeyConstants.GENERAL_E035001, e.getMessage()), HttpStatus.BAD_REQUEST);
//		}
//	}

}
