package com.qipilang.lvyouplatform.exception;


public class AJSONException extends Exception{

	public int exceptionCode; 
	
	public AJSONException(String s, int exceptionCode) {
		super(s);
		this.exceptionCode = exceptionCode;
	}
}
