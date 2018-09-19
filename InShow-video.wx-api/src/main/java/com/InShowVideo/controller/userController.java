package com.InShowVideo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.Users;
import com.InShowVideo.pojo.vo.UsersVO;
import com.InShowVideo.services.usersService;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
}
