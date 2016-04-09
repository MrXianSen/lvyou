package com.qipilang.lvyouplatform.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.albery.circledemo.bean.User;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qipilang.lvyouplatform.R;
import com.qipilang.lvyouplatform.bean.MessageList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyApplication extends Application {
	//默认存放图片额路径，格式如下：qp/Images/
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + 
			File.separator + "qp" + File.separator + "Images" + File.separator;

	public static Context mContext;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		initImageLoader();
	}
	public static Context getContext(){
		return mContext;
	}
	
	/********************************************************************************
	 * DESCRIPTION:					初始化ImageLoder
	 *******************************************************************************/
	public void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions
				.Builder()
				.showImageForEmptyUri(R.color.bg_no_photo)//没有正确图片时显示的图片
				.showImageOnFail(R.color.bg_no_photo)//图片加载失败是显示的图片
				.showImageOnLoading(R.color.bg_no_photo)//图片加载过程中显示的图片
				.cacheInMemory(true)//缓存在内存中
				.cacheOnDisk(true)//缓存在硬盘中
				.build();

		File cacheDir = new File(DEFAULT_SAVE_IMAGE_PATH);
		ImageLoaderConfiguration imageconfig = new ImageLoaderConfiguration.Builder(this)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(200)
				.diskCache(new UnlimitedDiskCache(cacheDir))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(options)
				.build();
		ImageLoader.getInstance().init(imageconfig);
	}

	public void initService(){
	}
}