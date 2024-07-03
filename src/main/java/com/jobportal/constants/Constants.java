package com.jobportal.constants;

public class Constants {

	public static final String EMAIL_REGEX = "[a-z0-9A-Z][a-z0-9A-Z_.]*@[a-z0-9A-Z]+([.][a-zA-Z]+)+";

	public static final String PASSWORD_REGEX = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
			+ "(?=\\S+$).{8,25}$";

	public static final String NAME_REGEX = "^[a-zA-Z]+([\\s.]+[a-zA-Z]+)*$";

	public static final String PHONE_REGEX = "^(\\+91|0)?[6-9]\\d{9}$";

	public static final String PERMISSION_NAME_REGEX = "^[A-Za-z]+$";

	public static final String OTP_REGEX = "\\b\\d{6}\\b";

	public static final String EMAIL_SENDER = "balaji995588@gmail.com";

	public static final String REGISTRATION_SUBJECT = "Registration Successful";

	public static final String LOGIN_OTP = "Otp for login";

	public static final String PASSWORD_RESET_OTP = "Otp for reset password";

	public static final String ADMIN = "Admin";

	public static final String USER = "User";

	public static final String RECRUITER = "Recruiter";

	public static final String SUCCESS_STATUS = "Success";

	public static final String ERROR_STATUS = "Error";

	public static final String PAGE_SIZE = "10";

	public static final String PAGE_NUMBER = "1";

	public static final String SORT_BY = "";

	public static final String IS_ADMIN = "IsAdmin";

	public static final String IS_RECRUITER = "IsRecruiter";

	public static final String IS_USER = "IsUser";

	public static final String[] columnNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH",
			"AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
			"BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR",
			"BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ",
			"CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB",
			"DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT",
			"DU", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL",
			"EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD",
			"FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV",
			"FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN",
			"GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF",
			"HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX",
			"HY", "HZ" };

	public static final String[] permissionheaders = { "PermisionName", "Controller", "Url", "Method" };

}
