package com.InShowVideo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.vo.topicsVO;
import com.InShowVideo.services.topicsService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="话题相关的Controller",tags= {"话题相关业务的controller"})
@RequestMapping("/topic")
public class topicsController extends BasicController {
	@Autowired
	private topicsService tService;
	@ApiOperation(value="获取话题列表的接口",notes="获取话题列表的接口")
	@ApiImplicitParam(name="page",value="动态加载的数据页数（从0开始）",required=true,dataType="int",paramType="query")
	@GetMapping("/getAll")
	public JSONResult getAllTopics(int page) {
		if(page>=0) {
			List<topicsVO>  list = tService.getAllTopic(page);
			JSONResult.ok(list);
		}
		return JSONResult.errorMsg("未知错误，你的小可爱迷路啦");
	}
	
	
	@ApiOperation(value="按热度获取话题列表的接口",notes="按热度获取话题列表的接口")
	@ApiImplicitParam(name="page",value="动态加载的数据页数（从0开始）",required=true,dataType="int",paramType="query")
	@GetMapping("/getHart")
	public JSONResult getAllTopicsByHart(int page) {
		if(page>=0) {
			List<topicsVO>  list = tService.getAllTopicByHart(page);
			JSONResult.ok(list);
		}
		return JSONResult.errorMsg("未知错误，你的小可爱迷路啦");
	}
	

}
