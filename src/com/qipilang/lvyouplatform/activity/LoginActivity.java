package com.qipilang.lvyouplatform.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

public class LoginActivity extends Activity implements OnClickListener {

	private Button loginButton;
	private TextView backText;
	private EditText accountEditText;
	private EditText passwordEditText;
	private String userName;
	private String passwordString;
	
	private ProgressDialog dialog;

	private SharedPreferencesUtil logUser;
	
	private static int STATE_CODE = 0;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.LOGIN_ACTIVITY, this);
		setContentView(R.layout.activity_login);
		
		//初始化当前登录用户的管理类
		logUser = new SharedPreferencesUtil(this);
		
		init();
	}

	private void init(){
		setTitle(Constants.LOGIN);
		loginButton = (Button) findViewById(R.id.log_login);
		backText = (TextView)findViewById(R.id.login_back);
		accountEditText = (EditText) findViewById(R.id.log_account);
		passwordEditText = (EditText) findViewById(R.id.log_password);
		loginButton.setOnClickListener(this);
		backText.setOnClickListener(this);
	}

	private void loginSuccess(int userID) {
		saveToSharedPreferences(userID);
		
		Log.d("TAG", logUser.getString("userID", "0"));

		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		dialog.dismiss();
		// 关闭当前登录Activity中的所有Activity
		ActivityManager.getInstance().clear();
	}
	private void login() {
		userName = accountEditText.getText().toString();
		passwordString = passwordEditText.getText().toString();

		if (!checkInput())
			return;
		dialog = ProgressDialog.show(this, "登录提示", "正在登录，请稍等...");
		Thread loginThread = new Thread(loginRunnable);
		loginThread.start();
	}
	
	private boolean checkInput() {
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwordString)) {
			Toast.makeText(this, Constants.TIP_NOT_NULL, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}
	
	private void saveToSharedPreferences(int userID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userID);
		userInfo.setUserName(userName);
		userInfo.setPassword(passwordString);
		//TODO add code here
		logUser.updateSharedPreferences(userInfo);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.log_login:
			login();
			break;
		case R.id.login_back:
			ActivityManager.getInstance().deleteActivity(Constants.LOGIN_ACTIVITY);
			break;
		default:
			return;
		}
	}
	
	/*********************************************************************************
	 * DESCRIPTION:					LOGIN THREAD AND HANDLER						 *
	 *********************************************************************************/
	@SuppressLint("HandlerLeak")
	Handler loginHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);

			Bundle data = msg.getData();

			int code = data.getInt(Constants.LOGIN_RESPONSE);
			switch (code) {
			case Constants.USER_NOT_EXIST_INT:
				dialog.dismiss();
				Toast.makeText(LoginActivity.this,
						Constants.USER_NOT_EXIST_STRING, Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.USENAME_OR_PASSWORD_ERROR_INT:
				dialog.dismiss();
				Toast.makeText(LoginActivity.this,
						Constants.USERNAME_OR_PASSWORD_ERROR_STRING,
						Toast.LENGTH_SHORT).show();
				break;
			default:
				if (code > 0) {
					loginSuccess(code);
					return;
				}
				dialog.dismiss();
				Toast.makeText(LoginActivity.this,
						"您的网络不稳定...",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
	};
	Runnable loginRunnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			STATE_CODE = UserManagement.Instance
					.login(userName, passwordString, logUser);
			data.putInt(Constants.LOGIN_RESPONSE, STATE_CODE);
			msg.setData(data);
			loginHandler.sendMessage(msg);
		}
	};
}
