package com.jobportal.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class GlobalFunctions {

	public final static String CUSTUM_ATTRIBUTE_USER_ID = "X-user-id";
	public final static String CUSTUM_ATTRIBUTE_USER_ROLES = "X-user-roles";
	public final static String CUSTUM_ATTRIBUTE_USER_PERMISSIONS = "X-user-permission";

	public static String getFileUrl(String url) {
		if (url == null) {
			return null;
		}

		String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download-file/").path(url)
				.toUriString();

		String urls = fileUrl.replace("http", "https");
		return urls;
	}

	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		boolean flag = contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
				? true
				: false;

		return flag;

	}
}
