package com.qipilang.lvyouplatform.activity;


import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.ActivityManager;

public class SpotSelectActivity extends WheelBaseActivity implements OnClickListener,
		OnWheelScrollListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spot_select);
		
		ActivityManager.getInstance().addActivity(Constants.SPOT_SELECT_ACTIVITY, this);
		
		setUpViews();
		setUpListener();
		setUpData();
	}

	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		
		back = (TextView)findViewById(R.id.text_back);
		back.setOnClickListener(this);
	}

	private void setUpListener() {

		// ���change�¼�
		mViewProvince.addScrollingListener(this);
		// ���change�¼�
		mViewCity.addScrollingListener(this);
		// ���change�¼�
		mViewDistrict.addScrollingListener(this);
		// ���onclick�¼�
		mBtnConfirm.setOnClickListener(this);
		
	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				SpotSelectActivity.this, mProvinceDatas));
		// ���ÿɼ���Ŀ����
		mViewProvince.setVisibleItems(5);
		mViewCity.setVisibleItems(5);
		mViewDistrict.setVisibleItems(5);

		updateCities();
		updateAreas();
	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			getSelectedResult();
			break;
		case R.id.text_back:
			ActivityManager.getInstance().deleteActivity(Constants.SPOT_SELECT_ACTIVITY);
			break;
		default:
			break;
		}
	}

	private void getSelectedResult() {
		Intent intent = new Intent(SpotSelectActivity.this, SpotActivity.class);
		intent.putExtra("type", Constants.SCENE_INFO);
		intent.putExtra("sceneInfo", mCurrentCityName + "_" + mCurrentDistrictName);
		startActivity(intent);
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onScrollingFinished(WheelView wheel) {
		WheelView iWheel;
		if (wheel == mViewProvince) {
			updateCities();
			iWheel = mViewDistrict;
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[iWheel
			        .getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewCity) {
			updateAreas();
			iWheel = mViewDistrict;
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[iWheel
			        .getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[wheel
					.getCurrentItem()];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}
}
