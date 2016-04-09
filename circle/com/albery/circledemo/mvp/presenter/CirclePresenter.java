package com.albery.circledemo.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.CommentConfig;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.FavortItem;
import com.albery.circledemo.bean.User;
import com.albery.circledemo.mvp.modle.CircleModel;
import com.albery.circledemo.mvp.modle.IDataRequestListener;
import com.albery.circledemo.mvp.view.ICircleView;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.net.CircleManagement;
import com.qipilang.lvyouplatform.util.SharedPreferencesUtil;

@SuppressLint("HandlerLeak") public class CirclePresenter {
	private CircleModel mCircleModel;
	private ICircleView mCircleView;
	private Context context;

	// 帖子的附加内�?
	private CircleItem circleItem;
	private CommentItem newCommentItem = null;
	private User user;
	private CommentConfig config;

	private SharedPreferencesUtil logUser;

	public CirclePresenter(ICircleView view, SharedPreferencesUtil logUser,
			Context context) {
		this.mCircleView = view;
		this.context = context;
		mCircleModel = new CircleModel();
		this.logUser = logUser;
	}

	public void setCircleItem(CircleItem circleItem) {
		this.circleItem = circleItem;
	}

	public CircleItem getCircleItem() {
		return this.circleItem;
	}

	/**
	 * 
	 * @Title: deleteCircle
	 * @Description: 删除动�??
	 * @param circleId
	 * @return void
	 * @throws
	 */
	public void deleteCircle(final String circleId) {
		mCircleModel.deleteCircle(new IDataRequestListener() {
			@Override
			public void loadSuccess(Object object) {
				mCircleView.update2DeleteCircle(circleId);
			}
		});
	}

	/**
	 * 
	 * @Title: addFavort
	 * @Description: 点赞
	 * @param circlePosition
	 * @return void
	 * @throws
	 */
	public void addFavort(int circlePosition) {
		this.circlePosition = circlePosition;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				int code = CircleManagement.Instance.addFavorite(
						CircleItem.selectItemId,
						logUser.getString("userID", "0"),
						logUser.getString("userName", "匿名"));
				code = (code == 1) ? Constants.ADD_FAVORITE_SUCCESS
						: Constants.ADD_FAVORITE_FAILED;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 
	 * @Title: deleteFavort
	 * @Description: 取消点赞
	 * @param @param circlePosition
	 * @param @param favortId
	 * @return void
	 * @throws
	 */
	public void deleteFavort(int circlePosition, final String userID,
			final String circleID) {
		this.circlePosition = circlePosition;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				int code = CircleManagement.Instance.cancleFavorite(circleID,
						userID);

				code = (code == 1) ? Constants.DELETE_FAVORITE_SUCCESS
						: Constants.DELETE_FAVORITE_FAILED;

				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 
	 * @Title: addComment
	 * @Description: 评论
	 * @param content
	 * @param config
	 *            CommentConfig
	 * @return void
	 * @throws
	 */
	public void addComment(String content, CommentConfig config) {
		if (config == null) {
			return;
		}
		this.config = config;
		newCommentItem = new CommentItem();
		user = new User(logUser.getString("userID", "0"), logUser.getString(
				"userName", "匿名"), logUser.getString("headUrl", ""));
		if (config.replyUser == null)
			config.replyUser = new User("", "", "");
		User toReplyUser = config.replyUser;
		newCommentItem.setCircleID(CircleItem.selectItemId);
		newCommentItem.setContent(content);
		newCommentItem.setToReplyUser(toReplyUser);
		newCommentItem.setUser(user);
		newCommentItem.setUserHeaderUrl(logUser.getString("headUrl", ""));
		new Thread(addCommentRunnable).start();
	}

	Runnable addCommentRunnable = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			User user = newCommentItem.getUser();
			User toReplyUser = newCommentItem.getToReplyUser();

			String content = newCommentItem.getContent();

			int code = CircleManagement.Instance.addComment(
					newCommentItem.getCircleID(), user, toReplyUser, content);
			code = (code == 1) ? Constants.ADD_COMMENT_SUCCESS
					: Constants.ADD_COMMENT_FAILED;
			data.putInt("code", code);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	/**
	 * 
	 * @Title: deleteComment
	 * @Description: 删除评论
	 * @param @param circlePosition
	 * @param @param commentId
	 * @return void
	 * @throws
	 */
	private int circlePosition;
	String commentID;

	public void deleteComment(int circlePosition, final String commentId) {
		this.circlePosition = circlePosition;
		commentID = commentId;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bundle data = new Bundle();
				Message msg = new Message();

				int code = CircleManagement.Instance.deleteComment(commentId);
				code = (code == 1) ? Constants.DELETE_COMMENT_SUCCESS
						: Constants.DELETE_COMMENT_FAILED;
				data.putInt("code", code);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig) {
		mCircleView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int code = data.getInt("code");
			switch (code) {
			case Constants.ADD_COMMENT_SUCCESS:
				// 添加到界面中
				mCircleView.update2AddComment(config.circlePosition,
						newCommentItem);
				break;
			case Constants.ADD_COMMENT_FAILED:
				Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
				break;
			case Constants.DELETE_COMMENT_SUCCESS:
				mCircleView.update2DeleteComment(circlePosition, commentID);
				break;
			case Constants.DELETE_COMMENT_FAILED:
				Toast.makeText(context, "删除评论失败(︶︿�?)o ", Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.ADD_FAVORITE_SUCCESS:
				FavortItem item = new FavortItem();
				item.setCircleID(CircleItem.selectItemId);
				item.setUser(new User(logUser.getString("userID", ""), logUser
						.getString("userName", "匿名"), logUser.getString(
						"headUrl", "")));
				mCircleView.update2AddFavorite(circlePosition, item);
				break;
			case Constants.ADD_FAVORITE_FAILED:
				Toast.makeText(context, "点赞失败(︶︿�?)o ", Toast.LENGTH_SHORT)
						.show();
				break;
			case Constants.DELETE_FAVORITE_SUCCESS:
				mCircleView.update2DeleteFavort(circlePosition,
						logUser.getString("userID", ""),
						CircleItem.selectItemId);
				break;
			case Constants.DELETE_FAVORITE_FAILED:
				Toast.makeText(context, "取消失败(︶︿�?)o ", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

}
