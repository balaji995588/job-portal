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
@Table(name = "permissions")
@SQLDelete(sql = "update permissions set is_active=false where id=?")
@Where(clause = "is_active=true")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "permission_name", unique = true)
	private String permissionName;

	@Column(name = "url")
	private String url;

	@Column(name = "controller")
	private String controller;

	@Column(name = "method")
	private String method;

	@Column(name = "is_active")
	private boolean isActive = true;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
	private List<RolePermissionEntity> rolePermission;

}
