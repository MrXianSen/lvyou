package com.qipilang.lvyouplatform.activity;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
//import com.qipilang.lvyouplatform.activity.MainActivity.MyPagerAdapter;
//import com.qipilang.lvyouplatform.fragment.BBSFragment;
//import com.qipilang.lvyouplatform.fragment.FoundFragment;
//import com.qipilang.lvyouplatform.fragment.MessageFragment;
import com.qipilang.lvyouplatform.fragment.SpotCircleFragment;
import com.qipilang.lvyouplatform.fragment.SCIFragment;
//import com.qipilang.lvyouplatform.fragment.SpotFragment;
import com.qipilang.lvyouplatform.fragment.SpotInfroFragment;
import com.qipilang.lvyouplatform.util.ActivityManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class SpotActivity extends FragmentActivity{
	private SpotInfroFragment spotInfroFragment;
	private SCIFragment spotCurrentinfroFragment;
	private SpotCircleFragment spotCircleFragment;
	
	public String sceneID;
	
	ViewPager viewPager;
	private PagerSlidingTabStrip tabs;
	private DisplayMetrics dm;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityManager.getInstance().addActivity(Constants.SPOT_ACTIVITY, this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_spot_main);
		dm = getResources().getDisplayMetrics();
		viewPager = (ViewPager) findViewById(R.id.spot_pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.spot_tabs);
		MyPagerAdapter mp = new MyPagerAdapter(getSupportFragmentManager());

		Log.v("mytag", viewPager.toString());
		viewPager.setAdapter(mp);
		Log.v("mytag", mp.toString());
		tabs.setViewPager(viewPager);
		setTabValues();
	}

	private void setTabValues() {
		// ����Tab���Զ��������Ļ��
		tabs.setShouldExpand(true);
		// ����Tab�ķָ�����͸����
		tabs.setDividerColor(Color.TRANSPARENT);
		// ����Tab�ײ��ߵĸ߶�
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// ����Tab Indicator�ĸ߶�
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// ����Tab�������ֵĴ�С
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// ����Tab Indicator����ɫ
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// ����ѡ��Tab���ֵ���ɫ (�Զ���ķ���)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// ȡ�����Tabʱ�ı���ɫ
		tabs.setTabBackground(0);
		
	}
	
	public class MyPagerAdapter extends FragmentPagerAdapter{
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "������Ϣ", "ʵʱ��Ϣ", "ȥ������"};

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (spotInfroFragment== null) {
					spotInfroFragment= new SpotInfroFragment();
				}
				return spotInfroFragment;
			case 1:
				if (spotCurrentinfroFragment == null) {
					spotCurrentinfroFragment= new SCIFragment();
				}
				return spotCurrentinfroFragment;
			case 2:
				if (spotCircleFragment == null) {
					spotCircleFragment = new SpotCircleFragment();
				}
				return spotCircleFragment ;
			default:
				return null;
			}
		}
	}
}
