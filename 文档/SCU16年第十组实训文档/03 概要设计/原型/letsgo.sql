/*
Navicat MySQL Data Transfer

Source Server         : wx_se
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : letsgo

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2016-03-11 17:59:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(10) NOT NULL auto_increment,
  `time` varchar(20) default NULL,
  `userId` int(10) default NULL,
  `userName` varchar(18) default NULL,
  `questionId` int(10) default NULL,
  `text` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------

-- ----------------------------
-- Table structure for `apply`
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int(10) NOT NULL auto_increment,
  `time` varchar(20) default NULL,
  `sourId` int(10) default NULL,
  `sourName` varchar(18) default NULL,
  `destId` int(10) default NULL,
  `destName` varchar(18) default NULL,
  `state` int(2) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `province` varchar(20) default NULL,
  `hot` int(2) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) NOT NULL auto_increment,
  `userId` int(10) default NULL,
  `userNickname` varchar(18) default NULL,
  `replyId` int(10) default NULL,
  `replyNickname` varchar(18) default NULL,
  `memoryId` int(10) default NULL,
  `text` varchar(200) default NULL,
  `time` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `favorite`
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` int(10) NOT NULL auto_increment,
  `memoryId` int(10) default NULL,
  `userId` int(10) default NULL,
  `userNickname` varchar(18) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favorite
-- ----------------------------

-- ----------------------------
-- Table structure for `follow`
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(10) NOT NULL auto_increment,
  `sourId` int(10) default NULL,
  `sourName` varchar(18) default NULL,
  `destId` int(10) default NULL,
  `destName` varchar(18) default NULL,
  `accessInfo` int(2) default NULL,
  `accessNews` int(2) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('1', '1', 'asasas1', '2', 'QWQWW', '0', '1');
INSERT INTO `follow` VALUES ('2', '1', 'asasas1', '3', 'qw', '0', '1');

-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(10) NOT NULL auto_increment,
  `sourId` int(10) default NULL,
  `sourName` varchar(18) default NULL,
  `destId` int(10) default NULL,
  `destName` varchar(18) default NULL,
  `accessInfo` int(2) default NULL,
  `accessNews` int(2) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('1', '1', 'ssa', '2', 'sasas', '1', '1');
INSERT INTO `friend` VALUES ('2', '1', 'ASS', '3', 'WQW', '1', '1');

-- ----------------------------
-- Table structure for `information`
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` int(10) NOT NULL auto_increment,
  `sceneId` int(10) default NULL,
  `title` varchar(20) default NULL,
  `text` varchar(200) default NULL,
  `time` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of information
-- ----------------------------

-- ----------------------------
-- Table structure for `memory`
-- ----------------------------
DROP TABLE IF EXISTS `memory`;
CREATE TABLE `memory` (
  `id` int(10) NOT NULL auto_increment,
  `userId` int(10) default NULL,
  `username` varchar(18) default NULL,
  `headPic` varchar(50) default NULL,
  `text` varchar(200) default NULL,
  `time` varchar(50) default NULL,
  `sceneId` int(10) default NULL,
  `sceneName` varchar(20) default NULL,
  `img` varchar(20) default NULL,
  `video` varchar(20) default NULL,
  `music` varchar(20) default NULL,
  `location` varchar(30) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of memory
-- ----------------------------

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(10) NOT NULL auto_increment,
  `sourUserId` int(10) default NULL,
  `sourName` varchar(18) default NULL,
  `destUserId` int(10) default NULL,
  `destName` varchar(18) default NULL,
  `time` varchar(20) default NULL,
  `text` varchar(200) default NULL,
  `isPublic` int(2) unsigned zerofill default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '1', 'saas', '3', 'saasa', '1457233845858', 'assaassa', '00');
INSERT INTO `message` VALUES ('2', '1', 'saas', '3', 'saasa', '1457233899284', 'assaassa', '00');
INSERT INTO `message` VALUES ('3', '1', 'saas', '3', 'saasa', '1457233981657', 'assaassa', '00');
INSERT INTO `message` VALUES ('4', '1', 'saas', '3', 'saasa', '1457667118772', 'assaassa', '00');

-- ----------------------------
-- Table structure for `plan`
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `id` int(10) NOT NULL auto_increment,
  `userId` int(10) default NULL,
  `time` varchar(20) default NULL COMMENT '发布时间',
  `startTime` varchar(20) default NULL,
  `endTime` varchar(20) default NULL,
  `text` varchar(200) default NULL,
  `targetUser` int(2) default NULL,
  `travelLoc` varchar(30) default NULL,
  `maxPerson` int(2) default NULL,
  `personList` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan
-- ----------------------------

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(10) NOT NULL auto_increment,
  `time` varchar(20) default NULL,
  `userId` int(10) default NULL,
  `userName` varchar(18) default NULL,
  `text` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for `scene`
-- ----------------------------
DROP TABLE IF EXISTS `scene`;
CREATE TABLE `scene` (
  `id` int(10) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `cityId` int(10) default NULL,
  `hot` int(2) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scene
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL auto_increment,
  `limits` int(2) default NULL,
  `username` varchar(18) default NULL,
  `password` varchar(18) default NULL,
  `nickname` varchar(18) default NULL,
  `sex` varchar(6) default NULL,
  `age` int(3) default NULL,
  `tel` varchar(12) default NULL,
  `ppquestion` varchar(30) default NULL,
  `ppanswer` varchar(30) default NULL,
  `birthday` varchar(20) default NULL,
  `address` varchar(50) default NULL,
  `email` varchar(30) default NULL,
  `hobby` varchar(20) default NULL,
  `edubackground` varchar(10) default NULL,
  `lastLoginIp` varchar(30) default NULL,
  `lastLoginPort` varchar(10) default NULL,
  `registerTime` varchar(20) default NULL,
  `headPic` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '0', 'akbery', '123456', null, '男', '0', null, '你初中班主任？', 'zhangsan', '2016-2-11', null, null, null, null, '172.18.10.58', '15078', '2016/03/11 17:17:50', null);
INSERT INTO `user` VALUES ('2', '0', 'dtujfdgh', '123456', null, '男', '0', null, '你初中班主任？', 'dfhuygg', '2016-2-11', null, null, null, null, '172.18.10.58', '6560', '2016/03/11 17:19:46', null);
INSERT INTO `user` VALUES ('3', '0', 'fwuehhdddhhd', '123456', null, '男', '0', null, '你初中班主任？', 'dhdjbfb', '2016-2-11', null, null, null, null, '172.18.10.58', '16421', '2016/03/11 17:24:22', null);

-- ----------------------------
-- Table structure for `webinfo`
-- ----------------------------
DROP TABLE IF EXISTS `webinfo`;
CREATE TABLE `webinfo` (
  `id` int(10) NOT NULL auto_increment,
  `historyAddIn` varchar(5) default NULL,
  `time` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of webinfo
-- ----------------------------
INSERT INTO `webinfo` VALUES ('1', '5', '2016/03/07');
INSERT INTO `webinfo` VALUES ('2', '23', '2016/03/08');
INSERT INTO `webinfo` VALUES ('3', '17', '2016/03/09');
INSERT INTO `webinfo` VALUES ('5', '367', '2016/03/11');
