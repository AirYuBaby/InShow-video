package com.InShowVideo.services.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.InShowVideo.mapper.UsersClickVideosMapper;
import com.InShowVideo.mapper.UsersFansMapper;
import com.InShowVideo.mapper.UsersLikeVideosMapper;
import com.InShowVideo.mapper.UsersMapper;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.UsersClickVideos;
import com.InShowVideo.pojo.UsersFans;
import com.InShowVideo.pojo.UsersLikeVideos;
import com.InShowVideo.services.usersService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
 
@Service
public class IUsersService implements usersService {
	
	
	@Autowired
	private UsersMapper userMapper;
	@Autowired
	private Sid sid;
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	@Autowired
	private UsersClickVideosMapper usersClickVideosMapper;
	@Autowired
	private UsersFansMapper usersFansMapper;
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id",userId);
		Users user = userMapper.selectOneByExample(userExample);
		
		return user;

	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isuserLikevideo(String userId,String videoId) {
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);
		
		if (list != null && list.size() >0) {
			return true;
		}
		return false;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean isuserClickvideo(String userId, String videoId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		
		Example example = new Example(UsersClickVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		List<UsersClickVideos> list = usersClickVideosMapper.selectByExample(example);
		
		if (list != null && list.size() >0) {
			return true;
		}
		return false;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void fansPickusers(String followId,String userId)  {
        String relId = sid.nextShort();
		
		UsersFans userFan = new UsersFans();
		userFan.setId(relId);
		userFan.setUserId(followId);
		userFan.setFanId(userId);
		
		usersFansMapper.insert(userFan);
		
		//做个标记，不知道有没有写反了
		userMapper.addFansCounts(followId);
		userMapper.addFollowersCounts(userId);
		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users updataUser(Users user) {
		if(StringUtils.isNoneBlank(user.getOpenid())) {
			Example userExample = new Example(Users.class);
			Criteria c = userExample.createCriteria();
			c.andEqualTo("openid", user.getOpenid());
			List<Users> u2 = userMapper.selectByExample(userExample);
			if(u2.size()>0) {
				user.setId(u2.get(0).getId());
				
				userMapper.updateByPrimaryKey(user);
				return user;
			}else {
				user.setId(sid.nextShort());
				userMapper.insert(user);
				return user;
			}
		}
		return null;
	}
	@Override
	public void fansUnpickusers(String followId,String userId) {
		// TODO Auto-generated method stub
		Example example =new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId",followId);
		criteria.andEqualTo("fanId",userId);
		
		usersFansMapper.deleteByExample(example);
		
		userMapper.delectFansCounts(followId);
		userMapper.delectFollowersCounts(userId);
	}
	@Override
	public boolean isfansPickuser(String follewId, String userId) {
		
		if (StringUtils.isBlank(follewId) || StringUtils.isBlank(userId)) {
			return false;
		}
		
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", follewId);
		criteria.andEqualTo("fanId", userId);
		
		List<UsersFans> list = usersFansMapper.selectByExample(example);
		
		if (list != null && list.size() >0) {
			return true;
		}
		
		return false;
	}
	
	public Users isInDb(String openId) {
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("openid", openId);
		Users user = userMapper.selectOneByExample(example);
		return user;
	}
	
}
