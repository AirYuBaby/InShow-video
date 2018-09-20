package com.InShowVideo.pojo.vo;
 
public class publisherVideo {
	
	private UsersVO publisher;
	private boolean userLikevideo;
	private boolean userClickvideo;
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
	
}
