package com.qipilang.lvyouplatform.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

/**************************************************************************
 * 
 * DESCRIPTION: 程序启动首先调用的Activity
 * 
 * @author 张建国
 * 
 * @since 2016.3.5
 * 
 * @version 1.0
 * 
 *************************************************************************/
@SuppressLint
("HandlerLeak") public class BeginActivity extends Activity implements OnClickListener {

	private TextView regText;
	private TextView loginView;
	private TextView forgetPasswordText;
	private Intent intent;
	
	String userName;
	String password;
	
	private ProgressDialog dialog;
	
	private SharedPreferencesUtil logUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//1. Add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.BEGIN_ACTIVITY, this);
		//2. Set full screen
		setFullScreen();
		setContentView(R.layout.activity_begin);
		//3. initialize event of views 
		if(loginAutomatic()){
			return;
		}
		init();
	}

	/**
	 * 初始化界面
	 */
	private void init() {
		forgetPasswordText = (TextView)findViewById(R.id.forget_password);
		regText = (TextView) findViewById(R.id.text_reg);
		loginView = (TextView) findViewById(R.id.login);
		//添加事件
		regText.setOnClickListener(this);
		loginView.setOnClickListener(this);
		forgetPasswordText.setOnClickListener(this);
	}

	/**
	 * 设置全屏
	 */
	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	private boolean loginAutomatic(){
		logUser = new SharedPreferencesUtil(this);
		
		if(logUser != null){
			userName = logUser.getString("userName", null);
			password = logUser.getString("password", null);
			if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(password)){
				dialog = ProgressDialog.show(this, "", "");
				new Thread(runnable).start();
			}
		}
		return false;
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			int code = UserManagement.Instance.login(userName, password, logUser);
			data.putInt("code", code);
			msg.setData(data);
			
			handler.sendMessage(msg);
		}
	};
	
	Handler handler = new Handler(){
		@SuppressLint("HandlerLeak") 
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("code");
			switch (code) {
			case Constants.USER_NOT_EXIST_INT:
				dialog.dismiss();
				Toast.makeText(BeginActivity.this,
						Constants.USER_NOT_EXIST_STRING, Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.USENAME_OR_PASSWORD_ERROR_INT:
				dialog.dismiss();
				Toast.makeText(BeginActivity.this,
						Constants.USERNAME_OR_PASSWORD_ERROR_STRING,
						Toast.LENGTH_SHORT).show();
				break;
			default:
				if (code > 0) {
					loginSuccess(code);
					return;
				}
				dialog.dismiss();
				Toast.makeText(BeginActivity.this,
						"您的网络不稳定...",
						Toast.LENGTH_SHORT).show();
				return;
			}
		};
	};
	private void loginSuccess(int userID) {
		Log.d("TAG", logUser.getString("userID", "0"));

		Intent intent = new Intent(BeginActivity.this, MainActivity.class);
		startActivity(intent);
		dialog.dismiss();
		// 关闭当前登录Activity中的所有Activity
		ActivityManager.getInstance().clear();
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		//注册
		case R.id.text_reg:
			intent = new Intent(BeginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		//登录
		case R.id.login:
			intent = new Intent(BeginActivity.this, LoginActivity.class);
			startActivity(intent);
			break;
		//忘记密码
		case R.id.forget_password:
			intent = new Intent(BeginActivity.this, FindPasswordActivity.class);
			startActivity(intent);
			break;
		//其他错误
		default:
			Toast.makeText(this, "System Error", Toast.LENGTH_SHORT).show();
			return;
		}
	}
}
