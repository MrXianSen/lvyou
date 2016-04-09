package com.qipilang.lvyouplatform.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.BBSAdapter.ViewHolder;
import com.qipilang.lvyouplatform.bean.SCIList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpotCurrentinfroAdapter extends BaseAdapter {
	
	private List<SCIList> list;
	private LayoutInflater inflater;
	
	public SpotCurrentinfroAdapter(Context context, List<SCIList> list) {
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	
	}
	
	public void onDataChange(List<SCIList> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		SCIList information = list.get(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.sci_item, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.sci_title);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.sending_date);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.sci_content);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(information.getTitle());
		viewHolder.time.setText(information.getTime());
		viewHolder.text.setText(information.getText());

		return convertView;
	}
	
	
	
	class ViewHolder {
		TextView title;
		TextView time;
		TextView text;
	}

}
