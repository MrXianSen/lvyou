package com.qipilang.lvyouplatform.bean;

import com.qipilang.lvyouplatform.R.id;

public class Message {
	
	private int sourID;						//发送消息的用户ID
		
	private String sourName;				//发送消息用户姓名
	
	private int destID;						//接收消息的用户ID
	
	private String destName;				//接收消息的用户姓名
	
	private String content;					//消息内容
			
	private boolean isRead;					//消息是否已经被读取
	
	private int type;
	
	public static final int TYPE_RECEIVED = 0;
	public static final int TYPE_SENT = 1;
	
	public Message() {
	}

	public Message(String content,int type) {
		this.content = content;
		this.type = type;
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
	
	public int getType() {
		return type;
	}
	
	public void  setType(int type) {
		this.type = type;
	}
}

