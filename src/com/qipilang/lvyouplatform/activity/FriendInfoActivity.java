package com.qipilang.lvyouplatform.activity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.FriendInfo;
import com.qipilang.lvyouplatform.bean.Limit;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.FriendManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FriendInfoActivity extends Activity implements OnClickListener {

	private ImageView profilePic;

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
	private TextView setting;
	private TextView noLimiInfo;

	private String friendID;
	private String friendName;
	private String userID;
	private SharedPreferencesUtil logUser;

	private FriendInfo friendInfo;
	
	private ProgressDialog dialog;
	private Limit limit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_info);
		
		ActivityManager.getInstance().addActivity(Constants.FRIEND_INFO_ACTIVITY, this);

		logUser = new SharedPreferencesUtil(this);

		friendID = getIntent().getExtras().getString("friendID");
		friendName = getIntent().getExtras().getString("friendName");
		userID = logUser.getString("userID", "0");

		initView();
		loadData();
	}

	private void initView() {
		profilePic = (ImageView) findViewById(R.id.friend_pic);

		myPosts = (Button) findViewById(R.id.friend_post);

		nickname = (TextView) findViewById(R.id.friend_nickname);
		gender = (TextView) findViewById(R.id.friend_gender);
		age = (TextView) findViewById(R.id.friend_age);
		birthday = (TextView) findViewById(R.id.friend_birthday);
		edu = (TextView) findViewById(R.id.friend_edu);
		tel = (TextView) findViewById(R.id.friend_tel);
		email = (TextView) findViewById(R.id.friend_email);
		addr = (TextView) findViewById(R.id.friend_addr);
		hobby = (TextView) findViewById(R.id.friend_hobby);
		back = (TextView) findViewById(R.id.friend_Info_back);
		setting = (TextView)findViewById(R.id.settings);

		nickname.setText(friendName);
		profilePic.setImageResource(R.drawable.default_head_pic);

		myPosts.setOnClickListener(this);
		back.setOnClickListener(this);
		setting.setOnClickListener(this);
	}

	private void loadData() {
		dialog = ProgressDialog.show(FriendInfoActivity.this, "提示", "正在努力加载...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				friendInfo = FriendManagement.Instance.getFriendInfo(userID,
						friendID);
				int code;
				if (friendInfo == null)
					code = Constants.GET_FRIEND_INFO_FAILED;
				else
					code = Constants.GET_FRIEND_INFO_SUCCESS;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
			Bundle data = msg.getData();
			int code = data.getInt("code");
			switch (code) {
			case Constants.GET_FRIEND_INFO_FAILED:
				Toast.makeText(FriendInfoActivity.this, "获取失败(ノへ￣、)...",
						Toast.LENGTH_SHORT).show();
				break;
			case Constants.GET_FRIEND_INFO_SUCCESS:
				getFriendInfoSuccess();
				break;
			}
		};
	};

	private void getFriendInfoSuccess() {
		limit = friendInfo.getLimit();
		UserInfo friendItem = friendInfo.getUserInfo();
		if ("1".equals(limit.getAccessInfo())) {
			if (!StringUtil.isEmpty(friendItem.getHeadUrl()))
				ImageLoader.getInstance().displayImage(
						Constants.BASE_URL + friendItem.getHeadUrl(),
						profilePic);
			gender.setText(friendItem.getGender());
			age.setText(CastUtil.castString(friendItem.getAge()));
			birthday.setText(friendItem.getBirthday());
			edu.setText(friendItem.getEducationBackgtound());
			tel.setText(friendItem.getTeliphoneNumber());
			email.setText(friendItem.getEmail());
			addr.setText(friendItem.getAddress());
			hobby.setText(friendItem.getHobby());
			return;
		}
		LinearLayout friendInfo = (LinearLayout)findViewById(R.id.friend_info);
		friendInfo.setVisibility(View.GONE);
		noLimiInfo = (TextView)findViewById(R.id.no_limit_info);
		noLimiInfo.setVisibility(View.VISIBLE);
		
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.friend_Info_back:
			ActivityManager.getInstance().deleteActivity(Constants.FRIEND_INFO_ACTIVITY);
			break;
		case R.id.friend_post:
			friendCircle();
			break;
		case R.id.settings:
			Intent intent = new Intent(FriendInfoActivity.this, SettingActivity.class);
			intent.putExtra("friendID", friendID);
			startActivity(intent);
			break;
		}
	}
	
	private void friendCircle(){
		Intent intent = new Intent(FriendInfoActivity.this, CircleActivity.class);
		intent.putExtra("circleType", Constants.MY_CIRCLE);
		intent.putExtra("userID", friendID);
		intent.putExtra("limit", limit.getAccessNews());
		startActivity(intent);
	}
}
