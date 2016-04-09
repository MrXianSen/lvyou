package com.albery.circledemo.bean;

import java.io.Serializable;

public class CommentItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String selectedCommentID;
	
	private String id;
	private String circleID;
	private User user;
	private String userHeaderUrl;
	private User toReplyUser;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(User toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	public String getCircleID() {
		return circleID;
	}
	public void setCircleID(String circleID) {
		this.circleID = circleID;
	}
	public String getUserHeaderUrl() {
		return userHeaderUrl;
	}
	public void setUserHeaderUrl(String userHeaderUrl) {
		this.userHeaderUrl = userHeaderUrl;
	}
}
