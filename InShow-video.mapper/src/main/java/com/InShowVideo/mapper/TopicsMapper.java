package com.InShowVideo.mapper;

import java.util.List;

import com.InShowVideo.pojo.Topics;
import com.InShowVideo.utils.MyMapper;

public interface TopicsMapper extends MyMapper<Topics> {
	public List<Topics> queryAllByHart();
 }