package com.InShowVideo.mapper;
 
import java.util.List;

import com.InShowVideo.pojo.Barrages;
import com.InShowVideo.utils.MyMapper;

public interface BarragesMapper extends MyMapper<Barrages> {
	public List<Barrages> getAllBarrages(String videoId);
}