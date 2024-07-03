package com.jobportal.util;

public class SuccessKeyConstants {

	// Type of success key for front end,back end and api
	public static final String FRONT_END = "JOB-PORTAL-S01";
	public static final String BACK_END = "JOB-PORTAL-S02";
	public static final String API = "JOB-PORTAL-S03";

	// Type of module
	private static final String USER = "11";
	private static final String ROLE = "12";
	private static final String PERMISSION = "13";
	private static final String JOB = "14";
	private static final String USER_ROLE = "15";
	private static final String ROLE_PERMISSION = "16";
	private static final String USER_JOB = "17";
	private static final String TOKEN = "18";
	private static final String FILE = "19";
	private static final String OTP = "20";

	// Type of success message
	private static final String USER_REGISTERD_CODE = "01";
	private static final String USER_UPDATED_CODE = "02";
	private static final String USER_DELETED_CODE = "03";
	private static final String USER_FETCHED_CODE = "04";
	private static final String EMAIL_UPDATED_CODE = "05";
	private static final String PASSWORD_UPDATED_CODE = "06";
	private static final String USER_LOGIN_CODE = "07";

	private static final String ROLE_ADDED_CODE = "01";
	private static final String ROLE_UPDATED_CODE = "02";
	private static final String ROLE_DELETED_CODE = "03";
	private static final String ROLE_FETCHED_CODE = "04";

	private static final String JOB_ADDED_CODE = "01";
	private static final String JOB_UPDATED_CODE = "02";
	private static final String JOB_DELETED_CODE = "03";
	private static final String JOB_FETCHED_CODE = "04";

	private static final String PERMISSION_ADDED_CODE = "01";
	private static final String PERMISSION_UPDATED_CODE = "02";
	private static final String PERMISSION_DELETED_CODE = "03";
	private static final String PERMISSION_FETCHED_CODE = "04";
	private static final String PERMISSION_UPLOADED_CODE = "05";

	private static final String USER_ROLE_ADDED_CODE = "01";
	private static final String USER_ROLE_UPDATED_CODE = "02";
	private static final String USER_ROLE_DELETED_CODE = "03";
	private static final String USER_ROLE_FETCHED_CODE = "04";

	private static final String ROLE_PERMISSION_ADDED_CODE = "01";
	private static final String ROLE_PERMISSION_UPDATED_CODE = "02";
	private static final String ROLE_PERMISSION_DELETED_CODE = "03";
	private static final String ROLE_PERMISSION_FETCHED_CODE = "04";

	private static final String USER_JOB_APPLIED_CODE = "01";
	private static final String USER_JOB_UPDATED_CODE = "02";
	private static final String USER_JOB_DELETED_CODE = "03";
	private static final String USER_JOB_FETCHED_CODE = "04";

	private static final String TOKEN_GENERATED_CODE = "01";

	private static final String FILE_UPLOADED_CODE = "01";

	private static final String OTP_SENT_CODE = "01";
	private static final String OTP_VALIDATED_CODE = "02";
	/*
	 * Success key combination
	 */
	public static final String USER_S031101 = API + USER + USER_REGISTERD_CODE;
	public static final String USER_S031102 = API + USER + USER_UPDATED_CODE;
	public static final String USER_S031103 = API + USER + USER_DELETED_CODE;
	public static final String USER_S031104 = API + USER + USER_FETCHED_CODE;
	public static final String USER_S031105 = API + USER + EMAIL_UPDATED_CODE;
	public static final String USER_S031106 = API + USER + PASSWORD_UPDATED_CODE;
	public static final String USER_S031107 = API + USER + USER_LOGIN_CODE;

	public static final String ROLE_S031201 = API + ROLE + ROLE_ADDED_CODE;
	public static final String ROLE_S031202 = API + ROLE + ROLE_UPDATED_CODE;
	public static final String ROLE_S031203 = API + ROLE + ROLE_DELETED_CODE;
	public static final String ROLE_S031204 = API + ROLE + ROLE_FETCHED_CODE;

	public static final String PERMISSION_S031301 = API + PERMISSION + PERMISSION_ADDED_CODE;
	public static final String PERMISSION_S031302 = API + PERMISSION + PERMISSION_UPDATED_CODE;
	public static final String PERMISSION_S031303 = API + PERMISSION + PERMISSION_DELETED_CODE;
	public static final String PERMISSION_S031304 = API + PERMISSION + PERMISSION_FETCHED_CODE;
	public static final String PERMISSION_S031305 = API + PERMISSION + PERMISSION_UPLOADED_CODE;

	public static final String JOB_S031401 = API + JOB + JOB_ADDED_CODE;
	public static final String JOB_S031402 = API + JOB + JOB_UPDATED_CODE;
	public static final String JOB_S031403 = API + JOB + JOB_DELETED_CODE;
	public static final String JOB_S031404 = API + JOB + JOB_FETCHED_CODE;

	public static final String USER_ROLE_S031501 = API + USER_ROLE + USER_ROLE_ADDED_CODE;
	public static final String USER_ROLE_S031502 = API + USER_ROLE + USER_ROLE_UPDATED_CODE;
	public static final String USER_ROLE_S031503 = API + USER_ROLE + USER_ROLE_DELETED_CODE;
	public static final String USER_ROLE_S031504 = API + USER_ROLE + USER_ROLE_FETCHED_CODE;

	public static final String ROLE_PERMISSION_S031601 = API + ROLE_PERMISSION + ROLE_PERMISSION_ADDED_CODE;
	public static final String ROLE_PERMISSION_S031602 = API + ROLE_PERMISSION + ROLE_PERMISSION_UPDATED_CODE;
	public static final String ROLE_PERMISSION_S031603 = API + ROLE_PERMISSION + ROLE_PERMISSION_DELETED_CODE;
	public static final String ROLE_PERMISSION_S031604 = API + ROLE_PERMISSION + ROLE_PERMISSION_FETCHED_CODE;

	public static final String USER_JOB_S031701 = API + USER_JOB + USER_JOB_APPLIED_CODE;
	public static final String USER_JOB_S031702 = API + USER_JOB + USER_JOB_UPDATED_CODE;
	public static final String USER_JOB_S031703 = API + USER_JOB + USER_JOB_DELETED_CODE;
	public static final String USER_JOB_S031704 = API + USER_JOB + USER_JOB_FETCHED_CODE;

	public static final String TOKEN_S031801 = API + TOKEN + TOKEN_GENERATED_CODE;

	public static final String FILE_S031901 = API + FILE + FILE_UPLOADED_CODE;
	public static final String OTP_S032001 = API + OTP + OTP_SENT_CODE;
	public static final String OTP_S032002 = API + OTP + OTP_VALIDATED_CODE;
}
