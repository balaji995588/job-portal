package com.jobportal.entities;

import java.io.Serializable;
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
@Table(name = "roles")
@SQLDelete(sql = "update roles set is_active=false where id=?")
@Where(clause = "is_active=true")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_name", unique = true)
	private String roleName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<UserRoleEntity> userRole;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<RolePermissionEntity> rolePermission;

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
}
