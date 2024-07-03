package com.jobportal.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface BulkUploadService {

//	boolean checkExcelFormat(MultipartFile file);

	Map<Object, Object> uploadUsers(MultipartFile file, Long userId) throws Exception;

	Map<Object, Object> uploadRecruiters(MultipartFile file, Long userId) throws Exception;

//	boolean checkCsvFormat(MultipartFile file);

}
