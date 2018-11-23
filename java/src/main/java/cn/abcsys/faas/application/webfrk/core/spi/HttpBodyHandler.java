/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.webfrk.core.spi;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import cn.abcsys.faas.application.webfrk.core.HttpController;
import cn.abcsys.faas.application.webfrk.core.HttpHandlerConfigure;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @since   2018/4/28
 */
public abstract class HttpBodyHandler implements CommandLineRunner {

	public final static Logger m_logger = Logger.getLogger(HttpController.class);
	
	/**********************************************************
	 * 
	 * 
	 * 
	 **********************************************************/
	
	@Autowired
	protected HttpHandlerConfigure configure;
	
	@Override
	public void run(String... args) throws Exception {
		configure.addHandler(getOperation(), getClass());
	}
	
	public abstract String getOperation();
	
	
}
