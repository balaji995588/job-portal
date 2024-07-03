package com.jobportal.entities;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "login_otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "otp")
	private String otp;

	private LocalDateTime generatedAt;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;
}
