/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since  2018年5月3日
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanDefinition {

	String[] names() default {};
}
