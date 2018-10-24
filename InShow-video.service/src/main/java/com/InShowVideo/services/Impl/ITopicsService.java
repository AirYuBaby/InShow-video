package com.InShowVideo.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.InShowVideo.mapper.TopicsMapper;
import com.InShowVideo.mapper.UsersMapper;
import com.InShowVideo.mapper.VideosMapper;
import com.InShowVideo.mapper.VideosMapperCustom;
import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Topics;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.pojo.vo.topicsVO;
import com.InShowVideo.services.topicsService;
import com.InShowVideo.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class ITopicsService implements topicsService {
	@Autowired
	private TopicsMapper tMapper;
	@Autowired
	private UsersMapper uMapper;
	@Autowired
	private VideosMapper videosMapper;
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	@Autowired
	private Sid sid;
	@Override
	public List<topicsVO> getAllTopic(int page) {
		PageHelper.startPage(page, 7);
		List<Topics> list = tMapper.selectAll();
		
		
		
		List<topicsVO> voList = new ArrayList<topicsVO>();
		String name = "";
		String avargUrl ="";
		for( Topics t: list ) {
			topicsVO tVO = new topicsVO();
			Example userExample = new Example(Users.class);
			Criteria c =  userExample.createCriteria();
			c.andEqualTo("id", t.getUserId());
			
			name = uMapper.selectOneByExample(userExample).getUsername();
			avargUrl=uMapper.selectOneByExample(userExample).getAvatarurl();
			
			tVO.setTopic(t);
			tVO.setUsername(name);
			tVO.setAvargUrl(avargUrl);
			voList.add(tVO);
		}
		
		return voList;
	}

	@Override
	public List<topicsVO> getAllTopicByHart(int page) {
		PageHelper.startPage(page, 7);
		List<Topics> list = tMapper.queryAllByHart();
		ArrayList<topicsVO> voList = new ArrayList<topicsVO>();
		String name = "";
		String avargUrl ="";
		for( Topics t: list ) {
			topicsVO tVO = new topicsVO();
			Example userExample = new Example(Topics.class);
			Criteria c =  userExample.createCriteria();
			c.andEqualTo("id", t.getUserId());
			
			name = uMapper.selectOneByExample(userExample).getUsername();
			avargUrl=uMapper.selectOneByExample(userExample).getAvatarurl();
			tVO.setAvargUrl(avargUrl);
			tVO.setTopic(t);
			tVO.setUsername(name);
			voList.add(tVO);
		}
		
		return voList;
	}

	@Override
	public void addVideotopic(String topicId) {
		
		tMapper.updateparticipationCounts(topicId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveTopic(Topics topic) {
		String tid=sid.nextShort();
		topic.setId(tid);
		tMapper.insertSelective(topic);
		return tid;
	}

	@Override
	public List<VideosVO> getAllVideoInTopic(int page,String topicId) {
		
		PageHelper.startPage(page, 4);
		List<VideosVO> videosVo = videosMapperCustom.getAllVideoInTopic(topicId);
		
		return videosVo;
	}
}
