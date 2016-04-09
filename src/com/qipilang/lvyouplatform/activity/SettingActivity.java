package com.qipilang.lvyouplatform.activity;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.Limit;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.FriendManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.view.CheckSwitchButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener{

	private CheckSwitchButton mCheckSwithcButton;
	private CheckSwitchButton mCheckSwithcButton2;
	
	private TextView settingBack;
	
	private Button settingBtn;
	
	private SharedPreferencesUtil logUser;
	private String friendID;
	String userID;
	String accessInfo = "0";
	String accessNews = "0";
	
	Limit limit;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManager.getInstance().addActivity(Constants.SETTING_ACTIVITY, this);
		logUser = new SharedPreferencesUtil(this);
		friendID = getIntent().getExtras().getString("friendID");
		setContentView(R.layout.activity_setting_page);
		userID = logUser.getString("userID", "0");
		initView();
		loadData();
	}
	
	private void initView() {
		mCheckSwithcButton = (CheckSwitchButton)findViewById(R.id.mCheckSwithcButton);
		mCheckSwithcButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					accessInfo = "1";
				}else{
					accessInfo = "0";
				}
			}
		});
		mCheckSwithcButton2 = (CheckSwitchButton)findViewById(R.id.mCheckSwithcButton2);
		mCheckSwithcButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					accessNews = "1";
				}else{
					accessNews = "0";
				}
			}
		});
		
		settingBack = (TextView)findViewById(R.id.setting_back);
		settingBtn = (Button)findViewById(R.id.setting_button);
		settingBack.setOnClickListener(this);
		settingBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.setting_back:
			ActivityManager.getInstance().deleteActivity(Constants.SETTING_ACTIVITY);
			break;
		case R.id.setting_button:
			updateLimit();
			break;
		}
	}
	
	private void loadData(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				limit = FriendManagement.Instance.getLimit(userID, friendID);
				int code;
				code = (limit == null) ? Constants.GET_LIMIT_FAILED : Constants.GET_LIMIT_SUCCESS;
				
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void updateLimit(){
		if(mCheckSwithcButton.isChecked())
			accessInfo = "1";
		if(mCheckSwithcButton2.isChecked())
			accessNews = "1";
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				int code = FriendManagement.Instance.updateLimit(userID, friendID, accessInfo, accessNews);
				
				code = (code == 1) ? Constants.UPDATE_LIMIT_SUCCESS : Constants.UPDATE_LIMIT_FAILED;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("code");
			switch(code){
			case Constants.UPDATE_LIMIT_SUCCESS:
				Toast.makeText(SettingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
				break;
			case Constants.UPDATE_LIMIT_FAILED:
				if(accessInfo.equals("1"))
					mCheckSwithcButton.setChecked(true);
				else
					mCheckSwithcButton.setChecked(false);
				if(accessNews.equals("1"))
					mCheckSwithcButton2.setChecked(true);
				else
					mCheckSwithcButton2.setChecked(false);
				break;
			case Constants.GET_LIMIT_SUCCESS:
				if(limit.getAccessInfo().equals("1"))
					mCheckSwithcButton.setChecked(true);
				else
					mCheckSwithcButton.setChecked(false);
				if(limit.getAccessNews().equals("1"))
					mCheckSwithcButton2.setChecked(true);
				else
					mCheckSwithcButton2.setChecked(false);
				break;
			case Constants.GET_LIMIT_FAILED:
				Toast.makeText(SettingActivity.this, "获取失败...", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
}
