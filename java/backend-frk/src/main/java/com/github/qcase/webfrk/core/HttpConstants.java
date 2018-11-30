/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;

/**
 * @author wuheng@(iscas.ac.cn)
 * @since 2018/11/27
 * 
 */
public class HttpConstants {

	/*****************************************************************
	 * 
	 *                       HTTP Response
	 * 
	 *****************************************************************/
	public final static int HTTP_RESPONSE_STATUS_OK = 200;

	public final static int HTTP_RESPONSE_STATUS_FAILED = 500;
	
	/******************************************************************
	 * 
	 *                       Http Handler Exceptions
	 * 
	 ******************************************************************/
	
	public final static String EXCEPTION_INVALID_REQUEST_URL = "Invalid servlet path was requested";
	
	public final static String EXCEPTION_INVALID_BEANDEFINITION_ANOTATION = "Invalid BeanDefinition was requested";
	
	public final static String EXCEPTION_INVALID_SERVICE_ANOTATION = "Invalid ServiceDefinition was requested";
}
