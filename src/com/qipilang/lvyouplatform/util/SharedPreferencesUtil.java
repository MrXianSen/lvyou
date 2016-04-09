package com.qipilang.lvyouplatform.util;

import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**************************************************************************
 * 
 * DESCRIPTION:					Manage the sharedPreferences
 * 								that contains the logged user info
 * 
 * @author 						albery
 *
 * @since						2016.3.13
 * 
 * @version 					1.0
 * 
 **************************************************************************/
public class SharedPreferencesUtil {

	private Context context;

	public SharedPreferencesUtil(Context context) {
		this.context = context;
		initSharedPreferences();
	}

	// 登录用户的数据
	public static SharedPreferences logUserSharedPreferences;

	/********************************************************************
	 * DESCRIPTION:				initialize SharedPreferences
	 *******************************************************************/
	public void initSharedPreferences() {
		logUserSharedPreferences = context.getSharedPreferences(
				Constants.LOGIN_USER_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
	}

	/********************************************************************
	 * DESCRIPTION:				update sharedPreferences
	 *******************************************************************/
	public void updateSharedPreferences(UserInfo userInfo) {
		SharedPreferences.Editor editor = logUserSharedPreferences.edit();
		if (userInfo.getId() != 0)
			editor.putString("userID", CastUtil.castString(userInfo.getId()));
		if (!StringUtil.isEmpty(userInfo.getUserName()))
			editor.putString("userName", userInfo.getUserName());
		if (!StringUtil.isEmpty(userInfo.getPassword()))
			editor.putString("password", userInfo.getPassword());
		if(!StringUtil.isEmpty(userInfo.getConfirmPassword()))
			editor.putString("confirmPassword", userInfo.getConfirmPassword());
		if(!StringUtil.isEmpty(userInfo.getBirthday()))
			editor.putString("birthday", userInfo.getBirthday());
		if(!StringUtil.isEmpty(userInfo.getGender()))
			editor.putString("gender", userInfo.getGender());
		if(!StringUtil.isEmpty(userInfo.getHeadUrl()))
			editor.putString("headUrl", userInfo.getHeadUrl());
		if(!StringUtil.isEmpty(userInfo.getHobby()))
			editor.putString("hobby", userInfo.getHobby());
		if(!StringUtil.isEmpty(userInfo.getTeliphoneNumber()))
			editor.putString("telNumber", userInfo.getTeliphoneNumber());
		if(!StringUtil.isEmpty(userInfo.getEmail()))
			editor.putString("email", userInfo.getEmail());
		if(!StringUtil.isEmpty(userInfo.getEducationBackgtound()))
			editor.putString("education", userInfo.getEducationBackgtound());
		if(!StringUtil.isEmpty(userInfo.getHobby()))
			editor.putString("hobby", userInfo.getHobby());
		if(!StringUtil.isEmpty(CastUtil.castString(userInfo.getAge())))
			editor.putString("age", CastUtil.castString(userInfo.getAge()));
		if(!StringUtil.isEmpty(userInfo.getAddress()))
			editor.putString("address", userInfo.getAddress());
		editor.commit();
	}

	/********************************************************************
	 * DESCRIPTION:				get string from sharedPreferences
	 *******************************************************************/
	public String getString(String key, String defaultString){
		if(logUserSharedPreferences.contains(key)){
			return logUserSharedPreferences.getString(key, defaultString);
		}
		return defaultString;
	}
	
	public void updateString(String key, String value){
		SharedPreferences.Editor editor = logUserSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/********************************************************************
	 * DESCRIPTION:				clear sharedPreferences
	 *******************************************************************/
	public void deleteSharedPreferences() {
		logUserSharedPreferences.edit().clear().commit();
	}
}
