package com.jobportal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.FileResponseDto;
import com.jobportal.entities.FileUploadEntity;
import com.jobportal.service.FileService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.SuccessKeyConstants;
import com.jobportal.util.SuccessMessageConstants;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;

	@PreAuthorize("hasRole('DownloadFile')")
	@GetMapping("/download-file/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {

		Resource resource = null;

		try {
			resource = fileService.loadFileAsResource(fileName);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstants.FILE_NOT_FOUND, e.getMessage()),
					HttpStatus.NOT_FOUND);

		}
		//// Try to determine file's content type
		String contentType = null;

		try {

			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		} catch (IOException ex) {

		}

		if (contentType == null) {

			contentType = "application/octet-stream";

		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@PreAuthorize("hasRole('UploadFile')")
	@PostMapping("/upload-file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") List<MultipartFile> files) throws Exception {

		@SuppressWarnings("unused")
		FileUploadEntity uploadEntity = new FileUploadEntity();

		try {
			boolean flag = true;
			List<String> addedFileNames = this.fileService.addFiles(files);
			for (String fileName : addedFileNames) {
				if (fileName == null) {
					flag = false;
				}
			}

			if (flag) {
				return new ResponseEntity<FileResponseDto>(new FileResponseDto(SuccessKeyConstants.FILE_S031901,
						SuccessMessageConstants.FILE_UPLOADED, null), HttpStatus.OK);
			} else {
				return new ResponseEntity<FileResponseDto>(
						new FileResponseDto(ErrorKeyConstants.FILE_E032101, ErrorMessageConstants.INVALID_FILE, null),
						HttpStatus.OK);
			}

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(ErrorKeyConstants.FILE_E032101, e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

//	@GetMapping(value = "/{id}")
//	public ResponseEntity<?> getFile(@PathVariable("id") Long id, HttpServletResponse response) {
//		InputStream file = this.fileService.getFile(id);
//
//		try {
//			final String type = "";
//			response.setContentType(type);
//			StreamUtils.copy(file, response.getOutputStream());
//			response.flushBuffer();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return ResponseEntity.ok().build();
//	}
}
