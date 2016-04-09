package com.qipilang.lvyouplatform.fragment;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.BBSOneItemActivity;
import com.qipilang.lvyouplatform.adapter.BBSAdapter;
import com.qipilang.lvyouplatform.bean.BBSList;
import com.qipilang.lvyouplatform.bean.PageBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.BBSManagement;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;
import com.qipilang.lvyouplatform.view.MyListView;
import com.qipilang.lvyouplatform.view.MyListView.ILoadListener;
import com.qipilang.lvyouplatform.view.MyListView.IRefalshListener;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class BBSFragment extends Fragment implements ILoadListener,IRefalshListener, OnClickListener{

	private List<BBSList> lisBbsLists;


	private BBSAdapter bbsAdapter;

	private Intent intent = null;
	MyListView listView;
	
	private EditText question;
	private ImageView submit;
	
	private SharedPreferencesUtil logUser;

	
	private boolean isLoad;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_bbs, container, false);
		
		logUser = new SharedPreferencesUtil(view.getContext());
		initView(view);
		loadData();
		return view;
	}
	
	
	private void initView(View view){
		listView = (MyListView) view.findViewById(R.id.bbs_listview);
		lisBbsLists = new ArrayList<BBSList>();
		bbsAdapter = new BBSAdapter(getActivity(), lisBbsLists);
		listView.setInterface(this, this);
		listView.setAdapter(bbsAdapter);
		question = (EditText)view.findViewById(R.id.question_text);
		submit = (ImageView)view.findViewById(R.id.question_send);
		
		question.setOnClickListener(this);
		submit.setOnClickListener(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BBSList objBbsList = lisBbsLists.get(position-1);
				intent = new Intent(getActivity(), BBSOneItemActivity.class);
				putBbsListObjIntoIntent(objBbsList);
				startActivity(intent);
			}
		});
	}
	
	private void loadData(){
		PageBean.currentPage = 0;
		isLoad = false;
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				int code;
				lisBbsLists = BBSManagement.Instance.getBBSList(PageBean.currentPage);
				
				if(lisBbsLists == null) code = Constants.REFRESH_DATA_FAILED;
				else code = Constants.REFRESH_DATA_SUCCESS;
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
			case Constants.REFRESH_DATA_FAILED:
				listView.reflashComplete();
				break;
			case Constants.REFRESH_DATA_SUCCESS:
				bbsAdapter.onDataChange(lisBbsLists);
				listView.reflashComplete();
				break;
			case Constants.LOAD_DATA_SUCCESS:
				bbsAdapter.onDataChange(lisBbsLists);
				listView.loadComplete();
				break;
			case Constants.LOAD_DATA_FAILED:
				Toast.makeText(view.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
				listView.loadComplete();
				break;
			case Constants.SUCCESS:
				question.setText("");
				loadData();
				break;
			case Constants.FAIL:
				Toast.makeText(view.getContext(), "发表失败...", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};
	
	
	private void putBbsListObjIntoIntent(BBSList objBbsList) {
		intent.putExtra("intBbsQUserId", objBbsList.getIntBbsQUserId());
		intent.putExtra("strBbsQSendDate", objBbsList.getStrBbsQSendDate());
		intent.putExtra("strBbsQContent", objBbsList.getStrBbsQContent());
		intent.putExtra("strBbsQUserNickName",
				objBbsList.getStrBbsQUserNickName());
		intent.putExtra("intBbsQUserProPicId",
				objBbsList.getIntBbsQUserProPicId());
		intent.putExtra("intBbsQId", objBbsList.getIntQId());
	}

	@Override
	public void onReflash() {
		loadData();
	}

	public int loadMoreData(){
		if(PageBean.currentPage < PageBean.totalPage){
			List<BBSList> temp = BBSManagement.Instance.getBBSList(++PageBean.currentPage);
			if(temp != null && temp.size() > 0){
				isLoad = true;
				updateDatas(temp);
				return Constants.LOAD_DATA_SUCCESS;
			}
		}
		return Constants.LOAD_DATA_FAILED;
	}

	private void updateDatas(List<BBSList> temp){
		if (temp == null || temp.size() == 0)
			return;

		int length = temp.size();

		for (int i = 0; i < length; i++) {
			lisBbsLists.add(temp.get(i));
		}
	}
	
	@Override
	public void onLoad() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				int code = loadMoreData();
				
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void submitQuestion(){
		final String content = question.getText().toString();
		final String userID = logUser.getString("userID", "");
		final String userName = logUser.getString("userName", "");
		
		if(StringUtil.isEmpty(content)){
			Toast.makeText(getActivity(), "问题内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		final BBSList bbsList = new BBSList();
		bbsList.setIntBbsQUserId(CastUtil.castInt(userID));
		bbsList.setStrBbsQUserNickName(userName);
		bbsList.setStrBbsQContent(content);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				int code = BBSManagement.Instance.postBbsList(bbsList);
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.question_send:
			submitQuestion();
			break;
		}
	}
}