package com.qipilang.lvyouplatform.fragment;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.SpotActivity;
import com.qipilang.lvyouplatform.adapter.SpotCurrentinfroAdapter;
import com.qipilang.lvyouplatform.bean.SCIList;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.SpotManagement;
import com.qipilang.lvyouplatform.view.MyListView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("HandlerLeak") public class SCIFragment extends Fragment {
	private List<SCIList> list = new ArrayList<SCIList>();
	private SpotCurrentinfroAdapter sciAdapter;
	private ListView listView;
	private SCIList sciList;
	
	private int type;
	private String sceneID;
	private String sceneInfo;
	
	private String scene;
	private View view;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_spot_currentinfro, container, false);
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
		if(list == null || list.size() == 0)
			loadData();
		listView = (ListView) view.findViewById(R.id.sci_listview);
		sciAdapter = new SpotCurrentinfroAdapter(getActivity(), list);
		listView.setAdapter(sciAdapter);
	}
	
	private void loadData(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				type = (type == Constants.SCENE_ID) ? 1 : 0;
				list = SpotManagement.Instance.getSCIList(type, scene);
				
				int code = (list == null) ? Constants.FAIL : Constants.SUCCESS;
				
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
			case Constants.FAIL:
				Toast.makeText(getActivity(), "获取信息失败...", Toast.LENGTH_SHORT).show();
				break;
			case Constants.SUCCESS:
				loadSuccess();
				break;
			}
		};
	};
	
	private void loadSuccess(){
		if(list == null || list.size() == 0) return;
		sciAdapter.onDataChange(list);
	}
}
