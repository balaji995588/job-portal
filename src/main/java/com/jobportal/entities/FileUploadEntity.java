package com.jobportal.entities;

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
@Table(name = "file_upload")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "filename")
	private String filename;

	@Column(name = "mimetype")
	private String mimetype;

	@Column(name = "encoding")
	private String encoding;

	@Column(name = "size")
	private Long size;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;
}