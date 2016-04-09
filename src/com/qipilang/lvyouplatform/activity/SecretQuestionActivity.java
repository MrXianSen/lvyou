package com.qipilang.lvyouplatform.activity;

import java.util.Arrays;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
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
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SecretQuestionActivity extends Activity implements OnClickListener {

	private Spinner ppQuestionText;
	private EditText ppAnswerText;
	private TextView completeText;

	private String ppQuestion;
	private String ppAnswer;
	
	private ArrayAdapter<String> adapter;				//下拉框适配器
	private List<String> list;

	private SharedPreferencesUtil logUser;
	
	private UserInfo user;

	private ProgressDialog dialog;						//等待进度框
	
	private TextView back;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			int code = data.getInt(Constants.REGISTER_RESPONSE);

			switch (code) {
			case Constants.USER_EXIST_ERROR_INT:
				dialog.dismiss();
				Toast.makeText(SecretQuestionActivity.this,
						Constants.USER_EXIST_ERROR_STRING, Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.USER_INFO_ERROR_INT:
				dialog.dismiss();
				Toast.makeText(SecretQuestionActivity.this,
						Constants.USER_INFO_ERROR_STRING, Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.PASSWORD_NOT_EQUAL:
				dialog.dismiss();
				Toast.makeText(SecretQuestionActivity.this,
						"两次密码不一致", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				dialog.dismiss();
				if (code > 0) {
					registerSuccess(code);
					return;
				}
				Toast.makeText(SecretQuestionActivity.this, "未知错误",
						Toast.LENGTH_SHORT).show();
				return;
			}
		};
	};
	Runnable registerRunnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			int code = UserManagement.Instance.register(user, logUser);
			data.putInt(Constants.REGISTER_RESPONSE, code);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.SECRETQUESTION_ACTIVITY, this);
		setContentView(R.layout.activity_secret_question);
		
		logUser = new SharedPreferencesUtil(this);
		
		init();
	}

	private void init(){
		ppQuestionText = (Spinner) findViewById(R.id.secret_question);
		ppAnswerText = (EditText) findViewById(R.id.secret_answer);
		completeText = (TextView) findViewById(R.id.secret_next_step);
		back = (TextView)findViewById(R.id.secret_back);
		
		back.setOnClickListener(this);
		// 初始化问题的下拉列表
		initializeSpinner();
		completeText.setOnClickListener(this);
	}
	
	private void initializeSpinner() {
		String[] array = getResources().getStringArray(R.array.ppQeustionArray);
		list = Arrays.asList(array);
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
		ppQuestionText.setAdapter(adapter);
		ppQuestionText.setOnItemSelectedListener(itemSelectedListener);
		ppQuestion = (String) ppQuestionText.getSelectedItem();
	}

	private boolean checkInput() {
		if (StringUtil.isEmpty(ppQuestion) || StringUtil.isEmpty(ppAnswer)) {
			Toast.makeText(this, Constants.TIP_NOT_NULL, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (!StringUtil.checkLength(Constants.MAX_PP_ANSWER, ppAnswer)) {
			Toast.makeText(this, Constants.TIP_LENGTH_TO_LONG,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void register() {
		ppAnswer = ppAnswerText.getText().toString();
		ppQuestion = (String) ppQuestionText.getSelectedItem();

		if (!checkInput())
			return;
		dialog = ProgressDialog.show(this, "注册提示", "正在注册，请稍后...");
		user = new UserInfo();
		user.setUserName(logUser.getString("userName", null));
		user.setPassword(logUser.getString("password", null));
		user.setConfirmPassword(logUser.getString("confirmPassword", null));
		user.setBirthday(logUser.getString("birthday", "1900-01-01"));
		user.setGender(logUser.getString("gender", "男"));
		user.setPpQuestion(ppQuestion);
		user.setPpAnswer(ppAnswer);

		// 创建登录线程
		Thread thread = new Thread(registerRunnable);
		thread.start();
	}
	public void registerSuccess(int userID) {
		logUser.updateString("userID", CastUtil.castString(userID));
		Intent intent = new Intent(SecretQuestionActivity.this,
				MainActivity.class);
		startActivity(intent);
		ActivityManager.getInstance().clear();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.secret_next_step:
			register();
			break;
		case R.id.secret_back:
			ActivityManager.getInstance().deleteActivity(Constants.SECRETQUESTION_ACTIVITY);
			break;
		default:
			return;
		}
	}

	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			arg0.setVisibility(View.VISIBLE);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			arg0.setVisibility(View.VISIBLE);
		}
	};
}
