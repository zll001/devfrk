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

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public abstract class HttpBodyHandler implements CommandLineRunner {

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
		for (Method method : getClass().getMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getAnnotation(BeanDefinition.class) != null) {
				configure.addHandler(method.getName(), new HandlerDesc(method, getClass()));
			}
		}
	}
}
