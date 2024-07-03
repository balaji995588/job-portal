package com.jobportal.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.jobportal.ilistdto.IUserRoleListDto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserExportServiceImpl {

	private XSSFWorkbook workbook;

	private XSSFSheet sheet;

	private List<IUserRoleListDto> iListUsersRoleDtos;

	public UserExportServiceImpl(XSSFWorkbook workbook, XSSFSheet sheet, List<IUserRoleListDto> iListUsersRoleDtos) {
		super();
		this.workbook = workbook;
		this.sheet = sheet;
		this.iListUsersRoleDtos = iListUsersRoleDtos;
	}

	public UserExportServiceImpl(List<IUserRoleListDto> iListUsersRoleDtos) {
		super();
		this.iListUsersRoleDtos = iListUsersRoleDtos;

		workbook = new XSSFWorkbook();

	}

	public UserExportServiceImpl() {
		super();

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);

		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);

	}

	private void createHeaderRow() {
		sheet = workbook.createSheet("Users And There Role");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(row, 0, "Users And There Role", style);
		font.setFontHeightInPoints((short) 10);

		row = sheet.createRow(1);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(row, 0, "ID", style);
		createCell(row, 1, "EMAIL", style);
		createCell(row, 2, "NAME", style);
		createCell(row, 3, "GENDER", style);
		createCell(row, 4, "PHONE NUMBER", style);
		createCell(row, 5, "ROLE", style);

	}

	private void writeJobData() {
		int rowCount = 2;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (IUserRoleListDto listOfUsers : iListUsersRoleDtos) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, listOfUsers.getId(), style);
			createCell(row, columnCount++, listOfUsers.getEmail(), style);
			createCell(row, columnCount++, listOfUsers.getName(), style);
			createCell(row, columnCount++, listOfUsers.getGender(), style);
			createCell(row, columnCount++, listOfUsers.getPhoneNumber(), style);
			createCell(row, columnCount++, listOfUsers.getRoleName(), style);

		}
	}

	public void exportDataToExcel(HttpServletResponse response) throws IOException {

		createHeaderRow();
		writeJobData();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
