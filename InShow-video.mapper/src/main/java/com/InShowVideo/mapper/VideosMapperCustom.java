package com.InShowVideo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.utils.MyMapper;
 
public interface VideosMapperCustom extends MyMapper<Videos> {
	
	/**
	 * @Description: 查询点赞视频
	 */
	public List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);
}