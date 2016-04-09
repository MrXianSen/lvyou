package com.albery.circledemo.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
/**
 * 
* @ClassName: CommonUtils 
* @Description: 
* @author yiw
*
 */
public class CommonUtils {

	//显示系统的软件盘
	public static void showSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		//imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
	
	//隐藏系统软件�?
	public static void hideSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}
	
	public static boolean isShowSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		//获取状�?�信�?
		return imm.isActive();//true 打开
	}
}
