package com.InShowVideo.services.Impl;

import com.InShowVideo.mapper.UsersFansMapper;
import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.UsersFans;
import com.InShowVideo.services.solrService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.apache.http.MalformedChunkCodingException;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ISolrService implements solrService {
	//solr服务器地址，要带上对用的core  即：/solr后的字段
	String solrUrl = "http://192.168.1.9:8888/solr/inshow_video2";
	HttpSolrClient solrClient = null;

	@Autowired
	UsersFansMapper UFmapper;
	public ISolrService() {

		solrClient = new HttpSolrClient(solrUrl);
	}

	public void Textselectall() throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		query.setRows(10);
		QueryResponse response = solrClient.query(query);
		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			System.out.println(doc.get("id"));
		}
	}
	@SuppressWarnings("deprecation")
	public boolean reFleshSolrData() {
		return false;
	}
	/**
	 * 简单查询，提供分页功能
	 * 
	 * @param key
	 * @param start
	 * @param rows
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public SolrDocumentList getDocs(String key, int start, int rows) throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.set("q", key);

		if (rows != 0) {
			query.setStart(start);
			query.setRows(rows);
		}
		QueryResponse response = solrClient.query(query);
		SolrDocumentList docs = response.getResults();
		return docs;
	}

	/**
	 * 根据关键字搜索bgm，实现分页，默认每页10条记录
	 * 
	 * @param key
	 * @param page
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public List<Bgm> selectBgmByKey(String key, int page) throws SolrServerException, IOException {
		if(key==null)
			key = "";
		List<Bgm> blist = new ArrayList<Bgm>();
		SolrDocumentList docs = getDocs("b_name:*" + key + "*", page*10, 10);
		for (SolrDocument doc : docs) {
			Bgm bgm = new Bgm();

			bgm.setAuthor(String.valueOf(doc.get("b_author")));
			
			bgm.setChooseCount(Integer.valueOf(doc.get("b_chooseCount").toString()));
			
			bgm.setId(String.valueOf(doc.get("id")));
			bgm.setName(String.valueOf(doc.get("b_name")));
			bgm.setPath(String.valueOf(doc.get("b_path")));
			blist.add(bgm);
		}
		return blist;
	}
	/**
	 * 按关键字查询我的粉丝（key字段设置为""，则查找所有关注我的人）
	 * @param userid
	 * @param key
	 * @param page
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public List<Map> selectUserFansByKey(String userid, String key, int page)
			throws SolrServerException, IOException {
		if(key==null)
			key = "";
		List<Map> ulist = new ArrayList<Map>();
		SolrDocumentList udocs = getDocs("uf_user_id:" + userid, 0, 0);
		ArrayList<String> list = new ArrayList<String>();
		for (SolrDocument udoc : udocs) {
			System.out.println("---fansid-----" + udoc.get("uf_fan_id").toString());
			list.add(udoc.get("uf_fan_id").toString());
		}
		String[] fansIds = new String[list.size()];
		list.toArray(fansIds);
		
		String finalFansid = "";
		for(String id : fansIds) {
			finalFansid += (id+",");
		}

		SolrQuery query = new SolrQuery();
		// 设置查询条件
//		query.add("q", "u_username:*");
		String queryStr = "u_username:*"+ key + "* AND(";
		int i = 0;
		for(String id : fansIds) {
			if(i!=0)
				queryStr += (" OR id:"+id);
			else 
				queryStr += (" id:"+id);
			i++;
		}
		queryStr += (")");
		query.setQuery(queryStr);
		
		// 设置分页
		query.setStart(page*5);
		query.setRows(5);
		// 获取结果
		System.out.println("-------开始找关键字");
		QueryResponse response = solrClient.query(query);
		SolrDocumentList docs = response.getResults();
		System.out.println("--------list长度----" + docs.size());
		for (SolrDocument doc : docs) {
			
			if (doc != null) {
				System.out.println("----------" + doc.get("u_username").toString());
			}

		}

		for (SolrDocument doc : docs) {
			//查找粉丝关系表--》我是否关注了粉丝
			String fansid = doc.get("id").toString();
			Example Example = new Example(UsersFans.class);
			Criteria criteria = Example.createCriteria();
			criteria.andEqualTo("userId",fansid);
			criteria.andEqualTo("fanId",userid);
			List<UsersFans> list2 = UFmapper.selectByExample(Example);
			boolean isFans = (list2.size()!=0);
			
			
			Users user = new Users();
			
			if (doc != null) {
				user.setAvatarurl(doc.get("u_avatarUrl") == null ? "" : doc.get("u_avatarUrl").toString());
				user.setCity(doc.get("u_city") == null ? "" : doc.get("u_city").toString());
				user.setCountry(doc.get("u_country") == null ? "" : doc.get("u_country").toString());
				user.setFansCounts(
						Integer.valueOf(doc.get("u_fans_counts") == null ? "0" : doc.get("u_fans_counts").toString()));
				user.setFollowCounts(Integer
						.valueOf(doc.get("u_follow_counts") == null ? "0" : doc.get("u_follow_counts").toString()));
				user.setGender(Integer.valueOf(doc.get("u_gender").toString()));
				user.setId(doc.get("id").toString());
				user.setNickname(doc.get("u_nickname").toString());
				user.setProvince(doc.get("u_province").toString());
				user.setReceiveLikeCounts(Integer.valueOf(
						doc.get("u_receive_like_counts") == null ? "0" : doc.get("u_receive_like_counts").toString()));
				user.setReportCounts(Integer
						.valueOf(doc.get("u_report_counts") == null ? "0" : doc.get("u_report_counts").toString()));
				user.setUsername(doc.get("u_username").toString());

			}
			Map m = new HashMap();
			m.put("user", user);
			m.put("isFans", isFans);
			ulist.add(m);
		}
		
		System.out.println("------结束啦------");
		return ulist;
	}
	/**
	 * 根据关键字查询我关注的人（key字段设置为""，则查找所有我关注的人）
	 * @param userid
	 * @param key
	 * @param page
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public List<Users> selectUserFollorByKey(String userid, String key, int page)
			throws SolrServerException, IOException {
		if(key==null)
			key = "";
		List<Users> ulist = new ArrayList<Users>();
		SolrDocumentList udocs = getDocs("uf_fan_id:" + userid, 0, 0);
		ArrayList<String> list = new ArrayList<String>();
		for (SolrDocument udoc : udocs) {
			System.out.println("---userid-----" + udoc.get("uf_user_id").toString());
			list.add(udoc.get("uf_user_id").toString());
		}
		String[] fansIds = new String[list.size()];
		list.toArray(fansIds);
		
		String finalFansid = "";
		for(String id : fansIds) {
			finalFansid += (id+",");
		}

		SolrQuery query = new SolrQuery();
		// 设置查询条件
//		query.add("q", "u_username:*");
		String queryStr = "u_username:*"+ key + "* AND(";
		int i = 0;
		for(String id : fansIds) {
			if(i!=0)
				queryStr += (" OR id:"+id);
			else 
				queryStr += (" id:"+id);
			i++;
		}
		queryStr += (")");
		query.setQuery(queryStr);
		

		
		// 设置分页
		query.setStart(page*5);
		query.setRows(5);
		// 获取结果
		System.out.println("-------开始找关键字");
		QueryResponse response = solrClient.query(query);
		SolrDocumentList docs = response.getResults();
		System.out.println("--------list长度----" + docs.size());
		for (SolrDocument doc : docs) {
			
			if (doc != null) {
				System.out.println("----------" + doc.get("u_username").toString());
			}

		}

		for (SolrDocument doc : docs) {
			Users user = new Users();
			// System.out.println("---username---"+doc.get("u_username"));
			if (doc != null) {
				user.setAvatarurl(doc.get("u_avatarUrl") == null ? "" : doc.get("u_avatarUrl").toString());
				user.setCity(doc.get("u_city") == null ? "" : doc.get("u_city").toString());
				user.setCountry(doc.get("u_country") == null ? "" : doc.get("u_country").toString());
				user.setFansCounts(
						Integer.valueOf(doc.get("u_fans_counts") == null ? "0" : doc.get("u_fans_counts").toString()));
				user.setFollowCounts(Integer
						.valueOf(doc.get("u_follow_counts") == null ? "0" : doc.get("u_follow_counts").toString()));
				user.setGender(Integer.valueOf(doc.get("u_gender").toString()));
				user.setId(doc.get("id").toString());
				user.setNickname(doc.get("u_nickname").toString());
				user.setProvince(doc.get("u_province").toString());
				user.setReceiveLikeCounts(Integer.valueOf(
						doc.get("u_receive_like_counts") == null ? "0" : doc.get("u_receive_like_counts").toString()));
				user.setReportCounts(Integer
						.valueOf(doc.get("u_report_counts") == null ? "0" : doc.get("u_report_counts").toString()));
				user.setUsername(doc.get("u_username").toString());

			}

			ulist.add(user);
		}
		System.out.println("------结束啦------");
		return ulist;
	}

	public static void main(String[] args) throws SolrServerException, IOException {
		
		List<Map> ulist = new ISolrService().selectUserFansByKey("2", "", 0);
		
		
		List<Users> ulist2 = new ISolrService().selectUserFollorByKey("2", "", 0);
		
	}
}
