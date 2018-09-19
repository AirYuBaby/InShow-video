package com.InShowVideo.controller;

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
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="视频相关操作的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class videoController extends BasicController{
	@Autowired
	private IVideoService iVideoService;
	
	@ApiOperation(value = "获取视频列表的接口")
	@ApiImplicitParam(name="page", value="页码（从0开始）",dataType = "query")
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
	
	

}
