package com.InShowVideo.services;

import com.InShowVideo.utils.PagedResult;
 
public interface videoService {
	
	/**
	 * 获取视频列表
	 * @param page
	 * @return
	 */
	public PagedResult getAllVideos(int page);
	/**
	 * 获取用户收藏的视频列表
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResult qureyMyLikeVideo(String userId,Integer page ,Integer pageSize);
	/**
	 * 用户收藏视频
	 * @param userId
	 * @param videoId
	 * @param publisherId
	 */
	public void userLikevideos(String userId,String videoId,String publisherId);
}
