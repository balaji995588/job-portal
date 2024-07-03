package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.entities.PermissionEntity;
import com.jobportal.entities.RoleEntity;
import com.jobportal.entities.RolePermissionEntity;
import com.jobportal.ilistdto.IRolePermissionListDto;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

	@Query(value = "select r.role_name as RoleName,string_agg(p.permission_name,',') as PermissionName from role_permissions rp join roles r on rp.role_id=r.id\r\n"
			+ "join permissions p on rp.permission_id=p.id where r.id=:roleId and rp.is_active=true group by r.id", nativeQuery = true)
	List<IRolePermissionListDto> getAllPermissionsByRoleId(@Param("roleId") Long roleId,
			Class<IRolePermissionListDto> class1);

	List<RolePermissionEntity> findByRole(RoleEntity role);

	@Query(value = "SELECT distinct p.permission_name as PermissionName FROM user_roles ur\r\n"
			+ "			JOIN users u ON ur.user_id = u.id JOIN roles r ON ur.role_id = r.id\r\n"
			+ "			JOIN role_permissions rp ON r.id = rp.role_id\r\n"
			+ "			JOIN permissions p ON rp.permission_id = p.id where u.id=:userId and rp.is_active=true", nativeQuery = true)
	List<String> getAllPermissionsOfUsers(@Param("userId") Long userId);

	boolean existsByRoleAndPermission(RoleEntity role, PermissionEntity permission);

//	@Query(value = "select r.name as RoleName,string_agg(p.action_name,',') as PermissionName from role_permissions rp join roles r on rp.role_id=r.id\r\n"
//			+ "join permissions p on rp.permission_id=p.id where rp.id=:id and rp.is_active=true group by r.id", nativeQuery = true)
//	IRolePermissionListDto getAllPermissionsByRolePermissionId(@Param("id") Long id,
//			Class<IRolePermissionListDto> class1);

////@Query(value = "select r.id as RoleId,String_agg(CAST(u.id AS varchar), ',') as UserId from roles r join user_roles ur on r.id=ur.role_id\r\n"
////		+ "join users u on u.id=ur.user_id where ur.role_id=:roleId and ur.is_active=true group by r.id", nativeQuery = true)
////IUserRoleListDto2 getUsersByRole(@Param("roleId") Long roleId, Class<IUserRoleListDto2> class1);

//	@Query(value = "select r.name as RoleName,string_agg(p.action_name,',') as PermissionName from role_permissions rp join roles r on rp.role_id=r.id\r\n"
//			+ "	join permissions p on rp.permission_id=p.id where  rp.is_active=true group by r.id ", nativeQuery = true)
//	List<IRolePermissionListDto> getAllPermissionsOfRoles(Class<IRolePermissionListDto> class1);
//
}
