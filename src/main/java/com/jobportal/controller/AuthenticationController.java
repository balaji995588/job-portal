package com.jobportal.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.constants.Constants;
import com.jobportal.dto.AuthDto;
import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.FileResponseDto;
import com.jobportal.dto.ForgotPasswordDto;
import com.jobportal.dto.OtpDto;
import com.jobportal.dto.OtpValidateDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.AuthService;
import com.jobportal.service.BulkUploadService;
import com.jobportal.service.OtpService;
import com.jobportal.service.PasswordOtpService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.GlobalFunctions;
import com.jobportal.util.JwtAuthenticationRequest;
import com.jobportal.util.JwtAuthenticationResponse;
import com.jobportal.util.RefreshTokenResponse;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private PasswordOtpService passwordOtpService;

	@Autowired
	private BulkUploadService bulkUploadService;

//	private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping("/register-user")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AuthDto authDto) {
		try {
			if (userRepository.existsByEmail(authDto.getEmail())) {

				return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031102,
						ErrorMessageConstants.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
			}

			if (authDto.getEmail().matches(Constants.EMAIL_REGEX)
					&& authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

				UserResponseDto registerdUser = this.authService.registerUser(authDto);
				ResponseEntity<SuccessResponseDto> responseEntity = new ResponseEntity<>(
						new SuccessResponseDto(SuccessKeyConstants.USER_S031101,
								SuccessMessageConstants.USER_REGISTERED, registerdUser),
						HttpStatus.OK);

				return responseEntity;
			}

			else if (!authDto.getEmail().matches(Constants.EMAIL_REGEX)) {

				ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031103,
								ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
						HttpStatus.BAD_REQUEST);
				// log.debug(responseEntity);
				return responseEntity;
			}

			else if (!authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

				ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031104,
								ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
						HttpStatus.BAD_REQUEST);
				// log.debug(responseEntity);
				return responseEntity;
			}

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
					HttpStatus.BAD_REQUEST);

			return responseEntity;
		} catch (Exception e) {

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105, e.getMessage()), HttpStatus.BAD_REQUEST);

			return responseEntity;
		}
	}

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	@PostMapping("/register-user-async")
	public CompletableFuture<ResponseEntity<?>> registerUserAsync(@Valid @RequestBody AuthDto authDto) {
		try {
			// CompletableFuture to represent the result of the API call asynchronously.
			return CompletableFuture.supplyAsync(() -> {
				if (userRepository.existsByEmail(authDto.getEmail())) {
					return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031102,
							ErrorMessageConstants.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
				}

				if (authDto.getEmail().matches(Constants.EMAIL_REGEX)
						&& authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

					UserResponseDto registeredUser = this.authService.registerUser(authDto);
					return new ResponseEntity<>(new SuccessResponseDto(SuccessKeyConstants.USER_S031101,
							SuccessMessageConstants.USER_REGISTERED, registeredUser), HttpStatus.OK);
				} else if (!authDto.getEmail().matches(Constants.EMAIL_REGEX)) {
					return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031103,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD), HttpStatus.BAD_REQUEST);
				} else if (!authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {
					return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031104,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD), HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031105,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD), HttpStatus.BAD_REQUEST);
				}
			}, executorService);
		} catch (Exception e) {
			return CompletableFuture.completedFuture(new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105, e.getMessage()), HttpStatus.BAD_REQUEST));
		}
	}

	@PreAuthorize("hasRole('AddRecruiter')")
	@PostMapping("/register-recruiter")
	public ResponseEntity<?> registerRecruiter(@Valid @RequestBody AuthDto authDto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long adminId) {
		try {
			if (userRepository.existsByEmail(authDto.getEmail())) {

				return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031102,
						ErrorMessageConstants.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
			}

			if (authDto.getEmail().matches(Constants.EMAIL_REGEX)
					&& authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

				UserResponseDto registerdUser = this.authService.registerRecruiter(authDto, adminId);
				ResponseEntity<SuccessResponseDto> responseEntity = new ResponseEntity<>(
						new SuccessResponseDto(SuccessKeyConstants.USER_S031101,
								SuccessMessageConstants.USER_REGISTERED, registerdUser),
						HttpStatus.OK);

				return responseEntity;
			}

			else if (!authDto.getEmail().matches(Constants.EMAIL_REGEX)) {

				ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031103,
								ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
						HttpStatus.BAD_REQUEST);
				// log.debug(responseEntity);
				return responseEntity;
			}

			else if (!authDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

				ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031104,
								ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
						HttpStatus.BAD_REQUEST);
				// log.debug(responseEntity);
				return responseEntity;
			}

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
					HttpStatus.BAD_REQUEST);

			return responseEntity;
		} catch (Exception e) {

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105, e.getMessage()), HttpStatus.BAD_REQUEST);

			return responseEntity;
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> createToken(@Valid @RequestBody JwtAuthenticationRequest request) {
		try {

			JwtAuthenticationResponse response = this.authService.login(request);

			if (response.getAccessToken() != null) {
				SuccessResponseDto responseDto = new SuccessResponseDto();
				responseDto.setKey(SuccessKeyConstants.USER_S031107);
				responseDto.setMessage(SuccessMessageConstants.LOGIN_SUCCESSFUL);
				responseDto.setData(response);

				ResponseEntity<SuccessResponseDto> responseEntity = new ResponseEntity<SuccessResponseDto>(responseDto,
						HttpStatus.OK);

				return responseEntity;
			}
			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105,
							ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD),
					HttpStatus.BAD_REQUEST);

			return responseEntity;
		} catch (Exception e) {

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031105, e.getMessage()), HttpStatus.BAD_REQUEST);

			return responseEntity;
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			request.getCookies();
			response.addCookie(null);

			if (session != null) {
//				session.invalidate();
				session.removeAttribute("Authorization");
				session.setAttribute("Authorization", null);
			}
			return ResponseEntity.ok("Logout Successfull");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error in logout", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> generateAccessTokenFromRefreshToken(@RequestParam(defaultValue = "") String refreshToken) {

		try {
			String accessToken = this.authService.refreshAndGetAuthenticationToken(refreshToken);
			RefreshTokenResponse tokenResponse = new RefreshTokenResponse(accessToken);
			if (accessToken != null) {
				return new ResponseEntity<>(new SuccessResponseDto(SuccessKeyConstants.TOKEN_S031801,
						SuccessMessageConstants.TOKEN_GENERATED, tokenResponse), HttpStatus.OK);
			}

			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.TOKEN_E031901, ErrorMessageConstants.INVALID_TOKEN),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorKeyConstants.TOKEN_E031901, ErrorMessageConstants.INVALID_TOKEN),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/generateOtp")
	public ResponseEntity<?> generateOtp(@Valid @RequestBody OtpDto otpDto) {
		try {
			String otp = null;
			otp = this.otpService.sendOtp(otpDto);
//			System.err.println(otp);
			if (otp != null) {
				return new ResponseEntity<SuccessResponseDto>(new SuccessResponseDto(SuccessKeyConstants.OTP_S032001,
						SuccessMessageConstants.OTP_SENT, "Otp is valid for 15 minutes"), HttpStatus.OK);
			}
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031103, ErrorMessageConstants.USER_NOT_FOUND),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.USER_E031103, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/validateOtp")
	public ResponseEntity<?> validateOtp(@Valid @RequestBody OtpValidateDto otpValidateDto) {
		try {
			boolean flag = this.otpService.validateOtp(otpValidateDto.getEmail(), otpValidateDto.getOtp());

			if (flag) {
				JwtAuthenticationResponse response = this.authService.loginWithOtp(otpValidateDto.getEmail());

				ResponseEntity<SuccessResponseDto> responseEntity = new ResponseEntity<SuccessResponseDto>(
						new SuccessResponseDto(SuccessKeyConstants.USER_S031107,
								SuccessMessageConstants.LOGIN_SUCCESSFUL, response),
						HttpStatus.OK);
				return responseEntity;
			}

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.OTP_E032202, ErrorMessageConstants.INVALID_OTP),
					HttpStatus.BAD_REQUEST);

			return responseEntity;
		} catch (Exception e) {

			ResponseEntity<ErrorResponseDto> responseEntity = new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.OTP_E032202, e.getMessage()), HttpStatus.BAD_REQUEST);

			return responseEntity;
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody EmailOtpDto emailOtpDto) {
		try {

			String otp = this.passwordOtpService.passwordResetOtp(emailOtpDto);

			if (otp != null) {
				return new ResponseEntity<>(new SuccessResponseDto(SuccessKeyConstants.OTP_S032001,
						SuccessMessageConstants.OTP_SENT, "Otp is valid for 15 minutes"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("User not found", "Not found"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/set-password")
	public ResponseEntity<?> setPassword(@RequestBody EmailOtpDto emailOtpDto) {
		try {

			String otp = this.passwordOtpService.passwordResetOtp(emailOtpDto);

			if (otp != null) {
				return new ResponseEntity<>(new SuccessResponseDto(SuccessKeyConstants.OTP_S032001,
						SuccessMessageConstants.OTP_SENT, "Otp is valid for 15 minutes"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("User not found", "Not found"), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/confirm-password")
	public ResponseEntity<?> forgotPasswordConfirm(@RequestBody ForgotPasswordDto forgotPasswordDto) throws Exception {

		try {

			boolean flag = this.passwordOtpService.validateOtp(forgotPasswordDto);

			if (flag) {
				return new ResponseEntity<>(
						new SuccessResponseDto("successfull", "Password updated successfully", null), HttpStatus.OK);
			} else {

				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND),
						HttpStatus.BAD_REQUEST);

			}

		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.USER_E031101, e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UploadUsers')")
	@PostMapping("/upload-users")
	public ResponseEntity<?> userBulkUpload(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			if (file.isEmpty()) {
				throw new ResourceNotFoundException(ErrorKeyConstants.FILE_E032101, "File is empty");
			}

			if (GlobalFunctions.checkExcelFormat(file)) {
				Map<Object, Object> uploadUsers = this.bulkUploadService.uploadUsers(file, userId);
				Object object = uploadUsers.get("Status");
				if (object.equals(Constants.SUCCESS_STATUS)) {
					ResponseEntity<FileResponseDto> successResponse = new ResponseEntity<FileResponseDto>(
							new FileResponseDto("Success", "File uploaded successfully", uploadUsers),
							HttpStatus.CREATED);
//					String string = successResponse.getBody().getData().toString();
//					System.err.println(string);
					return successResponse;
				} else {
					return new ResponseEntity<FileResponseDto>(new FileResponseDto(ErrorKeyConstants.FILE_E032102,
							ErrorMessageConstants.INVALID_FILE_DATA, uploadUsers), HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<ErrorResponseDto>(
						new ErrorResponseDto("Invalid file format", "Please upload excel file"),
						HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.FILE_E032101, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UploadRecruiters')")
	@PostMapping("/upload-recruiters")
	public ResponseEntity<?> recruiterBulkUpload(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			if (file.isEmpty()) {
				throw new ResourceNotFoundException(ErrorKeyConstants.FILE_E032101, "File is empty");
			}

			if (GlobalFunctions.checkExcelFormat(file)) {
				Map<Object, Object> uploadUsers = this.bulkUploadService.uploadRecruiters(file, userId);
				Object object = uploadUsers.get("Status");
				if (object.equals(Constants.SUCCESS_STATUS)) {
					ResponseEntity<FileResponseDto> successResponse = new ResponseEntity<FileResponseDto>(
							new FileResponseDto("Success", "File uploaded successfully", uploadUsers),
							HttpStatus.CREATED);
//					String string = successResponse.getBody().getData().toString();
//					System.err.println(string);
					return successResponse;
				} else {
					return new ResponseEntity<FileResponseDto>(new FileResponseDto(ErrorKeyConstants.FILE_E032102,
							ErrorMessageConstants.INVALID_FILE_DATA, uploadUsers), HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<ErrorResponseDto>(
						new ErrorResponseDto("Invalid file format", "Please upload excel file"),
						HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorResponseDto>(
					new ErrorResponseDto(ErrorKeyConstants.FILE_E032101, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
