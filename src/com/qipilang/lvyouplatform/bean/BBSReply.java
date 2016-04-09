package com.qipilang.lvyouplatform.bean;

import java.io.Serializable;

public class BBSReply {

	private int intBbsQID; 				// 问题ID

	private int intBbsRID; 				// 回复ID
	private String strBbsRDate;			// 回复时间
	private String strBbsRContent; 		// 回复内容

	private int intBbsReplyerId; 			// 回复人ID
	private String strBbsReplyerNickName; // 回复人昵称
	public int getIntBbsQID() {
		return intBbsQID;
	}
	public void setIntBbsQID(int intBbsQID) {
		this.intBbsQID = intBbsQID;
	}
	public int getIntBbsRID() {
		return intBbsRID;
	}
	public void setIntBbsRID(int intBbsRID) {
		this.intBbsRID = intBbsRID;
	}
	public String getStrBbsRDate() {
		return strBbsRDate;
	}
	public void setStrBbsRDate(String strBbsRDate) {
		this.strBbsRDate = strBbsRDate;
	}
	public String getStrBbsRContent() {
		return strBbsRContent;
	}
	public void setStrBbsRContent(String strBbsRContent) {
		this.strBbsRContent = strBbsRContent;
	}
	public int getIntBbsReplyerId() {
		return intBbsReplyerId;
	}
	public void setIntBbsReplyerId(int intBbsReplyerId) {
		this.intBbsReplyerId = intBbsReplyerId;
	}
	public String getStrBbsReplyerNickName() {
		return strBbsReplyerNickName;
	}
	public void setStrBbsReplyerNickName(String strBbsReplyerNickName) {
		this.strBbsReplyerNickName = strBbsReplyerNickName;
	}
	public BBSReply(int intBbsQID, int intBbsRID, String strBbsRDate,
			String strBbsRContent, int intBbsReplyerId,
			String strBbsReplyerNickName) {
		super();
		this.intBbsQID = intBbsQID;
		this.intBbsRID = intBbsRID;
		this.strBbsRDate = strBbsRDate;
		this.strBbsRContent = strBbsRContent;
		this.intBbsReplyerId = intBbsReplyerId;
		this.strBbsReplyerNickName = strBbsReplyerNickName;
	}
	public BBSReply() {
		super();
	}
	
}
