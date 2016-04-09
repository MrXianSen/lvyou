package com.qipilang.lvyouplatform.service;


import android.content.Context;
import android.content.Intent;

/**************************************************************************
 * 
 * DESCRIPTION:	服务管理类
 * 
 * @author 		张建国
 *
 * @since 		2016.3.3
 * 
 * @version 	1.0
 *
 *************************************************************************/
public class ServiceManager{
	
	private Context context;
	private String serviceName;
	
	public ServiceManager(Context context, String serviceName){
		this.context = context;
		this.serviceName = serviceName;
	}
	
	public void startService(){
		Thread serviceThread = new Thread(new Runnable(){
			@Override
			public void run() {
				Intent intent = getIntent(serviceName);
				context.startService(intent);
			}});
		serviceThread.start();
	}
	
	public void stopService(){
		Intent intent = getIntent(serviceName);
		context.stopService(intent);
	}
	
	public Intent getIntent(String serviceName){
		return new Intent(serviceName);
	}
}
