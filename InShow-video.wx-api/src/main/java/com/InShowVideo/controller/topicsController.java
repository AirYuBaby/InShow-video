package com.InShowVideo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.InShowVideo.pojo.Topics;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.pojo.vo.VideosVO;
import com.InShowVideo.pojo.vo.topicsVO;
import com.InShowVideo.services.topicsService;
import com.InShowVideo.utils.JSONResult;
import com.InShowVideo.utils.PagedResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value="话题相关的Controller",tags= {"话题相关业务的controller"})
@RequestMapping("/topic")
public class topicsController extends BasicController {
	@Autowired
	private topicsService tService;
	@ApiOperation(value = "上传话题", notes = "上传话题的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "topicName", value = "话题名称", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "topicDesc", value = "话题描述", required = true, dataType = "String", paramType = "form"),
			 })
	@PostMapping(value = "/uploadtopic", headers = "content-type=multipart/form-data")
	public JSONResult uploadtopic(
			String userId,
			String topicName,
			String topicDesc,
			@ApiParam(value = "话题封面", required = true)MultipartFile file) throws Exception {
		
		String topicPathDB="/" + userId + "/topic";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		// 文件上传的最终保存路径
		String finalCoverPath = "";
		try {
			if (file != null) {
				
				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					
					finalCoverPath = FILESPACE + topicPathDB + "/" + fileName;
					// 设置数据库保存的路径
					topicPathDB += ("/" + fileName);
					
					File outFile = new File(finalCoverPath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
				
			} else {
				return JSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.errorMsg("上传出错...");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		
		Topics topic =new Topics();
		topic.setCoverPath(topicPathDB);
		topic.setCreateTime(new Date());
		topic.setTopicDesc(topicDesc);
		topic.setTopicName(topicName);
		topic.setUserId(userId);
		topic.setStatus(1);
		
		String topicId = tService.saveTopic(topic);
		return JSONResult.ok(topicId);
	}
	@ApiOperation(value="获取话题列表的接口",notes="获取话题列表的接口")
	@ApiImplicitParam(name="page",value="动态加载的数据页数（从0开始）",required=true,dataType="int",paramType="query")
	@GetMapping("/getAll")
	public JSONResult getAllTopics(int page) {
		if(page>=0) {
			List<topicsVO>  list = tService.getAllTopic(page);
			for (topicsVO topicsVO : list) {
				System.out.println(topicsVO.getUsername());
			}
			return JSONResult.ok(list);
		}
		return JSONResult.errorMsg("未知错误，你的小可爱迷路啦");
	}
	
	
	@ApiOperation(value="按热度获取话题列表的接口",notes="按热度获取话题列表的接口")
	@ApiImplicitParam(name="page",value="动态加载的数据页数（从0开始）",required=true,dataType="int",paramType="query")
	@GetMapping("/getHart")
	public JSONResult getAllTopicsByHart(int page) {
		if(page>=0) {
			List<topicsVO>  list = tService.getAllTopicByHart(page);
			return JSONResult.ok(list);
		}
		return JSONResult.errorMsg("未知错误，你的小可爱迷路啦");
	}
	
	
	@ApiOperation(value="话题视频列表页",notes="话题视频列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page",value="动态加载的数据页数（从0开始）",required=true,dataType="int",paramType="query"),
		@ApiImplicitParam(name="topicId",value="话题ID",required=true,dataType="String",paramType="query")
	})
	@GetMapping("/getAllVideoInTopic")
	public JSONResult getAllVideoInTopic(int page,String topicId) {
		System.out.println(page+"--------"+topicId);
		if(page>=0) {
			List<VideosVO> videoVO = tService.getAllVideoInTopic(page, topicId);
			return JSONResult.ok(videoVO);
		}
		return JSONResult.errorMsg("未知錯誤");
	}

}
