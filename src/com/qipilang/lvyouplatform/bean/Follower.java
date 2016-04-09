package com.qipilang.lvyouplatform.bean;

public class Follower {
	private int id;
	
	private int sourID;
	
	private String sourName;
	
	private int destID;
	
	private String destName;
	
	private boolean isAccessInfo;
	
	private boolean isAccessName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isAccessInfo() {
		return isAccessInfo;
	}

	public void setAccessInfo(boolean isAccessInfo) {
		this.isAccessInfo = isAccessInfo;
	}

	public boolean isAccessName() {
		return isAccessName;
	}

	public void setAccessName(boolean isAccessName) {
		this.isAccessName = isAccessName;
	}
}
