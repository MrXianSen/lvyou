package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qipilang.lvyouplatform.bean.Friend;
import com.qipilang.lvyouplatform.bean.FriendInfo;
import com.qipilang.lvyouplatform.bean.Limit;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

public class FriendManagement {

	public static FriendManagement Instance = new FriendManagement();
	
	public List<Friend> getFriendList(String userID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userID);
		
		String url = Constants.BASE_URL + Constants.GET_FRIEND_LIST_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.jsonToFriednList(responseJson);
	} 
	
	public FriendInfo getFriendInfo(String userId, String friendId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("friendId", friendId);
		
		String url = Constants.BASE_URL + Constants.GET_FRIEND_INFO_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		int state = TranslateUtil.getStateCode(responseJson, "getState");
		if(state == 0)
			return null;
		String accessInfo = CastUtil.castString(TranslateUtil.getStateCode(responseJson, "accessInfo"));
		String accessNews = CastUtil.castString(TranslateUtil.getStateCode(responseJson, "accessNews"));
		Limit limit = new Limit();
		limit.setAccessInfo(accessInfo);
		limit.setAccessNews(accessNews);
		
		UserInfo userInfo = TranslateUtil.jsonToFriendInfo(responseJson);
		
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setLimit(limit);
		friendInfo.setUserInfo(userInfo);
		
		return friendInfo;
	}
	
	public int updateLimit(String userID, String friendID, String accessInfo, String accessNews){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userID);
		params.put("friendId", friendID);
		params.put("accessInfo", accessInfo);
		params.put("accessNews", accessNews);
		
		String url = Constants.BASE_URL + Constants.UPDATE_LIMIT_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(responseJson, "updateState");
	}
	
	public Limit getLimit(String userID, String friendID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userID);
		params.put("friendId", friendID);
		
		String url = Constants.BASE_URL + Constants.GET_ACCESS_URL;
		Limit limit = new Limit();
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		int getState = TranslateUtil.getStateCode(responseJson, "getState");
		if(getState == 0)
			return null;
		limit.setAccessInfo(CastUtil.castString(TranslateUtil.getStateCode(responseJson, "accessInfo")));
		limit.setAccessNews(CastUtil.castString(TranslateUtil.getStateCode(responseJson, "accessNews")));
		
		return limit;
	}
	
	public int addFriend(String sourID, String sourName, String destID, String destName){
		Map<String, String> params = new HashMap<String, String>();
		params.put("sourId", sourID);
		params.put("sourName", sourName);
		params.put("destId", destID);
		params.put("destName", destName);
		
		String url = Constants.BASE_URL + Constants.ADD_FRIEND;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(responseJson, "applyState");
	}
}
