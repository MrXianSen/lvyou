package com.qipilang.lvyouplatform.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.BBSReply;
import com.qipilang.lvyouplatform.util.CastUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 自定义的Adapter 继承自BaseAdapter
 * 
 * @author Administrator
 * 
 */
public class BBSOneItemAdapter extends BaseAdapter {

	List<BBSReply> lisBbsReplies;
	LayoutInflater inflater;

	public BBSOneItemAdapter(Context context, ArrayList<BBSReply> list) {
		this.lisBbsReplies = list;
		this.inflater = LayoutInflater.from(context);
	}

	/***************************************************************
	 * DESCRIPTION: 当数据变化时通知Adapter更新界面
	 * 
	 * @param list
	 ***************************************************************/
	public void onDataChange(List<BBSReply> list) {
		this.lisBbsReplies = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return lisBbsReplies.size();
	}

	@Override
	public Object getItem(int position) {
		return lisBbsReplies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		BBSReply reply = (BBSReply)lisBbsReplies.get(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bbs_auserlistview, null);
			viewHolder.bbsReplyerUserNameTextView = (TextView) convertView
					.findViewById(R.id.bbsRUserNameTextView);
			viewHolder.bbsRDaTextView = (TextView) convertView
					.findViewById(R.id.bbsRSendDateTextView);
			viewHolder.bbsRContentTextView = (TextView) convertView
					.findViewById(R.id.bbsRContentTextView);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.bbsReplyerUserNameTextView.setText(CastUtil.castString(reply.getStrBbsReplyerNickName()));
		viewHolder.bbsRDaTextView.setText(CastUtil.castString(reply.getStrBbsRDate()));
		viewHolder.bbsRContentTextView.setText(CastUtil.castString(reply.getStrBbsRContent()));
		
		return convertView;
	}

	class ViewHolder {
		TextView bbsReplyerUserNameTextView;
		TextView bbsRDaTextView;
		TextView bbsRContentTextView;
	}

}