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
	 * 用户减少粉丝数
	 * @param userId
	 */
	public void delectFansCounts(@Param("userId")String userId);
	/**
	 * 用户增加关注数
	 * @param fansId
	 */
	
	public void addFollowersCounts(@Param("userId")String userId);
	/**
	 * 用户减少关注数
	 * @param userId
	 */
	public void delectFollowersCounts(@Param("userId")String userId);
	/**
	 * 用户增加被收藏数
	 */
	public void addreceiveLikeCounts(@Param("publisherId")String publisherId);
	/**
	 * 用户减少被收藏数
	 * @param publisherId
	 */
	public void delectreceiveLikeCounts(@Param("publisherId")String publisherId);
}