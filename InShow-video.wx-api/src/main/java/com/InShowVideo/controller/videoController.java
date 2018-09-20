package com.InShowVideo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.mapper.VideosMapper;
import com.InShowVideo.services.Impl.IVideoService;
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
	private IVideoService iVideoService;
	
	@ApiOperation(value = "获取视频列表的接口")
	@ApiImplicitParam(name="page", value="页码（从0开始）",paramType = "query")
	@PostMapping("/getByPage")
	public JSONResult getVideoByPage(int page) {
		System.out.println("page-----------"+page);
		PagedResult result ;
		if(page >= 0) {
			result = iVideoService.getAllVideos(page);
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
		
		return JSONResult.ok();
	}
	

}
