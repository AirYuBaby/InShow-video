package com.InShowVideo.services;

import com.InShowVideo.pojo.Videos;
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
	/**
	 * 用户取消收藏视频
	 * @param userId
	 * @param videoId
	 * @param publisherId
	 */
	public void userunLikevideos(String userId,String videoId,String publisherId);
	/**
	 * 获取我关注的用户的视频列表
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResult queryMyFollowerVideo(String userId,Integer page, Integer pageSize);
	/**
	 * 用户点赞视频
	 * @param userId
	 * @param videoId
	 */
	
	public void userClickvideos(String userId,String videoId);
	
	public String saveVideo(Videos video);
}
