package com.InShowVideo.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InShowVideo.mapper.BgmMapper;
import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.services.bgmService;
import com.github.pagehelper.PageHelper;
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

}
