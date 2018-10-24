package com.InShowVideo.services.Impl;

import java.util.Date;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InShowVideo.mapper.UsersMapper;
import com.InShowVideo.mapper.UsersReportMapper;
import com.InShowVideo.mapper.VideosMapperCustom;
import com.InShowVideo.pojo.UsersReport;
import com.InShowVideo.services.reportService;
@Service
public class IreportService implements reportService {
	@Autowired
	private Sid sid;
	@Autowired
	private UsersReportMapper usersReportMapper;
	@Autowired
	private UsersMapper userMapper;
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Override
	public void saveReport(UsersReport userReport) {
		String rid= sid.nextShort();
		userReport.setId(rid);
		userReport.setCreateDate(new Date());
		
		usersReportMapper.insert(userReport);
		
		userMapper.addreportCounts(userReport.getDealUserId());
		
		videosMapperCustom.changeVideoStatus(userReport.getDealVideoId());
		
	}

}
