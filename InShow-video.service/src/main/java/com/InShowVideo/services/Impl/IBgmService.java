package com.InShowVideo.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InShowVideo.mapper.BgmMapper;
import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.services.bgmService;
import com.InShowVideo.utils.JSONResult;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class IBgmService implements bgmService {
	@Autowired
	private BgmMapper bmapper;
	@Override
	public List<Bgm> getAllbgm(int page) {
		PageHelper.startPage(page, 10);
		List<Bgm> bgms = bmapper.selectAll();
		return bgms;
	}
	@Override
	public List<Bgm> getBgmByChoose(int page) {
		PageHelper.startPage(page, 10);
		List<Bgm> bgms = bmapper.queryByHart();
		return bgms;
	}
	@Override
	public Bgm bgmBeChoose(String bgmid) {
		Example bgmExample = new Example(Users.class);
		Criteria criteria = bgmExample.createCriteria();
		criteria.andEqualTo("id",bgmid);
		
		Bgm bgm = bmapper.selectOneByExample(bgmExample);
		if(bgm!=null) {
			bgm.setChooseCount(bgm.getChooseCount()+1);
			bmapper.updateByPrimaryKey(bgm);
			return bgm;
		}
		return null;
	}
	

}
