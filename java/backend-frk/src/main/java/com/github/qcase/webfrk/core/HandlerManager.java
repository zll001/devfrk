/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
@Component
public class HandlerManager  {

	protected Map<String, HandlerDesc> handlers = new HashMap<String, HandlerDesc>();

	
	public HandlerManager() {
		super();
	}

	public void addHandler(String kind, HandlerDesc hp) {
		handlers.put(kind, hp);
	}

	public HandlerDesc geHandler(String kind) throws Exception {
		return handlers.get(kind);
	}

	public static class HandlerDesc {
		
		protected final Method method;
		
		protected final Class<?> clazz;

		public HandlerDesc(Method method, Class<?> clazz) {
			super();
			this.method = method;
			this.clazz = clazz;
		}

		public Method getMethod() {
			return method;
		}

		public Class<?> getClazz() {
			return clazz;
		}
	}
}
