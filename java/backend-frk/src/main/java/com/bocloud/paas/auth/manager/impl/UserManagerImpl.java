package com.bocloud.paas.auth.manager.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.bocloud.paas.auth.entity.User;
import com.bocloud.paas.auth.manager.UserManager;
import com.bocloud.paas.auth.repository.UserMapper;
import com.github.qcase.webfrk.core.spi.ServiceDefinition;

/**
 * 用户服务实现类
 * 
 */
@ServiceDefinition
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User  getUserDetail(Integer userId) {
		User  userInfo = userMapper.getUser(userId);
		return userInfo;
	}

	@Override
	public List<User> getUsers() {
		List<User> userList = userMapper.getUsers();
		return userList;
	}

	@Override
	public int insert(User user) {
		return userMapper.insert(user);
	}

}
