package com.qipilang.lvyouplatform.activity;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.UserInfo;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.publish.Bimp;
import com.qipilang.lvyouplatform.publish.FileUtils;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

public class EditProfileActivity extends Activity implements OnClickListener {

	private ImageView myProPic;

	private TextView editProfilePic;
	private Button editDoneButton;
	private Button editCancelButton;

	private EditText editNickname;
	private EditText editGender;
	private EditText editAge;
	private EditText editBirthday;
	private EditText editEdu;
	private EditText editTel;
	private EditText editEmail;
	private EditText editAddr;
	private EditText editHobby;

	private ProgressDialog dialog;

	private TextView back;

	private SharedPreferencesUtil logUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityManager.getInstance().addActivity(
				Constants.EDIT_PROFILE_ACTIVITY, this);

		setContentView(R.layout.activity_edit_profile);

		logUser = new SharedPreferencesUtil(this);
		initView();
		loadData();

	}

	private void initView() {
		myProPic = (ImageView) findViewById(R.id.my_pro_pic);

		editProfilePic = (TextView) findViewById(R.id.edit_profile_pic);
		editDoneButton = (Button) findViewById(R.id.edit_done);
		editCancelButton = (Button) findViewById(R.id.edit_cancel);

		editNickname = (EditText) findViewById(R.id.edit_nickname);
		editGender = (EditText) findViewById(R.id.edit_gender);
		editAge = (EditText) findViewById(R.id.edit_age);
		editBirthday = (EditText) findViewById(R.id.edit_birthday);
		editEdu = (EditText) findViewById(R.id.edit_edu);
		editTel = (EditText) findViewById(R.id.edit_tel);
		editEmail = (EditText) findViewById(R.id.edit_email);
		editAddr = (EditText) findViewById(R.id.edit_addr);
		editHobby = (EditText) findViewById(R.id.edit_hobby);
		back = (TextView) findViewById(R.id.edit_Info_back);

		back.setOnClickListener(this);
		editProfilePic.setOnClickListener(this);
		editDoneButton.setOnClickListener(this);
		editCancelButton.setOnClickListener(this);
	}

	private void loadData() {
		editNickname.setText(logUser.getString("userName", "未填写"));
		if (!StringUtil.isEmpty(logUser.getString("headUrl", null))){
			String url = Constants.BASE_URL + logUser.getString("headUrl", null);
			ImageLoader.getInstance().displayImage(url, myProPic);
		}
		else
			myProPic.setImageResource(R.drawable.default_head_pic);
		String gender = logUser.getString("gender", "未填写");
		editGender.setText(gender);
		String age = logUser.getString("age", "0");
		editAge.setText(age);
		editBirthday.setText(logUser.getString("birthday", "未填写"));
		editEdu.setText(logUser.getString("education", "未填写"));
		editTel.setText(logUser.getString("telNumber", "未填写"));
		editEmail.setText(logUser.getString("email", "未填写"));
		editAddr.setText(logUser.getString("address", "未填写"));
		editHobby.setText(logUser.getString("hobby", "未填写"));
		loading();
	}

	protected void onRestart() {
		loading();
		super.onRestart();
	};

	public void updateUserInfoSuccess() {
		Intent intent = new Intent(EditProfileActivity.this,
				PersonalInfoActivity.class);
		startActivity(intent);
		dialog.dismiss();
		ActivityManager.getInstance().deleteActivity(
				Constants.EDIT_PROFILE_ACTIVITY);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			switch (msg.what) {
			case 1:
				if (Bimp.bmp.size() > 0)
					myProPic.setImageBitmap(Bimp.bmp.get(0));
				else
					myProPic.setImageResource(R.drawable.default_head_pic);
				break;
			}

			int updateResult = data.getInt("uploadResult");
			switch (updateResult) {
			case Constants.UPLOAD_SUCCESS:
				updateUserInfoSuccess();
				break;
			case Constants.UPLOAD_ERROR:
				dialog.dismiss();
				if (StringUtil.isEmpty(logUser.getString("headUrl", null)))
					myProPic.setImageResource(R.drawable.default_head_pic);
				else
					ImageLoader.getInstance().displayImage(
							Constants.BASE_URL
									+ logUser.getString("headUrl", null),
							myProPic);
				Toast.makeText(EditProfileActivity.this, "提交失败...",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.drr.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						try {
							String path = Bimp.drr.get(Bimp.max);
							System.out.println(path);
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							// 保存到文件中
							FileUtils.saveBitmap(bm, "" + newStr);
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public void updateUserInfo() {
		if (StringUtil.isEmpty(editNickname.getText().toString())) {
			Toast.makeText(EditProfileActivity.this, "用户名不能为空(。﹏。*) ",
					Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				RequestParams params = new RequestParams();
				params.addBodyParameter("userId",
						CastUtil.castString(logUser.getString("userID", null)));
				params.addBodyParameter("userName", editNickname.getText()
						.toString());
				params.addBodyParameter("sex", editGender.getText().toString());
				params.addBodyParameter("age", CastUtil.castString(CastUtil
						.castInt((editAge.getText().toString()))));
				params.addBodyParameter("birthday", editBirthday.getText()
						.toString());
				params.addBodyParameter("edubackground", editEdu.getText()
						.toString());
				params.addBodyParameter("tel", editTel.getText().toString());
				params.addBodyParameter("email", editEmail.getText().toString());
				params.addBodyParameter("address", editAddr.getText()
						.toString());
				params.addBodyParameter("hobby", editHobby.getText().toString());
				if (Bimp.drr.size() > 0) {
					String headUrl = Bimp.drr.get(0);
					params.addBodyParameter("headPic", new File(headUrl));
				} else
					params.addBodyParameter("headPic", "");
				HttpUtil.uploadMethod(params, handler, 1);
			}
		}).run();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.edit_Info_back:
			ActivityManager.getInstance().deleteActivity(
					Constants.EDIT_PROFILE_ACTIVITY);
			break;
		case R.id.edit_profile_pic:
			new PopupWindows(EditProfileActivity.this, myProPic);
			break;
		case R.id.edit_done:
			dialog = ProgressDialog.show(EditProfileActivity.this, "提示",
					"正在提交...");
			updateUserInfo();
			break;
		case R.id.edit_cancel:
			ActivityManager.getInstance().deleteActivity(
					Constants.EDIT_PROFILE_ACTIVITY);
			break;
		}
	}

	/************************************************************************************
	 * DESCRIPTION: 点击添加图片时弹出框
	 * 
	 * @author 张建国
	 ***********************************************************************************/
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			bt1.setVisibility(View.GONE);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);

			// 相册中选取照片
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 清空Bimp中的数据
					Bimp.drr.clear();
					Bimp.bmp.clear();
					Bimp.max = 0;
					Intent intent = new Intent(EditProfileActivity.this,
							PicChoose.class);
					startActivity(intent);
					dismiss();
				}
			});
			// 取消
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	/***********************************************************************************
	 * DESCRIPTION: 相机拍照
	 **********************************************************************************/
	public void photo() {
		// 拍照
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myimage/", String.valueOf(System.currentTimeMillis())
				+ ".jpg");
		path = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 1 && resultCode == -1) {
				Bimp.drr.add(path);
				loading();
			}
			break;
		}
	}
}
