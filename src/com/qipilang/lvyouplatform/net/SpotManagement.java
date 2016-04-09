package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.albery.circledemo.bean.User;
import com.qipilang.lvyouplatform.bean.SCIList;
import com.qipilang.lvyouplatform.bean.Scene;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

public class SpotManagement {
	public static SpotManagement Instance = new SpotManagement();
	
	public List<Scene> getSceneList(){
		String url = Constants.BASE_URL + Constants.SPOT_FRAGMENT_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(null, Constants.ENCODING, url);
		int state = TranslateUtil.getStateCode(responseJson, "getState");
		if(state <= 0) return null;
		return TranslateUtil.jsonToScene(responseJson);
	}
	
	public List<Scene> getSceneList(int type, String scene){
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", CastUtil.castString(type));
		params.put("scene", scene);
		
		String url = Constants.BASE_URL + Constants.GET_DETAIL_SCENE_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		int state = TranslateUtil.getStateCode(responseJson, "getState");
		if(state == 0) return null;
		return TranslateUtil.jsonToScene(responseJson);
	}
	
	public List<SCIList> getSCIList(int type, String scene){
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", CastUtil.castString(type));
		params.put("scene", scene);
		
		String url = Constants.BASE_URL + Constants.GEt_INFORMATION_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		int state = TranslateUtil.getStateCode(responseJson, "getState");
		if(state == 0) return null;
		return TranslateUtil.jsonToSCIList(responseJson);
	} 
	
	public List<User> getSCIPerson(int type, String scene){
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", CastUtil.castString(type));
		params.put("scene", scene);
		
		String url = Constants.BASE_URL + Constants.GET_SPOT_PERSON_URL;
		
		String responseJson = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		int state = TranslateUtil.getStateCode(responseJson, "getState");
		if(state == 0) return null;
		try {
			JSONArray array = new JSONObject(responseJson).getJSONArray("userList");
			return TranslateUtil.jsonToUser(array.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
