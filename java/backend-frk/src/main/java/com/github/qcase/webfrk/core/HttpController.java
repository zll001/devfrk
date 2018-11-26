/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;


import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
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
	
	@RequestMapping(method = RequestMethod.GET, value = {"/get*", "/list*", "/query*"})
	public @ResponseBody String dispatchVaildGetRequest(
								HttpServletRequest request) throws Exception{
		return handleVaildHttpRequest(request, null);
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.DELETE, 
												RequestMethod.PUT}, value = {"/*"})
	public @ResponseBody String dispatchVaildPostOrPutOrDeleteRequest(
				HttpServletRequest request, @RequestBody JSONObject body) throws Exception{
		return handleVaildHttpRequest(request, body);
	}
	
	protected String handleVaildHttpRequest(
			HttpServletRequest request, 
			JSONObject body) throws Exception{
		String servletPath = request.getServletPath().substring(1);
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
