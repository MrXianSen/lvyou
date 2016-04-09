package com.qipilang.lvyouplatform.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.IndexBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;

/**************************************************************************
 * 
 * DESCRIPTION:	用户动态管理
 * 
 * @author 		张建国
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class NewsManagement {
	public static NewsManagement Instance = new NewsManagement();
	
	public NewsManagement(){}
	
	/**************************************************
	 * DESCRIPTION:			发布新的帖子（动态，消息）
	 * @param news			动态对象的实例
	 * @return				返回动态列表集合
	 *************************************************/
	public List<CircleItem> releaseNew(CircleItem news){
		return null;
	}
}
