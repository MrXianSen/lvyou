package com.qipilang.lvyouplatform.common;

public class Constants {
	
	//Activity Name
	public static final String BEGIN_ACTIVITY = "BeginActivity";
	public static final String CIRCLE_ACTIVITY = "CircleActivity";
	public static final String FINDPASSWORD_ACTIVITY = "FindPasswordActivity";
	public static final String IMAGEGRIDACTIVITY = "ImageGridActivity";
	public static final String IMAGERPAGER_ACTIVITY = "ImagePagerActivity";
	public static final String LOGIN_ACTIVITY = "LoginActivity";
	public static final String MAIN_ACTIVITY = "MainActivity";
	public static final String PHOTO_ACTIVITY = "PhotoActivity";
	public static final String PICCHOOSE_ACTIVITY = "PicChooseActivity";
	public static final String PUBLISHEDACTIVITY = "PublishedActivity";
	public static final String REGISTER_ACTIVITY = "RegisterActivity";
	public static final String RESERPASSWORD_ACTIVITY = "ResetActivity";
	public static final String SECRETQUESTION_ACTIVITY = "SecretQuestionActivity";
	public static final String PERSONAL_INFO_ACTIVITY = "PersonalInfoActivity";
	public static final String EDIT_PROFILE_ACTIVITY = "EditProfileActivity";
	public static final String HEADER_PIC_CHOOSE_ACTIVITY = "PicChoose";
	public static final String HEAD_PIC_CHOOSE = "HeadPicChoose";
	public static final String FRIEND_LIST_ACTIVITY = "FriendListActivity";
	public static final String FRIEND_INFO_ACTIVITY = "FriendInfoActivity";
	public static final String SETTING_ACTIVITY = "SettingActivity";
	public static final String BBS_ITEM_ACTIVITY = "BBSOneItemActivty";
	public static final String SPOT_SELECT_ACTIVITY = "SpotSelectActivity";
	public static final String SPOT_ACTIVITY = "SpotActivity";
	
	
	//Address of project
	public static final String BASE_URL = "http://172.18.10.58:8080/SSH2/";
	//The port of receive message
	public static final int ACCEPT_MSG_PORT = 6789;
	//Encoding method
	public static final String ENCODING = "UTF-8";
	
	//Action address
	public static final String REGISTER_ACTION_URL = "RegisterAction.action";
	public static final String IPBEAN_ACTION_URL = "GetAddrAction.action";
	public static final String LOGIN_ACTION_URL = "LoginAction.action";
	public static final String CHECK_SECRET_QUESTION_URL = "CheckQuestionAction.action";
	public static final String RESET_PASSWORD_URL = "ResetPasswordAction.action";
	public static final String UPLOAD_CIRCLE_URL = "FileUploadAction.action";
	public static final String GET_CIRCLE_ITEM_URL = "GetMemoryAction.action";
	public static final String ADD_COMMENT_URL = "AddCommentAction.action";
	public static final String DELETE_COMMENT_URL = "DeleteCommentAction.action";
	public static final String ADD_FAVORITE_URL = "AddFavoriteAction.action";
	public static final String CANCLE_FAVORITE_URL = "CanFavoriteAction.action";
	public static final String PERSONAL_INFO_URL = "GetUserInfoAction.action";
	public static final String EDIT_PROFILE_URL = "ModifyUserInfoAction.action";
	public static final String MY_MEMORY_ACTION_URL = "MyMemoryAction.action";
	public static final String GET_FRIEND_LIST_URL = "GetFriendListAction.action";
	public static final String GET_FRIEND_INFO_URL = "GetFriendInfoAction.action";
	public static final String UPDATE_LIMIT_URL = "UpdateAccessAction.action";
	public static final String GET_ACCESS_URL = "GetAccessAction.action";
	public static final String BBSPOSTREPLY_ACTION_URL = "AddAnswerAction.action";
	public static final String BBSLIST_ACTION_URL = "GetQuestionAction.action";
	public static final String BBSPOSTBBSLIS_ACTION_URL = "AddQuestionAction.action";
	public static final String BBSREPLISE_ACTION_URL = "GetAnswerAction.action";
	public static final String SPOT_FRAGMENT_URL = "GetRandomSceneAction.action";
	public static final String GET_DETAIL_SCENE_URL = "GetSceneDetailAction.action";
	public static final String GEt_INFORMATION_URL = "GetInformationAction.action";
	public static final String GET_SPOT_PERSON_URL = "GetUserBySceneAction.action";
	public static final String ADD_FRIEND = "ApplyFriendAction.action";
	public static final String GET_MESSAGE_URL = "GetMessageAction.action";
	public static final String APLY_REPLY = "AddFriendAction.action";
	
	//Service Name
	public static final String CONNECTION_SERVICE = "com.qipilang.lvyouplatform.service.ConnectionService";
	public static final String NOTIFICATION_SERVICE = "com.qipilang.lvyouplatform.service.NotificationService";
	
	//SharedPreferences name
	public static final String RECEIVE_MESSAGE_SHAREDPREFERENCES = "receiveMsgPreferences";
	public static final String REGISTER_USER_SHAREDPREFERENCES = "registerUserPreferences";
	public static final String LOGIN_USER_SHAREDPREFERENCES = "loginUserPreferences";
	
	//Notification string
	public static final String NOTIFICATION_TRICKER = "你有一条新消息";
	
	//Gender code
	public static final String MALE = "男";
	public static final String FEMALE = "女";
	
	public static final int MIN_LENGTH_OF_INPUT = 6;
	public static final int MAX_PP_QUESTION = 100;
	public static final int MAX_PP_ANSWER = 200;
	public static final int TIME_OUT = 10 * 1000;
	
	//Response code string
	public static final String REGISTER_RESPONSE = "register_code";
	public static final String LOGIN_RESPONSE = "login_code";
	public static final String USER_NOT_EXIST_STRING = "用户不存在";
	public static final String USERNAME_OR_PASSWORD_ERROR_STRING = "用户名或者密码错误";
	public static final String USER_EXIST_ERROR_STRING = "用户已经存在";
	public static final String USER_INFO_ERROR_STRING = "输入的用户信息不符合规范";
	
	//Toast message
	public static final String TIP_NOT_NULL = "输入信息不能为空";
	public static final String TIP_LENGTH_ERROR = "用户名和密码长度不能小于6个字符";
	public static final String TIP_PASSWORD_ERROR = "两次输入的密码不相同";
	public static final String LOGIN = "登录";
	public static final String REGISTER = "注册";
	public static final String MAIN = "主页";
	public static final String TIP_LENGTH_TO_LONG = "答案长度过大，请重新输入";
	
	//Response code int
	public static final int USER_NOT_EXIST_INT = -1;
	public static final int USENAME_OR_PASSWORD_ERROR_INT = -2;
	public static final int USER_EXIST_ERROR_INT = -1;
	public static final int USER_INFO_ERROR_INT = -2;
	public static final int PASSWORD_NOT_EQUAL = -3;
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int UNKNOWN_ERROR = -10;
	
	public static final int UPLOAD_SUCCESS = 1;
	public static final int UPLOAD_ERROR = -1;
	public static final int NO_MORE_CIRCLEITEM = 0;
	
	public static final int ADD_COMMENT_SUCCESS = 0x00000001;
	public static final int ADD_COMMENT_FAILED	= 0x00000002;
	public static final int DELETE_COMMENT_SUCCESS = 0x00000003;
	public static final int DELETE_COMMENT_FAILED = 0x00000004;
	public static final int ADD_FAVORITE_SUCCESS = 0x00000005;
	public static final int ADD_FAVORITE_FAILED	 = 0x00000006;
	public static final int DELETE_FAVORITE_SUCCESS = 0x00000007;
	public static final int DELETE_FAVORITE_FAILED = 0x00000008;
	public static final int GET_USER_INFO_SUCCESS = 0x00000009;
	public static final int GET_USER_INFO_FAILED = 0x00000010;
	public static final int REFRESH_DATA_SUCCESS = 0x00000011;
	public static final int REFRESH_DATA_FAILED = 0x00000012;
	public static final int LOAD_DATA_SUCCESS = 0x00000013;
	public static final int LOAD_DATA_FAILED = 0x00000014;
	public static final int UPDATE_USERINFO_SUCCESS = 0x00000015;
	public static final int UPDATE_USERINFO_FAILED = 0x00000016;
	public static final int GET_FRIEND_LIST_SUCCESS = 0x00000017;
	public static final int GET_FRIEND_LIST_FAILED = 0x00000018;
	public static final int GET_FRIEND_INFO_FAILED = 0x00000019;
	public static final int GET_FRIEND_INFO_SUCCESS = 0x00000020;
	public static final int IS_LOGIN = 0x00000021;
	public static final int UPDATE_LIMIT_SUCCESS = 0x00000022;
	public static final int UPDATE_LIMIT_FAILED = 0x00000023;
	public static final int GET_LIMIT_SUCCESS = 0x00000024;
	public static final int GET_LIMIT_FAILED = 0x00000025;
	public static final int ADD_SUCCESS = 0x00000026;
	public static final int ADD_FAIL = 0x00000027;
	
	public static final int SCENE_INFO = 0x00000026;
	public static final int SCENE_ID = 0x00000027;
	
	public static final int MY_CIRCLE = 0x00000017;
	public static final int ALL_CIRCLE = 0x00000018;
	
	public static final int WAITING_DONE = 5;
}