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
 * DESCRIPTION:	�������ʵ��������Ӧ�ķ���
 * 
 * @author 		�Ž���
 *
 * @since 		2016.3.4
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class CommunicationManagement {
	public static CommunicationManagement Instance = new CommunicationManagement();
	
	/**********************************************************************
	 * DESCRIPTION:				��ȡ��������IP��ַ�Ͷ˿ں�
	 * @param userID			��ǰ�û���ID
	 * @param targetID			�����������ID
	 * @return					���ط�װ�������Ķ���ʵ��
	 *********************************************************************/
	public IPBean getTargetAddres(int userID, int targetID){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", CastUtil.castString(userID));
		params.put("targetUserId", CastUtil.castString(targetID));
		//��װ���ʵ�ַ
		StringBuilder url = new StringBuilder(Constants.BASE_URL);
		url.append(Constants.IPBEAN_ACTION_URL);
		//TODO ��Ӷ�Ӧ�Ĵ���Action�ĵ�ַ
		String json = HttpUtil._requestAndGetResponse(params, Constants.ENCODING, url.toString());
		
		return TranslateUtil.jsonToIPBean(json);
	}
}
