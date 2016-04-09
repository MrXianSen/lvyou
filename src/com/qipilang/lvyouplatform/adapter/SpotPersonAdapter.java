package com.qipilang.lvyouplatform.adapter;

import java.util.List;

import com.albery.circledemo.bean.User;
import com.qipilang.lvyouplatform.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpotPersonAdapter extends BaseAdapter {

	private List<User> list;
	private Context context;
	private LayoutInflater inflater;
	
	public SpotPersonAdapter(Context context, List<User> list){
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	public void onDataChange(List<User> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(list != null && list.size() > 0){
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
		User user = list.get(position);
		
		ViewHolder holder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_person, null);
			holder = new ViewHolder();
			holder.textName = (TextView)convertView.findViewById(R.id.user_name);
			holder.imgPlus = (ImageView)convertView.findViewById(R.id.plus);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.textName.setText(user.getName());
		return convertView;
	}
	
	class ViewHolder{
		public TextView textName;
		public ImageView imgPlus;
	}
}
