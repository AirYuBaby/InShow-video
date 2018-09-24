package com.InShowVideo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.vo.UsersVO;
import com.InShowVideo.pojo.vo.publisherVideo;
import com.InShowVideo.services.usersService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
 
@RestController
@Api(value="用户相关业务的接口",tags= {"用户相关业务的controller"})
@RequestMapping("/user")
public class userController {
	
	@Autowired
	private usersService userservice;
	@ApiOperation(value="查询用户信息",notes="查询用户信息的接口")
	@ApiImplicitParam(name="userId",value="用户ID",required=true,dataType="String",paramType="query")
	@PostMapping("/query")
	public JSONResult query(String userId) throws Exception{
		
		
		if(StringUtils.isBlank(userId)) {
			return JSONResult.errorMap("用户id不能为空");
		}
		Users userInfo = userservice.queryUserInfo(userId);
		UsersVO usersVo =new UsersVO();
		BeanUtils.copyProperties(userInfo, usersVo);
		return JSONResult.ok(usersVo);
		
	}
	@ApiOperation(value="查询发布页信息",notes="查询查询发布页的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="loginuserId",value="登陆者ID",required=true,dataType="String",paramType="form"),
			@ApiImplicitParam(name="videoId",value="视频ID",required=true,dataType="String",paramType="form"),
			@ApiImplicitParam(name="publisherId",value="发布者ID",required=true,dataType="String",paramType="form")
	})
	@PostMapping("/qureyPublisher")
	public JSONResult qureyPublisher(String loginuserId,String videoId,String publisherId) {
		
		if(StringUtils.isBlank(publisherId)) {
			return JSONResult.errorMap("发布者id不能为空");
		}
		//查询发布者信息
		Users userInfo =userservice.queryUserInfo(publisherId);
		UsersVO usersPublisher=new UsersVO();
		BeanUtils.copyProperties(userInfo, usersPublisher);
		//查询用户是否收藏该视频
		boolean userLikevideo =userservice.isuserLikevideo(loginuserId,videoId);
		//查询用户是否点赞该视频
		boolean userClickvideo =userservice.isuserClickvideo(loginuserId, videoId);
		publisherVideo pv =new publisherVideo();
		pv.setPublisher(usersPublisher);
		pv.setUserLikevideo(userLikevideo);
		pv.setUserClickvideo(userClickvideo);	
		return JSONResult.ok(pv);
	}
	@ApiOperation(value="粉丝关注用户的信息",notes="粉丝关注用户的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value="用户ID",required=true,dataType="String",paramType="form"),
			@ApiImplicitParam(name="fansId",value="粉丝ID",required=true,dataType="String",paramType="form")
	})
	@PostMapping("/fanspick")
	public JSONResult fanspick(String userId,String fansId) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(fansId)) {
			return JSONResult.errorMsg("");
		}
		userservice.fansPickusers(userId, fansId);
		return JSONResult.ok("关注成功");
	}
	
	@ApiOperation(value="粉丝取消关注的接口",notes="粉丝取消关注用户的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value="用户ID（就是我的id）",required=true,dataType="String",paramType="form"),
			@ApiImplicitParam(name="followId",value="被关注人id（就是对方的id）",required=true,dataType="String",paramType="form")
	})
	@PostMapping("/fanspick")
	public JSONResult fansUnpick(String userId,String followId) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(followId)) {
			return JSONResult.errorMsg("数据出错");
		}
//		userservice.fansUnpickUsers(userId, followId);
		return JSONResult.ok("关注成功");
	}
}
