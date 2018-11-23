/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.webfrk.core;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since  2018年4月28日
 * 
 * <p>
 * The {@code HttpResponse} class represents the return
 * value should be bound to the web response body.
 */
public class HttpHandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056743329986575387L;
	
	
	protected int status;

	public HttpHandlerException(int status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}


	public HttpHandlerException(int status, String message) {
		super(message);
		this.status = status;
	}


	public HttpHandlerException(String message) {
		super(message);
	}


	public int getStatus() {
		return status;
	}

}
