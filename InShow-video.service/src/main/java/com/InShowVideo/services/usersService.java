package com.InShowVideo.services;

import com.InShowVideo.pojo.Users;

public interface usersService {
	/**
	 * 根据用户id查找用户信息
	 * @param userId
	 * @return
	 */
	public Users queryUserInfo(String userId);
	/**
	 * 判断用户是否收藏该视频
	 * @return
	 */
	public boolean isuserLikevideo(String loginuserId,String videoId);
	/**
	 * 判断用户是否點贊该视频
	 * @return
	 */
	public boolean isuserClickvideo(String loginuserId,String videoId);
	/**
	 * 粉丝关注用户
	 * @param userId
	 * @param fansId
	 */
	
	public void fansPickusers(String userId,String fansId);
}
