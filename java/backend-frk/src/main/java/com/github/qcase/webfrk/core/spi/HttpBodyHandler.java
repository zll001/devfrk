/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.spi;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.github.qcase.webfrk.core.HttpController;
import com.github.qcase.webfrk.core.HttpHandlerManager;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public abstract class HttpBodyHandler implements CommandLineRunner {

	/**
	 * 
	 */
	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**********************************************************
	 * 
	 * 
	 * 
	 **********************************************************/
	
	@Autowired
	protected HttpHandlerManager configure;
	
	@Override
	public void run(String... args) throws Exception {
		configure.addHandler(getOperation(), getClass());
	}
	
	public abstract String getOperation();
	
	
}
