package com.InShowVideo.services;

import java.util.List;

import com.InShowVideo.pojo.Bgm;

public interface bgmService {
	/**
	 * 按页数获取全部bgm（无排序）
	 * @param page
	 * @return
	 */
	public List<Bgm> getAllbgm( int page);
	/**
	 * 按页数获取全部bgm（按热度排序）
	 * @param page
	 * @return
	 */
	public List<Bgm> getBgmByChoose(int page);
	/**
	 * bgm被选择，获取bgm信息
	 * @param bgmid
	 * @return
	 */
	public Bgm bgmBeChoose(String bgmid);
	
	
}
