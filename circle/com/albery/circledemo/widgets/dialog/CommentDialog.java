package com.albery.circledemo.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.mvp.presenter.CirclePresenter;
import com.albery.circledemo.utils.DatasUtil;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;
/**
 * 
* @ClassName: CommentDialog 
* @Description: 评论长按对话框，保护复制和删�? 
* @author yiw
* @date 2015-12-28 下午3:36:39 
*
 */
@SuppressWarnings("deprecation")
public class CommentDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context mContext;
	private CirclePresenter mPresenter;
	private CommentItem mCommentItem;
	private int mCirclePosition;
	
	private SharedPreferencesUtil logUser;

	public CommentDialog(Context context, CirclePresenter presenter,
			CommentItem commentItem, int circlePosition) {
		super(context, R.style.comment_dialog);
		mContext = context;
		this.mPresenter = presenter;
		this.mCommentItem = commentItem;
		this.mCirclePosition = circlePosition;
		
		logUser = new SharedPreferencesUtil(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);
		initWindowParams();
		initView();
	}

	private void initWindowParams() {
		Window dialogWindow = getWindow();
		// 获取屏幕宽�?�高�?
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65

		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setAttributes(lp);
	}

	private void initView() {
		TextView copyTv = (TextView) findViewById(R.id.copyTv);
		copyTv.setOnClickListener(this);
		TextView deleteTv = (TextView) findViewById(R.id.deleteTv);
		//TODO
		if (mCommentItem != null
				&& logUser.getString("userID", "0").equals(
						mCommentItem.getUser().getId())) {
			deleteTv.setVisibility(View.VISIBLE);
		} else {
			deleteTv.setVisibility(View.GONE);
		}
		deleteTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.copyTv:
			if (mCommentItem != null) {
				ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setText(mCommentItem.getContent());
			}
			dismiss();
			break;
		case R.id.deleteTv:
			if (mPresenter != null && mCommentItem != null) {
				mPresenter.deleteComment(mCirclePosition, mCommentItem.getId());
			}
			dismiss();
			break;
		default:
			break;
		}
	}
	
	//TODO 删除自己的评论线�?
	Runnable deleteCommentRunnable = new Runnable() {
		@Override
		public void run() {
			
		}
	};
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
}
