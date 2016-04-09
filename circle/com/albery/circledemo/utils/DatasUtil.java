package com.albery.circledemo.utils;

import com.albery.circledemo.bean.CircleItem;
import com.albery.circledemo.bean.CommentItem;
import com.albery.circledemo.bean.FavortItem;
import com.albery.circledemo.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @ClassName: DatasUtil
 * @author yiw
 * @date 2015-12-28 下午4:16:21
 * 
 */
public class DatasUtil {
	public static final String[] CONTENTS = { "", "哈哈", "今天是个好日�?", "呵呵", "图不�?",
			"我勒个去" };
	public static final String[] PHOTOS = {
			"http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134e61e0f90211f95cad1c85e36.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0fecc3e83ef586034a85edf723d.jpg",
			"http://cdn.duitang.com/uploads/item/201309/17/20130917111400_CNmTr.thumb.224_0.png",
			"http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg",
			"http://pic4.nipic.com/20091101/3672704_160309066949_2.jpg",
			"http://pic4.nipic.com/20091203/1295091_123813163959_2.jpg",
			"http://pic31.nipic.com/20130624/8821914_104949466000_2.jpg",
			"http://pic6.nipic.com/20100330/4592428_113348099353_2.jpg",
			"http://pic9.nipic.com/20100917/5653289_174356436608_2.jpg",
			"http://img10.3lian.com/sc6/show02/38/65/386515.jpg",
			"http://pic1.nipic.com/2008-12-09/200812910493588_2.jpg",
			"http://pic2.ooopic.com/11/79/98/31bOOOPICb1_1024.jpg" };
	public static final String[] HEADIMG = {
			"http://img.wzfzl.cn/uploads/allimg/140820/co140R00Q925-14.jpg",
			"http://www.feizl.com/upload2007/2014_06/1406272351394618.png",
			"http://v1.qzone.cc/avatar/201308/30/22/56/5220b2828a477072.jpg%21200x200.jpg",
			"http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg",
			"http://v1.qzone.cc/avatar/201408/20/17/23/53f468ff9c337550.jpg!200x200.jpg",
			"http://cdn.duitang.com/uploads/item/201408/13/20140813122725_8h8Yu.jpeg",
			"http://img.woyaogexing.com/touxiang/nv/20140212/9ac2117139f1ecd8%21200x200.jpg",
			"http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212295986807.jpg" };

	public static List<User> users = new ArrayList<User>();
	/**
	 * 动�?�id自增�?
	 */
	private static int circleId = 0;
	/**
	 * 点赞id自增�?
	 */
	private static int favortId = 0;
	/**
	 * 评论id自增�?
	 */
	private static int commentId = 0;
	public static final User curUser = new User("0", "自己", HEADIMG[0]);
	static {
		User user1 = new User("1", "张三", HEADIMG[1]);
		User user2 = new User("2", "李四", HEADIMG[2]);
		User user3 = new User("3", "隔壁老王", HEADIMG[3]);
		User user4 = new User("4", "赵六", HEADIMG[4]);
		User user5 = new User("5", "田七", HEADIMG[5]);
		User user6 = new User("6", "Naoki", HEADIMG[6]);
		User user7 = new User("7", "这个名字是不是很长，哈哈！因为我是用来测试换行的", HEADIMG[7]);

		users.add(curUser);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		users.add(user7);
	}

	public static List<CircleItem> createCircleDatas() {
		List<CircleItem> circleDatas = new ArrayList<CircleItem>();
		for (int i = 0; i < 15; i++) {
			CircleItem item = new CircleItem();
			User user = getUser();
			item.setId(String.valueOf(circleId++));
			item.setUser(user);
			item.setContent(getContent());
			item.setCreateTime("12�?24�?");

			item.setFavorters(createFavortItemList());
			item.setComments(createCommentItemList());
			if (getRandomNum(10) % 2 == 0) {
				item.setType("1");// 链接
				item.setLinkImg("http://pics.sc.chinaz.com/Files/pic/icons128/2264/%E8%85%BE%E8%AE%AFQQ%E5%9B%BE%E6%A0%87%E4%B8%8B%E8%BD%BD1.png");
				item.setLinkTitle("百度�?下，你就知道");
			} else {
				item.setType("2");// 图片
				item.setPhotos(createPhotos());
			}
			circleDatas.add(item);
		}

		return circleDatas;
	}

	public static User getUser() {
		return users.get(getRandomNum(users.size()));
	}

	public static String getContent() {
		return CONTENTS[getRandomNum(CONTENTS.length)];
	}

	public static int getRandomNum(int max) {
		Random random = new Random();
		int result = random.nextInt(max);
		return result;
	}

	public static List<String> createPhotos() {
		List<String> photos = new ArrayList<String>();
		int size = getRandomNum(PHOTOS.length);
		if (size > 0) {
			if (size > 9) {
				size = 9;
			}
			for (int i = 0; i < size; i++) {
				String photo = PHOTOS[getRandomNum(PHOTOS.length)];
				if (!photos.contains(photo)) {
					photos.add(photo);
				} else {
					i--;
				}
			}
		}
		return photos;
	}

	public static List<FavortItem> createFavortItemList() {
		int size = getRandomNum(users.size());
		List<FavortItem> items = new ArrayList<FavortItem>();
		List<String> history = new ArrayList<String>();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				FavortItem newItem = createFavortItem();
				String userid = newItem.getUser().getId();
				if (!history.contains(userid)) {
					items.add(newItem);
					history.add(userid);
				} else {
					i--;
				}
			}
		}
		return items;
	}

	public static FavortItem createFavortItem() {
		FavortItem item = new FavortItem();
		item.setId(String.valueOf(favortId++));
		item.setUser(getUser());
		return item;
	}

	public static FavortItem createCurUserFavortItem() {
		FavortItem item = new FavortItem();
		item.setId(String.valueOf(favortId++));
		item.setUser(curUser);
		return item;
	}
	public static List<CommentItem> createCommentItemList() {
		List<CommentItem> items = new ArrayList<CommentItem>();
		int size = getRandomNum(10);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				items.add(createComment());
			}
		}
		return items;
	}

	public static CommentItem createComment() {
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent("哈哈");
		User user = getUser();
		item.setUser(user);
		if (getRandomNum(10) % 2 == 0) {
			while (true) {
				User replyUser = getUser();
				if (!user.getId().equals(replyUser.getId())) {
					item.setToReplyUser(replyUser);
					break;
				}
			}
		}
		return item;
	}
	public static CommentItem createPublicComment(String content) {
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		return item;
	}
	public static CommentItem createReplyComment(User replyUser, String content) {
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		item.setToReplyUser(replyUser);
		return item;
	}

}
