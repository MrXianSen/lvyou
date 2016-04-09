package com.qipilang.lvyouplatform.activity;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.MessageAdapter;
import com.qipilang.lvyouplatform.bean.Message;

import android.R.string;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MessageActivity extends Activity implements
		android.view.View.OnClickListener {
	private ListView messageListView;
	private EditText inputEditText;
	private Button sendButton;
	private MessageAdapter messageAdapter;
	private List<Message> messageList = new ArrayList<Message>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		initMessage();
		messageAdapter = new MessageAdapter(this, R.layout.item_message,
				messageList);
		inputEditText = (EditText) findViewById(R.id.msg_input_text);
		sendButton = (Button) findViewById(R.id.msg_send);
		messageListView = (ListView) findViewById(R.id.msg_list_view);
		messageListView.setAdapter(messageAdapter);
		sendButton.setOnClickListener(this);

	}

	private void initMessage() {
		Message message1 = new Message("建国儿子好", Message.TYPE_RECEIVED);
		messageList.add(message1);
		Message message2 = new Message("徐琛爸爸好", Message.TYPE_SENT);
		messageList.add(message2);
		Message message3 = new Message("儿子给爸爸买烟了吗", Message.TYPE_RECEIVED);
		messageList.add(message3);
	}

	@Override
	public void onClick(View v) {

		String content = inputEditText.getText().toString();
		if (!"".equals(content)) {
			Message message = new Message(content, Message.TYPE_SENT);
			messageList.add(message);
			// 有新消息是刷新
			messageAdapter.notifyDataSetChanged();
			// 定位到最后一行
			messageListView.setSelection(messageList.size());
			// 清空
			inputEditText.setText("");
		}
	}
}
