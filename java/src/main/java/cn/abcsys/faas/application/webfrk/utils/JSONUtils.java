/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.webfrk.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @since   2018/4/28
 */
public class JSONUtils {

	public static String toJSONString(Object obj) {
		if (ObjectUtils.isNull(obj)) {
			throw new NullPointerException("Object is null");
		}
		return JSON.toJSONString(obj);
	}

}
