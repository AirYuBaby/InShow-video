package com.InShowVideo.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.services.bgmService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "bgm相关的Controller", tags = { "bgm相关业务的controller" })
@RequestMapping("/bgm")
public class bgmController extends BasicController {
	@Autowired
	private bgmService bservice;

	@ApiOperation(value = "获取bgm列表的接口", notes = "获取bgm列表的接口")
	@ApiImplicitParam(name = "page", value = "动态加载的数据页数（从0开始）", required = true, dataType = "int", paramType = "query")
	@PostMapping("/getAll")
	public JSONResult getBgmByPage(String page) {
		
		if (page == null) {
			return JSONResult.errorMsg("页码错误，你的小可爱呢不到视频啦");
		} else {
			
			int Page=Integer.valueOf(page).intValue();
			List<Bgm> list = bservice.getAllbgm(Page);
			return JSONResult.ok(list);
		}

	}

	@ApiOperation(value = "按热度获取bgm列表的接口", notes = "按热度获取bgm列表的接口")
	@ApiImplicitParam(name = "page", value = "动态加载的数据页数（从0开始）", required = true, dataType = "int", paramType = "query")
	@PostMapping("/getAllByHart")
	public JSONResult getBgmByHart(String page) {
		
		int Page=Integer.valueOf(page).intValue();
		if (Page >= 0) {
			List<Bgm> list = bservice.getBgmByChoose(Page);
			for (Bgm bgm : list) {
				System.out.println();
			}
			return JSONResult.ok(list);
		} else {
			return JSONResult.errorMsg("页码错误，你的小可爱呢不到视频啦");
		}
	}
}
