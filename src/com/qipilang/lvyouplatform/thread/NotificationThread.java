package com.qipilang.lvyouplatform.thread;

import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.activity.BeginActivity;
import com.qipilang.lvyouplatform.activity.MainActivity;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.util.CastUtil;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.webkit.WebView.FindListener;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;


public class NotificationThread extends Thread{

	private boolean isTopApplication;			//当前程序是否运行在前台
	private Context context;
	
	private NotificationCompat.Builder mBuilder;
	private SharedPreferences receiveMsg;
	
	public NotificationThread(Context context, boolean isTopApplicatioin){
		this.context = context;
		this.isTopApplication = isTopApplicatioin;
	}
	
	@Override
	public void run() {
		handleNotify();
	}
	
	public void handleNotify(){
		if(!isTopApplication){
			sendNotify();
		}else{
			addViewToMessageView();
		}
	}
	
	public void sendNotify(){
		mBuilder = new Builder(context);
		//TODO add app icon
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setTicker(Constants.NOTIFICATION_TRICKER);
		mBuilder.setAutoCancel(true);
		//为通知服务添加相应的数据
		RemoteViews itemView = new RemoteViews(context.getPackageName(), R.layout.item_notification);
		//设置显示数据
		itemView.setTextViewText(R.id.item_notification_userName, "张建国");
		itemView.setTextViewText(R.id.item_notification_content, "你好");
		mBuilder.setContent(itemView);
		
		//TODO add result activity
		Intent resultIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		NotificationManager notificationManager = (NotificationManager)context.
				getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, mBuilder.build());
	}
	
	public void addViewToMessageView(){
		
	}
}
