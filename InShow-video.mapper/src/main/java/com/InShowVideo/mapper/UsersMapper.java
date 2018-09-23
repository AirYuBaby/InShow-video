package com.InShowVideo.mapper;

import org.apache.ibatis.annotations.Param;

import com.InShowVideo.pojo.Users;
import com.InShowVideo.utils.MyMapper;
 
public interface UsersMapper extends MyMapper<Users> {
	/**
	 * 用户增加粉丝数
	 * @param userId
	 */
	public void addFansCounts(@Param("userId")String userId);
	/**
	 * 用户增加关注数
	 * @param fansId
	 */
	
	public void addFollowersCounts(@Param("userId")String userId);
	/**
	 * 
	 */
	public void addreceiveLikeCounts(@Param("publisherId")String publisherId);
}