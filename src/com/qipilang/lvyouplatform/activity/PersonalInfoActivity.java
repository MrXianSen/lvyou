package com.qipilang.lvyouplatform.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

public class PersonalInfoActivity extends Activity implements OnClickListener{

	private ImageView profilePic;

	private Button editProf;
	private Button myPosts;

	private TextView nickname;
	private TextView gender;
	private TextView age;
	private TextView birthday;
	private TextView edu;
	private TextView tel;
	private TextView email;
	private TextView addr;
	private TextView hobby;
	private TextView back;
	
	private UserInfo userInfo;
	private String userID;
	private SharedPreferencesUtil logUser;
	
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.PERSONAL_INFO_ACTIVITY, this);
		
		setContentView(R.layout.activity_my_personal_info);
		logUser = new SharedPreferencesUtil(this);
		userID = logUser.getString("userID", "0");
		//initialize view
		initView();
		//load data
		loadData();
	}

	private void initView() {
		profilePic = (ImageView) findViewById(R.id.pro_pic);

		editProf = (Button) findViewById(R.id.edit_profile);
		myPosts = (Button) findViewById(R.id.my_posts);

		nickname = (TextView) findViewById(R.id.my_nickname);
		gender = (TextView) findViewById(R.id.my_gender);
		age = (TextView) findViewById(R.id.my_age);
		birthday = (TextView) findViewById(R.id.my_birthday);
		edu = (TextView) findViewById(R.id.my_edu);
		tel = (TextView) findViewById(R.id.my_tel);
		email = (TextView) findViewById(R.id.my_email);
		addr = (TextView) findViewById(R.id.my_addr);
		hobby = (TextView) findViewById(R.id.my_hobby);
		back = (TextView)findViewById(R.id.user_Info_back);
		
		editProf.setOnClickListener(this);
		myPosts.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	
	private void getUserInfoSuccess(){
		if(!StringUtil.isEmpty(userInfo.getHeadUrl())){
			String headPic = Constants.BASE_URL + userInfo.getHeadUrl();
			ImageLoader.getInstance().displayImage(headPic, profilePic);
		}
		else
			profilePic.setImageResource(R.drawable.default_head_pic);
		if(!StringUtil.isEmpty(userInfo.getUserName()))
			nickname.setText(userInfo.getUserName());
		else
			nickname.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getGender()))
			gender.setText(userInfo.getGender());
		else
			gender.setText("未填写");
		if(userInfo.getAge() > 0)
			age.setText(CastUtil.castString(userInfo.getAge()));
		else
			age.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getBirthday()))
			birthday.setText(userInfo.getBirthday());
		else
			birthday.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getEducationBackgtound()))
			edu.setText(userInfo.getEducationBackgtound());
		else
			edu.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getTeliphoneNumber()))
			tel.setText(userInfo.getTeliphoneNumber());
		else
			tel.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getEmail()))
			email.setText(userInfo.getEmail());
		else
			email.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getAddress()))
			addr.setText(userInfo.getAddress());
		else
			addr.setText("未填写");
		if(!StringUtil.isEmpty(userInfo.getHobby()))
			hobby.setText(userInfo.getHobby());
		else
			hobby.setText("未填写");
	}
	
	@SuppressLint("HandlerLeak") 
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			dialog.dismiss();
			Bundle data = msg.getData();
			
			int code = data.getInt("code");
			
			switch(code){
			case Constants.GET_USER_INFO_SUCCESS:
				getUserInfoSuccess();
				break;
			case Constants.GET_USER_INFO_FAILED:
				break;
			}
		};
	};
	
	private void loadData(){
		dialog = ProgressDialog.show(this, "提示", "正在努力加载(๑•̀ㅂ•́)و✧...");
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				userInfo = UserManagement.Instance.getUserInfo(userID);
				
				int code = (userInfo != null) ? Constants.GET_USER_INFO_SUCCESS : Constants.GET_USER_INFO_FAILED;
				data.putInt("code", code);
				msg.setData(data);
				
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void editInfo(){
		Intent intent = new Intent(this, EditProfileActivity.class);
		startActivity(intent);
		logUser.updateSharedPreferences(userInfo);
	}
	
	private void myCircle(){
		Intent intent = new Intent(PersonalInfoActivity.this, CircleActivity.class);
		intent.putExtra("circleType", Constants.MY_CIRCLE);
		intent.putExtra("userID", userID);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.edit_profile:
			editInfo();
			break;
		case R.id.my_posts:
			myCircle();
			break;
		case R.id.user_Info_back:
			ActivityManager.getInstance().deleteActivity(Constants.PERSONAL_INFO_ACTIVITY);
			break;
		}
	}
}
