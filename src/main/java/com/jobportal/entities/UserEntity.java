package com.jobportal.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.jobportal.enums.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@SQLDelete(sql = "update users set is_active=false where id=?")
@Where(clause = "is_active=true")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "password")
	private String password;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedat;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserRoleEntity> userRole;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserJobEntity> userJob;

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"password\":\"" + password
				+ "\",\"phoneNumber\":\"" + phoneNumber + "\"}";
	}

}
