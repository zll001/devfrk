/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.core.handlers;

import org.springframework.stereotype.Component;

import cn.abcsys.faas.application.webfrk.core.spi.HttpBodyHandler;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since  2018年5月6日
 *
 */
@Component
public class CheckApplication extends HttpBodyHandler {

	/* (non-Javadoc)
	 * @see cn.abcsys.faas.application.webfrk.core.spi.HttpBodyHandler#getOperation()
	 */
	@Override
	public String getOperation() {
		return "checkApplication.do";
	}

}
