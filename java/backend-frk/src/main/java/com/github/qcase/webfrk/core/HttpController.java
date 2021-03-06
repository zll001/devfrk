/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;


import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.qcase.webfrk.core.spi.BeanDefinition;

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
public class HttpController implements ApplicationContextAware {

	/**
	 * logger 
	 */
	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**
	 * handler means how to deal with the request for 
	 * specified servletPath 
	 */
	@Autowired
	protected HandlerManager handlers;
	
	protected static ApplicationContext ctx;
	
	/**
	 * @param request    servlet path should be startwith 'get', 'list', or 'query'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.GET, value = {"/**/get*", "/**/list*", "/**/query*"})
	public @ResponseBody String dispatchVaildGetRequest(HttpServletRequest request, 
						@RequestBody(required = false)  JSONObject body) throws Exception{
		if (request.getParameterMap().size() != 0) {
			throw new Exception("Unsupport parameters for 'Get' request");
		}
 		return handleHttpRequest(getServletPath(request), body);
	}
	
	/**
	 * @param request     servlet path cannot be startwith 'get', 'list', or 'query'
	 * @param body        the body must be json format
	 * @return            the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception  it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = {"/**/*"})
	public @ResponseBody String dispatchVaildPostOrPutRequest(
				HttpServletRequest request, @RequestBody JSONObject body) throws Exception{
		// in fact, it can be checked by regular expression at @RequestMapping,
		// and we would improve it later
		String path = getServletPath(request);
		int length  = path.indexOf("/");
		String spath = path.substring(length + 1);
		if (spath.startsWith("get") || spath.startsWith("list") 
										|| spath.startsWith("query") 
										|| spath.startsWith("delete")
										|| spath.startsWith("remove")) {
			throw new Exception(HttpConstants.EXCEPTION_INVALID_REQUEST_URL);
		}
		return handleHttpRequest(spath, body);
	}
	
	/**
	 * @param request    servlet path should be startwith 'delete', or 'remove'
	 * @return           the {@code HttpBodyHandler} result. In fact, it may be an exception.
	 * @throws Exception it can be any exception that {@code HttpBodyHandler} throws
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = {"/**/delete*", "/**/remove*"})
	public @ResponseBody String dispatchVaildDeleteRequest(HttpServletRequest request, 
												@RequestBody  JSONObject body) throws Exception{
 		return handleHttpRequest(getServletPath(request), body);
	}
	
	/**
	 * @param servletPath
	 * @param body
	 * @return
	 * @throws Exception
	 */
	protected String handleHttpRequest(String servletPath, 
								JSONObject body) throws Exception{
		
		m_logger.info("Begin to deal with " + servletPath);
		
		try {
			Method target = handlers.geHandler(servletPath).getMethod();
			Object[] params = getParams(body, target);
			Object returnObject = null;
			if (params != null) {
				returnObject = target.invoke(
							getInstance(servletPath), 
							params);
			} else {
				returnObject = target.invoke(
						getInstance(servletPath));
			}
			m_logger.info("Successfully deal with " + servletPath);
			HttpResponse resp = new HttpResponse(HttpConstants
					.HTTP_RESPONSE_STATUS_OK, returnObject);
			return JSON.toJSONString(resp);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(HttpConstants.EXCEPTION_INVALID_BEANDEFINITION_ANOTATION);
		}
		
	}
	
	@RequestMapping(value = {"/*", "/*/*/**"})
	@ResponseBody
	public String handleInvalidHttpRequestURL(HttpServletRequest request) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
						+ " the reason is: " + HttpConstants.EXCEPTION_INVALID_REQUEST_URL);
		return HttpFrkUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED
        				, HttpConstants.EXCEPTION_INVALID_REQUEST_URL));
	}
	
	@ExceptionHandler
	@ResponseBody
	public String handleInvalidHttpRequestException(HttpServletRequest request, Exception e) {
		m_logger.error("Fail to deal with " + request.getServletPath() 
									+ ", the reason is: " + String.valueOf(e.getMessage()));
        return HttpFrkUtils.toJSONString(
        		new HttpResponse(HttpConstants.HTTP_RESPONSE_STATUS_FAILED, String.valueOf(e.getMessage())));
	}
	
	
	/**************************************************
	 * 
	 * 
	 * 
	 **************************************************/

	protected String getServletPath(HttpServletRequest request) {
		return request.getRequestURI().substring(
				request.getContextPath().length() + 1);
	}
	
	protected Object getInstance(String servletPath) throws Exception {
		String name = HttpFrkUtils.getName(handlers.geHandler(
							servletPath).getClazz().getSimpleName());
		return ctx.getBean(name);
	}
	
	protected Object[] getParams(JSONObject body, Method targetMethod) {
		
		Class<?>[] pTypes = targetMethod.getParameterTypes();
		String[] pNames = targetMethod.getAnnotation(BeanDefinition.class).names();
		
		if (pTypes.length == 1 && pNames.length == 0) {
			return new Object[] {body.toJavaObject(pTypes[0])};
		}
		
		Object[] pObjects = (pTypes.length != 0) ? new Object[pTypes.length] : null;		
		for (int i = 0; i < pTypes.length; i++) {
			pObjects[i] = body.getObject(pNames[i], pTypes[i]);
		}
		return pObjects;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (ctx == null) {
			ctx = applicationContext;
		}
	}

}
