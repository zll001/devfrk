/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.utils;

import java.util.Properties;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since  2018年5月7日
 *
 */
public class ConvertorUtils {

	public static String rename(Properties prop, String key) {
		return prop.getProperty(key);
	}
}
