package com.hframwork.result;

import org.springframework.http.HttpStatus;

public class Result<T> {
	/**
	 * 数据
	 */
	private T data;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 返回结果码
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> Result<T> error(T data, String message) {
		Result<T> result = new Result<T>();
		result.setData(data);
		result.setMessage(message);
		result.setSuccess(false);
		return result;
	}

	public static <T> Result<T> error(T data) {

		return error(data, null);
	}

	public static <T> Result<T> error(String message) {
		return error(null, message);
	}

	public static <T> Result<T> error() {
		return error(null, null);
	}

	public static <T> Result<T> success(T data, String message) {
		Result<T> result = new Result<T>();
		result.setData(data);
		result.setMessage(message);
		result.setSuccess(true);
		result.setCode(String.valueOf(HttpStatus.OK.value()));
		return result;
	}

	public static <T> Result<T> success(T data) {
		return success(data, null);
	}

	public static <T> Result<T> success(String message) {

		return success(null, message);
	}

	public static <T> Result<T> success() {

		return success(null, null);
	}

	public Result() {
		super();
	}

	public Result(T data, boolean success, String message, T date, String code) {
		super();
		this.data = data;
		this.success = success;
		this.message = message;
		this.code = code;
	}

}
