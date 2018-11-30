/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 * 
 * <p>
 * The {@code HttpResponse} class represents the return
 * value should be bound to the web response body.
 */
public class HttpResponse {

	/**
	 * neither 200 or 500
	 */
	protected int status;
	
	/**
	 * it represents the exception information,
	 * otherwise it should be null
	 */
	protected String message;
	
	/**
	 * if it is not an exception, the response
	 * is the object.
	 */
	protected Object result;
	
	public HttpResponse() {
		super();
	}
	
	public HttpResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public HttpResponse(int status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
