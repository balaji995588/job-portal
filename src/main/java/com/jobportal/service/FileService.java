package com.jobportal.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	InputStream getFile(Long id);

//	String addFile(MultipartFile file) throws Exception;

	List<String> addFiles(List<MultipartFile> files) throws Exception;

	List<InputStream> getFiles(int pageNumber, int pageSize);

	Resource loadFileAsResource(String fileName) throws Exception;

//	List<byte[]> getFileBytesList(Integer pageNumber, Integer pageSize, String path);
}
