package com.qipilang.lvyouplatform.activity;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class ResetPasswordActivity extends Activity implements OnClickListener {

	private EditText newPasswordText;
	private TextView submitText;
	private TextView backText;

	private String newPassword;
	private String userName;
	
	private int isLogin = Constants.IS_LOGIN;

	private SharedPreferencesUtil logUser;

	private ProgressDialog dialog;

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();

			int code = UserManagement.Instance.resetPassword(userName,
					newPassword);

			data.putInt("resetPasswordResponse", code);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("resetPasswordResponse");
			// 取消等待界面
			switch (code) {
			case Constants.SUCCESS:
				resetSuccess();
				break;
			case Constants.FAIL:
				resetFailed();
				break;
			default:
				otherError();
				return;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add this activity to activity manager
		ActivityManager.getInstance().addActivity(
				Constants.RESERPASSWORD_ACTIVITY, this);
		setContentView(R.layout.activity_reset_password);
		
		if(getIntent().getExtras().containsKey("isLogin")){
			isLogin = getIntent().getExtras().getInt("isLogin");
		}
		
		logUser = new SharedPreferencesUtil(this);
		init();
	}

	private void init() {
		newPasswordText = (EditText) findViewById(R.id.new_password);
		submitText = (TextView) findViewById(R.id.reset_next_step);
		backText = (TextView) findViewById(R.id.reset_back);
		submitText.setOnClickListener(this);
		backText.setOnClickListener(this);
	}

	private boolean checkInput() {
		if (StringUtil.isEmpty(newPassword)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (StringUtil.checkLength(6, newPassword)) {
			Toast.makeText(this, "密码长度不能小于6个字符", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void resetSuccess() {
		
		// 跳转到登录界面
		Intent intent;
		if(isLogin == Constants.IS_LOGIN){
			intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
			startActivity(intent);
			logUser.updateString("password", newPassword);
			ActivityManager.getInstance().deleteActivity(Constants.RESERPASSWORD_ACTIVITY);
		}
		else{
			intent = new Intent(ResetPasswordActivity.this,
				LoginActivity.class);
			startActivity(intent);
			ActivityManager.getInstance().clear();
		}
		dialog.dismiss();
	}
	private void resetFailed(){
		dialog.dismiss();
		Toast.makeText(ResetPasswordActivity.this, "重置密码失败",
				Toast.LENGTH_SHORT).show();
	}
	private void otherError(){
		dialog.dismiss();
		Toast.makeText(ResetPasswordActivity.this, "网络错误...",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.reset_next_step:
			newPassword = newPasswordText.getText().toString();

			userName = logUser.getString("userName", null);
			if (!checkInput())
				return;
			dialog = ProgressDialog.show(ResetPasswordActivity.this, "提示",
					"正在提交，请稍等...");
			// 提交重置密码请求
			Thread thread = new Thread(runnable);
			thread.start();
			break;
		case R.id.reset_back:
			ActivityManager.getInstance().deleteActivity(Constants.RESERPASSWORD_ACTIVITY);
			break;
		default:
			return;
		}
	}
}
