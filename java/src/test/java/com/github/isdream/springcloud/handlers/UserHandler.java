/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.isdream.springcloud.handlers;

import cn.abcsys.faas.application.webfrk.core.annotation.BeanDefinition;
import cn.abcsys.faas.application.webfrk.core.spi.HttpBodyHandler;

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
