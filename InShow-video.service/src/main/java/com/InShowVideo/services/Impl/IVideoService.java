package com.InShowVideo.services.Impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InShowVideo.mapper.VideosMapper;
import com.InShowVideo.mapper.VideosMapperCustom;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.services.videoService;
import com.InShowVideo.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class IVideoService implements videoService {
	@Autowired
	private VideosMapper videoMapper;
	@Autowired
	private VideosVO videosVO;
	
	private VideosMapperCustom videosMapperCustom;
	
	public PagedResult getAllVideos(int page) {
		System.out.println("-------begin");
		List<Videos> videos = videoMapper.selectAll();
		System.out.println(videos);
		System.out.println("----------开始分页");
		PageInfo<Videos> pageList = new PageInfo<>(videos);
		System.out.println("----------分");

		PagedResult pagedResult = new PagedResult();
		System.out.println("----------页");

		pagedResult.setTotal(pageList.getPages());
		System.out.println("----------啦");

		pagedResult.setRows(videos);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		System.out.println("-------end");
		return pagedResult;
	}

	@Override
	public PagedResult qureyMyLikeVideo(String userId, Integer page, Integer pageSize) {
		
  
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}

}
