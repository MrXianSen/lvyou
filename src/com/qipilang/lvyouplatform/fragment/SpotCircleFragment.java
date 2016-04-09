package com.qipilang.lvyouplatform.fragment;

import java.util.ArrayList;
import java.util.List;

import com.albery.circledemo.bean.User;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.SpotPersonAdapter;
import com.qipilang.lvyouplatform.bean.Friend;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.FriendManagement;
import com.qipilang.lvyouplatform.net.SpotManagement;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SpotCircleFragment extends Fragment implements OnItemClickListener {

	private View view;

	private List<User> list = new ArrayList<User>();

	private SpotPersonAdapter adapter;

	private ListView listView;

	private int type;

	private String sceneID;

	private String sceneInfo;

	private String scene;

	private User selectedUser;

	private SharedPreferencesUtil logUser;

	private AlertDialog.Builder alertDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		logUser = new SharedPreferencesUtil(getActivity());
		view = inflater
				.inflate(R.layout.fragment_spot_circle, container, false);

		type = getActivity().getIntent().getExtras().getInt("type");
		if (type == Constants.SCENE_ID) {
			sceneID = getActivity().getIntent().getExtras()
					.getString("sceneID");
			scene = sceneID;
		} else {
			sceneInfo = getActivity().getIntent().getExtras()
					.getString("sceneInfo");
			scene = sceneInfo;
		}

		initView();
		return view;
	}

	private void initView() {
		listView = (ListView) view.findViewById(R.id.spot_persons);
		if (list == null || list.size() == 0)
			loadData();
		adapter = new SpotPersonAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	private void loadData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				type = (type == Constants.SCENE_ID) ? 1 : 0;
				list = SpotManagement.Instance.getSCIPerson(type, scene);

				int code = (list == null) ? Constants.FAIL : Constants.SUCCESS;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();

			int code = data.getInt("code");
			switch (code) {
			case Constants.FAIL:
				Toast.makeText(getActivity(), "获取数据失败...", Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.SUCCESS:
				loadSuccess();
				break;
			case Constants.ADD_SUCCESS:
				Toast.makeText(getActivity(), "请求发送成功，请耐心等待...",
						Toast.LENGTH_SHORT).show();
				break;
			case Constants.ADD_FAIL:
				Toast.makeText(getActivity(), "请求发送失败...",
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	private void loadSuccess() {
		adapter.onDataChange(list);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedUser = list.get(position);
		alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("提示");
		alertDialog.setMessage("加为好友？");
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton("添加", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				addFriend();
			}
		});
		alertDialog.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		alertDialog.show();
	}

	private void addFriend() {
		final String sourID = logUser.getString("userID", "");
		final String sourName = logUser.getString("userName", "");
		final String destID = selectedUser.getId();
		final String destName = selectedUser.getName();
		
		if(sourID.equals(destID)){
			Toast.makeText(getActivity(), "不能自己添加自己哟︿(￣︶￣)︿", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				int code = FriendManagement.Instance.addFriend(sourID,
						sourName, destID, destName);

				code = (code == 1) ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;

				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
}
