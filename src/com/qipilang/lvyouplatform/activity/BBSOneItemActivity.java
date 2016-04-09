package com.qipilang.lvyouplatform.activity;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.BBSOneItemAdapter;
import com.qipilang.lvyouplatform.bean.BBSList;
import com.qipilang.lvyouplatform.bean.BBSReply;
import com.qipilang.lvyouplatform.bean.PageBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.BBSManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;
import com.qipilang.lvyouplatform.util.TranslateUtil;
import com.qipilang.lvyouplatform.view.MyListView;
import com.qipilang.lvyouplatform.view.MyListView.ILoadListener;
import com.qipilang.lvyouplatform.view.MyListView.IRefalshListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BBSOneItemActivity extends Activity implements ILoadListener,
		IRefalshListener, OnClickListener {

	private List<BBSReply> lisBbsReplies = new ArrayList<BBSReply>();
	private BBSList objBbsList;
	private Intent intent = new Intent();
	private BBSOneItemAdapter bbsOneItemAdapter;

	private TextView bbsQUserNickNameTextView;
	private TextView bbsQSendDateTextView;
	private TextView bbsQContentTextView;
	
	private EditText replyText;
	private ImageView replyImg;
	
	private TextView back;
	
	private MyListView bbsListView;
	
	private SharedPreferencesUtil logUser;
	
	private int questionID;

	private String jsonString;
	
	private ProgressDialog dialog;

	@SuppressLint("HandlerLeak") 
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
	
			int code = msg.getData().getInt("code");
			
			switch (code) {
			case Constants.REFRESH_DATA_SUCCESS:
				jsonString = (String) msg.obj;
				lisBbsReplies = TranslateUtil.jsonToBbsReplies(jsonString);
				if(lisBbsReplies == null){
					return;
				}
				bbsOneItemAdapter.onDataChange(lisBbsReplies);
				bbsListView.reflashComplete();
				break;
			case Constants.REFRESH_DATA_FAILED:
				Toast.makeText(BBSOneItemActivity.this, "刷新数据失败∩╮(︶︿︶)╭∩╮ ",
						Toast.LENGTH_SHORT).show();
			case Constants.LOAD_DATA_SUCCESS:
				bbsOneItemAdapter.onDataChange(lisBbsReplies);
				bbsListView.loadComplete();
				break;
			case Constants.LOAD_DATA_FAILED:
				Toast.makeText(BBSOneItemActivity.this, "加载完成 ",
						Toast.LENGTH_SHORT).show();
				bbsListView.loadComplete();
				break;
			case Constants.SUCCESS:
				dialog.dismiss();
				replyText.setText("");
				getReplyJson();
				break;
			case Constants.FAIL:
				dialog.dismiss();
				Toast.makeText(BBSOneItemActivity.this, "回复失败 ",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbsoneitem);
		ActivityManager.getInstance().addActivity(Constants.BBS_ITEM_ACTIVITY, BBSOneItemActivity.this);
		// initBBSReply();

		logUser = new SharedPreferencesUtil(this);
		bbsQUserNickNameTextView = (TextView) findViewById(R.id.bbsQUserNicknameTextView);
		bbsQSendDateTextView = (TextView) findViewById(R.id.bbsQSendDateTextView);
		bbsQContentTextView = (TextView) findViewById(R.id.bbsQContentTextView);

		intent = getIntent();
		getBBSListObjectFromIntent(intent);

		bbsQContentTextView.setText(objBbsList.getStrBbsQContent());
		bbsQSendDateTextView.setText(objBbsList.getStrBbsQSendDate());
		bbsQUserNickNameTextView.setText(objBbsList.getStrBbsQUserNickName());
		replyText = (EditText)findViewById(R.id.reply_content);
		replyImg = (ImageView)findViewById(R.id.send_reply);
		replyImg.setOnClickListener(this);
		
		bbsOneItemAdapter = new BBSOneItemAdapter(this, (ArrayList<BBSReply>) lisBbsReplies);
		bbsListView = (MyListView) findViewById(R.id.bbs_AuserListView);
		bbsListView.setAdapter(bbsOneItemAdapter);
		bbsListView.setInterface(BBSOneItemActivity.this, BBSOneItemActivity.this);
		
		back = (TextView)findViewById(R.id.bbs_item_back);
		back.setOnClickListener(this);
		
		questionID = getIntent().getExtras().getInt("intBbsQId");
		
		getReplyJson();
	}

	public void getBBSListObjectFromIntent(Intent intent) {

		String strBbsQUserNickName = intent
				.getStringExtra("strBbsQUserNickName");
		System.out.println(strBbsQUserNickName);
		int intBbsQUserId = intent.getIntExtra("intBbsQUserId", 0);
		System.out.println(intBbsQUserId);
		String strBbsQSendDate = intent.getStringExtra("strBbsQSendDate");
		System.out.println(strBbsQSendDate);
		String strBbsQContent = intent.getStringExtra("strBbsQContent");
		System.out.println(strBbsQContent);
		int intBbsQUserProPicId = intent.getIntExtra("intBbsQUserProPicId", 0);
		System.out.println(intBbsQUserProPicId);
		int intBbsQId = intent.getIntExtra("intBbsQId", 0);
		System.out.println(intBbsQId);

		objBbsList = new BBSList(intBbsQUserId, intBbsQUserProPicId,
				strBbsQUserNickName, strBbsQSendDate, strBbsQContent, intBbsQId);

	}


	private void getReplyJson() {
		PageBean.currentPage = 0;
		// isLoaded = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				String json = null;

				json = BBSManagement.Instance.getReplies(
						objBbsList.getIntQId(), PageBean.currentPage);
				System.out.println(json);
				if (json == null || json.length() == 0) {
					data.putInt("code", Constants.REFRESH_DATA_FAILED);
					
				} else {
					data.putInt("code", Constants.REFRESH_DATA_SUCCESS);
				}
				msg.setData(data);
				msg.obj = json;
				handler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public void onReflash() {
		getReplyJson();
	}

	@Override
	public void onLoad() {
		new Thread(new Runnable() {
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

	private int loadMoreData() {
		if (PageBean.currentPage < PageBean.totalPage) {
			List<BBSReply> tempLisBbsReplies = TranslateUtil
					.jsonToBbsReplies(BBSManagement.Instance.getReplies(
							objBbsList.getIntBbsQUserId(),
							++PageBean.currentPage));
			if (tempLisBbsReplies != null && tempLisBbsReplies.size() != 0) {
				updateDatas(tempLisBbsReplies);
				return Constants.LOAD_DATA_SUCCESS;
			}
		}
		return Constants.LOAD_DATA_FAILED;
	}

	private void updateDatas(List<BBSReply> bbsReplies) {

		if (bbsReplies == null || bbsReplies.size() == 0)
			return;

		int length = bbsReplies.size();

		for (int i = 0; i < length; i++) {
			lisBbsReplies.add(bbsReplies.get(i));
		}
	}

	private void submitReply(){
		String text = replyText.getText().toString();
		
		if(StringUtil.isEmpty(text)){
			Toast.makeText(this, "恢复内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		String userID = logUser.getString("userID", "");
		String userName = logUser.getString("userName", "");
		
		final BBSReply reply = new BBSReply();
		reply.setIntBbsQID(questionID);
		reply.setIntBbsReplyerId(CastUtil.castInt(userID));
		reply.setStrBbsReplyerNickName(userName);
		reply.setStrBbsRContent(text);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				
				int code = BBSManagement.Instance.postReply(reply);
				
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.bbs_item_back:
			ActivityManager.getInstance().deleteActivity(Constants.BBS_ITEM_ACTIVITY);
			break;
		case R.id.send_reply:
			dialog = ProgressDialog.show(this, "", "");
			submitReply();
			break;
		}
	}

}