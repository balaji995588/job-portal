package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.PasswordOtpEntity;

public interface PasswordOtpRepository extends JpaRepository<PasswordOtpEntity, Long> {

	PasswordOtpEntity findByEmailIgnoreCase(String email);

}
