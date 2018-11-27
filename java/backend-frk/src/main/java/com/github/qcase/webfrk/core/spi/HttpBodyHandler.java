/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.spi;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.github.qcase.webfrk.core.HttpController;
import com.github.qcase.webfrk.core.HandlerManager;
import com.github.qcase.webfrk.core.HandlerManager.HandlerDesc;
import com.github.qcase.webfrk.core.HttpConstants;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public abstract class HttpBodyHandler implements CommandLineRunner {

	public final static String POSTFIX = "Service";
	
	/**
	 * logger
	 */
	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**
	 * All handers
	 */
	@Autowired
	protected HandlerManager configure;
	
	/**
	 * 
	 */
	public HttpBodyHandler() {
		super();
	}

	/**********************************************************
	 * 
	 * 
	 * 
	 **********************************************************/
	
	@Override
	public void run(String... args) throws Exception {
		
		String classname = getClass().getSimpleName();
		if (!classname.endsWith(POSTFIX) || classname.indexOf("/") != -1) {
			m_logger.error(HttpConstants.EXCEPTION_INVALID_SERVICE_ANOTATION);
			return;
		}
		
		String name = classname.substring(0, classname.length() - POSTFIX.length());
		String serv = name.substring(0, 1).toLowerCase() + name.substring(1);
		
		for (Method method : getClass().getMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getAnnotation(BeanDefinition.class) != null) {
				configure.addHandler(serv + "/" + method.getName(), new HandlerDesc(method, getClass()));
				m_logger.info("servelet path '" + serv + "/" + method.getName()  + "' registered sucessful.");
			}
		}
	}
}
