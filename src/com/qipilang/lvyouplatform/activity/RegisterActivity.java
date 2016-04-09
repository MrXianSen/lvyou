package com.qipilang.lvyouplatform.activity;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

public class RegisterActivity extends Activity implements OnClickListener,
		OnDateSetListener, OnCheckedChangeListener, OnFocusChangeListener {

	private SharedPreferences regUserPreferences;
	
	private RadioGroup genderGroup;
	private TextView nextStep;
	private TextView backText;
	private EditText userNameText;
	private EditText passwordText;
	private EditText confirmPasswordText;
	private EditText birthdayText;

	private String gender = Constants.MALE;
	private String userName;
	private String password;
	private String confirmPassword;
	private String birthday;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	//时间选择器
	private DatePickerDialog birthdayPicker;
	
	private SharedPreferencesUtil logUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.REGISTER_ACTIVITY, this);
		setContentView(R.layout.activity_resgiter);
		
		logUser = new SharedPreferencesUtil(this);
		init();
	}
	
	/**
	 * 初始化界面
	 */
	private void init(){
		setTitle(Constants.REGISTER);
		userNameText = (EditText) findViewById(R.id.reg_input_username);
		passwordText = (EditText) findViewById(R.id.reg_input_password);
		confirmPasswordText = (EditText) findViewById(R.id.reg_input_password_confirm);
		birthdayText = (EditText) findViewById(R.id.text_birthday);
		genderGroup = (RadioGroup)findViewById(R.id.gender_btn);
		nextStep = (TextView) findViewById(R.id.next_step);
		backText = (TextView)findViewById(R.id.reg_back);
		//添加事件处理方法
		nextStep.setOnClickListener(this);
		backText.setOnClickListener(this);
		birthdayText.setOnClickListener(this);
		birthdayText.setOnFocusChangeListener(this);
		genderGroup.setOnCheckedChangeListener(this);
	}

	
	/**
	 * 显示时间选择器
	 */
	private void showDatePicker() {
		Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		birthdayPicker = new DatePickerDialog(this, this, mYear, mMonth, mDay);
		birthdayPicker.show();
	}

	/**
	 * 更新显示时间
	 */
	private void updateDate() {
		birthdayText.setText(mYear + "-" + mMonth + "-" + mDay);
	}

	private void nextStep() {
		userName = userNameText.getText().toString();
		password = passwordText.getText().toString();
		confirmPassword = confirmPasswordText.getText().toString();
		birthday = birthdayText.getText().toString();

		if (!checkInput())
			return;
		//将用户信息保存到SharedPreference中，以便进行后续操作
		UserInfo userInfo = new UserInfo();
		userInfo.setId(0);
		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		userInfo.setConfirmPassword(confirmPassword);
		userInfo.setBirthday(birthday);
		userInfo.setGender(gender);
		logUser.updateSharedPreferences(userInfo);
		
		//进入到密保问题
		Intent intent = new Intent(RegisterActivity.this, SecretQuestionActivity.class);
		startActivity(intent);
	}

	private boolean checkInput() {
		//非空检测
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)
				|| StringUtil.isEmpty(confirmPassword)
				|| StringUtil.isEmpty(birthday) || StringUtil.isEmpty(gender)){
			Toast.makeText(this, Constants.TIP_NOT_NULL, Toast.LENGTH_SHORT).show();
			return false;
		}
		//字符长度检测
		if (StringUtil.checkLength(Constants.MIN_LENGTH_OF_INPUT, userName)
				|| StringUtil.checkLength(Constants.MIN_LENGTH_OF_INPUT,
						password)
				|| StringUtil.checkLength(Constants.MIN_LENGTH_OF_INPUT,
						confirmPassword)){
			Toast.makeText(this, Constants.TIP_LENGTH_ERROR, Toast.LENGTH_SHORT).show();
			return false;
		}
		//密码匹配检测
		if (!StringUtil.isEqual(password, confirmPassword)){
			Toast.makeText(this, Constants.TIP_PASSWORD_ERROR, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**************************************************************************
	 * DESCRUOTION: 			override method								  *
	 **************************************************************************/
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedID) {
		switch (checkedID) {
		case R.id.gender_male:
			gender = Constants.MALE;
			break;
		case R.id.gender_female:
			gender = Constants.FEMALE;
			break;
		default:
			gender = null;
			return;
		}
	}
	@Override
	public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
			int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		updateDate();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next_step:
			nextStep();
			break;
		case R.id.text_birthday:
			showDatePicker();
			break;
		case R.id.reg_back:
			ActivityManager.getInstance().deleteActivity(Constants.REGISTER_ACTIVITY);
			break;
		default:
			return;
		}
	}
	@Override
	public void onFocusChange(View view, boolean focused) {
		if(focused)
			showDatePicker();
	}
}
