package com.InShowVideo.services;

import com.InShowVideo.utils.PagedResult;
 
public interface videoService {
	
	public PagedResult getAllVideos(int page);
	public PagedResult qureyMyLikeVideo(String userId,Integer page ,Integer pageSize);

}
