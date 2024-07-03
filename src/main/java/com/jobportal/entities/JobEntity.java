package com.jobportal.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@SQLDelete(sql = "update jobs set is_active=false where id=?")
@Where(clause = "is_active=true")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false, length = 1000)
	private String description;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "is_active")
	private boolean isActive = true;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	private List<UserJobEntity> userJob;

}
