package com.qipilang.lvyouplatform.bean;

import com.qipilang.lvyouplatform.R.id;

public class Message {
	
	private int sourID;						//������Ϣ���û�ID
		
	private String sourName;				//������Ϣ�û�����
	
	private int destID;						//������Ϣ���û�ID
	
	private String destName;				//������Ϣ���û�����
	
	private String content;					//��Ϣ����
			
	private boolean isRead;					//��Ϣ�Ƿ��Ѿ�����ȡ
	
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

