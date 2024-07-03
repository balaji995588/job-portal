package com.jobportal.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.entities.FileUploadEntity;
import com.jobportal.exception.FileStorageException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.FileUploadRepository;
import com.jobportal.service.FileService;
import com.jobportal.util.ErrorMessageConstants;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileUploadRepository fileRepository;

	@Value("${project.files}")
	private String path;

//	public FileServiceImpl() throws Exception {
//
//		System.err.println("PATH " + path);
//		this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
//
//		System.err.println("FILE STORAGE LOCATION " + fileStorageLocation);
//		try {
//			Files.createDirectories(this.fileStorageLocation);
//		} catch (Exception ex) {
//
//			throw new ResourceNotFoundException(ErrorMessageConstants.DIRECTORY_NOT_CREATED, ex.getMessage());
//		}
//
//	}

	@Override
	public List<String> addFiles(List<MultipartFile> files) throws Exception {
		List<String> filenames = new ArrayList<>(); // List to store filenames of saved files

		for (MultipartFile file : files) {
			FileUploadEntity fileEntity = new FileUploadEntity();
			String name = file.getOriginalFilename();

			String fileName = StringUtils.cleanPath(name);
			if (fileName.contains("..")) {

				throw new FileStorageException(ErrorMessageConstants.INVALID_FILE + fileName);
			}
			if (file.isEmpty()) {
				throw new IllegalArgumentException("File is empty");
			}
			// Full Path
			String filePath = this.path + File.separator + name;

			File f = new File(path);

			if (!f.exists()) {
				f.mkdir();
			}

			try {
				// Save file to specified path
				Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new Exception("File upload exception");
			}

			// Create Apache Tika instance
			Tika tika = new Tika();

			// Detect MIME type
			String mimeType = tika.detect(file.getOriginalFilename());

			// Read contents of the file into a byte array
			byte[] fileBytes = file.getBytes();

			// Detect encoding
			String encoding = tika.detect(fileBytes);

			fileEntity.setFilename(name);
			fileEntity.setSize(file.getSize());
			fileEntity.setEncoding(encoding);
			fileEntity.setMimetype(mimeType);
			fileEntity.setOriginalName(name);
			this.fileRepository.save(fileEntity);

			filenames.add(name); // Add filename to the list of saved filenames
		}

		return filenames;
	}

//	@Override
//	public String addFile(MultipartFile file) throws Exception {
//
////		System.err.println("PATH " + path);
//		FileUploadEntity fileEntity = new FileUploadEntity();
//		String name = file.getOriginalFilename();
//
//		// Full Path
//		String filePath = this.path + File.separator + name;
//
//		File f = new File(path);
//
//		if (!f.exists()) {
//			f.mkdir();
//		}
//
//		try {
//			// Save file to specified path
//			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			throw new Exception("File upload exception");
//		}
//
//		// Create Apache Tika instance
//		Tika tika = new Tika();
//
//		// Detect MIME type
//		String mimeType = tika.detect(file.getOriginalFilename());
//
//		// Read contents of the file into a byte array
//		byte[] fileBytes = file.getBytes();
//
//		// Detect encoding
//		String encoding = tika.detect(fileBytes);
//
//		fileEntity.setFilename(name);
//		fileEntity.setSize(file.getSize());
//		fileEntity.setEncoding(encoding);
//		fileEntity.setMimetype(mimeType);
//		fileEntity.setOriginalName(name);
//		this.fileRepository.save(fileEntity);
//
//		return name;
//	}

	@Override
	public Resource loadFileAsResource(String fileName) throws Exception {
		try {

			String name = this.path + File.separator + fileName;
//			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Path filePath = Paths.get(name);
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;

			} else {
				throw new Exception(ErrorMessageConstants.FILE_NOT_FOUND);
			}

		} catch (MalformedURLException ex) {
			throw new Exception(ErrorMessageConstants.FILE_NOT_FOUND);
		}
	}

	@Override
	public InputStream getFile(Long id) {
		FileUploadEntity fileEntity = this.fileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FileEntity not found", "FileEntity not found"));
		String fullPath = this.path + File.separator + fileEntity.getFilename();

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("InputStream", inputStream);
		return inputStream;
	}

	@Override
	public List<InputStream> getFiles(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<FileUploadEntity> fileEntities = this.fileRepository.findAll(pageable);

		List<InputStream> files = new ArrayList<>();

		for (FileUploadEntity fileEntity : fileEntities) {
			String fullPath = path + File.separator + fileEntity.getFilename();

			try {
				InputStream inputStream = new FileInputStream(fullPath);
				files.add(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return files;
	}

//	@Override
//	public List<byte[]> getFileBytesList(Integer pageNumber, Integer pageSize, String path) {
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//		Page<FileEntity> fileEntities = this.fileRepository.findAll(pageable);
//		System.out.println(fileEntities);
//		List<byte[]> fileBytesList = new ArrayList<>();
//
//		for (FileEntity fileEntity : fileEntities) {
//			String fullPath = path + File.separator + fileEntity.getVideo();
//
//			try {
//				byte[] fileBytes = Files.readAllBytes(Paths.get(fullPath));
//				fileBytesList.add(fileBytes);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return fileBytesList;
//	}

}