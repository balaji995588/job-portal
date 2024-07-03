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

import com.jobportal.ilistdto.IUserJobsListDto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserJobExportServiceImpl {

	private XSSFWorkbook workbook;

	private XSSFSheet sheet;

	private List<IUserJobsListDto> iListUsersJobDtos;

	public UserJobExportServiceImpl(XSSFWorkbook workbook, XSSFSheet sheet, List<IUserJobsListDto> iListUsersJobDtos) {
		super();
		this.workbook = workbook;
		this.sheet = sheet;
		this.iListUsersJobDtos = iListUsersJobDtos;
	}

	public UserJobExportServiceImpl(List<IUserJobsListDto> iListUsersJobDtos) {
		super();
		this.iListUsersJobDtos = iListUsersJobDtos;

		workbook = new XSSFWorkbook();

	}

	public UserJobExportServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
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
		sheet = workbook.createSheet("Users And Aplied Jobs");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(row, 0, "Users And Aplied Jobs ", style);
		font.setFontHeightInPoints((short) 10);

		row = sheet.createRow(1);
		font.setFontHeight(16);
		style.setFont(font);
		createCell(row, 0, "ID", style);
		createCell(row, 1, "USERNAME", style);
		createCell(row, 2, "EMAIL", style);
		createCell(row, 3, "JOBID", style);
		createCell(row, 4, "JOBTITLE", style);

	}

	private void writeJobData() {
		int rowCount = 2;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (IUserJobsListDto UserAppliedJob : iListUsersJobDtos) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, UserAppliedJob.getId(), style);
			createCell(row, columnCount++, UserAppliedJob.getName(), style);
			createCell(row, columnCount++, UserAppliedJob.getEmail(), style);
			createCell(row, columnCount++, UserAppliedJob.getJobId(), style);
			createCell(row, columnCount++, UserAppliedJob.getJobTitle(), style);

		}
	}

	public void exportDataToExcel(HttpServletResponse responce) throws IOException {
		createHeaderRow();
		writeJobData();
		ServletOutputStream outputStream = responce.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}
}
