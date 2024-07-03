package com.jobportal.util;

public class ErrorKeyConstants {

	// Type of error key for front end,back end and api
	public static final String FRONT_END = "JOB-PORTAL-E01";
	public static final String BACK_END = "JOB-PORTAL-E02";
	public static final String API = "JOB-PORTAL-E03";

	// Type of module
	private static final String USER = "11";
	private static final String ROLE = "12";
	private static final String PERMISSION = "13";
	private static final String JOB = "14";
	private static final String USER_ROLE = "15";
	private static final String ROLE_PERMISSION = "16";
	private static final String USER_JOB = "17";
	private static final String DATA = "18";
	private static final String TOKEN = "19";
	private static final String URL = "20";
	private static final String FILE = "21";
	private static final String OTP = "22";
	private static final String GENERAL = "50";

	// Errors under user module
	private static final String USER_NOT_FOUND_CODE = "01";
	private static final String EMAIL_ALREADY_EXISTS_CODE = "02";
	private static final String INVALID_EMAIL_CODE = "03";
	private static final String INVALID_PASSWORD_CODE = "04";
	private static final String INVALID_EMAIL_OR_PASSWORD_CODE = "05";

	// Errors under role module
	private static final String ROLE_NOT_FOUND_CODE = "01";
	private static final String ROLE_ALREADY_EXISTS_CODE = "02";

	// Errors under permission module
	private static final String PERMISSION_NOT_FOUND_CODE = "01";
	private static final String PERMISSION_ALREADY_EXISTS_CODE = "02";

	private static final String JOB_NOT_FOUND_CODE = "01";
	private static final String JOB_ALREADY_EXISTS_CODE = "02";

	private static final String USER_ROLE_NOT_FOUND_CODE = "01";
	private static final String USER_ROLE_ALREADY_EXISTS_CODE = "02";

	private static final String ROLE_PERMISSION_NOT_FOUND_CODE = "01";
	private static final String ROLE_PERMISSION_ALREADY_EXISTS = "02";

	private static final String USER_JOB_NOT_FOUND_CODE = "01";
	private static final String USER_JOB_ALREADY_EXISTS_CODE = "02";

	private static final String INVALID_DATA_CODE = "01";
	private static final String DATA_ALREADY_PRESENT = "02";

	private static final String INVALID_TOKEN_CODE = "01";
	private static final String TOKEN_EXPIRED_CODE = "02";

	private static final String URL_NOT_FOUND_CODE = "01";
	private static final String INVALID_URL_CODE = "02";

	private static final String INVALID_FILE_FORMAT_CODE = "01";
	private static final String INVALID_FILE_DATA = "02";

	private static final String OTP_NOT_FOUND_CODE = "01";
	private static final String INVALID_OTP_CODE = "02";

	private static final String SOMETHING_WENT_WRONG = "01";
	/*
	 * Error key constants It is a combination of error source ,module and module
	 * error
	 */
	public static final String USER_E031101 = API + USER + USER_NOT_FOUND_CODE;
	public static final String USER_E031102 = API + USER + EMAIL_ALREADY_EXISTS_CODE;
	public static final String USER_E031103 = API + USER + INVALID_EMAIL_CODE;
	public static final String USER_E031104 = API + USER + INVALID_PASSWORD_CODE;
	public static final String USER_E031105 = API + USER + INVALID_EMAIL_OR_PASSWORD_CODE;

	public static final String ROLE_E031201 = API + ROLE + ROLE_NOT_FOUND_CODE;
	public static final String ROLE_E031202 = API + ROLE + ROLE_ALREADY_EXISTS_CODE;

	public static final String PERMISSION_E031301 = API + PERMISSION + PERMISSION_NOT_FOUND_CODE;
	public static final String PERMISSION_E031302 = API + PERMISSION + PERMISSION_ALREADY_EXISTS_CODE;

	public static final String JOB_E031401 = API + JOB + JOB_NOT_FOUND_CODE;
	public static final String JOB_E031402 = API + JOB + JOB_ALREADY_EXISTS_CODE;

	public static final String USER_ROLE_E031501 = API + USER_ROLE + USER_ROLE_NOT_FOUND_CODE;
	public static final String USER_ROLE_E031502 = API + USER_ROLE + USER_ROLE_ALREADY_EXISTS_CODE;

	public static final String ROLE_PERMISSION_E031601 = API + ROLE_PERMISSION + ROLE_PERMISSION_NOT_FOUND_CODE;
	public static final String ROLE_PERMISSION_E031602 = API + ROLE_PERMISSION + ROLE_PERMISSION_ALREADY_EXISTS;

	public static final String USER_JOB_E031701 = API + USER_JOB + USER_JOB_NOT_FOUND_CODE;
	public static final String USER_JOB_E031702 = API + USER_JOB + USER_JOB_ALREADY_EXISTS_CODE;

	public static final String DATA_E031801 = API + DATA + INVALID_DATA_CODE;
	public static final String DATA_E031802 = API + DATA + DATA_ALREADY_PRESENT;

	public static final String TOKEN_E031901 = API + TOKEN + INVALID_TOKEN_CODE;
	public static final String TOKEN_E031902 = API + TOKEN + TOKEN_EXPIRED_CODE;

	public static final String URL_E032001 = API + URL + URL_NOT_FOUND_CODE;
	public static final String URL_E032002 = API + URL + INVALID_URL_CODE;

	public static final String FILE_E032101 = API + FILE + INVALID_FILE_FORMAT_CODE;
	public static final String FILE_E032102 = API + FILE + INVALID_FILE_DATA;

	public static final String OTP_E032201 = API + OTP + OTP_NOT_FOUND_CODE;
	public static final String OTP_E032202 = API + OTP + INVALID_OTP_CODE;

	public static final String GENERAL_E035001 = API + GENERAL + SOMETHING_WENT_WRONG;

}
