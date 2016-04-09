package com.qipilang.lvyouplatform.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

import com.qipilang.lvyouplatform.application.MyApplication;
import com.qipilang.lvyouplatform.bean.Message;
import com.qipilang.lvyouplatform.util.TranslateUtil;

public class ReceiveThread extends Thread {

	private Socket receiverSocket;

	public ReceiveThread(Socket socket) {
		this.receiverSocket = socket;
	}

	@SuppressLint("NewApi")
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(receiverSocket.getInputStream()));
			String line = reader.readLine();
			if (!line.isEmpty() && line != "") {
				// TODO ������ȡ�����ݣ���ͻ����ֻ�������ʾ
				NotificationThread notification = new NotificationThread(MyApplication.mContext, isApplicationForeground());
				notification.start();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * DESCRIPTION: �жϴ˳����Ƿ���ǰ̨����
	 * 
	 * @return true��ǰ̨���� false����̨����
	 **************************************************************************/
	public boolean isApplicationForeground() {
		Context context = MyApplication.mContext;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);

		if (tasks != null && !tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(
					context.getPackageName())) {
				return true;
			}
		}
		return false;
	}
}
