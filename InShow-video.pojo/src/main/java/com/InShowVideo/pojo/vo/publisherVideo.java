package com.InShowVideo.pojo.vo;
 
public class publisherVideo {
	
	private UsersVO publisher;
	private boolean userLikevideo;
	private boolean userClickvideo;
	private boolean fansPickuser;
	public UsersVO getPublisher() {
		return publisher;
	}
	public void setPublisher(UsersVO publisher) {
		this.publisher = publisher;
	}
	public boolean isUserLikevideo() {
		return userLikevideo;
	}
	public void setUserLikevideo(boolean userLikevideo) {
		this.userLikevideo = userLikevideo;
	}
	public boolean isUserClickvideo() {
		return userClickvideo;
	}
	public void setUserClickvideo(boolean userClickvideo) {
		this.userClickvideo = userClickvideo;
	}
	public boolean isFansPickuser() {
		return fansPickuser;
	}
	public void setFansPickuser(boolean fansPickuser) {
		this.fansPickuser = fansPickuser;
	}
	
}
