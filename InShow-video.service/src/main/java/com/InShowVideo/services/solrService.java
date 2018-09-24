package com.InShowVideo.services;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Users;

public interface solrService {
	/**
	 * 根据关键字搜索bgm，实现分页，默认每页10条记录
	 * 
	 * @param key
	 * @param page
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public List<Bgm> selectBgmByKey(String key, int page)throws SolrServerException, IOException;
	/**
	 * 按关键字查询我的粉丝（key字段设置为""，则查找所有关注我的人）
	 * @param userid
	 * @param key
	 * @param page
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public List<Users> selectUserFansByKey(String userid, String key, int page)throws SolrServerException, IOException ;
	/**
	 * 根据关键字查询我关注的人（key字段设置为""，则查找所有我关注的人）
	 * @param userid
	 * @param key
	 * @param page
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public List<Users> selectUserFollorByKey(String userid, String key, int page)throws SolrServerException, IOException;
}
