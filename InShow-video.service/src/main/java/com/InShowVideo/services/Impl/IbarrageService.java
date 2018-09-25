package com.InShowVideo.services.Impl;

import java.util.Date;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InShowVideo.mapper.BarragesMapper;
import com.InShowVideo.pojo.Barrages;
import com.InShowVideo.services.barrageService;

@Service
public class IbarrageService implements barrageService {
	@Autowired
	private Sid sid;
	@Autowired
	private BarragesMapper barragesMapper;
	
	@Override
	public void saveBarrage(Barrages barrages) {
		String bid=sid.nextShort();
		barrages.setId(bid);
		barrages.setCreateTime(new Date());
		
		barragesMapper.insert(barrages);
		
	}

	@Override
	public List<Barrages> getAllBarrage(String videoId) {
		
		List<Barrages> list = barragesMapper.getAllBarrages(videoId);
		return list;
	}

}
