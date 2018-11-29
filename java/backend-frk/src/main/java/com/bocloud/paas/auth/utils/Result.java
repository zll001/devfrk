package com.bocloud.paas.auth.utils;

public class Result {

	private boolean success;
	private String message;
	private Object data;// 操作返回的数据
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	/**
	 * @param success
	 * @param data
	 * @param message
	 */
	public Result(boolean success, Object data, String message) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public Result() {
		super();
	}
	
	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + "]";
	}
	
}
