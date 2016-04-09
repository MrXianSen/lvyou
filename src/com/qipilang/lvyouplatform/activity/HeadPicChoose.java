package com.qipilang.lvyouplatform.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.publish.AlbumHelper;
import com.qipilang.lvyouplatform.publish.Bimp;
import com.qipilang.lvyouplatform.publish.ImageGridAdapter;
import com.qipilang.lvyouplatform.publish.ImageItem;
import com.qipilang.lvyouplatform.util.ActivityManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HeadPicChoose extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	private List<ImageItem> dataList;
	private GridView gridView;
	private ImageGridAdapter adapter;// 自定义的adapter
	private AlbumHelper helper;
	private Button bt;
	private TextView btnCancle;

	@SuppressLint("HandlerLeak") 
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(HeadPicChoose.this, "只能选取1张照片", 400).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);
		ActivityManager.getInstance().addActivity(Constants.HEAD_PIC_CHOOSE, this);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();
		bt = (Button) findViewById(R.id.bt);
		btnCancle = (TextView)findViewById(R.id.select_pic_cancle);
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().deleteActivity(Constants.HEAD_PIC_CHOOSE);
			}
		});
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
					Intent intent = new Intent(HeadPicChoose.this,
							EditProfileActivity.class);
					startActivity(intent);
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < 1) {
						Bimp.drr.add(list.get(i));
					}
				}
				ActivityManager.getInstance().deleteActivity(Constants.HEAD_PIC_CHOOSE);
				ActivityManager.getInstance().deleteActivity(Constants.HEADER_PIC_CHOOSE_ACTIVITY);
			}

		});
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(HeadPicChoose.this, dataList,
				mHandler, 1);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new com.qipilang.lvyouplatform.publish.ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();
			}

		});

	}
}
