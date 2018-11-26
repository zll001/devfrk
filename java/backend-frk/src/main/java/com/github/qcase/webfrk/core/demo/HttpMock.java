/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.demo;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.qcase.webfrk.core.spi.BeanDefinition;
import com.github.qcase.webfrk.core.spi.HttpBodyHandler;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 *
 */
@Component
public class HttpMock extends HttpBodyHandler {

	@BeanDefinition
	public String createMock(Map<String, String> map) {
		return map.toString();
	}
	
	@BeanDefinition
	public String listMock() {
		return "This is Mock";
	}
	
	@BeanDefinition(names= {"A", "B"})
	public String deleteMock(String name, Map<String, String> map) {
		return name + map.toString();
	} 
	
}
