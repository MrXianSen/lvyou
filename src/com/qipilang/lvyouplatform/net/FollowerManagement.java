package com.qipilang.lvyouplatform.net;

import java.util.List;

import com.qipilang.lvyouplatform.bean.Applyer;
import com.qipilang.lvyouplatform.bean.Follower;
import com.qipilang.lvyouplatform.bean.Friend;

/**************************************************************************
 * 
 * DESCRIPTION:	关注者管理
 * 
 * @author 		张建国
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class FollowerManagement {
	public static FollowerManagement Instance = new FollowerManagement();
	
	/*************************************************************
	 * DESCRIPTION:				删除关注者
	 * @param followingID		关注者ID，当前用户的ID
	 * @param followedID		被关注者的ID
	 * @return					关注者列表
	 ************************************************************/
	public List<Follower> delFollowed(int followingID, int followedID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				添加被关注者
	 * @param followingID		关注者ID，当前用户的ID
	 * @param followingName		关注者姓名，当前用户的ID
	 * @param followedID		被关注者ID
	 * @param followedName		被关注者姓名
	 * @return					添加是否成功
	 * 							1，成功
	 * 							0，失败
	 ************************************************************/
	public int addFollowed(int followingID, String followingName, int followedID, String followedName){
		return 0;
	}

	/*************************************************************
	 * DESCRIPTION:				删除好友
	 * @param userID			当前用户的ID
	 * @param delFriendID		被删除的用户的ID
	 * @return					好友列表，并且刷新界面
	 ************************************************************/
	public List<Friend> delFriend(int userID, int delFriendID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				添加好友
	 * @param userID			当前用户ID
	 * @param userName			当前用户名
	 * @param addedFriendID		被添加的用户的ID
	 * @param addedFriendName	被添加的用户的用户名
	 * @return					返回用户列表
	 ************************************************************/
	public List<Friend> addFriend(int userID, String userName, int addedFriendID, String addedFriendName){
		return null;
	}
	
	/*************************************************************
	 * DESCRIPTION:				获取好友列表
	 * @param userID			当前用户的ID
	 * @return					好友列表
	 ************************************************************/
	public List<Friend> getFriendList(int userID){
		return null;
	}
	
	/*************************************************************
	 * DESCRIPTION:				获取关注者列表
	 * @param userID			当前用户的ID
	 * @return					返回关注者列表
	 ************************************************************/
	public List<Follower> getFollowerList(int userID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				获取申请者ID
	 * @param userID			当前用户ID
	 * @return					返回申请列表
	 ************************************************************/
	public List<Applyer> getApplyerList(int userID){
		return null;
	}

	/*************************************************************
	 * DESCRIPTION:				删除申请列表中的数据
	 * @param userID			当前登录用的ID
	 * @param addedID			被添加的用户的ID
	 * @return					返回是否删除成功的状态码
	 * 							1，删除成功
	 * 							0，删除失败
	 ************************************************************/
	public int delApplyer(int userID, int addedID){
		return 0;
	}
	
	/*************************************************************
	 * DESCRIPTION:				用户申请添加好友
	 * @param userID			当前登录用户的ID
	 * @param userName			当前登录用户的用户名
	 * @param addedID			被添加的用户的ID
	 * @param addedName			被添加的用户的用户名
	 * @return					返回申请列表中申请状态
	 ************************************************************/
	public int addApplyer(int userID, String userName, int addedID, String addedName){
		return 0;
	}
	/*************************************************************
	 * DESCRIPTION:				设置好友是否可以访问自己的个人信息
	 * @param userID			当前用户的ID
	 * @param friendID			被处理的好友的ID
	 ************************************************************/
	public void setAccessInfo(int userID, int friendID){
	}
	
	/*************************************************************
	 * DESCRIPTION:				设置好友是否可以查看自己的动态
	 * @param userID			当前用户的ID
	 * @param friendID			被处理的好友的ID
	 ************************************************************/
	public void setAccessNews(int userID, int friendID){
	}
	
	/*************************************************************
	 * 设置用户
	 * @param isAccessInfo
	 ************************************************************/
	public void isAccessInfo(boolean isAccessInfo){}
	
	/**
	 * ***********************************************************
	 * @param isAccessNews
	 */
	public void isAccessNews(boolean isAccessNews){}
}

