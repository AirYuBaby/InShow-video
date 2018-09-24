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
import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Topics;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.vo.topicsVO;
import com.InShowVideo.services.topicsService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class ITopicsService implements topicsService {
	@Autowired
	private TopicsMapper tMapper;
	@Autowired
	private UsersMapper uMapper;
	@Autowired
	private Sid sid;
	@Override
	public List<topicsVO> getAllTopic(int page) {
		PageHelper.startPage(page, 10);
		List<Topics> list = tMapper.selectAll();
		
		System.out.println("--------topic--userid----------------"+list.get(0).getUserId());
		
		ArrayList<topicsVO> voList = new ArrayList<topicsVO>();
		String name = "";
		topicsVO tVO = new topicsVO();
		for( Topics t: list ) {
			Example userExample = new Example(Topics.class);
			Criteria c =  userExample.createCriteria();
			c.andEqualTo("id", t.getUserId());
			
			name = uMapper.selectOneByExample(userExample).getUsername();
			
			System.out.println("----------getname-----------"+name);
			tVO.setTopic(t);
			tVO.setUsername(name);
			voList.add(tVO);
		}
		
		return voList;
	}

	@Override
	public List<topicsVO> getAllTopicByHart(int page) {
		PageHelper.startPage(page, 10);
		List<Topics> list = tMapper.queryAllByHart();
		ArrayList<topicsVO> voList = new ArrayList<topicsVO>();
		String name = "";
		topicsVO tVO = new topicsVO();
		for( Topics t: list ) {
			Example userExample = new Example(Topics.class);
			Criteria c =  userExample.createCriteria();
			c.andEqualTo("id", t.getUserId());
			
			name = uMapper.selectOneByExample(userExample).getUsername();
			tVO.setTopic(t);
			tVO.setUsername(name);
			voList.add(tVO);
		}
		
		return voList;
	}

	@Override
	public void addVideotopic(String topicId) {
		System.out.println("+++++++++++++++++****************"+topicId);
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
}
