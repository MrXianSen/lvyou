package com.qipilang.lvyouplatform.bean;

public class IPBean {
	
	private int userID;
	
	private String userName;
	
	private String lastLoginIP;
	
	private String lastLoginPort;
	
	public IPBean(){}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public String getLastLoginPort() {
		return lastLoginPort;
	}

	public void setLastLoginPort(String lastLoginPort) {
		this.lastLoginPort = lastLoginPort;
	}
}
