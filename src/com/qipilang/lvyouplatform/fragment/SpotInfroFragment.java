package com.qipilang.lvyouplatform.fragment;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.Scene;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.SpotManagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak") public class SpotInfroFragment extends Fragment  {

	private int type;
	
	private TextView titleText;
	private TextView contentText;
	private ImageView viewImage;
	
	private String sceneID;
	private String sceneInfo;
	
	private List<Scene> sceneList;
	private String scene;
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_spot_infro, container,
				false);
		type = getActivity().getIntent().getExtras().getInt("type");
		if(type == Constants.SCENE_ID){
			sceneID = getActivity().getIntent().getExtras().getString("sceneID");
			scene = sceneID;
		}
		else{
			sceneInfo = getActivity().getIntent().getExtras().getString("sceneInfo");
			scene = sceneInfo;
		}
		
		initView();
		return view;
	}
	
	private void initView(){
		loadData();
		titleText = (TextView)view.findViewById(R.id.spot_name_location);
		contentText = (TextView)view.findViewById(R.id.spot_detail);
		viewImage = (ImageView)view.findViewById(R.id.spot_Image);
	}
	
	private void loadData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				type = (type == Constants.SCENE_ID) ? 1 : 0;
				
				sceneList = SpotManagement.Instance.getSceneList(type, scene);

				int code = (sceneList == null) ? Constants.FAIL
						: Constants.SUCCESS;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			
			int code = data.getInt("code");
			switch(code){
			case Constants.SUCCESS:
				loadDataSuccess();
				break;
			case Constants.FAIL:
				Toast.makeText(getActivity(), "º”‘ÿ ß∞‹...", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	private void loadDataSuccess(){
		int length = sceneList.size();
		if(length > 0){
			titleText.setText(sceneList.get(0).getName());
			contentText.setText(sceneList.get(0).getDescription());
			
			ImageLoader.getInstance().displayImage(Constants.BASE_URL + sceneList.get(0).getPicUrl(), viewImage);
		}
		else{
			contentText.setText("º”‘ÿ ß∞‹®q°…®r(¶·¶‰¶·)®q°…®r");
			contentText.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
		}
	}
}
