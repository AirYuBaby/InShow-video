package com.InShowVideo.controller;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.InShowVideo.utils.AES_128_CBC;
import com.InShowVideo.utils.HttpRequest;
import com.InShowVideo.utils.JSONResult;
 
public class loginController extends BasicController{
//	@Autowired
//	private userService service;
	HttpRequest http = new HttpRequest();
	AES_128_CBC cbc = new AES_128_CBC();
	public JSONResult login(
			String js_code,
			String encryptedData,
			String iv,
			String nickName,
			String avatarUrl,
			String gender,
			String city,
			String province,
			String country) {
		if(StringUtils.isNotBlank(js_code)) {
			//appid跟appsecret都是注册小程序后才存在的常量
			String appID = "";
			String appSecret = "";
			
			String wxUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+appSecret+"&js_code="+js_code+"&grant_type=authorization_code";
			String wxdata = http.httpsRequest(wxUrl, "post", null);
			
			JSONObject data = new JSONObject(wxdata);
			
			String openid = data.getString("openId");
			
			
			String key = data.getString("session_key");
		}
		
		
		return JSONResult.ok();
	}

}
