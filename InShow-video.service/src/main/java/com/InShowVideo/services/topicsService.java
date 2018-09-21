package com.InShowVideo.services;

import java.util.List;

import com.InShowVideo.pojo.Topics;
import com.InShowVideo.pojo.vo.topicsVO;

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

}
