package com.qipilang.lvyouplatform.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.qipilang.lvyouplatform.bean.Message;
import com.qipilang.lvyouplatform.common.Constants;
import com.qipilang.lvyouplatform.thread.NotificationThread;
import com.qipilang.lvyouplatform.thread.ReceiveThread;
import com.qipilang.lvyouplatform.util.TranslateUtil;

/**************************************************************************
 * 
 * DESCRIPTION: �������ӹ��ܵĺ�̨����
 * 
 * @author �Ž���
 * 
 * @since 2016.3.3
 * 
 * @version 1.0
 * 
 *************************************************************************/
public class ConnectionService extends Service {

	private CommunicationThread communicationThread;

	@Override
	public void onCreate() {
		super.onCreate();
		communicationThread = new CommunicationThread();
		communicationThread.start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	class CommunicationThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;

		public CommunicationThread() {
			try {
				// ��ǰ�û�����6789�˿�
				serverSocket = new ServerSocket(Constants.ACCEPT_MSG_PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					// ��ȡ�������ӵ��û���Socket
					Log.v("TAG", "Accept user.......");
					socket = serverSocket.accept();
					ReceiveThread receiverThread = new ReceiveThread(socket);
					receiverThread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
