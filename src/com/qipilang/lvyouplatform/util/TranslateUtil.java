package com.qipilang.lvyouplatform.util;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.FavortItem;
import com.albery.circledemo.bean.User;
import com.qipilang.lvyouplatform.bean.BBSList;
import com.qipilang.lvyouplatform.bean.BBSReply;
import com.qipilang.lvyouplatform.bean.Friend;
import com.qipilang.lvyouplatform.bean.IPBean;
import com.qipilang.lvyouplatform.bean.Message;
import com.qipilang.lvyouplatform.bean.MessageList;
import com.qipilang.lvyouplatform.bean.PageBean;
import com.qipilang.lvyouplatform.bean.SCIList;
import com.qipilang.lvyouplatform.bean.Scene;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;

/**************************************************************************
 * 
 * DESCRIPTION: 实现JSON数据和对象的装换
 * 
 * @author 张建国
 * 
 * @since 2016.3.4
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class TranslateUtil {

	public static int getStateCode(String json, String key) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			return CastUtil.castInt(jsonObject.getString(key));
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return Constants.UNKNOWN_ERROR;
	}

	public static List<UserInfo> jsonToUserInfo(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			JSONArray array = new JSONArray(json);
			int length = array.length();

			for (int i = 0; i < length; i++) {
				JSONObject item = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setId(item.getInt("userId"));
				userInfo.setUserName(item.getString("userName"));
				userInfo.setGender(item.getString("sex"));
				userInfo.setAge(CastUtil.castInt(item.getInt("age")));
				userInfo.setBirthday(item.getString("birthday"));
				userInfo.setTeliphoneNumber(item.getString("tel"));
				userInfo.setAddress(item.getString("address"));
				userInfo.setEmail(item.getString("email"));
				userInfo.setHobby(item.getString("hobby"));
				userInfo.setEducationBackgtound(item.getString("edubackground"));
				// TODO modify code here
				userInfo.setHeadUrl(item.getString("headPic"));
				list.add(userInfo);
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<User> jsonToUser(String json) {
		List<User> list = new ArrayList<User>();
		try {
			JSONArray array = new JSONArray(json);
			int arrLength = array.length();

			if (null == array || arrLength == 0)
				return null;

			for (int i = 0; i < arrLength; i++) {
				JSONObject item = array.getJSONObject(i);
				String id = item.getString("userId");
				String userName = item.getString("userName");
				User user = new User(id, userName, "");
				list.add(user);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*********************************************************************
	 * DESCRIPTION: 将字符串装换为IPBean对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @return 返回封装IP地址的IPBean实例
	 ********************************************************************/
	public static IPBean jsonToIPBean(String json) {

		IPBean ipBean = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			ipBean = new IPBean();
			ipBean.setUserID(CastUtil.castInt(jsonObject.getString("userId")));
			ipBean.setUserName(CastUtil.castString(jsonObject
					.getString("userName")));
			ipBean.setLastLoginIP(CastUtil.castString(jsonObject
					.getString("lastLoginIp")));
			ipBean.setLastLoginPort(CastUtil.castString(jsonObject
					.getString("lastLoginPort")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ipBean;
	}

	public static List<MessageList> jsonToMessageList(String json) {
		if (StringUtil.isEmpty(json))
			return null;

		List<MessageList> list = new ArrayList<MessageList>();

		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array = obj.getJSONArray("messageList");

			if (array == null || array.length() == 0)
				return null;
			int length = array.length();
			for (int i = 0; i < length; i++) {
				MessageList message = new MessageList();
				JSONObject item = array.getJSONObject(i);
				message.setDestID(CastUtil.castInt(item
						.getString("destId")));
				message.setDestName(CastUtil.castString(item
						.getString("destName")));
				message.setSourID(CastUtil.castInt(item
						.getString("sourId")));
				message.setSourName(CastUtil.castString(item
						.getString("sourName")));
				message.setContent(CastUtil.castString(item
						.getString("text")));
				message.setRead(false);
				message.setType(CastUtil.castInt(item.getInt("type")));
				message.setImageId(CastUtil.castString(item.getString("headPic")));
				
				list.add(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<CircleItem> jsonToCircleItem(String json) {

		if (StringUtil.isEmpty(json))
			return null;

		List<CircleItem> list = new ArrayList<CircleItem>();
		JSONArray array;
		int responseCode = 0;
		int totalPage;
		try {
			JSONObject object = new JSONObject(json);

			responseCode = object.getInt("memoryState");
			// TODO
			int totalRecord = object.getInt("countRecord");
			PageBean.totalPage = ((totalRecord % PageBean.PAGE_SIZE) == 0) ? (totalRecord / PageBean.PAGE_SIZE)
					: (totalRecord / PageBean.PAGE_SIZE) + 1;

			array = object.getJSONArray("memoryList");

			int arrLength = array.length();
			if (null == array || arrLength == 0)
				return null;

			for (int i = 0; i < arrLength; i++) {
				JSONObject item = array.getJSONObject(i);
				CircleItem circleItem = new CircleItem();
				circleItem
						.setId(CastUtil.castString(item.getString("memoryId")));
				circleItem.setContent(CastUtil.castString(item
						.getString("text")));
				circleItem.setCreateTime(CastUtil.castString(item
						.getString("time")));
				circleItem.setType("2");
				JSONArray photos = item.getJSONArray("imgList");
				circleItem.setPhotos(jsonToStrList(photos.toString(), "img"));
				JSONArray favortItem = item.getJSONArray("favoriteList");
				circleItem
						.setFavorters(jsonToFavortItem(favortItem.toString()));
				JSONArray comments = item.getJSONArray("commentList");
				circleItem.setComments(jsonToCommentItem(comments.toString()));
				User user = new User(item.getString("userId"),
						item.getString("userName"), item.getString("headPic"));
				circleItem.setUser(user);
				circleItem.setSceneId(item.getString("sceneId"));
				circleItem.setSceneName(item.getString("sceneName"));

				list.add(circleItem);
			}
			return list;
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static List<String> jsonToStrList(String json, String key) {
		List<String> strList = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			int arrLength = array.length();
			if (null == array || arrLength == 0)
				return null;

			for (int i = 0; i < arrLength; i++) {
				JSONObject item = array.getJSONObject(i);
				String str = item.getString(key);
				strList.add(str);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strList;
	}

	public static String jsonToString(String json, String key) {
		try {
			JSONObject obj = new JSONObject(json);
			return obj.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<FavortItem> jsonToFavortItem(String json) {
		List<FavortItem> favList = new ArrayList<FavortItem>();

		try {
			JSONArray array = new JSONArray(json);
			int arrLength = array.length();

			if (null == array || 0 == arrLength)
				return null;

			for (int i = 0; i < arrLength; i++) {
				JSONObject item = array.getJSONObject(i);
				String favID = item.getString("favoriteId");
				User user = new User(item.getString("userId"),
						item.getString("userNickname"), "");

				FavortItem favortItem = new FavortItem();
				favortItem.setId(favID);
				favortItem.setUser(user);
				favortItem.setCircleID(item.getString("memoryId"));

				favList.add(favortItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return favList;
	}

	public static List<CommentItem> jsonToCommentItem(String json) {
		List<CommentItem> commentList = new ArrayList<CommentItem>();

		try {
			JSONArray array = new JSONArray(json);
			int arrLength = array.length();

			if (null == array || 0 == arrLength)
				return null;

			for (int i = 0; i < arrLength; i++) {
				JSONObject item = array.getJSONObject(i);
				String commentID = item.getString("commentId");
				String commentContent = item.getString("text");
				User user = new User(item.getString("userId"),
						item.getString("userNickname"), "");
				User replyUser = new User(item.getString("replyId"),
						item.getString("replyNickname"), "");

				CommentItem commentItem = new CommentItem();
				commentItem.setId(commentID);
				commentItem.setContent(commentContent);
				commentItem.setUser(user);
				commentItem.setToReplyUser(replyUser);
				commentItem.setCircleID(item.getString("memoryId"));

				commentList.add(commentItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return commentList;
	}

	public static List<Friend> jsonToFriednList(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		List<Friend> list = new ArrayList<Friend>();

		try {
			JSONArray array = new JSONArray(json);

			int length = array.length();
			if (array == null || length == 0)
				return null;
			for (int i = 0; i < length; i++) {
				JSONObject item = array.getJSONObject(i);
				Friend friend = new Friend();
				friend.setDestID(CastUtil.castInt(item.get("destId")));
				friend.setDestName(CastUtil.castString(item.get("destName")));

				list.add(friend);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static UserInfo jsonToFriendInfo(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		try {
			JSONObject object = new JSONObject(json);

			JSONArray friendItem = object.getJSONArray("friendItem");

			return jsonToUserInfo(friendItem.toString()).get(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<BBSReply> jsonToBbsReplies(String json) {

		List<BBSReply> lisBbsReplies = new ArrayList<BBSReply>();
		BBSReply objBbsReply = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			int state = (Integer) jsonObject.get("getState");
			if (state == 0) {
				return null;
			}
			JSONArray jsonArray = jsonObject.getJSONArray("answerList");
			JSONObject tempJsonObject = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				objBbsReply = new BBSReply();
				tempJsonObject = jsonArray.getJSONObject(i);
				objBbsReply.setIntBbsReplyerId(CastUtil.castInt(tempJsonObject
						.get("userId")));
				objBbsReply.setIntBbsRID(CastUtil.castInt(tempJsonObject
						.get("answerId")));
				objBbsReply.setStrBbsRDate(CastUtil.castString(tempJsonObject
						.getString("time")));
				objBbsReply.setStrBbsRContent(CastUtil
						.castString(tempJsonObject.getString("text")));
				objBbsReply.setStrBbsReplyerNickName(CastUtil
						.castString(tempJsonObject.getString("userName")));
				lisBbsReplies.add(objBbsReply);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lisBbsReplies;
	}

	public static List<BBSList> jsonBBSList(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		;
		List<BBSList> list = new ArrayList<BBSList>();

		try {
			JSONObject item = new JSONObject(json);
			JSONArray array = item.getJSONArray("questionList");
			int length;
			if (array == null || array.length() == 0)
				return list;
			length = array.length();

			for (int i = 0; i < length; i++) {
				JSONObject obj = array.getJSONObject(i);
				String userID = obj.getString("userId");
				String userName = obj.getString("userName");
				String text = obj.getString("text");
				String time = obj.getString("time");
				String questionID = obj.getString("questionId");
				BBSList bean = new BBSList();
				bean.setIntBbsQUserId(CastUtil.castInt(userID));
				bean.setIntQId(CastUtil.castInt(questionID));
				bean.setStrBbsQUserNickName(userName);
				bean.setStrBbsQSendDate(time);
				bean.setStrBbsQContent(text);

				list.add(bean);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Scene> jsonToScene(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		List<Scene> list = new ArrayList<Scene>();

		try {
			JSONObject item = new JSONObject(json);

			JSONArray array = item.getJSONArray("sceneList");

			if (array == null)
				return list;
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = array.getJSONObject(i);
				Scene scene = new Scene();
				scene.setId(obj.getString("id"));
				scene.setName(obj.getString("name"));
				scene.setCityID(obj.getString("cityId"));
				scene.setCityName(obj.getString("cityName"));
				scene.setDescription(obj.getString("text"));
				scene.setPicUrl(obj.getString("pic"));

				list.add(scene);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<SCIList> jsonToSCIList(String json) {
		if (StringUtil.isEmpty(json))
			return null;
		List<SCIList> list = new ArrayList<SCIList>();
		try {
			JSONObject obj = new JSONObject(json);

			JSONArray array = obj.getJSONArray("informationList");

			if (array == null || array.length() == 0)
				return list;
			int length = array.length();

			for (int i = 0; i < length; i++) {
				JSONObject item = array.getJSONObject(i);
				SCIList sci = new SCIList();
				sci.setId(item.getString("id"));
				sci.setTitle(item.getString("title"));
				sci.setText(item.getString("text"));
				sci.setTime(item.getString("time"));

				list.add(sci);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
