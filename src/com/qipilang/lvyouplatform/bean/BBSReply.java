package com.qipilang.lvyouplatform.bean;

import java.io.Serializable;

public class BBSReply {

	private int intBbsQID; 				// ����ID

	private int intBbsRID; 				// �ظ�ID
	private String strBbsRDate;			// �ظ�ʱ��
	private String strBbsRContent; 		// �ظ�����

	private int intBbsReplyerId; 			// �ظ���ID
	private String strBbsReplyerNickName; // �ظ����ǳ�
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
