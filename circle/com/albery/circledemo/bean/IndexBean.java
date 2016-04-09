package com.albery.circledemo.bean;

import java.util.List;

public class IndexBean {
	
	private int totalPage;
	
	private List<CircleItem> dataList;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<CircleItem> getDataList() {
		return dataList;
	}

	public void setDataList(List<CircleItem> dataList) {
		this.dataList = dataList;
	}
}
