package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qipilang.lvyouplatform.bean.MessageList;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

public class MessageManagement {
	
	public static MessageManagement Instance = new MessageManagement();
	
	public List<MessageList> getMessageList(String userID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userID);
		
		String url = Constants.BASE_URL + Constants.GET_MESSAGE_URL;
		
		String json = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.jsonToMessageList(json);
	}
	
	public int replyAply(String sourID, String sourName, String userID, String userName, int type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("destId", userID);
		params.put("destName", userName);
		params.put("type", CastUtil.castString(type));
		params.put("sourId", sourID);
		params.put("sourName", sourName);
		
		String url = Constants.BASE_URL + Constants.APLY_REPLY;
		
		String json = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(json, "addState");
	}
}
