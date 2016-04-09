package com.qipilang.lvyouplatform.net;

import java.util.List;

import com.qipilang.lvyouplatform.bean.Applyer;
import com.qipilang.lvyouplatform.bean.Follower;
import com.qipilang.lvyouplatform.bean.Friend;

/**************************************************************************
 * 
 * DESCRIPTION:	��ע�߹���
 * 
 * @author 		�Ž���
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class FollowerManagement {
	public static FollowerManagement Instance = new FollowerManagement();
	
	/*************************************************************
	 * DESCRIPTION:				ɾ����ע��
	 * @param followingID		��ע��ID����ǰ�û���ID
	 * @param followedID		����ע�ߵ�ID
	 * @return					��ע���б�
	 ************************************************************/
	public List<Follower> delFollowed(int followingID, int followedID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				��ӱ���ע��
	 * @param followingID		��ע��ID����ǰ�û���ID
	 * @param followingName		��ע����������ǰ�û���ID
	 * @param followedID		����ע��ID
	 * @param followedName		����ע������
	 * @return					����Ƿ�ɹ�
	 * 							1���ɹ�
	 * 							0��ʧ��
	 ************************************************************/
	public int addFollowed(int followingID, String followingName, int followedID, String followedName){
		return 0;
	}

	/*************************************************************
	 * DESCRIPTION:				ɾ������
	 * @param userID			��ǰ�û���ID
	 * @param delFriendID		��ɾ�����û���ID
	 * @return					�����б�����ˢ�½���
	 ************************************************************/
	public List<Friend> delFriend(int userID, int delFriendID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				��Ӻ���
	 * @param userID			��ǰ�û�ID
	 * @param userName			��ǰ�û���
	 * @param addedFriendID		����ӵ��û���ID
	 * @param addedFriendName	����ӵ��û����û���
	 * @return					�����û��б�
	 ************************************************************/
	public List<Friend> addFriend(int userID, String userName, int addedFriendID, String addedFriendName){
		return null;
	}
	
	/*************************************************************
	 * DESCRIPTION:				��ȡ�����б�
	 * @param userID			��ǰ�û���ID
	 * @return					�����б�
	 ************************************************************/
	public List<Friend> getFriendList(int userID){
		return null;
	}
	
	/*************************************************************
	 * DESCRIPTION:				��ȡ��ע���б�
	 * @param userID			��ǰ�û���ID
	 * @return					���ع�ע���б�
	 ************************************************************/
	public List<Follower> getFollowerList(int userID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				��ȡ������ID
	 * @param userID			��ǰ�û�ID
	 * @return					���������б�
	 ************************************************************/
	public List<Applyer> getApplyerList(int userID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				ɾ�������б��е�����
	 * @param userID			��ǰ��¼�õ�ID
	 * @param addedID			����ӵ��û���ID
	 * @return					�����Ƿ�ɾ���ɹ���״̬��
	 * 							1��ɾ���ɹ�
	 * 							0��ɾ��ʧ��
	 ************************************************************/
	public int delApplyer(int userID, int addedID){
		return 0;
	}
	
	/*************************************************************
	 * DESCRIPTION:				�û�������Ӻ���
	 * @param userID			��ǰ��¼�û���ID
	 * @param userName			��ǰ��¼�û����û���
	 * @param addedID			����ӵ��û���ID
	 * @param addedName			����ӵ��û����û���
	 * @return					���������б�������״̬
	 ************************************************************/
	public int addApplyer(int userID, String userName, int addedID, String addedName){
		return 0;
	}
	/*************************************************************
	 * DESCRIPTION:				���ú����Ƿ���Է����Լ��ĸ�����Ϣ
	 * @param userID			��ǰ�û���ID
	 * @param friendID			������ĺ��ѵ�ID
	 ************************************************************/
	public void setAccessInfo(int userID, int friendID){
	}
	
	/*************************************************************
	 * DESCRIPTION:				���ú����Ƿ���Բ鿴�Լ��Ķ�̬
	 * @param userID			��ǰ�û���ID
	 * @param friendID			������ĺ��ѵ�ID
	 ************************************************************/
	public void setAccessNews(int userID, int friendID){
	}
	
	/*************************************************************
	 * �����û�
	 * @param isAccessInfo
	 ************************************************************/
	public void isAccessInfo(boolean isAccessInfo){}
	
	/**
	 * ***********************************************************
	 * @param isAccessNews
	 */
	public void isAccessNews(boolean isAccessNews){}
}

