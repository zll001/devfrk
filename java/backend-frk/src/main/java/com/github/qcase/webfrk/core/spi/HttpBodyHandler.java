/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.github.qcase.webfrk.core.HandlerManager;
import com.github.qcase.webfrk.core.HandlerManager.HandlerDesc;
import com.github.qcase.webfrk.core.HttpConstants;
import com.github.qcase.webfrk.core.HttpController;
import com.github.qcase.webfrk.core.HttpFrkUtils;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public abstract class HttpBodyHandler implements CommandLineRunner, ApplicationContextAware {

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
	
	protected static ApplicationContext ctx;
	
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
		if (classname.endsWith(POSTFIX) || classname.indexOf("/") != -1) {
			registerService(classname);
//			autowiredField(classname);
		} else {
			m_logger.error(HttpConstants.EXCEPTION_INVALID_SERVICE_ANOTATION);
		}
	}

	private void autowiredField(String classname) throws IllegalAccessException {
		for (Field field : getClass().getDeclaredFields()) {
			if (field.getAnnotation(Autowired.class) != null) {
				field.setAccessible(true);
				field.set(ctx.getBean(HttpFrkUtils.getName(
						classname)), ctx.getBean(field.getName()));
			}
		}
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (ctx == null) {
			ctx = applicationContext;
		}
	}

	private void registerService(String classname) {
		String name = classname.substring(0, classname.length() - POSTFIX.length());
		String serv = HttpFrkUtils.getName(name);
		
		for (Method method : getClass().getMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getAnnotation(BeanDefinition.class) != null) {
				configure.addHandler(serv + "/" + method.getName(), new HandlerDesc(method, getClass()));
				m_logger.info("servelet path '" + serv + "/" + method.getName()  + "' registered sucessful.");
			}
		}
	}

}
