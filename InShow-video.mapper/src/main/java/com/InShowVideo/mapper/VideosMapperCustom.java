package com.InShowVideo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.utils.MyMapper;
 
public interface VideosMapperCustom extends MyMapper<Videos> {
	
	/**
	 * @Description: 查询收藏视频
	 */
	public List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);
	/**
	 * 查询我关注的人的视频列表
	 * @param userId
	 * @return
	 */
	public List<VideosVO> queryMyFollowerVideo(@Param("userId")String userId);
	/**
	 * @Description: 增加视频收藏数
	 * @param videoId
	 */
	public void addlikecountsByvideo(@Param("videoId") String videoId);
	/**
	 * 减少视频收藏数
	 * @param videoId
	 */
	public void delectlikecountsByvideo(@Param("videoId")String videoId);
	/**
	 * 增加视频的点赞数
	 * @param videoId
	 */
	
	public void addClickcountsByvideo(@Param("videoId")String videoId);
}