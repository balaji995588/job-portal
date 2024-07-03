package com.jobportal.exception;

public class InvalidEmailOrPasswordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private String message;

	@Override
	public String toString() {
		return "InvalidEmailOrPasswordException [key=" + key + ", message=" + message + "]";
	}

	public InvalidEmailOrPasswordException(String key, String message) {
		super();
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
