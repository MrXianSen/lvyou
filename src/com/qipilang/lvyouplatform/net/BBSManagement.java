package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.qipilang.lvyouplatform.bean.BBSList;
import com.qipilang.lvyouplatform.bean.BBSReply;
import com.qipilang.lvyouplatform.bean.PageBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

/**************************************************************************
 * 
 * DESCRIPTION: BBS管理，处理BBS模块数据库数据采集
 * 
 * @author 王俊翔
 * 
 * @modify 张建国
 * 
 * @since 2016.3.13
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class BBSManagement {
	public static BBSManagement Instance = new BBSManagement();

	/*****************************************************************
	 * DESCRIPTION: 发送一条BBSList数据
	 * 
	 * @param bbslist
	 *            一个BBSList对象
	 * @return String对象:发表成功与否状态码
	 ****************************************************************/
	public List<BBSList> getBBSList(int nextPage) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("nextPage", CastUtil.castString(nextPage));

		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.BBSLIST_ACTION_URL);
		// TODO 添加对应的处理Action的地址
		String json = HttpUtil._requestAndGetResponse(params,
				Constants.ENCODING, url.toString());
		int state = TranslateUtil.getStateCode(json, "getState");
		PageBean.totalPage = ((TranslateUtil.getStateCode(json, "countRecord") % 10) == 0) ? (TranslateUtil
				.getStateCode(json, "countRecord") / 10) : ((TranslateUtil
				.getStateCode(json, "countRecord") / 10) + 1);
		if (state == 0)
			return null;
		return TranslateUtil.jsonBBSList(json);
	}

	/*****************************************************************
	 * DESCRIPTION: 获取回复列表
	 * 
	 * @param nextPage
	 *            下一页
	 * @return 包含json对象的字符串,获得BBSList 对象串
	 ****************************************************************/
	public String getReplies(int questionId, int nextPage) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("questionId", CastUtil.castString(questionId));
		params.put("nextPage", CastUtil.castString(nextPage));

		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.BBSREPLISE_ACTION_URL);
		// TODO 添加对应的处理Action的地址
		String json = HttpUtil._requestAndGetResponse(params,
				Constants.ENCODING, url.toString());
		
		PageBean.totalPage = ((TranslateUtil.getStateCode(json, "countRecord") % 10) == 0) ? (TranslateUtil
				.getStateCode(json, "countRecord") / 10) : ((TranslateUtil
				.getStateCode(json, "countRecord") / 10) + 1);
		return json;
	}

	/*****************************************************************
	 * DESCRIPTION: 发送一条BBSList数据
	 * 
	 * @param bbslist
	 *            一个BBSList对象
	 * @return String对象:发表成功与否状态码
	 ****************************************************************/
	public int postBbsList(BBSList bbsList) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(bbsList.getIntBbsQUserId()));
		params.put("userName",
				CastUtil.castString(bbsList.getStrBbsQUserNickName()));
		params.put("text", CastUtil.castString(bbsList.getStrBbsQContent()));
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.BBSPOSTBBSLIS_ACTION_URL);
		// TODO 添加对应的处理Action的地址
		String response = HttpUtil._requestAndGetResponse(params,
				Constants.ENCODING, url.toString());

		int code = (TranslateUtil.getStateCode(response, "addState") == 1) ? Constants.SUCCESS
				: Constants.FAIL;
		return code;
	}

	/*****************************************************************
	 * DESCRIPTION: 发送一条BBSReply数据
	 * 
	 * @param bbsReply
	 *            一个BBSReply对象
	 * @return String对象:发表成功与否状态码
	 ****************************************************************/
	public int postReply(BBSReply bbsReply) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(bbsReply.getIntBbsQID()));
		params.put("questionId", CastUtil.castString(bbsReply.getIntBbsQID()));
		params.put("text", CastUtil.castString(bbsReply.getStrBbsRContent()));
		params.put("userName",
				CastUtil.castString(bbsReply.getStrBbsReplyerNickName()));

		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.BBSPOSTREPLY_ACTION_URL);
		// TODO 添加对应的处理Action的地址
		String response = HttpUtil._requestAndGetResponse(params,
				Constants.ENCODING, url.toString());
		int code = (TranslateUtil.getStateCode(response, "addState") == 1) ? Constants.SUCCESS
				: Constants.FAIL;
		return code;
	}
}