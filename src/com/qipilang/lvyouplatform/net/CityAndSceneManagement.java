package com.qipilang.lvyouplatform.net;

import java.util.List;


import com.qipilang.lvyouplatform.bean.City;
import com.qipilang.lvyouplatform.bean.Scene;

/**************************************************************************
 * 
 * DESCRIPTION:	城市和景点管理
 * 
 * @author 		张建国
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class CityAndSceneManagement {
	
	public static CityAndSceneManagement Instance = new CityAndSceneManagement();
	
	
	public CityAndSceneManagement(){}
	
	/****************************************************************
	 * DESCRIPTION:			获取省份列表	
	 * @return				返回省份数据集合
	 ***************************************************************/
	public List<String> getProvince(){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			获取城市列表
	 * @return				返回城市数据集合
	 ***************************************************************/
	public List<City> getCityList(){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			获取所有的景点数据
	 * @return				返回所有景点数据的集合
	 ***************************************************************/
	public List<Scene> getSceneList(){
		return null;
	}
	
	/****************************************************************
	 * DSCRIPTION:			某个城市的景点数据
	 * @param cityID		城市ID
	 * @return				返回某个城市的数据集合
	 ***************************************************************/
	public List<City> getSceneList(int cityID){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			根据省份获取景点数据
	 * @param provinceName	省份名称
	 * @return				某个省份的数据集合
	 ***************************************************************/
	public List<City> getSceneList(String provinceName){
		return null;
		
	}
}
