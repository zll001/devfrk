/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core;

import com.alibaba.fastjson.JSON;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public class HttpFrkUtils {

	/**
	 * convert Java object to JSON String
	 * 
	 * @param obj   obejct
	 * @return      return JSON String   
	 */
	public static String toJSONString(Object obj) {
		if (isNull(obj)) {
			throw new NullPointerException("Object is null");
		}
		return JSON.toJSONString(obj);
	}

	/**
	 * check whether the object is empty or not
	 * 
	 * @param obj  object
	 * @return     return true if 'obj' is not null, otherwise return false
	 */
	public static boolean isNull (Object obj) {
		return (obj == null) ? true : false;
	}
	
	public static String getName(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
}
