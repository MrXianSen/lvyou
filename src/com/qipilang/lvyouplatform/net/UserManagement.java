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
 * DESCRIPTION:	�û�����
 * 
 * @author 		�Ž���
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
	 * DESCRIPTION:			�û�ע��
	 * @param user			ע���û�ʵ��
	 * @return				ע���Ƿ�ɹ�
	 * 						�ɹ���userID
	 * 						�û����Ѿ����ڣ� -1
	 * 						��������ݳ������������ƣ�-2
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
	 * DESCRIPTION:			�û���¼
	 * @param userName		�û���
	 * @param password		����
	 * @return				���ص�½״̬
	 * 						userID,�ɹ�
	 * 						-1���û�������
	 * 						-2����������û�������
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
	 * DESCRIPTION:			�ύ�ܱ����� 
	 * @param userName		�û���
	 * @param ppQuestion	�ܱ�����
	 * @param ppAnswer		�û��ش���ܱ���
	 * @return				ͨ������״̬������û��Ƿ�ش���ȷ
	 * 						1��ȷ��0����
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
	 * DESCRIPTION:			�û��ش��ܱ���ȷ����������
	 * @param userName		�û���
	 * @param newPwd		������
	 * @return				�������������״̬�룬0����ʧ�ܣ�1�ɹ�
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
