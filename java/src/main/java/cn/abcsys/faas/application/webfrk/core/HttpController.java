/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.webfrk.core;


import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.abcsys.faas.application.webfrk.core.annotation.BeanDefinition;
import cn.abcsys.faas.application.webfrk.core.spi.HttpBodyHandler;
import cn.abcsys.faas.application.webfrk.utils.JSONUtils;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @since   2018/4/28
 */
@RestController
@ComponentScan
public class HttpController {

	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	@Autowired
	protected HttpHandlerConfigure configure;
	
	@RequestMapping("/*")
	@ResponseBody
	public String handleVaildHttpRequest(
							HttpServletRequest request, 
							@RequestBody JSONObject body) throws Exception{
		String servletPath = getOperator(request, body);
		m_logger.info("Begin to deal with " + request.getServletPath());
		
		Method targetMethod = getTargetMethod(servletPath);
		
		Object returnObject = targetMethod.invoke(
						getInstance(servletPath), 
						getParams(body, targetMethod));
		
		m_logger.info("Successfully deal with " + request.getServletPath());
		return JSON.toJSONString(returnObject);
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
	protected String getOperator(HttpServletRequest request, 
							JSONObject body) {
		return request.getServletPath().substring(1);
	}

	protected HttpBodyHandler getInstance(String servletPath)
			throws InstantiationException, IllegalAccessException, Exception {
		return (HttpBodyHandler) configure.geHandler(servletPath).newInstance();
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
		for (Method method : configure.geHandler(servletPath).getMethods()) {
			if (method.isAnnotationPresent(BeanDefinition.class)) {
				return method;
			}
		}
		throw new HttpHandlerException(HttpConstants.EXCEPTION_MISSING_BEANDEFINITION_ANOTATION);
	}
}
