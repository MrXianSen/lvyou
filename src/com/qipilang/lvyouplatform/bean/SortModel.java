package com.qipilang.lvyouplatform.bean;

public class SortModel {

	private String id;
	private String name;   //��ʾ������
	private String sortLetters;  //��ʾ����ƴ��������ĸ
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
}
