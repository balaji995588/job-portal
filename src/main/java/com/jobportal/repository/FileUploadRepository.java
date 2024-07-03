package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entities.FileUploadEntity;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {

}
