/**
 * Copyright (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.qcase.webfrk;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.qcase.webfrk.core.HandlerManager;
import com.github.qcase.webfrk.core.HttpController;
import com.github.qcase.webfrk.core.spi.BeanDefinition;
import com.github.qcase.webfrk.core.spi.ServiceDefinition;

/**
 * @author wuheng(@iscas.ac.cn)
 * @date 2018/11/27
 * @desc
 */
public class SpecTest {
	
	public final static String CODEBASE = "src/main/java";
	
	public final static String JAVA = ".java";
	
	public final static Set<String> ignoreClass = new HashSet<String>();
	
	static {
		ignoreClass.add(HttpController.class.getName());
		ignoreClass.add(BeanDefinition.class.getName());
		ignoreClass.add(ServiceDefinition.class.getName());
		ignoreClass.add(HandlerManager.class.getName());
	}
	
	public final static Set<String> blacklist = new HashSet<String>();
	
	static {
		blacklist.add(RequestMapping.class.getName());
		blacklist.add(GetMapping.class.getName());
		blacklist.add(PostMapping.class.getName());
		blacklist.add(DeleteMapping.class.getName());
		blacklist.add(PutMapping.class.getName());
		blacklist.add(RestController.class.getName());
		blacklist.add(Controller.class.getName());
		blacklist.add(ControllerAdvice.class.getName());
		blacklist.add(RequestBody.class.getName());
		blacklist.add(ResponseBody.class.getName());
		blacklist.add(Component.class.getName());
		blacklist.add(Service.class.getName());
		blacklist.add(Bean.class.getName());
	}
	
	public final static Map<String, Set<String>> errors = new HashMap<String, Set<String>>();
	
	@Test
	public void check() throws Exception {
		list(null, new File(CODEBASE));
		if (!errors.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			for (String name : errors.keySet()) {
				sb.append("class '" + name + "' violates constraints: " 
							+ errors.get(name)).append("\n");
			}
			throw new Exception(sb.toString());
		}
	}
	
	protected void list(String base, File file) throws Exception {
		for (String f : file.list()) {
			File nfile = new File(file.getAbsolutePath(), f);
			if (nfile.isDirectory()) {
				String nbase = (base == null) ? nfile.getName() :
					         		base + "." + nfile.getName();
				list(nbase, nfile);
			} else if (nfile.getName().endsWith(JAVA)) {
				String name = base + "." + nfile.getName().substring(
						0, nfile.getName().length() - JAVA.length());
				
				if (ignoreClass.contains(name)) {
					continue;
				}
				checkClass(name);
				checkMethod(name);
				checkField(name);
			}
		}
	}

	private void checkField(String name) throws ClassNotFoundException {
		// filed
		for (Field filed : Class.forName(name).getFields()) {
			for (Annotation a : filed.getAnnotations()) {
				if (blacklist.contains(a.annotationType().getName())) {
					Set<String> set = errors.get(name);
					if (set == null) {
						set = new HashSet<String>();
						errors.put(name, set);
					}
					
					if (!set.contains(a.getClass().getName())) {
						set.add(a.annotationType().getName());
					}
				}
			} 
		}
	}

	private void checkMethod(String name) throws ClassNotFoundException {
		// method
		for (Method method : Class.forName(name).getMethods()) {
			for (Annotation a : method.getAnnotations()) {
				if (blacklist.contains(a.annotationType().getName())) {
					Set<String> set = errors.get(name);
					if (set == null) {
						set = new HashSet<String>();
						errors.put(name, set);
					}
					
					if (!set.contains(a.getClass().getName())) {
						set.add(a.annotationType().getName());
					}
				}
			} 
			
			for (Parameter param : method.getParameters()) {
				for (Annotation a : param.getAnnotations()) {
					if (!a.annotationType().getName().startsWith("javax.validation.constraints")) {
						Set<String> set = errors.get(name);
						if (set == null) {
							set = new HashSet<String>();
							errors.put(name, set);
						}
						
						if (!set.contains(a.getClass().getName())) {
							set.add(a.annotationType().getName());
						}
					}
				} 
			}
		}
	}

	private void checkClass(String name) throws ClassNotFoundException {
		// class
		for (Annotation a : Class.forName(name).getAnnotations()) {
			if (blacklist.contains(a.annotationType().getName())) {
				Set<String> set = errors.get(name);
				if (set == null) {
					set = new HashSet<String>();
					errors.put(name, set);
				}
				
				if (!set.contains(a.getClass().getName())) {
					set.add(a.annotationType().getName());
				}
			}
		}
	}
}
