package com.InShowVideo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.pojo.Users;
import com.InShowVideo.services.Impl.IUsersService;
import com.InShowVideo.utils.AES_128_CBC;
import com.InShowVideo.utils.HttpRequest;
import com.InShowVideo.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@Api(value="用户登陆模块")
public class loginController<K> extends BasicController{
	@Autowired
	private IUsersService service;
	HttpRequest http = new HttpRequest();
	AES_128_CBC cbc = new AES_128_CBC();
	@ApiOperation(value = "用户登录访问的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="js_code", value="唯一登陆码", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="encryptedData", value="加密数据字符串", required=false, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="iv", value="初始加密向量", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="nickName", value="昵称", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="avatarUrl", value="头像地址", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="gender", value="性别", required=true, 
				dataType="int", paramType="query"),
		@ApiImplicitParam(name="city", value="城市", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="province", value="省份", required=true, 
				dataType="String", paramType="query"),
		@ApiImplicitParam(name="country", value="国家", required=true, 
				dataType="String", paramType="query")
	})
	@PostMapping("/login")
	public JSONResult login(
			String js_code,
			String encryptedData,
			String iv,
			String nickName,
			String avatarUrl,
			int gender,
			String city,
			String province,
			String country) {
		if(StringUtils.isNotBlank(js_code)) {
			//appid跟appsecret都是注册小程序后才存在的常量
			System.out.println("----------"+js_code+"-------"+nickName);
			String appID = "";
			String appSecret = "";
			//测试用假数据
			String openid = "1111";
			try {
				String wxUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+appSecret+"&js_code="+js_code+"&grant_type=authorization_code";
				String wxdata = http.httpsRequest(wxUrl, "GET", null);
				
				System.out.println("------------"+wxdata);
				JSONObject data = new JSONObject(wxdata);
				
				openid = data.getString("openId");
				String key = data.getString("session_key");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Users user = new Users();
			user.setOpenid(openid);
			user.setUsername(nickName);
			user.setNickname(nickName); 
			user.setAvatarurl(avatarUrl); 
//			user.setGender(Integer.valueOf(gender)); 
			user.setGender(gender);
			user.setCity(city);
			user.setProvince(province); 
			user.setCountry(country); 
			
			Users u2 = service.updataUser(user);
			if(u2!=null) {
				String token = setUserRedisSessionToken(u2);
				Map map = new HashMap<>();
				System.out.println("token-----------"+redis.get(USER_REDIS_SESSION + ":" + u2.getId()));
				map.put("userid", u2.getId());
				map.put("token", token);
				
				JSONResult.ok(map);
			}else {
				JSONResult.errorMsg("未知错误，登陆信息无法校验");
			}
		}
		
		
		return JSONResult.errorMsg("未知错误");
	}
	public String setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		return uniqueToken;
	}

}
