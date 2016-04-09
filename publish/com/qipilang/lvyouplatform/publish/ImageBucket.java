package com.qipilang.lvyouplatform.publish;


import java.util.List;


/****************************************************
 * 
 * DESCRIPTION:				一个相册中的内容
 * 
 * @author 					albery
 *
 ***************************************************/
public class ImageBucket {
	public int count = 0;
	public String bucketName;			//相册名
	public List<ImageItem> imageList;	//相册中的照片对象
}
