package com.InShowVideo.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.InShowVideo.mapper.UsersMapper;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.services.usersService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class IUsersService implements usersService {
	
	@Autowired
	private UsersMapper userMapper;
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id",userId);
		Users user = userMapper.selectOneByExample(userExample);
		
		return user;
		

	}
	
}
