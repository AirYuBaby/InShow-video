package com.InShowVideo.services.Impl;
 
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.InShowVideo.mapper.UsersClickVideosMapper;
import com.InShowVideo.mapper.UsersLikeVideosMapper;
import com.InShowVideo.mapper.UsersMapper;
import com.InShowVideo.mapper.VideosMapper;
import com.InShowVideo.mapper.VideosMapperCustom;
import com.InShowVideo.pojo.UsersClickVideos;
import com.InShowVideo.pojo.UsersLikeVideos;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.services.videoService;
import com.InShowVideo.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class IVideoService implements videoService {
	@Autowired
	private VideosMapper videoMapper;
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	@Autowired
	private UsersLikeVideosMapper usersLikevideosMapper;
	@Autowired
	private UsersClickVideosMapper usersClickvideosMapper;
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllVideos(int page) {
		System.out.println("-------begin");
		PageHelper.startPage(page, 4);
		List<Videos> videos = videoMapper.selectAll();
		System.out.println(videos);
		System.out.println("----------开始分页");
		System.out.println("----------分");

		PagedResult pagedResult = new PagedResult();
		System.out.println("----------页");

		System.out.println("----------啦");

		pagedResult.setRows(videos);
		pagedResult.setPage(page);
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

	@Override
	public void userLikevideos(String userId, String videoId, String publisherId) {
		String ulvId=sid.nextShort();
		
		UsersLikeVideos ulv = new UsersLikeVideos();
		ulv.setId(ulvId);
		ulv.setUserId(userId);
		ulv.setVideoId(videoId);
		usersLikevideosMapper.insert(ulv);
		//增加视频的收藏数
		videosMapperCustom.addlikecountsByvideo(videoId);
		//增加用户的收藏视频数
		usersMapper.addreceiveLikeCounts(publisherId);
	}

	@Override
	public void userClickvideos(String userId, String videoId) {
		
		String ucvId =sid.nextShort();
		UsersClickVideos ucv =new UsersClickVideos();
		ucv.setId(ucvId);
		ucv.setUserId(userId);
		ucv.setVideoId(videoId);
		usersClickvideosMapper.insert(ucv);
		
		//增加视频的点赞数
		
		videosMapperCustom.addClickcountsByvideo(videoId);
	}

	@Override
	public PagedResult queryMyFollowerVideo(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyFollowerVideo(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		return pagedResult;		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		String vid=sid.nextShort();
		video.setId(vid);
		videoMapper.insertSelective(video);
		return vid;
	}

	@Override
	public void userunLikevideos(String userId, String videoId, String publisherId) {
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		usersLikevideosMapper.deleteByExample(example);
		//减少视频的收藏数
		videosMapperCustom.delectlikecountsByvideo(videoId);
		//减少发布者的被收藏数
		usersMapper.delectreceiveLikeCounts(publisherId);
	}

	@Override
	public void userUnclickVideos(String userId, String videoId, String publisherId) {
		Example example = new Example(UsersClickVideos.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		
		usersClickvideosMapper.deleteByExample(example);
		//减少视频的点赞数
		videosMapperCustom.delectClickcountsByvideo(videoId);
		
	}

	@Override
	public PagedResult getVideoByUserid(String userid, int page) {
		Example e = new Example(Videos.class);
		Criteria c =  e.createCriteria();
		c.andEqualTo("userId", userid);
		PageHelper.startPage(page, 10);
		List<Videos> list = videoMapper.selectByExample(e);
		PagedResult pr = new PagedResult();
		pr.setPage(page);
		pr.setRows(list);
		return pr;
	}
	


}
