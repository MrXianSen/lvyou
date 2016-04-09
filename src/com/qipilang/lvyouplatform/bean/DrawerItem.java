package com.qipilang.lvyouplatform.bean;

public class DrawerItem {
	private String item;
	private int imageId;
	
	public DrawerItem(String item, int imageId) {
		this.item = item;
		this.imageId = imageId;
	}
	
	public String getItem(){
		return item;
	}
	
	public int getImageId() {
		return imageId;
	}
}
