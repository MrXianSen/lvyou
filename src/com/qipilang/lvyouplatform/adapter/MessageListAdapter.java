package com.qipilang.lvyouplatform.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;

import com.albery.circledemo.bean.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.MessageAdapter.ViewHolder;
import com.qipilang.lvyouplatform.bean.MessageList;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.StringUtil;

public class MessageListAdapter extends BaseAdapter {

	private List<MessageList> list;
	private LayoutInflater inflater;

	public MessageListAdapter(Context context, List<MessageList> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.notifyDataSetChanged();
	}

	/***************************************************************
	 * DESCRIPTION: 当数据变化时通知Adapter更新界面
	 * 
	 * @param list
	 ***************************************************************/
	public void onDataChange(List<MessageList> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		MessageList user = list.get(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_message_list, null);
			viewHolder = new ViewHolder();

			viewHolder.ml_profile_pic = (ImageView) convertView
					.findViewById(R.id.messagelist_image);
			viewHolder.ml_nickname = (TextView) convertView
					.findViewById(R.id.messagelist_name);
			viewHolder.sending_date = (TextView) convertView
					.findViewById(R.id.messagelist_date);
			viewHolder.ml_last_message = (TextView) convertView
					.findViewById(R.id.messagelist_lastone);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (user.isRead()) {
			list.remove(position);
		} else {
			if (user.getType() == 1 || StringUtil.isEmpty(user.getImage()))
				viewHolder.ml_profile_pic.setImageResource(R.drawable.avtar);
			else {
				ImageLoader.getInstance().displayImage(
						Constants.BASE_URL + user.getImage(),
						viewHolder.ml_profile_pic);
			}
			viewHolder.ml_nickname.setText(user.getSourName());
			viewHolder.sending_date.setText(user.getDate());
			viewHolder.ml_last_message.setText(user.getContent());
		}

		return convertView;
	}

	class ViewHolder {
		ImageView ml_profile_pic;
		TextView ml_nickname;
		TextView sending_date;
		TextView ml_last_message;
	}
}
