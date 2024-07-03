package com.jobportal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.entities.RoleEntity;
import com.jobportal.entities.UserEntity;
import com.jobportal.entities.UserRoleEntity;
import com.jobportal.ilistdto.IUserRoleListDto;

import jakarta.servlet.http.HttpServletResponse;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	List<UserRoleEntity> findByRole(RoleEntity role);

	UserRoleEntity findByUser(UserEntity user);

	boolean existsByUser(UserEntity user);

	@Query(value = "Select u.id as Id, u.email as Email,u.name as Name,u.gender as Gender,u.phone_number as PhoneNumber,r.role_name as RoleName from users u \r\n"
			+ "join user_roles ur on u.id=ur.user_id join roles r on r.id=ur.role_id where r.id=:roleId order by u.id desc", nativeQuery = true)
	Page<IUserRoleListDto> getAllUsersByRole(@Param("roleId") Long roleId, Pageable pageable,
			Class<IUserRoleListDto> class1);

	@Query(value = "SELECT u.id as Id, u.email as Email, u.name as Name, u.gender as Gender, u.phone_number as PhoneNumber, r.role_name as RoleName "
			+ "FROM users u " + "JOIN user_roles ur ON u.id=ur.user_id " + "JOIN roles r ON r.id=ur.role_id "
			+ "WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "OR LOWER(u.gender) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "OR LOWER(u.phone_number) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "OR LOWER(r.role_name) LIKE LOWER(CONCAT('%', :search, '%')) " + "ORDER BY u.id DESC", nativeQuery = true)
	Page<IUserRoleListDto> getAllUsersBySearch(@Param("search") String search, Pageable pageable,
			Class<IUserRoleListDto> class1);

	@Query(value = "Select u.id as Id, u.email as Email,u.name as Name,u.gender as Gender,u.phone_number as PhoneNumber,r.role_name as RoleName from users u \r\n"
			+ "join user_roles ur on u.id=ur.user_id join roles r on r.id=ur.role_id where r.role_name='User' order by u.id desc", nativeQuery = true)
	List<IUserRoleListDto> getAllUsers(HttpServletResponse response, Class<IUserRoleListDto> class1);

	@Query(value = "Select u.id as Id, u.email as Email,u.name as Name,u.gender as Gender,u.phone_number as PhoneNumber,r.role_name as RoleName from users u \r\n"
			+ "join user_roles ur on u.id=ur.user_id join roles r on r.id=ur.role_id where r.role_name='Recruiter' order by u.id desc", nativeQuery = true)
	List<IUserRoleListDto> getAllRecruiters(HttpServletResponse response, Class<IUserRoleListDto> class1);

}
