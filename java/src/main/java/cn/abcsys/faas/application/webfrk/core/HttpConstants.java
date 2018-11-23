/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.webfrk.core;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2018年4月28日
 * 
 */
public class HttpConstants {

	/*****************************************************************
	 * 
	 *                       HTTP Request
	 * 
	 *****************************************************************/
	public final static String HTTP_REQUEST_PATH_INFO = "pathInfo";

	public final static String HTTP_REQUEST_USER_INFO = "userInfo";

	public final static String HTTP_REQUEST_PROXY_INFO = "proxyInfo";

	public final static String HTTP_REQUEST_PARAM_INFO = "paramInfo";
	
	public final static String HTTP_REQUEST_CONSTRAINTS = "constaints";
	
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
	
	public final static String EXCEPTION_MISSING_BEANDEFINITION_ANOTATION = "Annotation BeanDefinition is missing";
	
	public final static String EXCEPTION_INVALID_BEANDEFINITION_ANOTATION = "Annotation BeanDefinition is invalid";
}
