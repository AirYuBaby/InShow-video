package com.InShowVideo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
 
import com.InShowVideo.pojo.Users;
import com.InShowVideo.services.Impl.IUsersService;
import com.InShowVideo.utils.AES_128_CBC;
import com.InShowVideo.utils.HttpRequest;
import com.InShowVideo.utils.JSONResult;
import com.google.gson.Gson;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(value="用户登陆模块")
@RequestMapping("/login")
public class loginController<K> extends BasicController{
	@Autowired
	private IUsersService service;
	HttpRequest http = new HttpRequest();
	AES_128_CBC cbc = new AES_128_CBC();
	WxMaJscode2SessionResult session = null;
	@ApiOperation(value = "用户登录访问的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="js_code", value="唯一登陆码", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="rawsData", value="JSON格式user数据", required=true, 
		dataType="String", paramType="form"),
		@ApiImplicitParam(name="appID", value="appId", required=true, 
		dataType="String", paramType="form"),
		@ApiImplicitParam(name="appSecret", value="appSecret", required=true, 
		dataType="String", paramType="form"),
		//query
	}) 
	//@RequestBody
	@PostMapping("/login")
	public JSONResult login( String js_code,
			 String rawsData,
			 String appID,
			 String appSecret) {
//		if(StringUtils.isNotBlank(jsCode)) {
			//appid跟appsecret都是注册小程序后才存在的常量
		System.out.println("----appID-----"+appID);
		System.out.println("****appSecret******"+appSecret);
			if(js_code.indexOf("{")==0) {
				js_code = new JSONObject(js_code).getString("js_code");
			}
			
//			String appID = "wx1d4ad7d9f581827a";
//			String appSecret = "91bc5b1a6aa524fbc9f47318da4cac15";
//			String appID = "wx2d76ca429ca918c5";
//			String appSecret = "ea4732a65b68b241581a167e58899967";
			//测试用假数据
			String openid = "";
			String code =js_code;
			System.out.println("******************************"+code);
			try {
				String wxUrl ="https://api.weixin.qq.com/sns/jscode2session?appid="+appID.trim()+"&secret="+appSecret.trim()+"&js_code="+js_code.trim()+"&grant_type=authorization_code".trim();
				//String wxUrl ="https://api.weixin.qq.com/sns/jscode2session?appid={appID}&secret={appSecret}&js_code={js_code}&grant_type=authorization_code";
				String wxdata = http.httpsRequest(wxUrl, "GET", null);
				System.out.println("------------"+wxdata);
				JSONObject data = new JSONObject(wxdata);
				System.out.println("********************************"+data.toString());
				openid = data.getString("openid");
				String key = data.getString("session_key");
				
				JSONObject userInfo = new JSONObject(rawsData);
				System.out.println("********************************"+userInfo.toString());
//				RestTemplate restTemplate =new RestTemplate();
//				
//				ResponseEntity<String> responseEntity =restTemplate.exchange(wxUrl, HttpMethod.GET,null,String.class);
//				if(responseEntity!=null&&responseEntity.getStatusCode()==HttpStatus.OK) {
//					String sessionData =responseEntity.getBody();
//					Gson gson = new Gson();
//					data = gson.fromJson(sessionData, JSONObject.class);
//					openid = data.getString("openId");
//					String key = data.getString("session_key");
//				}
				


				System.out.println(openid);
				Users user = new Users();
				user.setOpenid(openid);
				user.setUsername(userInfo.getString("nickName"));
				user.setNickname(userInfo.getString("nickName")); 
				user.setAvatarurl(userInfo.getString("avatarUrl")); 
//				user.setGender(Integer.valueOf(gender)); 
				user.setGender(userInfo.getInt("gender"));
				user.setCity(userInfo.getString("city"));
//				user.setProvince(userInfo.getString(" "));
				user.setProvince(userInfo.getString("province")); 
				user.setCountry(userInfo.getString("country")); 
				user.setFansCounts(0);
				user.setFollowCounts(0);
				user.setReceiveLikeCounts(0);
				user.setReportCounts(0);
				
				
				Users u2 = service.updataUser(user);
				System.out.println("--------------入库完毕");
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
		
		
		return JSONResult.errorMsg("未知错误");
	}
	public String setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		return uniqueToken;
	}
	@PostMapping("/login2")
	public JSONResult login2(
			String js_code) {
		System.out.println("------------"+js_code);
		return JSONResult.ok(js_code);
	}
}
