package com.InShowVideo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.services.videoService;
import com.InShowVideo.utils.JSONResult;
import com.InShowVideo.utils.PagedResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
 
@RestController
@Api(value="视频相关操作的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class videoController extends BasicController{
	@Autowired
	private videoService videoService;
	
	@ApiOperation(value = "获取视频列表的接口")
	@ApiImplicitParam(name="page", value="页码（从0开始）",paramType = "query")
	@PostMapping("/getByPage")
	public JSONResult getVideoByPage(int page) {
		System.out.println("page-----------"+page);
		PagedResult result ;
		if(page >= 0) {
			result = videoService.getAllVideos(page);
		}else {
			return JSONResult.errorMsg("页码发生错误，你的小可爱取不到数据啦");
		}
		
		return JSONResult.ok(result);
		
	}
	@ApiOperation(value = "获取我收藏的视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户Id",paramType = "form"),
		@ApiImplicitParam(name="page", value="页码（从0开始）",paramType = "form"),
		@ApiImplicitParam(name="pageSize", value="每页展示的视频数量",paramType = "form")
	})
	@PostMapping("/showMylike")
	public JSONResult showMylike(String userId,Integer page,Integer pageSize) {
		
		if(StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("");
		}
		if(page==null) {
			page=1;
		}
		if(pageSize==null) {
			pageSize=4;
		}
		PagedResult videoList = videoService.qureyMyLikeVideo(userId, page, pageSize);
		return JSONResult.ok(videoList);
	}
	@ApiOperation(value = "获取我收藏的视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户Id",paramType = "form"),
		@ApiImplicitParam(name="page", value="页码（从0开始）",paramType = "form"),
		@ApiImplicitParam(name="pageSize", value="每页展示的视频数量",paramType = "form")
	})
	@PostMapping("/showmyfollers")
	public JSONResult showmyfollers(String userId,Integer page,Integer pageSize) {
		if(StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("");
		}
		if(page==null) {
			page=1;
		}
		if(pageSize==null) {
			pageSize=4;
		}
		PagedResult videoList=videoService.queryMyFollowerVideo(userId, page, pageSize);
		return JSONResult.ok();
	}
	
	
	@ApiOperation(value = "关注的用户发布的视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户Id",paramType = "form"),
		@ApiImplicitParam(name="videoId", value="视频Id",paramType = "form"),
		@ApiImplicitParam(name="publisherId", value="发布者Id",paramType = "form")
	})
	@PostMapping("/userLikevideo")
	public JSONResult userLikevideo(String userId,String videoId,String publisherId) {
		
		videoService.userLikevideos(userId, videoId, publisherId);
		return JSONResult.ok();
	}
	@ApiOperation(value = "用户点赞视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户Id",paramType = "form"),
		@ApiImplicitParam(name="videoId", value="视频Id",paramType = "form"),
	})
	@PostMapping("/userClickvideo")
	public JSONResult userClickvideo(String userId,String videoId) {
		videoService.userClickvideos(userId, videoId);
		return JSONResult.ok();
	}
}
