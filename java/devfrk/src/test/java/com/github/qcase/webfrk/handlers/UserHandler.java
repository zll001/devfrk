/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.handlers;

import com.github.qcase.webfrk.core.annotation.BeanDefinition;
import com.github.qcase.webfrk.core.spi.HttpBodyHandler;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @since   2018/5/3
 * 
 */ 
public class UserHandler extends HttpBodyHandler {

	@BeanDefinition(names= {"user"})
	public Object handleUser(User user) {
		return user;
	}
	
	@Override
	public String getOperation() {
		return "user";
	}

}
