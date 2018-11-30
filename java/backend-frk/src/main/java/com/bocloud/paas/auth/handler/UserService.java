package com.bocloud.paas.auth.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bocloud.paas.auth.entity.User;
import com.bocloud.paas.auth.manager.UserManager;
import com.github.qcase.webfrk.core.spi.BeanDefinition;
import com.github.qcase.webfrk.core.spi.HttpBodyHandler;
import com.github.qcase.webfrk.core.spi.ServiceDefinition;

@ServiceDefinition
public class UserService extends HttpBodyHandler {
	
	@Autowired
	private UserManager userManager;
	
//	@Log(module = BoCloudService.Bmp, target = "登录", operation = "登录页面初始化")
	@BeanDefinition
	public List<User> list() {
		List<User> users=null;
		try {
			users=userManager.getUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
}
