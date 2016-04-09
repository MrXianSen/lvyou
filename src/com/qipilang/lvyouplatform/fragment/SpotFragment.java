package com.qipilang.lvyouplatform.fragment;

import java.io.Serializable;
import java.util.List;

import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.SpotActivity;
import com.qipilang.lvyouplatform.activity.SpotSelectActivity;
import com.qipilang.lvyouplatform.bean.Scene;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.SpotManagement;
import com.qipilang.lvyouplatform.util.StringUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

@SuppressLint("HandlerLeak")
public class SpotFragment extends Fragment implements OnClickListener {

	private RelativeLayout more;

	private int index = 0;

	private List<Scene> sceneList;

	private TextView detail;

	private View view;
	private ImageSwitcher is;
	
	private Button recommendSpotButton;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_spot, container, false);
		initImageSwitcher();

		more = (RelativeLayout) view.findViewById(R.id.spot_select);
		more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),
						SpotSelectActivity.class);
				startActivity(intent);
			}
		});

		detail = (TextView) view.findViewById(R.id.recommend_spot_detail);

		recommendSpotButton = (Button) view
				.findViewById(R.id.click_in_recommend_spot);
		recommendSpotButton.setOnClickListener(this);
		
		if(sceneList == null || sceneList.size() == 0)
			loadData();
		else
			showView();
		return view;
	}

	private void loadData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				sceneList = SpotManagement.Instance.getSceneList();

				int code = (sceneList == null) ? Constants.FAIL
						: Constants.SUCCESS;
				data.putInt("code", code);
				msg.setData(data);

				handler.sendMessage(msg);
			}
		}).start();
	}

	private void initImageSwitcher() {
		// 得到ImageSwitcher对象
		is = (ImageSwitcher) view.findViewById(R.id.image_switcher);

		// 实现并设置工厂内部接口的makeView方法，用来显示视图。
		is.setFactory(new ViewFactory() {

			public View makeView() {
				return new ImageView(getActivity());
			}
		});

		// 设置图片来源
		is.setImageResource(R.drawable.im_skin_icon_imageload_failed);

		// 设置点击监听器
		is.setOnClickListener(this);

		// 设置切入动画
		is.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.slide_in_left));
		// 设置切出动画
		is.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.slide_out_right));
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();

			int code = data.getInt("code");
			switch (code) {
			case Constants.SUCCESS:
				showView();
				break;
			case Constants.FAIL:
				break;
			}
		};
	};

	private void showView() {
		if (sceneList != null && sceneList.size() > 0) {
			if (!StringUtil.isEmpty(sceneList.get(index).getPicUrl())) {
				ImageLoader.getInstance().displayImage(
						Constants.BASE_URL + sceneList.get(index).getPicUrl(),
						(ImageView) is.getCurrentView());
			}
			detail.setText(sceneList.get(index).getDescription());
		}
	}

	public void gotoDetail(){
		Intent intent = new Intent(getActivity(), SpotActivity.class);
		intent.putExtra("type", Constants.SCENE_ID);
		intent.putExtra("sceneID", sceneList.get(index).getId());
		startActivity(intent);
	}
	
	@Override
	public void onClick(View view) {
		if(sceneList == null || sceneList.size() == 0)
			return;
		switch (view.getId()) {
		case R.id.image_switcher:
			index++;
			if (index >= sceneList.size()) {
				index = 0;
			}
			ImageLoader.getInstance().displayImage(
					Constants.BASE_URL + sceneList.get(index).getPicUrl(),
					(ImageView) is.getCurrentView());
			detail.setText(sceneList.get(index).getDescription());
			break;
		case R.id.click_in_recommend_spot:
			gotoDetail();
			break;
		}
	}
}
