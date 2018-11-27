/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.demo;

import java.util.Map;

import com.github.qcase.webfrk.core.spi.BeanDefinition;
import com.github.qcase.webfrk.core.spi.HttpBodyHandler;
import com.github.qcase.webfrk.core.spi.ServiceDefinition;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 *
 */
@ServiceDefinition
public class MockService extends HttpBodyHandler {

	@BeanDefinition
	public String createMock(Map<String, String> map) {
		return map.toString();
	}
	
	@BeanDefinition
	public String listMock() {
		return "Mock";
	}
	
	@BeanDefinition(names= {"A", "B"})
	public String deleteMock(String name, Map<String, String> map) {
		return name + map.toString();
	} 
	
	@BeanDefinition
	public void listMock2() {
		return;
	}
	
}
