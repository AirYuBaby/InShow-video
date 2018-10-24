package com.InShowVideo.pojo.vo;

import com.InShowVideo.pojo.Topics;

public class topicsVO {
	private String username;
	private Topics topic;
	private String avargUrl;
	public String getAvargUrl() {
		return avargUrl;
	}
	public void setAvargUrl(String avargUrl) {
		this.avargUrl = avargUrl;
	}
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
