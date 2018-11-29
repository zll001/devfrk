package com.bocloud.paas.auth.manager;

import java.util.List;

import com.bocloud.paas.auth.entity.User;

/**
 * 用户信息接口
 */
public interface UserManager {
	/**
	 * 查询用户详细信息
	 * @param userId
	 * @return
	 */
	public User getUserDetail(Integer userId);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int insert(User user);

	/**
	 * 用户列表
	 * @return
	 */
	public List<User> getUsers();
}
