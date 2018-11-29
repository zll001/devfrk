package com.bocloud.paas.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bocloud.paas.auth.entity.User;

/**
 * 用户信息接口
 *
 */
@Mapper
public interface UserMapper {
	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public User getUser(@Param("userId")Integer userId);
	
	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public List<User> getUsers();
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int insert(User user);
}
