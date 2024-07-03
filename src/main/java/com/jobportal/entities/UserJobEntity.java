package com.jobportal.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_jobs")
@Where(clause = "is_active=true")
@SQLDelete(sql = "update user_jobs set is_active=false where id=?")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_active")
	private boolean isActive = true;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "job_id")
	private JobEntity job;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private Long createdBy;

	public Long getUserId() {
		return user.getId();
	}

	public String getUserName() {
		return user.getName();
	}

}
