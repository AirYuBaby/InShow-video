package com.InShowVideo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Users;
import com.InShowVideo.services.solrService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(value="跟搜索相关的Controller",tags= {"搜索相关业务的controller"})
@RequestMapping("/search")
public class searchController {
	@Autowired
	private solrService sservice;
	@ApiOperation(value="按关键字搜索bgm列表的接口",notes="按关键字搜索bgm列表的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="key",value="关键字",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="page",value="页数（从0开始）",required=true,dataType="int",paramType="query")
	})
	@GetMapping("/getBgmByKey")
	public JSONResult searchBgm(String key,int page) throws SolrServerException, IOException{
		
		List<Bgm> list = sservice.selectBgmByKey(key, page);
		
		return JSONResult.ok(list);
	}
	
	
	
	@ApiOperation(value="按关键字搜索粉丝列表的接口",notes="按关键字搜索粉丝列表的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userid",value="用户id",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="key",value="关键字",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="page",value="页数（从0开始）",required=true,dataType="int",paramType="query")
	})
	@GetMapping("/getFansByKey")
	public JSONResult searchFansByKey(String userid,String key,int page) throws SolrServerException, IOException{
		List<Map> list = sservice.selectUserFansByKey(userid, key, page);
		return JSONResult.ok(list) ;
	}
	@ApiOperation(value="按关键字搜索关注列表的接口",notes="按关键字搜索关注列表的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userid",value="用户id",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="key",value="关键字",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name="page",value="页数（从0开始）",required=true,dataType="int",paramType="query")
	})
	@GetMapping("/getFollowByKey")
	public JSONResult searchFollowByKey(String userid,String key,int page) throws SolrServerException, IOException{
		List<Users> list = sservice.selectUserFollorByKey(userid, key, page);
		return JSONResult.ok(list);
	}
}






