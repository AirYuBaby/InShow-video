package com.InShowVideo.services;

import java.util.List;

import com.InShowVideo.pojo.Barrages;

public interface barrageService {
	public void saveBarrage(Barrages barrages);
	public List<Barrages> getAllBarrage(String videoId);
}
