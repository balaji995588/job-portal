package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

	OtpEntity findByEmailIgnoreCase(String email);
}
