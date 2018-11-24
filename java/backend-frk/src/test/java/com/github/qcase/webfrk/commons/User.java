/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk.commons;

import javax.validation.constraints.NotNull;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since  2018年5月6日
 *
 */
public class User {

	@NotNull
	protected String name;
	
	protected int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public static void main(String [] args) {
		User user = new User();
		user.setAge(11);
		user.setName(null);
		System.out.println(user);
	}
}
