/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;

import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @since   2018/11/24
 */
@Component
public class HttpHandlerConfigure  {

	protected Properties handlers = new Properties();

	public void addHandler(String kind, Class<?> clazz) {
		handlers.put(kind, clazz);
	}

	public Class<?> geHandler(String kind) throws Exception {
		return (Class<?>) handlers.get(kind);
	}


}
