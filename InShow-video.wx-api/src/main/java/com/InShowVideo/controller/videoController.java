package com.InShowVideo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.InShowVideo.pojo.Bgm;
import com.InShowVideo.pojo.Videos;
import com.InShowVideo.services.bgmService;
import com.InShowVideo.services.topicsService;
import com.InShowVideo.services.videoService;
import com.InShowVideo.utils.FetchVideoCover;
import com.InShowVideo.utils.JSONResult;
import com.InShowVideo.utils.MergeVideoMp3;
import com.InShowVideo.utils.PagedResult;
import com.InShowVideo.utils.SheepMergeVideoMp3;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "视频相关操作的接口", tags = { "视频相关业务的controller" })
@RequestMapping("/video")
public class videoController extends BasicController {
	@Autowired
	private videoService videoService;
	@Autowired
	private bgmService bgmService;
	@Autowired
	private topicsService topicService;

	@ApiOperation(value = "上传视频", notes = "上传视频的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "audioId", value = "背景音乐id", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "topicId", value = "参与话题id", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoSecond", value = "背景音乐播放长度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "desc", value = "视频描述", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "bgmPosition", value = "bgm的起始播放位置", required = true, dataType = "String", paramType = "form"), })
	@PostMapping(value = "/uploadVideos", headers = "content-type=multipart/form-data")
	public JSONResult uploadVideos(

			String userId, String audioId, String topicId, double videoSecond, int videoWidth, int videoHeight,
			String desc, int bgmPosition, @ApiParam(value = "短视频", required = true) MultipartFile file)
			throws Exception {
		
		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户Id不能为空");
		}
		String videoPathDB = "\\" + userId + "\\video";
		//用来消音缓存文件地址
		String videoAAAAADb = "\\" + userId + "\\video";
		String coverPathDB = "/" + userId + "/video";
		String _videoFinalPath = "";//也是用来消音缓存文件地址
		String videoFinalPath = "";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (file != null) {
				// 获取文件原名
				String fileName = file.getOriginalFilename();

				String arrrayFileName[] = fileName.split("\\.");

				String prefixName = "";
				for (int i = 0; i < arrrayFileName.length; i++)
					prefixName += arrrayFileName[i];

				if (StringUtils.isNotBlank(prefixName)) {
					videoFinalPath = FILESPACE + videoPathDB + "\\" + fileName;
					videoPathDB += ("\\" + fileName);
					//消音缓存文件地址
					videoAAAAADb += ("\\_" + fileName);
					coverPathDB += ("/" + prefixName + ".jpg");

					File Ofile = new File(videoFinalPath);
					System.out.println(Ofile.toString());
					if (Ofile.getParentFile() != null || !Ofile.getParentFile().isDirectory()) {
						Ofile.getParentFile().mkdirs();
					}

					fileOutputStream = new FileOutputStream(Ofile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			} else {
				return JSONResult.errorMsg("上传出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.errorMsg("上传出错");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}

		if (StringUtils.isNotBlank(audioId)) {
			Bgm bmg = bgmService.bgmBeChoose(audioId);
			String audioInputPath = FILESPACE + bmg.getPath();

			MergeVideoMp3 videoMp3 = new MergeVideoMp3(FFMPEG_EXE);
			//*****这是带消音的合成
			SheepMergeVideoMp3 SVM = new SheepMergeVideoMp3(FFMPEG_EXE);

			String videoInputPath = videoFinalPath;

			String videoOutputName = UUID.randomUUID().toString() + ".mp4";
			videoPathDB = "/" + userId + "/video" + "/" + videoOutputName;
			videoFinalPath = FILESPACE + videoPathDB;
			//又是消音缓存文件的地址
			_videoFinalPath = FILESPACE + videoAAAAADb;
			//videoMp3.convertor(videoInputPath, audioInputPath, bgmPosition, videoSecond, videoFinalPath);
			SVM.convertor(videoInputPath, audioInputPath, videoSecond, bgmPosition, _videoFinalPath, videoFinalPath);

		}
		if (StringUtils.isNotBlank(topicId)) {
			System.out.println("topicID"+ "------------------------------"+topicId);
			topicService.addVideotopic(topicId);
		}

		FetchVideoCover videoCover = new FetchVideoCover(FFMPEG_EXE);

		videoCover.getCover(videoFinalPath, FILESPACE + coverPathDB);

		Videos video = new Videos();

		video.setAudioId(audioId);
		video.setUserId(userId);
		video.setVideoSeconds((float)videoSecond);
		video.setCoverPath(coverPathDB);
		video.setCreateTime(new Date());
		video.setVideoDesc(desc);
		video.setVideoHeight(videoHeight);
		video.setVideoWidth(videoWidth);
		video.setTopicId(topicId);
		video.setBgmPosition(bgmPosition);
		video.setStatus(1);
		video.setVideoPath(videoPathDB);

		String videoId = videoService.saveVideo(video);

		return JSONResult.ok(videoId);
	}

	@ApiOperation(value = "获取视频列表的接口")
	@ApiImplicitParam(name = "page", value = "页码（从0开始）", paramType = "query")
	@PostMapping("/getByPage")
	public JSONResult getVideoByPage(int page) {
		System.out.println("page-----------" + page);
		PagedResult result;
		if (page >= 0) {
			result = videoService.getAllVideos(page);
		} else {
			return JSONResult.errorMsg("页码发生错误，你的小可爱取不到数据啦");
		}

		return JSONResult.ok(result);

	}

	@ApiOperation(value = "获取我收藏的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "page", value = "页码（从0开始）", paramType = "form"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示的视频数量", paramType = "form") })
	@PostMapping("/showMylike")
	public JSONResult showMylike(String userId, Integer page, Integer pageSize) {
		System.out.println("有没有id啊："+userId);

		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("");
		}
		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 4;
		}
		PagedResult videoList = videoService.qureyMyLikeVideo(userId, page, pageSize);
		return JSONResult.ok(videoList);
	}

	@ApiOperation(value = "获取我关注的用户的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "page", value = "页码（从0开始）", paramType = "form"),
			@ApiImplicitParam(name = "pageSize", value = "每页展示的视频数量", paramType = "form") })
	@PostMapping("/showmyfollers")
	public JSONResult showmyfollers(String userId, Integer page, Integer pageSize) {
		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("");
		}
		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 4;
		}
		PagedResult videoList = videoService.queryMyFollowerVideo(userId, page, pageSize);
		return JSONResult.ok();
	}

	@ApiOperation(value = "用户收藏的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频Id", paramType = "form"),
			@ApiImplicitParam(name = "publisherId", value = "发布者Id", paramType = "form") })
	@PostMapping("/userLikevideo")
	public JSONResult userLikevideo(String userId, String videoId, String publisherId) {
		
		videoService.userLikevideos(userId, videoId, publisherId);
		return JSONResult.ok();
	}

	@ApiOperation(value = "用户点赞视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频Id", paramType = "form"), })
	@PostMapping("/userClickvideo")
	public JSONResult userClickvideo(String userId, String videoId) {
		
		videoService.userClickvideos(userId, videoId);
		return JSONResult.ok();
	}
	@ApiOperation(value = "用户取消收藏的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频Id", paramType = "form"),
			@ApiImplicitParam(name = "publisherId", value = "发布者Id", paramType = "form") })
	@PostMapping("/userunLikeVideo")
	public JSONResult userunLikeVideo(String userId,String videoId,String publisherId) {
		videoService.userunLikevideos(userId, videoId, publisherId);
		return JSONResult.ok();
	}

	@ApiOperation(value = "用户取消点赞的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频Id", paramType = "form"),
			@ApiImplicitParam(name = "publisherId", value = "发布者Id", paramType = "form") })
	@PostMapping("/userUnclickVideo")
	public JSONResult userUnclickVideo(String userId,String videoId,String publisherId) {
		videoService.userUnclickVideos(userId, videoId, publisherId);
		return JSONResult.ok();
	}
	
	@ApiOperation(value = "获取我的视频的接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
			@ApiImplicitParam(name = "page", value = "页数", paramType = "form") })
	@PostMapping("/myVideo")
	public JSONResult myVideo(String userId, int page) {
		if(userId!=null) {
			PagedResult pr = videoService.getVideoByUserid(userId, page);
			return JSONResult.ok(pr);
		}
		return JSONResult.errorMsg("获取失败");
	}
	
}
