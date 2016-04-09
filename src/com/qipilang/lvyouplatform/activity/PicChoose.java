package com.qipilang.lvyouplatform.activity;

import java.io.Serializable;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.publish.AlbumHelper;
import com.qipilang.lvyouplatform.publish.ImageBucket;
import com.qipilang.lvyouplatform.publish.ImageBucketAdapter;
import com.qipilang.lvyouplatform.util.ActivityManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class PicChoose extends Activity {
	private List<ImageBucket> dataList;
	private GridView gridView;
	private ImageBucketAdapter adapter;// 自定义adapter
	private AlbumHelper helper;
	
	private TextView btnCancle;
	
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);

		ActivityManager.getInstance().addActivity(Constants.HEADER_PIC_CHOOSE_ACTIVITY, this);
		
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		
		btnCancle = (TextView)findViewById(R.id.select_alblum_cancle);
		
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(PicChoose.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(PicChoose.this,
						HeadPicChoose.class);
				intent.putExtra(PicChoose.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivity(intent);
			}
		});
	
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().deleteActivity(Constants.HEADER_PIC_CHOOSE_ACTIVITY);
			}
		});
	}
}
