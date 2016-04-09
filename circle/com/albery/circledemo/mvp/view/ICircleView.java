package com.albery.circledemo.mvp.view;

import com.albery.circledemo.bean.CommentConfig;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.FavortItem;
/**
 * 
* @ClassName: ICircleViewUpdateListener 
* @Description: view,��Ӧ���������������
* @author yiw
* @date 2015-12-28 下午4:13:04 
*
 */
public interface ICircleView {

	public void update2DeleteCircle(String circleId);
	public void update2AddFavorite(int circlePosition, FavortItem addItem);
	public void update2DeleteFavort(int circlePosition, String userID, String circleID);
	public void update2AddComment(int circlePosition, CommentItem addItem);
	public void update2DeleteComment(int circlePosition, String commentId);

	public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);

}
