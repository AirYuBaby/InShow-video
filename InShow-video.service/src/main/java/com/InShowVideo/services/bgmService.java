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
	 * bgm被选择时使用，choose_count+1
	 * @param bgmid
	 * @return
	 */
	public boolean bgmBeChoose(String bgmid);
}
