package com.jobportal.serviceimpl;

import org.springframework.stereotype.Service;

@Service
public class UsersBulkUploadServiceImpl {

//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private FileService fileService;
//
//	public Map<Object, Object> uploadUsers(MultipartFile file, Long userId) throws Exception {
//		String fileNames = file.getOriginalFilename();
//		String fileName = StringUtils.cleanPath(fileNames);
//		if (fileName.contains("..")) {
//
//			throw new FileStorageException(ErrorMessageConstants.INVALID_FILE + fileName);
//		}
//		if (file.isEmpty()) {
//			throw new IllegalArgumentException("File is empty");
//		}
//
//		RoleEntity role = roleRepository.findByRoleName(Constants.USER);
//
////		System.err.println("ROLE " + role.getRoleName());
//		List<UserEntity> users = new ArrayList<>();
//		List<UserEntity> arrayList = new ArrayList<>();
//		List<UserStatus> status = new LinkedList<>();
//		List<UserMessage> message = new LinkedList<>();
//		XSSFCell cells;
//
//		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//		XSSFSheet worksheet = workbook.getSheetAt(0);
//		int cell = worksheet.getRow(0).getPhysicalNumberOfCells();
//
//		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
//			UserEntity userEntity = new UserEntity();
//			UserEntity userEntity2 = new UserEntity();
//			UserStatus userStatus = new UserStatus();
//			UserMessage userMessage = new UserMessage();
//			XSSFRow row = worksheet.getRow(i);
//			if (row == null || row.toString().isBlank() || row.toString().isEmpty()
//					|| row.getPhysicalNumberOfCells() == 0) {
//				worksheet.removeRow(row);
//				continue;
//			}
//			for (int j = 0; j < cell; j++) {
//				cells = row.getCell(j);
//
////				System.err.println("cells " + (j + 1) + " " + cells);
//				switch (j) {
//
//				case 0:
//					if (cells != null && !cells.toString().isBlank() && !cells.toString().isEmpty()) {
//
//						if (cells.getStringCellValue().matches(Constants.NAME_REGEX)) {
//
//							userEntity.setName(cells.getStringCellValue());
//							userEntity2.setName(cells.getStringCellValue());
//							userStatus.setNameStatus(Constants.SUCCESS_STATUS);
//							userMessage.setNameMessage("Name is valid");
//						} else {
//							userEntity2.setName(cells.getStringCellValue());
////							System.err.println(cells.getStringCellValue());
//							userStatus.setNameStatus(Constants.ERROR_STATUS);
//							userMessage.setNameMessage("Name cell is not valid");
//
////							throw new ResourceNotFoundException("", "Name Cell at row " + (i + 1) + " is not valid.");
//						}
//					} else {
//
////						System.err.println(cells.getStringCellValue());
//						userStatus.setNameStatus(Constants.ERROR_STATUS);
//						userMessage.setNameMessage("Name cell is empty");
//
////						throw new ResourceNotFoundException("", "Name Cell at row " + (i + 1) + " is empty.");
//					}
//					break;
//
//				case 1:
//					if (cells != null && !cells.toString().isBlank() && !cells.toString().isEmpty()) {
//
//						if (cells.getStringCellValue().matches(Constants.EMAIL_REGEX)) {
//							userEntity.setEmail(cells.getStringCellValue());
//							userEntity2.setEmail(cells.getStringCellValue());
//							userStatus.setEmailStatus(Constants.SUCCESS_STATUS);
//							userMessage.setEmailMessage("Email cell is valid ");
//						} else {
//							userEntity2.setEmail(cells.getStringCellValue());
//							userStatus.setEmailStatus(Constants.ERROR_STATUS);
//							userMessage.setEmailMessage("Email cell is not valid");
//
////							throw new ResourceNotFoundException("", "Email Cell at row " + (i + 1) + " is not valid.");
//						}
//					} else {
//
//						userStatus.setEmailStatus(Constants.ERROR_STATUS);
//						userMessage.setEmailMessage("Email cell is empty");
////						throw new ResourceNotFoundException("", "Email Cell at row " + (i + 1) + " is empty.");
//					}
//					break;
//
//				case 2:
//					if (cells != null && !cells.toString().isBlank() && !cells.toString().isEmpty()) {
//
//						if (cells.getStringCellValue().equals("MALE") || cells.getStringCellValue().equals("FEMALE")
//								|| cells.getStringCellValue().equals("OTHER")) {
//							GenderEnum gender = GenderEnum.valueOf(cells.getStringCellValue().toUpperCase());
//							userEntity.setGender(gender);
//							userEntity2.setGender(gender);
//							userStatus.setGenderStatus(Constants.SUCCESS_STATUS);
//							userMessage.setGenderMessage("Gender cell is valid ");
//
//						} else {
//							GenderEnum gender = GenderEnum.valueOf(cells.getStringCellValue().toUpperCase());
//							userEntity2.setGender(gender);
//							userStatus.setGenderStatus(Constants.ERROR_STATUS);
//							userMessage.setGenderMessage(
//									"Gender cell is not valid, choose either 'MALE','FEMALE' or 'OTHER' in block letters only");
//
//						}
//
//					} else {
//						userStatus.setGenderStatus(Constants.ERROR_STATUS);
//						userMessage.setGenderMessage("Gender cell is empty");
////						throw new ResourceNotFoundException("", "Gender Cell at row " + (i + 1) + " is empty.");
//					}
//					break;
//
//				case 3:
//					if (cells != null && !cells.toString().isBlank() && !cells.toString().isEmpty()) {
//
//						if (cells.getStringCellValue().matches(Constants.PHONE_REGEX)) {
//
//							userEntity.setPhoneNumber(cells.getStringCellValue());
//							userEntity2.setPhoneNumber(cells.getStringCellValue());
//							userStatus.setPhoneStatus(Constants.SUCCESS_STATUS);
//							userMessage.setPhoneMessage("Phone Number cell is valid ");
//
//						} else {
//							userEntity2.setPhoneNumber(cells.getStringCellValue());
//							userStatus.setPhoneStatus(Constants.ERROR_STATUS);
//							userMessage.setPhoneMessage("Phone number cell is not valid");
////							throw new ResourceNotFoundException("",
////									"Phone Number Cell at row " + (i + 1) + " is not valid.");
//						}
//					} else {
//						userStatus.setPhoneStatus(Constants.ERROR_STATUS);
//						userMessage.setPhoneMessage("Phone number cell is empty");
////						throw new ResourceNotFoundException("", "Phone Number Cell at row " + (i + 1) + " is empty.");
//					}
//					break;
//				}
//
//				userEntity.setRole(role);
//
//				userEntity.setCreatedBy(userId);
//				userEntity.setUpdatedBy(userId);
//			}
//			users.add(userEntity);
//			arrayList.add(userEntity2);
//			status.add(userStatus);
//			message.add(userMessage);
//		}
//
//		workbook.close();
//		boolean flag = true;
//		for (int i = 0; i < status.size(); i++) {
//			if (status.get(i).getNameStatus().equalsIgnoreCase(Constants.ERROR_STATUS)
//					|| status.get(i).getEmailStatus().equalsIgnoreCase(Constants.ERROR_STATUS)
//					|| status.get(i).getGenderStatus().equalsIgnoreCase(Constants.ERROR_STATUS)
//					|| status.get(i).getPhoneStatus().equalsIgnoreCase(Constants.ERROR_STATUS))
//				flag = false;
//		}
//
//		MultipartFile generatePDF;
//		String filename = null;
//		String url = null;
//		Map<Object, Object> map = new LinkedHashMap<>();
//		LocalDateTime currentDateTime = LocalDateTime.now();
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
//		String formattedDateTime = currentDateTime.format(formatter);
//		String name = Constants.USER + "_" + formattedDateTime;
//
//		if (flag) {
////			userRepository.saveAll(arrayList);
//			generatePDF = this.generatePDF(arrayList, status, message, "Successful", "All data uploaded successfully",
//					name);
//			filename = fileService.addFile(generatePDF);
//			url = GlobalFunctions.getFileUrl(filename);
//			map.put("Status", Constants.SUCCESS_STATUS);
//
//		}
//
//		else {
//			generatePDF = this.generatePDF(arrayList, status, message, "Failed",
//					"Data not added due to above errors in the file", name);
//			filename = fileService.addFile(generatePDF);
//			url = GlobalFunctions.getFileUrl(filename);
//			map.put("Status", Constants.ERROR_STATUS);
//		}
//
////		map.put("pdf", generatePDF);
////		map.put("users", arrayList);
//		map.put("FileUrl", url);
//		return map;
//	}
//
//	public MultipartFile generatePDF(List<UserEntity> userList, List<UserStatus> status, List<UserMessage> message,
//			String fileStatus, String fileMessage, String filename) throws IOException, DocumentException {
//
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//		// Create PDF document
//		Document document = new Document();
//		PdfWriter.getInstance(document, byteArrayOutputStream);
//		document.open();
//
//		// Define fonts for table header and content
//		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
//		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
//
//		// Define colors for success and error status
//		BaseColor successColor = BaseColor.GREEN;
//		BaseColor errorColor = BaseColor.RED;
//
//		// Create table with 12 columns (including status and message fields)
//		PdfPTable table = new PdfPTable(13);
//		table.setWidthPercentage(100);
//
//		// Set header cell background color
//		PdfPCell headerCell = new PdfPCell(new Phrase("Sr. No", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Name", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Status", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Message", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Email", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Status", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Message", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Gender", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Status", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Message", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Phone Number", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Status", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		headerCell = new PdfPCell(new Phrase("Message", headerFont));
//		headerCell.setBackgroundColor(BaseColor.BLUE);
//		table.addCell(headerCell);
//
//		// Add table rows with data from UserEntity list
//		for (int i = 0; i < userList.size(); i++) {
//			// Set status cell text color based on status value
//			table.addCell(new Phrase(String.valueOf(i + 1), contentFont));
//			table.addCell(new Phrase(userList.get(i).getName(), contentFont));
//			if (status.get(i).getNameStatus().equalsIgnoreCase(Constants.SUCCESS_STATUS)) {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getNameStatus(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getNameMessage(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//			} else {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getNameStatus(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getNameMessage(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//			}
//			table.addCell(new Phrase(userList.get(i).getEmail(), contentFont));
//			if (status.get(i).getEmailStatus().equalsIgnoreCase(Constants.SUCCESS_STATUS)) {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getEmailStatus(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getEmailMessage(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//			} else {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getEmailStatus(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getEmailMessage(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//			}
//			table.addCell(new Phrase(userList.get(i).getGender().toString(), contentFont));
//			if (status.get(i).getGenderStatus().equalsIgnoreCase(Constants.SUCCESS_STATUS)) {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getGenderStatus(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getGenderMessage(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//			} else {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getGenderStatus(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getGenderMessage(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//			}
//			table.addCell(new Phrase(userList.get(i).getPhoneNumber(), contentFont));
//			if (status.get(i).getPhoneStatus().equalsIgnoreCase(Constants.SUCCESS_STATUS)) {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getPhoneStatus(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getPhoneMessage(), contentFont));
//				contentCell.setBackgroundColor(successColor);
//				table.addCell(contentCell);
//			} else {
//				PdfPCell contentCell = new PdfPCell(new Phrase(status.get(i).getPhoneStatus(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//				contentCell = new PdfPCell(new Phrase(message.get(i).getPhoneMessage(), contentFont));
//				contentCell.setBackgroundColor(errorColor);
//				table.addCell(contentCell);
//			}
//		}
//
//		// Add table to document
//		document.add(table);
//
//		// Add file status and message
//		document.add(new Paragraph("Status: " + fileStatus));
//		document.add(new Paragraph("Message: " + fileMessage));
//
//		// Close document
//		document.close();
//
//		// Convert ByteArrayOutputStream to InputStream
//		InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//
//		// Create MultipartFile with random file name
//		filename = filename + ".pdf";
//		MultipartFile multipartFile = new MockMultipartFile(filename, filename, "application/pdf", inputStream);
//
//		return multipartFile;
//	}

}
