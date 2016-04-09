package com.qipilang.lvyouplatform.adapter;

import java.util.List;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.DrawerItem;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerItemAdapter extends ArrayAdapter<DrawerItem>{
	private int resourceId;

	public DrawerItemAdapter(Context context, int textViewResourceId,
			List<DrawerItem> objects) {
			super(context,textViewResourceId,objects);
			resourceId = textViewResourceId; 
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = new View(getContext());
		
		DrawerItem drawerItem = getItem(position);
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		}else {
			view = convertView;
		}
		ImageView itemImageView = (ImageView) view.findViewById(R.id.drawer_image);
		TextView itemName= (TextView) view.findViewById(R.id.drawer_item);
		itemImageView.setImageResource(drawerItem.getImageId());
		itemName.setText(drawerItem.getItem());
		
		return view;
	}
}
