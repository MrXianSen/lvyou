package com.qipilang.lvyouplatform.publish;


import java.io.Serializable;

/********************************************************
 * 
 * DESCRIPTION:				一个图片对象
 * 
 * @author 					albery
 *
 *******************************************************/
public class ImageItem implements Serializable {
	private static final long serialVersionUID = 1L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public boolean isSelected = false;
}
