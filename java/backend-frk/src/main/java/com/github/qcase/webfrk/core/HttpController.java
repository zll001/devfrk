/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;


import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.qcase.webfrk.core.annotation.BeanDefinition;
import com.github.qcase.webfrk.core.spi.HttpBodyHandler;
import com.github.qcase.webfrk.utils.JSONUtils;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 * 
 * The {@code HttpController} class is used to dispatch request 
 * to the related handler, if the handler is not found, it would 
 * throw an exception. 
 */
@RestController
@ComponentScan
public class HttpController {

	/**
	 * logger 
	 */
	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**
	 * handler means how to deal with the request for 
	 * specified servletPath 
	 */
	@Autowired
	protected HttpHandlerManager handlers;
	
	/**
	 * @param request    servlet path should be startwith 'get', 'list', or 'query'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.GET, value = {"/get*", "/list*", "/query*"})
	public @ResponseBody String dispatchVaildGetRequest(HttpServletRequest request, 
						@RequestBody(required = false)  JSONObject body) throws Exception{
		if (request.getParameterMap().size() != 0) {
			throw new Exception("Unsupport parameters for 'Get' request");
		}
 		return handleHttpRequest(request, null);
	}
	
	/**
	 * @param request     servlet path cannot be startwith 'get', 'list', or 'query'
	 * @param body        the body must be json format
	 * @return            the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception  it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.DELETE, 
												RequestMethod.PUT}, value = {"/*"})
	public @ResponseBody String dispatchVaildPostOrPutOrDeleteRequest(
				HttpServletRequest request, @RequestBody JSONObject body) throws Exception{
		// in fact, it can be checked by regular expression at @RequestMapping,
		// and we would improve it later
		String spath = request.getServletPath().substring(1);
		if (spath.startsWith("get") || spath.startsWith("list") 
										|| spath.startsWith("query")) {
			throw new Exception("Invalid servlet path was requested");
		}
		return handleHttpRequest(request, body);
	}
	
	protected String handleHttpRequest( HttpServletRequest request, 
												JSONObject body) throws Exception{
//		m_logger.info("Begin to deal with " + request.getServletPath());
//		
//		Method targetMethod = getTargetMethod(servletPath);
//		
//		Object returnObject = targetMethod.invoke(
//						getInstance(servletPath), 
//						getParams(body, targetMethod));
//		
//		m_logger.info("Successfully deal with " + request.getServletPath());
//		return JSON.toJSONString(returnObject);
		return "Hello";
	}
	
	@RequestMapping("/*/**")
	@ResponseBody
	public String handleInvalidHttpRequestURL(HttpServletRequest request) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
						+ " the reason is: " + HttpConstants.EXCEPTION_INVALID_REQUEST_URL);
		return JSONUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED
        				, HttpConstants.EXCEPTION_INVALID_REQUEST_URL));
	}
	
	@ExceptionHandler
	@ResponseBody
	public String handleInvalidHttpRequestException(HttpServletRequest request, Exception e) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
									+ ", the reason is: " + String.valueOf(e.getMessage()));
        return JSONUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED, String.valueOf(e.getMessage())));
	}
	
	
	/**************************************************
	 * 
	 * 
	 * 
	 **************************************************/

	protected HttpBodyHandler getInstance(String servletPath)
			throws InstantiationException, IllegalAccessException, Exception {
		return (HttpBodyHandler) handlers.geHandler(servletPath).newInstance();
	}
	
	protected Object[] getParams(JSONObject body, Method targetMethod) {
		Class<?>[] pTypes = targetMethod.getParameterTypes();
		String[] pNames = targetMethod.getAnnotation(BeanDefinition.class).names();
		
		Object[] pObjects = (pTypes.length != 0) ? new Object[pTypes.length] : null;		
		for (int i = 0; i < pTypes.length; i++) {
			pObjects[i] = body.getObject(pNames[i], pTypes[i]);
		}
		return pObjects;
	}

	protected Method getTargetMethod(String servletPath) throws Exception {
		for (Method method : handlers.geHandler(servletPath).getMethods()) {
			if (method.isAnnotationPresent(BeanDefinition.class)) {
				return method;
			}
		}
		throw new HttpHandlerException(HttpConstants.EXCEPTION_MISSING_BEANDEFINITION_ANOTATION);
	}
}
