package com.jobportal.util;

import java.util.Random;

public class OtpUtil {

	public static String numbers = "0123456789";
	public static Random random = new Random();

	public static final String SUBJECT = "Otp for login";

	public static char[] otp() {
		char ch[] = new char[6];
		int i;
		for (i = 0; i < ch.length; i++) {
			ch[i] = numbers.charAt(random.nextInt(numbers.length()));
		}

		return ch;
	}

	public static String message(String otp) {

		String message = "Your otp for login is " + otp;

		return message;
	}

	public static String resetPasswordMessage(String otp) {

		String message = "Your otp for reset password is " + otp;

		return message;
	}
}
