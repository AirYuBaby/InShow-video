package com.InShowVideo.pojo.vo;

import com.InShowVideo.pojo.Topics;

public class topicsVO {
	private String username;
	private Topics topic;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Topics getTopic() {
		return topic;
	}
	public void setTopic(Topics topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "topicsVO [username=" + username + ", topic=" + topic + "]";
	}
	

}
