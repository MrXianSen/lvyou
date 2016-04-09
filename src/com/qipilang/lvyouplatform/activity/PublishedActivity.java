package com.qipilang.lvyouplatform.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.lidroid.xutils.http.RequestParams;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.common.SpinnerData;
import com.qipilang.lvyouplatform.publish.Bimp;
import com.qipilang.lvyouplatform.publish.FileUtils;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.HttpUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PublishedActivity extends WheelBaseActivity implements OnClickListener, OnWheelScrollListener{

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send; // 发送按钮
	private TextView back; // 返回按钮
	private EditText contentText;

	private String content=""; // 发表的动态的文字内容
	private String province;
	private String city;
	private String scene;
	private List<String> pathDirs; // 选择的图片的路径

	private SharedPreferencesUtil logUser;
	private SharedPreferences contentSharedPreferences;
	
	private ProgressDialog dialog;
	
    
    private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	
    
    int type;
    

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().addActivity(Constants.PUBLISHEDACTIVITY,
				this);
		
		this.type = getIntent().getExtras().getInt("circleType");
		setContentView(R.layout.activity_selectimg);
		Init();
		
		setUpViews();
		setUpListener();
		setUpData();
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province_publish);
		mViewCity = (WheelView) findViewById(R.id.id_city_publish);
		mViewDistrict = (WheelView) findViewById(R.id.id_district_publish);
	}

	private void setUpListener() {

		// 添加change事件
		mViewProvince.addScrollingListener(this);
		// 添加change事件
		mViewCity.addScrollingListener(this);
		// 添加change事件
		mViewDistrict.addScrollingListener(this);
	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				PublishedActivity.this, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(5);
		mViewCity.setVisibleItems(5);
		mViewDistrict.setVisibleItems(5);

		updateCities();
		updateAreas();
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void onScrollingFinished(WheelView wheel) {
		WheelView iWheel;
		if (wheel == mViewProvince) {
			updateCities();
			iWheel = mViewDistrict;
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[iWheel
			        .getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewCity) {
			updateAreas();
			iWheel = mViewDistrict;
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[iWheel
			        .getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[wheel
					.getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	//初始化界面
	public void Init() {

		logUser = new SharedPreferencesUtil(this);
		contentText = (EditText)findViewById(R.id.circle_content);
		
		if(getSharedPreferences("circleContent", Activity.MODE_PRIVATE) != null){
			contentSharedPreferences = getSharedPreferences("circleContent", Activity.MODE_PRIVATE);
			contentText.setText(contentSharedPreferences.getString("content", null));
		}

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 初始化适配器
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					//将内容保存为共享数据
					if(!StringUtil.isEmpty(contentText.getText().toString()))
					{
						content = contentText.getText().toString();
						saveToSharedPreferences(content);
					}
					
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

		// 返回按钮
		back = (TextView) findViewById(R.id.publish_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				contentSharedPreferences.edit().clear().commit();
				ActivityManager.getInstance().deleteActivity(
						Constants.PUBLISHEDACTIVITY);
			}
		});
		// 点击发送按钮
		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				content = contentText.getText().toString();
				province = mCurrentProviceName;
				city = mCurrentCityName;
				scene = mCurrentDistrictName;
				pathDirs = new ArrayList<String>();
				for (int i = 0; i < Bimp.drr.size(); i++) {
					String Str = Bimp.drr.get(i).substring(
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					// 保存选择的图片的地址
					pathDirs.add(FileUtils.SDPATH+Str+".JPEG");
				}
				// 高清的压缩图片全部就在 list 路径里面了
				// 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
				// 完成上传服务器后 .........
				if(StringUtil.isEmpty(content) && (pathDirs == null || pathDirs.size() == 0)){
					Toast.makeText(PublishedActivity.this, "文字和图片不能都为空...", Toast.LENGTH_SHORT).show();
					ActivityManager.getInstance().deleteActivity(Constants.PUBLISHEDACTIVITY);
					return;
				}
				dialog = ProgressDialog.show(PublishedActivity.this, "提示", "正在提交，请稍后...");
				upload();
				contentSharedPreferences.edit().clear().commit();
			}
		});
	}

	private void saveToSharedPreferences(String content){
		contentSharedPreferences = getSharedPreferences("circleContent", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = contentSharedPreferences.edit();
		editor.putString("content", content);
		editor.commit();
	}
	
	Handler uploadIamgeHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int uploadResult = data.getInt("uploadResult");
			switch(uploadResult){
			case Constants.UPLOAD_SUCCESS:
				uploadSuccess();
				break;
			case Constants.UPLOAD_ERROR:
				uploadFailed();
				break;
			default:
				return;
			}
			Intent intent = new Intent(PublishedActivity.this, CircleActivity.class);
			intent.putExtra("circleType", type);
			intent.putExtra("userID", logUser.getString("userID", "0"));
			startActivity(intent);
			//结束发布动态Activity
			ActivityManager.getInstance().deleteActivity(Constants.PUBLISHEDACTIVITY);
		};
	};
	
	private void uploadSuccess(){
		dialog.dismiss();
		Toast.makeText(this, "发布成功!", Toast.LENGTH_SHORT).show();
	}
	
	private void uploadFailed(){
		dialog.dismiss();
		Toast.makeText(this, "发布失败...", Toast.LENGTH_SHORT).show();
	}

	
	/************************************************************************************
	 * DESCRIPTION:				upload pictures and data
	 ***********************************************************************************/
	private void upload(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				RequestParams params = new RequestParams();
				//get current logged user info
				String userName = logUser.getString("userName", "匿名");
				String headerUrl = logUser.getString("headUrl", "");
				String userID = logUser.getString("userID", "0");
				//set request parameters
				params.addBodyParameter("userId", CastUtil.castString(userID));
				params.addBodyParameter("userName", userName);
				params.addBodyParameter("content", content);
				params.addBodyParameter("province", province);
				params.addBodyParameter("city", city);
				params.addBodyParameter("scene", scene);
				params.addBodyParameter("headPic", headerUrl);
				int length = pathDirs.size();
				for(int i=0; i<length; i++){
					//the pictures of user want to upload
					params.addBodyParameter("file"+i, new File(pathDirs.get(i)));
				}
				HttpUtil.uploadMethod(params, uploadIamgeHandler, 0);
			}
		}).start();
	}
	
	/************************************************************************************
	 * DESCRIPTION:				添加GridView的适配器
	 * @author 					张建国
	 ***********************************************************************************/
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 试图容器
		private int selectedPosition = -1; // 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
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
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	/************************************************************************************
	 * DESCRIPTION:				点击添加图片时弹出框
	 * @author 					张建国
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
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this,
							PicChooseActivity.class);
					intent.putExtra("circleType", type);
					startActivity(intent);
					dismiss();
				}
			});
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
	 * DESCRIPTION:				相机拍照
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
			if (Bimp.drr.size() < 9 && resultCode == -1) {
				Bimp.drr.add(path);
			}
			break;
		}
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
	}
	@Override
	public void onClick(View arg0) {
	}
}
