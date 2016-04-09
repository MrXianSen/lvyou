package com.qipilang.lvyouplatform.activity;

import java.util.Arrays;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.UserManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class FindPasswordActivity extends Activity implements OnClickListener {

	private EditText userNameText;
	private Spinner ppQuestionText;
	private EditText ppAnswerText;

	private TextView findText;
	private TextView back;

	private String username;
	private String question;
	private String answer;

	private ProgressDialog dialog;

	private ArrayAdapter<String> adapter;
	private List<String> list;

	private SharedPreferences logUserPreferences;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("submitQuestionResponse");
			switch (code) {
			case Constants.SUCCESS:
				dialog.dismiss();
				submitQuestionSuccess();
				break;
			case Constants.FAIL:
				dialog.dismiss();
				Toast.makeText(FindPasswordActivity.this, "回答错误",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				dialog.dismiss();
				Toast.makeText(FindPasswordActivity.this, "其他错误",
						Toast.LENGTH_SHORT).show();
				return;
			}
		};
	};
	Runnable findPassworRunnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			int code = UserManagement.Instance.submitPpQuestion(username,
					question, answer);
			data.putInt("submitQuestionResponse", code);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Add this activity to activity manager
		ActivityManager.getInstance().addActivity(
				Constants.FINDPASSWORD_ACTIVITY, this);
		setContentView(R.layout.activity_find_password);
		init();
	}

	private void init() {
		userNameText = (EditText) findViewById(R.id.userName_input);
		ppQuestionText = (Spinner) findViewById(R.id.secret_question_find);
		ppAnswerText = (EditText) findViewById(R.id.secret_answer_find);
		findText = (TextView) findViewById(R.id.find_next_step);
		back = (TextView) findViewById(R.id.find_back);
		initializeSpinner();
		findText.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	private void submitQuestionSuccess() {
		logUserPreferences = getSharedPreferences(
				Constants.LOGIN_USER_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = logUserPreferences.edit();
		editor.putString("userName", username);
		editor.commit();
		Intent intent = new Intent(FindPasswordActivity.this,
				ResetPasswordActivity.class);
		intent.putExtra("isLogin", 0);
		startActivity(intent);
		ActivityManager.getInstance().deleteActivity(Constants.FINDPASSWORD_ACTIVITY);
	}

	private boolean checkInput() {
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(question)
				|| StringUtil.isEmpty(answer)) {
			Toast.makeText(this, Constants.TIP_NOT_NULL, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void initializeSpinner() {
		String[] array = getResources().getStringArray(R.array.ppQeustionArray);
		list = Arrays.asList(array);
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
		ppQuestionText.setAdapter(adapter);
		ppQuestionText.setOnItemSelectedListener(itemSelectedListener);
		question = (String) ppQuestionText.getSelectedItem();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.find_next_step:
			username = userNameText.getText().toString();
			question = ppQuestionText.getSelectedItem().toString();
			answer = ppAnswerText.getText().toString();

			if (!checkInput())
				return;
			dialog = ProgressDialog.show(this, "提示", "正在处理，请稍后...");
			Thread thread = new Thread(findPassworRunnable);
			thread.start();
			break;
		case R.id.find_back:
			ActivityManager.getInstance().deleteActivity(Constants.FINDPASSWORD_ACTIVITY);
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
