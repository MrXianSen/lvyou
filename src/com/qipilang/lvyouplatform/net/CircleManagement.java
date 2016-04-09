package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.User;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

public class CircleManagement {
	
	public static CircleManagement Instance = new CircleManagement();

	/*****************************************************************
	 * DESCRIPTION:					获取动态列表
	 * @param 						userID:用户ID
	 * @return						CircleItem集合
	 ****************************************************************/
	public List<CircleItem> getCircleItemList(int userID, int currPage, int type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(userID));
		params.put("nextPage", CastUtil.castString(currPage));
		
		String url;
		
		if(type == Constants.MY_CIRCLE)
			url = Constants.BASE_URL + Constants.MY_MEMORY_ACTION_URL;
		else
			url = Constants.BASE_URL + Constants.GET_CIRCLE_ITEM_URL;
		
		//发送请求，返回json数据
		String json = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		return TranslateUtil.jsonToCircleItem(json);
	}
	
	public int addComment(String circleID, User user, User toReplyUser, String content){
		Map<String, String> params = new HashMap<String, String>();
		params.put("comment.memoryId", circleID);
		params.put("comment.userId", user.getId());
		params.put("comment.userNickname", user.getName());
		params.put("comment.replyId", toReplyUser.getId());
		params.put("comment.replyNickname", toReplyUser.getName());
		params.put("comment.text", content);
		String selectedCommentID = "";
		if(CommentItem.selectedCommentID != null)
			selectedCommentID = CommentItem.selectedCommentID;
		params.put("comment.commentId", selectedCommentID);
		
		CommentItem.selectedCommentID = "0";
		
		String url = Constants.BASE_URL + Constants.ADD_COMMENT_URL;
		
		String responseCodeStr = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		return TranslateUtil.getStateCode(responseCodeStr, "commentState");
	}
	
	public int deleteComment(String commentID){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("commentId", commentID);
		String url = Constants.BASE_URL + Constants.DELETE_COMMENT_URL;
		
		String responseCodeStr = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(responseCodeStr, "deleteState");
	}
	
	public int addFavorite(String circleID, String userID, String userName){
		Map<String, String> params = new HashMap<String, String>();
		params.put("memoryId", circleID);
		params.put("userId", userID);
		params.put("userNickname", userName);
		
		String url = Constants.BASE_URL + Constants.ADD_FAVORITE_URL;
		
		String responseCodeStr = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(responseCodeStr, "addState");
	}
	
	public int cancleFavorite(String circleID, String userID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("memoryId", circleID);
		params.put("userId", userID);
		
		String url = Constants.BASE_URL + Constants.CANCLE_FAVORITE_URL;
		
		String responseCodeStr = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url);
		
		return TranslateUtil.getStateCode(responseCodeStr, "canState");
	}
}
