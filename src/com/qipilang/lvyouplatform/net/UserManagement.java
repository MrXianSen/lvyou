package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

/**************************************************************************
 * 
 * DESCRIPTION:	用户管理
 * 
 * @author 		张建国
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class UserManagement {
	public static UserManagement Instance = new UserManagement();
	
	public UserInfo getUserInfo(String userID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(userID));
		
		String url = Constants.BASE_URL + Constants.PERSONAL_INFO_URL;
		
		String userInfoJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		List<UserInfo> list = TranslateUtil.jsonToUserInfo(userInfoJson);
		
		UserInfo userInfo = (list != null && list.size() > 0) ? list.get(0) : null;
		return userInfo;
	}
	
	/*************************************************************
	 * DESCRIPTION:			用户注册
	 * @param user			注册用户实例
	 * @return				注册是否成功
	 * 						成功：userID
	 * 						用户名已经存在， -1
	 * 						所填的内容超过了字数限制，-2
	 * @since				2016.3.6
	 ************************************************************/
	public int register(UserInfo user, SharedPreferencesUtil logUser){
		Map<String, String> params = new HashMap<String, String>();
		params.put("user.userName",user.getUserName());
		params.put("user.password", user.getPassword());
		params.put("confirmPassword", user.getConfirmPassword());
		params.put("user.birthday", user.getBirthday());
		params.put("user.sex", user.getGender());
		params.put("user.ppquestion", user.getPpQuestion());
		params.put("user.ppanswer", user.getPpAnswer());
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.REGISTER_ACTION_URL);
		String responseCode = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url.toString());
		
		String headUrl = TranslateUtil.jsonToString(responseCode, "headPic");
		logUser.updateString("headUrl", headUrl);
		return TranslateUtil.getStateCode(responseCode, "regState");
	}
	
	/***********************************************************
	 * DESCRIPTION:			用户登录
	 * @param userName		用户名
	 * @param password		密码
	 * @return				返回登陆状态
	 * 						userID,成功
	 * 						-1，用户不存在
	 * 						-2，密码或者用户名错误
	 * @since				2016.3.6
	 ***********************************************************/
	public int login(String userName, String password, SharedPreferencesUtil logUser){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		params.put("password", password);
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.LOGIN_ACTION_URL);
		String responseCode = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url.toString());
		
		String headUrl = TranslateUtil.jsonToString(responseCode, "headPic");
		logUser.updateString("headUrl", headUrl);
		
		String head = logUser.getString("headUrl", null);
		
		return TranslateUtil.getStateCode(responseCode, "logState");
	}
	
	/*************************************************************
	 * DESCRIPTION:			提交密保问题 
	 * @param userName		用户名
	 * @param ppQuestion	密保问题
	 * @param ppAnswer		用户回答的密保答案
	 * @return				通过返回状态码告诉用户是否回答正确
	 * 						1正确，0错误
	 ************************************************************/
	public int submitPpQuestion(String userName, String ppQuestion, String ppAnswer){
		Map<String, String> params =  new HashMap<String, String>();
		params.put("userName", userName);
		params.put("ppquestion", ppQuestion);
		params.put("ppanswer", ppAnswer);
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.CHECK_SECRET_QUESTION_URL);
		String responeCode = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url.toString());
		return TranslateUtil.getStateCode(responeCode, "checkState");
	}

	/*************************************************************
	 * DESCRIPTION:			用户回答密保正确后重置密码
	 * @param userName		用户名
	 * @param newPwd		新密码
	 * @return				返回重置密码的状态码，0重置失败，1成功
	 ************************************************************/
	public int resetPassword(String userName, String newPwd){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		params.put("newPwd", newPwd);
		
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.RESET_PASSWORD_URL);
		
		String responseCode = HttpUtil._requestAndGetResponse(params,
				Constants.ENCODING, url.toString());
		return TranslateUtil.getStateCode(responseCode, "resetState");
	}
}
