package com.qipilang.lvyouplatform.bean;

public class MessageList {
	private int sourID;						//������Ϣ���û�ID
	
	private String sourName;				//������Ϣ�û�����
	
	private int destID;						//������Ϣ���û�ID
	
	private String destName;				//������Ϣ���û�����
	
	private String content;					//��Ϣ����
			
	private boolean isRead;					//��Ϣ�Ƿ��Ѿ�����ȡ
	
	private String image;
	
	private String Date;

	private String lastMessageString;      //���һ����Ϣ
	
	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public MessageList() {
	}

	public int getSourID() {
		return sourID;
	}

	public void setSourID(int sourID) {
		this.sourID = sourID;
	}

	public String getSourName() {
		return sourName;
	}

	public void setSourName(String sourName) {
		this.sourName = sourName;
	}

	public int getDestID() {
		return destID;
	}

	public void setDestID(int destID) {
		this.destID = destID;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public String getImage() {
		return image;
	}
	
	public void  setImageId(String image) {
		this.image = image;
	}
	
	
	public String getDate() {
		return Date;
	}
	
	public void setDate(String date) {
		this.Date = date;
	}
	
	public String getLastMessage() {
		return lastMessageString;
	}
	
	public void setLastMessage(String lastMessageString) {
		this.lastMessageString = lastMessageString;
	}
}
