package com.qipilang.lvyouplatform.net;

import java.util.List;


import com.qipilang.lvyouplatform.bean.City;
import com.qipilang.lvyouplatform.bean.Scene;

/**************************************************************************
 * 
 * DESCRIPTION:	���к;������
 * 
 * @author 		�Ž���
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
	 * DESCRIPTION:			��ȡʡ���б�	
	 * @return				����ʡ�����ݼ���
	 ***************************************************************/
	public List<String> getProvince(){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			��ȡ�����б�
	 * @return				���س������ݼ���
	 ***************************************************************/
	public List<City> getCityList(){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			��ȡ���еľ�������
	 * @return				�������о������ݵļ���
	 ***************************************************************/
	public List<Scene> getSceneList(){
		return null;
	}
	
	/****************************************************************
	 * DSCRIPTION:			ĳ�����еľ�������
	 * @param cityID		����ID
	 * @return				����ĳ�����е����ݼ���
	 ***************************************************************/
	public List<City> getSceneList(int cityID){
		return null;
	}
	
	/****************************************************************
	 * DESCRIPTION:			����ʡ�ݻ�ȡ��������
	 * @param provinceName	ʡ������
	 * @return				ĳ��ʡ�ݵ����ݼ���
	 ***************************************************************/
	public List<City> getSceneList(String provinceName){
		return null;
		
	}
}
