package com.jobportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.UserEntity;
import com.jobportal.ilistdto.IUserListDto;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

//	Page<IUserListDto> findByNameContainingIgnoreCase(String name, Pageable pageable, Class<IUserListDto> class1);

	UserEntity findByEmailIgnoreCase(String email);

	Boolean existsByEmail(String email);

	Page<IUserListDto> findById(Long id, Pageable pageable, Class<IUserListDto> class1);

	Page<IUserListDto> findByOrderByIdDesc(Pageable pageable, Class<IUserListDto> class1);

}
