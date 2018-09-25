package com.InShowVideo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.InShowVideo.pojo.Barrages;
import com.InShowVideo.services.barrageService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
 
@RestController
@Api(value = "弹幕相关操作的接口", tags = { "弹幕相关业务的controller" })
@RequestMapping("/barrage")
public class barrageController {
	@Autowired
	public barrageService barrageService;
	@ApiOperation(value = "发布弹幕的接口")
	@PostMapping("/videoBarrage")
	public JSONResult videoBarrage(@RequestBody Barrages barrage) {
		barrageService.saveBarrage(barrage);
		return JSONResult.ok("发布成功");
	}
	@ApiOperation(value = "获取所有弹幕的接口")
	@ApiImplicitParam(name="videoId",value="视频id",required=true,dataType="String",paramType="query")
	@PostMapping("/showAllBarrages")
	public JSONResult showAllBarrages(String videoId) {
		List<Barrages> list = barrageService.getAllBarrage(videoId);
		for (Barrages barrages : list) {
			System.out.println(list);
		}
		return JSONResult.ok(list);
	}
}
