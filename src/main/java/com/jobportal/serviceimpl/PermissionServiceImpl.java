package com.jobportal.serviceimpl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.constants.Constants;
import com.jobportal.dto.PermissionDto;
import com.jobportal.entities.PermissionEntity;
import com.jobportal.exception.FileStorageException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.ilistdto.IPermissionListDto;
import com.jobportal.repository.PermissionRepository;
import com.jobportal.service.FileService;
import com.jobportal.service.PermissionService;
import com.jobportal.util.ErrorKeyConstants;
import com.jobportal.util.ErrorMessageConstants;
import com.jobportal.util.GlobalFunctions;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private FileService fileService;

	@Override
	public PermissionDto addPermission(PermissionDto permissionDto, Long userId) {

		PermissionEntity permission = new PermissionEntity();
		permission.setPermissionName(permissionDto.getPermissionName());
		permission.setController(permissionDto.getController());
		permission.setUrl(permissionDto.getUrl());
		permission.setCreatedBy(userId);
		permission.setMethod(permissionDto.getMethod());
		this.permissionRepository.save(permission);
		permissionDto.setId(permission.getId());
		return permissionDto;

	}

	@Override
	public PermissionDto updatePermission(PermissionDto permissionDto, Long id, Long userId) {
		PermissionEntity permission = this.permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
						ErrorMessageConstants.PERMISSION_NOT_FOUND));
		permission.setPermissionName(permissionDto.getPermissionName());
		permission.setController(permissionDto.getController());
		permission.setUrl(permissionDto.getUrl());
		permission.setCreatedBy(userId);
		permission.setMethod(permissionDto.getMethod());
		this.permissionRepository.save(permission);
		permissionDto.setId(permission.getId());
		return permissionDto;
	}

	@Override
	public void deletePermission(Long id) {
		PermissionEntity permission = this.permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorKeyConstants.PERMISSION_E031301,
						ErrorMessageConstants.PERMISSION_NOT_FOUND));
		this.permissionRepository.delete(permission);
	}

	@Override
	@SuppressWarnings("resource")
	public Map<Object, Object> uploadPermissions(MultipartFile file, Long userId) throws Exception {
		String fileNames = file.getOriginalFilename();
		String fileName = StringUtils.cleanPath(fileNames);
		if (fileName.contains("..")) {

			throw new FileStorageException(ErrorMessageConstants.INVALID_FILE + fileName);
		}
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		XSSFCell cells;

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int cell = worksheet.getRow(0).getPhysicalNumberOfCells();

		List<String> headers = new LinkedList<>(Arrays.asList(Constants.permissionheaders));

		for (int n = 0; n < cell; n++) {
			XSSFCell cell2 = worksheet.getRow(0).getCell(n);

			if (!cell2.getStringCellValue().isBlank() && !cell2.getStringCellValue().isEmpty()
					&& cell2.getStringCellValue() != null) {
				if (cell2.getStringCellValue().equalsIgnoreCase(headers.get(n))) {
					continue;

				} else {
					throw new ResourceNotFoundException(fileNames, "Column Name at cell " + Constants.columnNames[n]
							+ " is Invalid it must be " + headers.get(n) + " in capital or small case");
				}

			} else {
				throw new ResourceNotFoundException("Not found",
						"Column name at cell " + Constants.columnNames[n] + " is empty");
			}
		}

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			PermissionEntity permissionEntity = new PermissionEntity();
			XSSFRow row = worksheet.getRow(i);
			if (row == null || row.toString().isBlank() || row.toString().isEmpty()
					|| row.getPhysicalNumberOfCells() == 0) {
				worksheet.removeRow(row);
				continue;
			}
			for (int j = 0; j < cell; j++) {
				cells = row.getCell(j);

//				System.err.println("cells " + (j + 1) + " " + cells);
				switch (j) {

				case 0:
					if (cells != null && !cells.toString().isBlank() && !cells.toString().isEmpty()) {

						if (cells.getStringCellValue().matches(Constants.PERMISSION_NAME_REGEX)) {

							permissionEntity.setPermissionName(cells.getStringCellValue());
						} else {

							throw new ResourceNotFoundException("Invalid",
									"PermissionName Cell at row " + (i + 1) + " is not valid.");
						}
					} else {

						throw new ResourceNotFoundException("Not found",
								"PermissionName Cell at row " + (i + 1) + " is empty.");
					}
					break;
				case 1:
					permissionEntity.setController(cells.getStringCellValue());
					break;

				case 2:
					permissionEntity.setUrl(cells.getStringCellValue());
					break;

				case 3:
					permissionEntity.setMethod(cells.getStringCellValue());
					break;

				}
			}
			permissionEntity.setCreatedBy(userId);
			this.permissionRepository.save(permissionEntity);

		}
		workbook.close();
		Map<Object, Object> map = new LinkedHashMap<>();
		List<MultipartFile> files = new LinkedList<>();
		files.add(file);
		List<String> uploadedFileNames = fileService.addFiles(files);
		List<String> urls = new LinkedList<>();
		for (String name1 : uploadedFileNames) {
			String url = GlobalFunctions.getFileUrl(name1);
			urls.add(url);
		}
		map.put("Fileurls", urls);
		return map;
	}

	@Override
	public List<IPermissionListDto> getAllPermissions() {
		List<IPermissionListDto> permissions = this.permissionRepository.findByOrderByIdDesc(IPermissionListDto.class);
		return permissions;

	}
}
