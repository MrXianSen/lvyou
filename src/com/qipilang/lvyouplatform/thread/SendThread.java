package com.qipilang.lvyouplatform.thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.qipilang.lvyouplatform.common.Constants;

public class SendThread extends Thread{
	private Socket sendSocket;
	private String sendMsg;

	public SendThread(String receiverIP, String sendMsg) {
		try {
			this.sendSocket = new Socket(receiverIP,
					Constants.ACCEPT_MSG_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sendMsg = sendMsg;
	}

	@Override
	public void run() {
		try {
			PrintWriter writer = new PrintWriter(
					sendSocket.getOutputStream());
			writer.write(sendMsg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
