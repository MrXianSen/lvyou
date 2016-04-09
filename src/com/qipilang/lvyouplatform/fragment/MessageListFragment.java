package com.qipilang.lvyouplatform.fragment;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.MainActivity;
import com.qipilang.lvyouplatform.adapter.MessageListAdapter;
import com.qipilang.lvyouplatform.application.MyApplication;
import com.qipilang.lvyouplatform.bean.MessageList;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.MessageManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MessageListFragment extends Fragment implements OnItemClickListener{
	
	public MessageListAdapter mAdapter;
	public ListView listView;
	
	public List<MessageList> list = new ArrayList<MessageList>();
	
	private AlertDialog.Builder alertDialog;
	
	private SharedPreferencesUtil logUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_messagelist, container, false);
		listView = (ListView) view.findViewById(R.id.messagelist_listview);
		
		logUser = new SharedPreferencesUtil(getActivity());
		initData();
		mAdapter = new MessageListAdapter(getActivity(), list);
		
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		return view;
	}
	
	private void initData(){
		final String userID = logUser.getString("userID", "0");
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				list = MessageManagement.Instance.getMessageList(userID);
				
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
			case Constants.SUCCESS:
				loadSuccess();
				break;
			case Constants.FAIL:
				Toast.makeText(getActivity(), "没有更多的数据", Toast.LENGTH_SHORT).show();
				break;
			case Constants.ADD_SUCCESS:
				initData();
				break;
			case Constants.ADD_FAIL:
				Toast.makeText(getActivity(), "处理失败...", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	private void loadSuccess(){
		mAdapter.onDataChange(list);
	}

	int type = 0;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final MessageList message = list.get(position);
		alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("提示");
		if(message.getType() == 0){
			alertDialog.setMessage(message.getSourName() + "想加你为好友！");
			alertDialog.setCancelable(false);
			
			alertDialog.setPositiveButton("接受", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					type = 1;
					replyAply(CastUtil.castString(message.getSourID()), message.getSourName());
				}
			});
			alertDialog.setNegativeButton("拒绝", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					type = 0;
					replyAply(CastUtil.castString(message.getSourID()), message.getSourName());
				}
			});
		}
		else if(message.getType() == 1){
			return;
		}
		else if(message.getType() == 2){
			alertDialog.setMessage("您已经处理过该申请！");
			alertDialog.setCancelable(true);
			
			alertDialog.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					return;
				}
			});
		}
		alertDialog.show();
	}
	
	private void replyAply(final String sourID, final String sourName){
		final String userID = logUser.getString("userID","0");
		final String userName = logUser.getString("userName", "");
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				int code = MessageManagement.Instance.replyAply(sourID, sourName, userID, userName, type);
				
				code = (code == 1) ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
}
