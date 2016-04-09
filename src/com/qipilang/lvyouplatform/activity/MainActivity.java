package com.qipilang.lvyouplatform.activity;

//import java.lang.reflect.Field;

import java.util.ArrayList;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.adapter.DrawerItemAdapter;
import com.qipilang.lvyouplatform.bean.DrawerItem;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.fragment.BBSFragment;
import com.qipilang.lvyouplatform.fragment.FoundFragment;
import com.qipilang.lvyouplatform.fragment.MessageListFragment;
import com.qipilang.lvyouplatform.fragment.SpotFragment;
import com.qipilang.lvyouplatform.service.ConnectionService;
import com.qipilang.lvyouplatform.service.ServiceManager;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
//import android.view.ViewConfiguration;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**************************************************************************
 * 
 * DESCRIPTION: 程序主界面，使用viewpager与第三方包pagerslidingtrip 使用fragment实现四个按钮的滑动与点击
 * 之前版本全部注释掉重做
 * 
 * @author zzq
 * 
 * @since 2016.3.9
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class MainActivity extends FragmentActivity implements OnItemClickListener{

	private FoundFragment foundFragment;
	private MessageListFragment messageFragment;
	private SpotFragment spotFragment;
	private BBSFragment bbsFragment;

	// 右滑菜单
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ArrayList<DrawerItem> menuList;
	private ArrayAdapter<DrawerItem> drawerAdapter;
	

	// 页面滑动参数
	ViewPager viewPager;
	private PagerSlidingTabStrip tabs;
	private DisplayMetrics dm;
	
	private SharedPreferencesUtil logUser;
	
	private AlertDialog.Builder alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		ActivityManager.getInstance().addActivity(Constants.MAIN_ACTIVITY, this);
		
		logUser = new SharedPreferencesUtil(this);
		
		//启动服务
//		ServiceManager serviceManageer = new ServiceManager(this, Constants.CONNECTION_SERVICE);
//		serviceManageer.startService();
		
		dm = getResources().getDisplayMetrics();
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(viewPager);
		setTabValues();
		initMenu();

	}

	private void initMenu() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		menuList = new ArrayList<DrawerItem>();
		
		DrawerItem nullItem = new DrawerItem("", 0);
		
		DrawerItem myInfro = new DrawerItem("我的信息", R.drawable.icon_profile);
		DrawerItem myFriend = new DrawerItem("我的好友", R.drawable.icon_group);
		DrawerItem myCircle = new DrawerItem("我的足迹", R.drawable.icon_cloud_alt);
		DrawerItem logout = new DrawerItem("注销登录", R.drawable.icon_close);
		DrawerItem modifyPassword = new DrawerItem("修改密码", R.drawable.icon_pencil_edit);
		menuList.add(nullItem);
		menuList.add(nullItem);
		menuList.add(nullItem);
		menuList.add(myInfro);
		menuList.add(myFriend);
		menuList.add(myCircle);
		menuList.add(modifyPassword);
		menuList.add(logout);
		
		
		drawerAdapter = new DrawerItemAdapter(this, R.layout.item_drawer, menuList);
		drawerList.setAdapter(drawerAdapter);
		drawerList.setOnItemClickListener(this);
	}

	private void setTabValues() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#99cc66"));
		// 设置选中Tab文字的颜色 (自定义的方法)
		tabs.setSelectedTextColor(Color.parseColor("#99cc66"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);

	}

	public class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "发现", "消息", "景点", "论坛" };

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
				if (foundFragment == null) {
					foundFragment = new FoundFragment();
				}
				return foundFragment;
			case 1:
				if (messageFragment == null) {
					messageFragment = new MessageListFragment();
				}
				return messageFragment;
			case 2:
				if (spotFragment == null) {
					spotFragment = new SpotFragment();
				}
				return spotFragment;
			case 3:
				if (bbsFragment == null) {
					bbsFragment = new BBSFragment();
				}
				return bbsFragment;
			default:
				return null;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		switch (position) {
		case 3:
			Intent intent = new Intent(MainActivity.this, PersonalInfoActivity.class);
			startActivity(intent);
			break;
		case 4:
			myFriendList();
			break;
		case 5:
			Intent myCircleIntent = new Intent(MainActivity.this, CircleActivity.class);
			myCircleIntent.putExtra("circleType", Constants.MY_CIRCLE);
			myCircleIntent.putExtra("userID", logUser.getString("userID", "0"));
			startActivity(myCircleIntent);
			break;
		case 6:
			upadtePassword();
			break;
		case 7:
			logout();
		default:
			break;
		}
		
	}

	private void myFriendList(){
		Intent intent = new Intent(MainActivity.this, FriendListActivity.class);
		startActivity(intent);
	}
	
	private void logout(){
		alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("提示");
		alertDialog.setMessage("您真的要退出吗？(ಥ _ ಥ)");
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Activity activity = ActivityManager.getInstance().getActivity(Constants.BEGIN_ACTIVITY);
				Intent intent = new Intent(MainActivity.this, activity.getClass());
				startActivity(intent);
				logUser.logUserSharedPreferences.edit().clear().commit();
				ActivityManager.getInstance().clear();
			}
		});
		alertDialog.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		alertDialog.show();
	}
	private void upadtePassword(){
		Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
		intent.putExtra("isLogin", Constants.IS_LOGIN);
		startActivity(intent);
	}
}
