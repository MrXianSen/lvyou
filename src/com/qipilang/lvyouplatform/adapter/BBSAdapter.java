package com.qipilang.lvyouplatform.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.BBSList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BBSAdapter extends BaseAdapter {

	private List<BBSList> list;
	private LayoutInflater inflater;

	public BBSAdapter(Context context, List<BBSList> list) {
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	/***************************************************************
	 * DESCRIPTION: 当数据变化时通知Adapter更新界面
	 * 
	 * @param list
	 ***************************************************************/
	public void onDataChange(List<BBSList> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
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

		BBSList information = list.get(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bbs_item, null);
			viewHolder.bbs_nickname = (TextView) convertView
					.findViewById(R.id.bbs_nickname);
			viewHolder.sending_date = (TextView) convertView
					.findViewById(R.id.sending_date);
			viewHolder.bbs_question = (TextView) convertView
					.findViewById(R.id.bbs_question);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bbs_nickname.setText(((BBSList) information)
				.getStrBbsQUserNickName());
		viewHolder.sending_date.setText(((BBSList) information)
				.getStrBbsQSendDate());
		viewHolder.bbs_question.setText(((BBSList) information)
				.getStrBbsQContent());

		return convertView;
	}

	class ViewHolder {
		TextView bbs_nickname;
		TextView sending_date;
		TextView bbs_question;
	}
}
