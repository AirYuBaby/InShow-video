package com.InShowVideo.services;

import java.util.List;

import com.InShowVideo.pojo.Videos;
import com.InShowVideo.utils.PagedResult;

public interface videoService {
	
	public PagedResult getAllVideos(int page);

}
