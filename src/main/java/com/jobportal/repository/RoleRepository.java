package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByRoleNameIgnoreCase(String roleName);

	RoleEntity findByRoleName(String name);
}
