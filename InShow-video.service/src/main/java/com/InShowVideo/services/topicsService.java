package com.InShowVideo.services;

import java.util.List;

import com.InShowVideo.pojo.Topics;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.pojo.vo.topicsVO;
import com.InShowVideo.utils.PagedResult;

public interface topicsService {
	/**
	 * 获取话题列表（分页）
	 * @param page
	 * @return
	 */
	public List<topicsVO> getAllTopic(int page);
	/**
	 * 根据热度获取话题列表（分页）
	 * @param page
	 * @return
	 */
	public List<topicsVO> getAllTopicByHart(int page);
	/**
	 * 用户发布视频参与某话题
	 * @param topicId
	 */
	public void  addVideotopic(String topicId);
	
	public String saveTopic(Topics topic);
	
	public List<VideosVO> getAllVideoInTopic(int page,String topicId);
}
