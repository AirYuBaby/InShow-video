package com.InShowVideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.UsersReport;
import com.InShowVideo.services.reportService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "举报相关操作的接口", tags = { "举报相关业务的controller" })
@RequestMapping("/report")
public class reportController {
	@Autowired
	public reportService reportService;
	
	@ApiOperation(value = "举报视频的接口")
	@PostMapping("/userReportvideos")
	public JSONResult userReportvideos(@RequestBody UsersReport userReport) {
		
		reportService.saveReport(userReport);
		return JSONResult.ok("举报成功");
	}
}
