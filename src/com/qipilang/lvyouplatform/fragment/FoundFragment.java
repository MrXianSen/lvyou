package com.qipilang.lvyouplatform.fragment;

import com.albery.circledemo.widgets.CircularImage;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.CircleActivity;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Toast;

public class FoundFragment extends Fragment implements
		android.view.View.OnClickListener {
	private SharedPreferencesUtil logUser;
	private CircularImage img;
	View view;
	
	RotateAnimation animation;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_found, container, false);
		
		logUser = new SharedPreferencesUtil(view.getContext());
		initView(view);

		return view;
	}
	private void initView(View view){
		img = (CircularImage)view.findViewById(R.id.circle_img);
		img.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.circle_img:
			animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(3000);
			img.setAnimation(animation);
			animation.startNow();
			Intent intent = new Intent(this.view.getContext(), CircleActivity.class);
			intent.putExtra("circleType", Constants.ALL_CIRCLE);
			intent.putExtra("userID", logUser.getString("userID", "0"));
			view.getContext().startActivity(intent);
			break;
		}
	}

}
