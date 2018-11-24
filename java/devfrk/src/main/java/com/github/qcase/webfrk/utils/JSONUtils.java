/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author wuheng(@iscas.ac.cn)
 * @since   2018/11/24
 */
public class JSONUtils {

	/**
	 * convert Java object to JSON String
	 * 
	 * @param obj   obejct
	 * @return      return JSON String   
	 */
	public static String toJSONString(Object obj) {
		if (ObjectUtils.isNull(obj)) {
			throw new NullPointerException("Object is null");
		}
		return JSON.toJSONString(obj);
	}

}
