package com.qipilang.lvyouplatform.activity;

import java.util.List;

import com.albery.circledemo.adapter.CircleAdapter;
import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.CommentConfig;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.FavortItem;
import com.albery.circledemo.mvp.presenter.CirclePresenter;
import com.albery.circledemo.mvp.view.ICircleView;
import com.albery.circledemo.utils.CommonUtils;
import com.albery.circledemo.widgets.CommentListView;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.PageBean;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.CircleManagement;
import com.qipilang.lvyouplatform.util.ActivityManager;
import com.qipilang.lvyouplatform.util.CastUtil;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
import com.qipilang.lvyouplatform.util.StringUtil;
import com.qipilang.lvyouplatform.view.MyListView;
import com.qipilang.lvyouplatform.view.MyListView.ILoadListener;
import com.qipilang.lvyouplatform.view.MyListView.IRefalshListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CircleActivity extends Activity implements ICircleView,
		ILoadListener, IRefalshListener {

	private CirclePresenter mPresenter;
	private CommentConfig mCommentConfig;
	private MyListView mCircleLv;
	private CircleAdapter mAdapter;
	// �༭��
	private LinearLayout mEditTextBody;
	private EditText mEditText;
	private ImageView sendIv;

	private TextView back;
	private TextView release;
	private TextView noLimit;
	
	private RelativeLayout content;

	private int mScreenHeight;
	private int mEditTextBodyHeight;
	private int mCurrentKeyboardH;
	private int mSelectCircleItemH;
	private int mSelectCommentItemOffset;
	

	private List<CircleItem> datas;

	private boolean isLoaded;
	
	private int type;

	private SharedPreferencesUtil logUser;
	String userId;
	String limit = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_circle);
		// Add this activity to activity manager
		ActivityManager.getInstance().addActivity(Constants.CIRCLE_ACTIVITY,
				this);
		
		this.type = getIntent().getExtras().getInt("circleType");
		this.userId = getIntent().getExtras().getString("userID");
		if(getIntent().getExtras().containsKey("limit"))
			limit = getIntent().getExtras().getString("limit");
		
		logUser = new SharedPreferencesUtil(this);
		mPresenter = new CirclePresenter(this, logUser, this);
		
		back = (TextView) findViewById(R.id.circle_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().deleteActivity(
						Constants.CIRCLE_ACTIVITY);
				return;
			}
		});
		if("0".equals(limit)){
			content = (RelativeLayout)findViewById(R.id.content);
			content.setVisibility(View.GONE);
			release = (TextView)findViewById(R.id.circle_release);
			release.setVisibility(View.GONE);
			
			noLimit = (TextView)findViewById(R.id.no_limit);
			noLimit.setVisibility(View.VISIBLE);
		}
		else{
			initView();
			loadData();
		}
	}

	@SuppressLint("NewApi") private void initView() {

		
		release = (TextView) findViewById(R.id.circle_release);

		// ��ת�������㼣ҳ��
		release.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CircleActivity.this,
						PublishedActivity.class);
				intent.putExtra("circleType", type);
				startActivity(intent);
			}
		});

		mCircleLv = (MyListView) findViewById(R.id.circleLv);
		// ��������ܿ�������ʾ�������ListView֮�����������
		mCircleLv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				if (mEditTextBody.getVisibility() == View.VISIBLE) {
					updateEditTextBodyVisible(View.GONE, null);
					return true;
				}
				return false;
			}
		});
		// ����ListView��Adapter
		mAdapter = new CircleAdapter(this);
		mAdapter.setCirclePresenter(mPresenter);
		mCircleLv.setAdapter(mAdapter);
		mCircleLv.setInterface(this, this);
		// �����
		mEditTextBody = (LinearLayout) findViewById(R.id.editTextBodyLl);
		mEditText = (EditText) findViewById(R.id.circleEt);
		sendIv = (ImageView) findViewById(R.id.sendIv);

		// ������Ͱ�ť
		sendIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPresenter != null) {
					String content = mEditText.getText().toString();
					if (StringUtil.isEmpty(content)) {
						Toast.makeText(CircleActivity.this, "�������ݲ���Ϊ��...",
								Toast.LENGTH_SHORT).show();
						return;
					}
					// �������
					mPresenter.addComment(content, mCommentConfig);
				}
				// ����ϵͳ����
				updateEditTextBodyVisible(View.GONE, null);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("code");
			switch (code) {
			case Constants.REFRESH_DATA_SUCCESS:
				mAdapter.onDataChange(datas);
				mCircleLv.reflashComplete();
				break;
			case Constants.REFRESH_DATA_FAILED:
				Toast.makeText(CircleActivity.this, "ˢ������ʧ�ܡɨr(����)�q�ɨr ",
						Toast.LENGTH_SHORT).show();
				break;
			case Constants.LOAD_DATA_FAILED:
				Toast.makeText(CircleActivity.this, "û�и���������@_@;)",
						Toast.LENGTH_SHORT).show();
				mCircleLv.loadComplete();
				break;
			case Constants.LOAD_DATA_SUCCESS:
				mCircleLv.loadComplete();
				mAdapter.onDataChange(datas);
				break;
			default:
				mCircleLv.loadComplete();
				break;
			}
		};
	};

	/**
	 * ����������ʱ��������
	 * 
	 * @param				userID�û���ID
	 */
	private void loadData() {
		PageBean.currentPage = 0;
		isLoaded = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				datas = CircleManagement.Instance.getCircleItemList(
						CastUtil.castInt(userId), PageBean.currentPage, type);
				if (datas == null || datas.size() == 0)
					data.putInt("code", Constants.REFRESH_DATA_FAILED);
				else
					data.putInt("code", Constants.REFRESH_DATA_SUCCESS);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
//		������
//		datas = DatasUtil.createCircleDatas();
//		mAdapter.setDatas(datas);
//		mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (mEditTextBody != null
					&& mEditTextBody.getVisibility() == View.VISIBLE) {
				mEditTextBody.setVisibility(View.GONE);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void update2DeleteCircle(String circleId) {
		List<CircleItem> circleItems = mAdapter.getDatas();
		for (int i = 0; i < circleItems.size(); i++) {
			if (circleId.equals(circleItems.get(i).getId())) {
				circleItems.remove(i);
				mAdapter.notifyDataSetChanged();
				return;
			}
		}
	}

	@Override
	public void update2AddFavorite(int circlePosition, FavortItem addItem) {
		loadData();
	}

	@Override
	public void update2DeleteFavort(int circlePosition, String userID, String circleID) {
		List<FavortItem> items = mAdapter.getDatas().get(circlePosition)
				.getFavorters();
		int length = items.size();
		for (int i = 0; i< length; i++) {
			if (userID.equals(items.get(i).getUser().getId()) && circleID.equals(items.get(i).getCircleID())) {
				items.remove(i);
				mAdapter.notifyDataSetChanged();
				return;
			}
		}
	}

	@Override
	public void update2AddComment(int circlePosition, CommentItem addItem) {
		if (addItem != null) {
			mAdapter.getDatas().get(circlePosition).getComments().add(addItem);
			mAdapter.notifyDataSetChanged();
		}
		// ��������ı�
		mEditText.setText("");
	}

	@Override
	public void update2DeleteComment(int circlePosition, String commentId) {
		loadData();
//		List<CommentItem> items = mAdapter.getDatas().get(circlePosition)
//				.getComments();
//		for (int i = 0; i < items.size(); i++) {
//			if (commentId.equals(items.get(i).getId())) {
//				items.remove(i);
//				mAdapter.notifyDataSetChanged();
//				return;
//			}
//		}
	}

	@Override
	public void updateEditTextBodyVisible(int visibility,
			CommentConfig commentConfig) {
		mCommentConfig = commentConfig;
		mEditTextBody.setVisibility(visibility);

		measureCircleItemHighAndCommentItemOffset(commentConfig);

		if (View.VISIBLE == visibility) {
			mEditText.requestFocus();
			// ��������
			CommonUtils.showSoftInput(mEditText.getContext(), mEditText);

		} else if (View.GONE == visibility) {
			// ���ؼ���
			CommonUtils.hideSoftInput(mEditText.getContext(), mEditText);
		}
	}

	private void measureCircleItemHighAndCommentItemOffset(
			CommentConfig commentConfig) {
		if (commentConfig == null)
			return;

		int firstPosition = mCircleLv.getFirstVisiblePosition();
		// ֻ�ܷ��ص�ǰ�ɼ������б�ɹ�����������
		View selectCircleItem = mCircleLv
				.getChildAt(commentConfig.circlePosition - firstPosition);
		if (selectCircleItem != null) {
			mSelectCircleItemH = selectCircleItem.getHeight();
		}

		if (commentConfig.commentType == CommentConfig.Type.REPLY) {
			// �ظ����۵����
			CommentListView commentLv = (CommentListView) selectCircleItem
					.findViewById(R.id.commentList);
			if (commentLv != null) {
				// �ҵ�Ҫ�ظ�������view,�������view����������̬�ײ��ľ���
				View selectCommentItem = commentLv
						.getChildAt(commentConfig.commentPosition);
				if (selectCommentItem != null) {
					// ѡ���commentItem��ѡ���CircleItem�ײ��ľ���
					mSelectCommentItemOffset = 0;
					View parentView = selectCommentItem;
					do {
						int subItemBottom = parentView.getBottom();
						parentView = (View) parentView.getParent();
						if (parentView != null) {
							mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
						}
					} while (parentView != null
							&& parentView != selectCircleItem);
				}
			}
		}
	}

	/**
	 * ����ˢ������
	 */
	@Override
	public void onReflash() {
		loadData();
	}

	private void updateDatas(List<CircleItem> list) {

		if (list == null || list.size() == 0)
			return;

		int length = list.size();

		for (int i = 0; i < length; i++) {
			datas.add(list.get(i));
		}
	}

	/**
	 * �������ظ��������
	 */
	@Override
	public void onLoad() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();
				int code = loadMoreData();
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}
	private int loadMoreData(){
		if (PageBean.currentPage < PageBean.totalPage) {
			List<CircleItem> temp = CircleManagement.Instance
					.getCircleItemList(CastUtil.castInt(userId),
							++PageBean.currentPage, type);
			if(temp != null && temp.size() != 0){
				isLoaded = true;
				updateDatas(temp);
				return Constants.LOAD_DATA_SUCCESS;
			}
		}
		return Constants.LOAD_DATA_FAILED;
	}
}
