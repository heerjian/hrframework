package com.hframwork.exception;

public class FileNotFindException extends RuntimeException {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	public FileNotFindException() {
		super();
	}

	public FileNotFindException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileNotFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileNotFindException(String message) {
		super(message);
	}

	public FileNotFindException(Throwable cause) {
		super(cause);
	}

}
