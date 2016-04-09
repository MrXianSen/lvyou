package com.qipilang.lvyouplatform.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GlobalHandler extends Handler{
	
	@Override
	public void handleMessage(Message msg) {
		Bundle data = msg.getData();
	}
}
