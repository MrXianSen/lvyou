package com.qipilang.lvyouplatform.bean;

import android.R.integer;

public class BBSList {

	private int intBbsQUserId; //�û�ID
	
	private int intBbsQUserProPicId; //�û�ͷ��ID
	
	private String strBbsQUserNickName; //�û��ǳ�	
	
	private String strBbsQSendDate;  //����ʱ��
	
	private String strBbsQContent;	//��������

	private	int	intQId;		//����ID

	public int getIntBbsQUserId() {
		return intBbsQUserId;
	}

	public void setIntBbsQUserId(int intBbsQUserId) {
		this.intBbsQUserId = intBbsQUserId;
	}

	public int getIntBbsQUserProPicId() {
		return intBbsQUserProPicId;
	}

	public void setIntBbsQUserProPicId(int intBbsQUserProPicId) {
		this.intBbsQUserProPicId = intBbsQUserProPicId;
	}

	public String getStrBbsQUserNickName() {
		return strBbsQUserNickName;
	}

	public void setStrBbsQUserNickName(String strBbsQUserNickName) {
		this.strBbsQUserNickName = strBbsQUserNickName;
	}

	public String getStrBbsQSendDate() {
		return strBbsQSendDate;
	}

	public void setStrBbsQSendDate(String strBbsQSendDate) {
		this.strBbsQSendDate = strBbsQSendDate;
	}

	public String getStrBbsQContent() {
		return strBbsQContent;
	}

	public void setStrBbsQContent(String strBbsQContent) {
		this.strBbsQContent = strBbsQContent;
	}

	public int getIntQId() {
		return intQId;
	}

	public void setIntQId(int intQId) {
		this.intQId = intQId;
	}

	public BBSList(int intBbsQUserId, int intBbsQUserProPicId,
			String strBbsQUserNickName, String strBbsQSendDate,
			String strBbsQContent, int intQId) {
		super();
		this.intBbsQUserId = intBbsQUserId;
		this.intBbsQUserProPicId = intBbsQUserProPicId;
		this.strBbsQUserNickName = strBbsQUserNickName;
		this.strBbsQSendDate = strBbsQSendDate;
		this.strBbsQContent = strBbsQContent;
		this.intQId = intQId;
	}

	public BBSList() {
		super();
	}
	
	
}
