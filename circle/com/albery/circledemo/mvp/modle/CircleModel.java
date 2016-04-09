package com.albery.circledemo.mvp.modle;

import android.os.AsyncTask;

public class CircleModel {
	
	
	public CircleModel(){}
	
	
	//删除朋友�?
	public void deleteCircle( final IDataRequestListener listener) {
		requestServer(listener);
	}
	//点赞
	public void addFavort( final IDataRequestListener listener) {
		requestServer(listener);
	}
	//取消点赞
	public void deleteFavort(final IDataRequestListener listener) {
		requestServer(listener);
	}
	//评论
	public void addComment( final IDataRequestListener listener) {
		requestServer(listener);
	}
	//删除评论
	public void deleteComment( final IDataRequestListener listener) {
		requestServer(listener);
	}
	
	private void requestServer(final IDataRequestListener listener) {
		new AsyncTask<Object, Integer, Object>(){
			@Override
			protected Object doInBackground(Object... params) {
				return null;
			}
			
			protected void onPostExecute(Object result) {
				listener.loadSuccess(result);
			};
		}.execute();
	}
	
	class BackGroundTask extends AsyncTask<Object, Integer, Object>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Object doInBackground(Object... arg0) {
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
		}
	}
}
