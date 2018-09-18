package com.InShowVideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.InShowVideo.utils.RedisOperator;



@RestController
public class BasicController {
	@Autowired
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION="user-redis-session";
	
	public static final String FILESPACE = "E:\\fun-video";
	
	public static final String FFMPEG_EXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
	
	public static final Integer PAGE_SIZE =5;
	
}
