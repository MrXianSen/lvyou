package com.qipilang.lvyouplatform.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qipilang.lvyouplatform.adapter.SortAdapter;
import com.qipilang.lvyouplatform.bean.Friend;
import com.qipilang.lvyouplatform.bean.SortModel;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.FriendManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.R;
import com.qipilang.sortlist.CharacterParser;
import com.qipilang.sortlist.ClearEditText;
import com.qipilang.sortlist.PinyinComparator;
import com.qipilang.sortlist.SideBar;
import com.qipilang.sortlist.SideBar.OnTouchingLetterChangedListener;

public class FriendListActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList = new ArrayList<SortModel>();
	private List<Friend> list;

	private SharedPreferencesUtil logUser;
	private ProgressDialog proDialog;
	
	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityManager.getInstance().addActivity(
				Constants.FRIEND_LIST_ACTIVITY, this);

		setContentView(R.layout.activity_friendlist);

		logUser = new SharedPreferencesUtil(this);
		initViews();
		initData();
	}

	private void initViews() {
		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);

		sideBar.setTextView(dialog);

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				SortModel sortModel = (SortModel) adapter.getItem(position);
				Intent intent = new Intent(FriendListActivity.this, FriendInfoActivity.class);
				intent.putExtra("friendID", sortModel.getId());
				intent.putExtra("friendName", sortModel.getName());
				startActivity(intent);
			}
		});


		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void initData() {
		proDialog = ProgressDialog.show(FriendListActivity.this, "", "");
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				list = FriendManagement.Instance.getFriendList(logUser
						.getString("userID", "0"));
				int code = (list != null && list.size() > 0) ? Constants.GET_FRIEND_LIST_SUCCESS
						: Constants.GET_FRIEND_LIST_FAILED;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			Bundle data = msg.getData();

			int code = data.getInt("code");
			
			proDialog.dismiss();
			switch (code) {
			case Constants.GET_FRIEND_LIST_SUCCESS:
				SourceDateList = filledData(list);
				// ����a-z��������Դ����
				Collections.sort(SourceDateList, pinyinComparator);
				adapter.updateListView(SourceDateList);
				break;
			case Constants.GET_FRIEND_LIST_FAILED:
				Toast.makeText(FriendListActivity.this, "����û�к��ѣ��Ͻ�ȥ������...",
						Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	private List<SortModel> filledData(List<Friend> list) {
		List<SortModel> sortModelList = new ArrayList<SortModel>();
		int length = list.size();
		for (int i = 0; i < length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(list.get(i).getDestName());
			String pingyin = characterParser.getSelling(list.get(i)
					.getDestName());
			String sortString = pingyin.substring(0, 1).toUpperCase();
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			sortModel.setId(CastUtil.castString(list.get(i).getDestID()));
			sortModelList.add(sortModel);
		}
		return sortModelList;
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}