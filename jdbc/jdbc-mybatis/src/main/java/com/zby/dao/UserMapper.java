package com.zby.dao;

import com.zby.entity.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User user);

	int insertSelective(User user);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User user);

	int updateByPrimaryKey(User user);
}