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
	public void fansPickusers(String followId,String userId);
	/*
	 * 粉丝取消关注
	 */
	public void fansUnpickusers(String followId,String userId);
	/**
	 * 登陆.....老用户更新.....新用户写入
	 * @param openid
	 * @return
	 */
	public Users updataUser(Users user);
	
	public boolean isfansPickuser(String follewId,String userId);
	
	public Users isInDb(String openId);
}
