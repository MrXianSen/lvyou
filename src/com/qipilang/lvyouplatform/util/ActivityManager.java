package com.qipilang.lvyouplatform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;

public class ActivityManager {
	private Map<String, Activity> activityMap = new HashMap<String, Activity>();
	private static ActivityManager Instance;
	
	private ActivityManager(){}
	
	public static ActivityManager getInstance(){
		if(null == Instance){
			Instance = new ActivityManager();
		}
		return Instance;
	}
	
	public void addActivity(String activityName, Activity activity){
		if(activityMap.containsKey(activityName)){
			Activity cur = activityMap.remove(activityName);
			cur.finish();
		}
		activityMap.put(activityName, activity);
	}
	
	public void deleteActivity(String activityName){
		Activity activity = activityMap.remove(activityName);
		activity.finish();
	}
	
	public Activity getActivity(String userName){
		return activityMap.get(userName);
	}
	
	public void clear(){
		Set<String> names = activityMap.keySet();
		for(Iterator<String> it = names.iterator();it.hasNext();){
			String name = it.next();
			activityMap.get(name).finish();
		}
	}
}
