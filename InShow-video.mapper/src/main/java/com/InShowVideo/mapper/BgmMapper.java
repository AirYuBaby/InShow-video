package com.InShowVideo.mapper;

import java.util.List;

import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.utils.MyMapper;

public interface BgmMapper extends MyMapper<Bgm> {
	
	public List<Bgm> queryByHart();
}