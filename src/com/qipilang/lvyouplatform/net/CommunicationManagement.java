package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.Map;

import com.qipilang.lvyouplatform.bean.IPBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;

/**************************************************************************
 * 
 * DESCRIPTION:	聊天管理，实现聊天相应的方法
 * 
 * @author 		张建国
 *
 * @since 		2016.3.4
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class CommunicationManagement {
	public static CommunicationManagement Instance = new CommunicationManagement();
	
	/**********************************************************************
	 * DESCRIPTION:				获取聊天对象的IP地址和端口号
	 * @param userID			当前用户的ID
	 * @param targetID			被操作对象的ID
	 * @return					返回封装聊天对象的对象实例
	 *********************************************************************/
	public IPBean getTargetAddres(int userID, int targetID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(userID));
		params.put("targetUserId", CastUtil.castString(targetID));
		//组装访问地址
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.IPBEAN_ACTION_URL);
		//TODO 添加对应的处理Action的地址
		String json = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url.toString());
		
		return TranslateUtil.jsonToIPBean(json);
	}
}
