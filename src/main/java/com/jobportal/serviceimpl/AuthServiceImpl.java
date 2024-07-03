package com.jobportal.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.configuration.JwtTokenUtil;
import com.jobportal.constants.CacheConstants;
import com.jobportal.constants.Constants;
import com.jobportal.dto.AuthDto;
import com.jobportal.dto.UserResponseDto;
import com.jobportal.entities.RoleEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.entities.UserRoleEntity;
import com.jobportal.exception.InvalidEmailOrPasswordException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.RoleRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.repository.UserRoleRepository;
import com.jobportal.service.AuthService;
import com.jobportal.service.EmailService;
import com.jobportal.service.RolePermissionService;
import com.jobportal.util.CacheOperations;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.JwtAuthenticationRequest;
import com.jobportal.util.JwtAuthenticationResponse;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CacheOperations cacheOperations;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RolePermissionService rolePermissionService;

	public Collection<? extends GrantedAuthority> getAuthorities(Long userId) {
		List<String> permissions = this.permissions(userId);

		List<SimpleGrantedAuthority> authority = new ArrayList<SimpleGrantedAuthority>();

		permissions.forEach(a -> {
			authority.add(new SimpleGrantedAuthority("ROLE_" + a));
		});

		return authority;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = this.userInformation(username);

		return new User(user.getEmail(), user.getPassword(), this.getAuthorities(user.getId()));

	}

	@Override
	public JwtAuthenticationResponse login(JwtAuthenticationRequest request) {

		UserEntity user = this.userInformation(request.getUsername());

		JwtAuthenticationResponse response = new JwtAuthenticationResponse();

		if (user.getEmail() == null) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND);
		}

		if (comparePassword(request.getPassword(), user.getPassword())) {

			UserDetails userDetails = loadUserByUsername(request.getUsername());

			final String accessToken = this.jwtTokenUtil.generateAccessToken(userDetails);

			final String refreshToken = this.jwtTokenUtil.generateRefreshToken(accessToken, userDetails);

			response.setAccessToken(accessToken);

			response.setRefreshToken(refreshToken);

			response.setPermissions(this.permissions(user.getId()));
			return response;
		}

		else {
			response.setAccessToken(null);
			response.setRefreshToken(null);
			response.setPermissions(null);
//			System.out.println("null response");
			return response;
		}
	}

	@Override
	public UserResponseDto registerUser(AuthDto authDto) {
		UserEntity entity = new UserEntity();
		entity.setName(authDto.getName());
		entity.setEmail(authDto.getEmail());
		entity.setPassword(this.passwordEncoder.encode(authDto.getPassword()));
		entity.setGender(authDto.getGender());
		entity.setPhoneNumber(authDto.getPhoneNumber());

		RoleEntity roleEntity = this.roleRepository.findByRoleNameIgnoreCase(Constants.USER);
		if (roleEntity == null) {
			throw new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201, ErrorMessageConstants.ROLE_NOT_FOUND);
		}
		this.repository.save(entity);
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		userRoleEntity.setUser(entity);
		userRoleEntity.setRole(roleEntity);
		this.userRoleRepository.save(userRoleEntity);
		try {
			this.emailService.sendEmail(entity.getEmail(), Constants.REGISTRATION_SUBJECT,
					"Dear User, \n\nYour registration has been successful.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(entity.getId());
		responseDto.setName(entity.getName());
		responseDto.setEmail(entity.getEmail());
		responseDto.setRoleName(roleEntity.getRoleName());
		responseDto.setGender(entity.getGender());
		responseDto.setPhonenumber(entity.getPhoneNumber());
		return responseDto;

	}

	@Override
	public UserResponseDto registerRecruiter(AuthDto authDto, Long adminId) {
		if (repository.existsByEmail(authDto.getEmail())) {
			throw new ResourceNotFoundException(ErrorKeyConstants.USER_E031102,
					ErrorMessageConstants.EMAIL_ALREADY_EXISTS);
		}
		UserEntity entity = new UserEntity();
		entity.setName(authDto.getName());
		entity.setEmail(authDto.getEmail());
		entity.setPassword(this.passwordEncoder.encode(authDto.getPassword()));
		entity.setGender(authDto.getGender());
		entity.setCreatedBy(adminId);
		entity.setPhoneNumber(authDto.getPhoneNumber());

		RoleEntity roleEntity = this.roleRepository.findByRoleNameIgnoreCase(Constants.RECRUITER);
		if (roleEntity == null) {
			throw new ResourceNotFoundException(ErrorKeyConstants.ROLE_E031201, ErrorMessageConstants.ROLE_NOT_FOUND);
		}
		this.repository.save(entity);
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		userRoleEntity.setUser(entity);
		userRoleEntity.setRole(roleEntity);
		this.userRoleRepository.save(userRoleEntity);
		try {
			this.emailService.sendEmail(entity.getEmail(), Constants.REGISTRATION_SUBJECT,
					"Dear Recruiter, \n\nYour registration has been successful.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(entity.getId());
		responseDto.setName(entity.getName());
		responseDto.setEmail(entity.getEmail());
		responseDto.setRoleName(roleEntity.getRoleName());
		responseDto.setGender(entity.getGender());
		responseDto.setPhonenumber(entity.getPhoneNumber());
		return responseDto;

	}

	@Override
	public String refreshAndGetAuthenticationToken(String refreshToken) throws Exception {
		try {
			String email = this.jwtTokenUtil.getUsernameFromToken(refreshToken);
			UserEntity user = this.userInformation(email);
			if (user != null) {

				final UserDetails userDetails = loadUserByUsername(email);

				if (this.jwtTokenUtil.canTokenBeRefreshed(refreshToken)
						&& this.jwtTokenUtil.validateToken(refreshToken, userDetails)
						&& this.jwtTokenUtil.getTokenType(refreshToken).equalsIgnoreCase("refresh")) {
					String accessToken = this.jwtTokenUtil.generateAccessToken(userDetails);

					return accessToken;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> permissions(Long userId) {
		List<String> permissions;
		String id = userId.toString();
		String key = CacheConstants.PERMISSIONS.concat(id);

		if (cacheOperations.isKeyExist(key, id)) {
			permissions = ((List<String>) cacheOperations.getFromCache(key, id));
//			System.err.println(permissions);
		} else {

			permissions = this.rolePermissionService.getPermissionsOfUser(userId);
//			System.err.println(permissions);
			cacheOperations.addInCache(key, id, permissions);

		}
		return permissions;
	}

	private Boolean comparePassword(String password, String hashPassword) {

		return passwordEncoder.matches(password, hashPassword);

	}

	@Override
	public UserEntity userInformation(String email) {
		UserEntity user = new UserEntity();

		if (cacheOperations.isKeyExist(email, email)) {
			String jsonString = (String) cacheOperations.getFromCache(email, email);

			try {
				ObjectMapper mapper = new ObjectMapper();

				TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
				};

				Map<String, Object> map = mapper.readValue(jsonString, typeReference);
//				System.out.println(map.toString());
				user.setId(((Integer) map.get("id")).longValue());
				user.setEmail((String) map.get("email"));
				user.setPassword((String) map.get("password"));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (!this.repository.existsByEmail(email)) {
			throw new InvalidEmailOrPasswordException(ErrorKeyConstants.USER_E031105,
					ErrorMessageConstants.INVALID_EMAIL_OR_PASSWORD);
		}

		else {

			user = this.repository.findByEmailIgnoreCase(email);

//			cacheOperations.putValueInHashWithExpiration(email, email, user.toString(), 12, TimeUnit.HOURS);
			cacheOperations.addInCache(email, email, user.toString());
		}

		return user;
	}

	@Override
	public JwtAuthenticationResponse loginWithOtp(String email) {
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();
		UserEntity user = this.userInformation(email);
		final UserDetails userDetails = loadUserByUsername(email);

		final String accessToken = this.jwtTokenUtil.generateAccessToken(userDetails);

		final String refreshToken = this.jwtTokenUtil.generateRefreshToken(accessToken, userDetails);

		response.setAccessToken(accessToken);
		response.setRefreshToken(refreshToken);
		response.setPermissions(this.permissions(user.getId()));
		return response;
	}

//	@Override
//	public boolean confirmPassword(ForgotPasswordDto forgotPasswordDto) {
//
//		UserEntity user = this.userInformation(forgotPasswordDto.getEmail());
//		if (user != null && this.otpService.validateOtp(forgotPasswordDto.getEmail(), forgotPasswordDto.getOtp())
//				&& forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
//			System.err.println("IF Condition");
//			user.setPassword(this.passwordEncoder.encode(forgotPasswordDto.getPassword()));
//			return true;
//		} else if (user == null) {
//			System.err.println("Else IF 1 Condition");
//			throw new ResourceNotFoundException(ErrorKeyConstants.USER_E031101, ErrorMessageConstants.USER_NOT_FOUND);
//		}
//
//		else if (!this.otpService.validateOtp(forgotPasswordDto.getEmail(), forgotPasswordDto.getOtp())) {
//			System.err.println("Else IF 2 Condition");
//			throw new ResourceNotFoundException(ErrorKeyConstants.OTP_E032202, ErrorMessageConstants.INVALID_OTP);
//		}
//
//		else if (!forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
//			System.err.println("Else IF 3 Condition");
//			throw new ResourceNotFoundException("Invalid password", ErrorMessageConstants.INVALID_PASSWORD);
//		}
//		return false;
//	}

}
