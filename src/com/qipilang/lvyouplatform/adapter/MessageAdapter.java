package com.qipilang.lvyouplatform.adapter;

import java.util.List;

import com.nineoldandroids.view.ViewHelper;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.MyAdapter.ViewHolder;
import com.qipilang.lvyouplatform.bean.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message>{
	
	private int resourceId;
	
	public MessageAdapter(Context context,int textViewResourceId , List<Message> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId,null);
			viewHolder = new ViewHolder();
			viewHolder.lefLayout = (LinearLayout) view.findViewById(R.id.msg_left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.msg_right_layout);
			viewHolder.leftMessage =(TextView) view.findViewById(R.id.msg_left_message);
			viewHolder.rightMessage=(TextView) view.findViewById(R.id.msg_right_message);
			view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if (message.getType() == message.TYPE_RECEIVED) {
			viewHolder.lefLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.leftMessage.setText(message.getContent());
		}else if (message.getType() == message.TYPE_SENT) {
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.lefLayout.setVisibility(View.GONE);
			viewHolder.rightMessage.setText(message.getContent());
		}
		
		return view;
	}
	
	class ViewHolder{
		LinearLayout lefLayout;
		LinearLayout rightLayout;
		TextView leftMessage;
		TextView rightMessage;
		
	}
}
