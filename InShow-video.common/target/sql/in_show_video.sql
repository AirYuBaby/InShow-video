/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : in_show_video

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-19 08:32:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `barrages`
-- ----------------------------
DROP TABLE IF EXISTS `barrages`;
CREATE TABLE `barrages` (
  `id` varchar(20) NOT NULL,
  `video_id` varchar(20) NOT NULL COMMENT '视频id',
  `comment` text NOT NULL COMMENT '弹幕内容',
  `create_time` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='弹幕评论表';

-- ----------------------------
-- Records of barrages
-- ----------------------------

-- ----------------------------
-- Table structure for `bgm`
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm` (
  `id` varchar(64) NOT NULL,
  `author` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='背景音乐表';

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('2001', '16516', '6516', 'kbk/kjkb/kk');
INSERT INTO `bgm` VALUES ('2002', '312321', '656', 'jhbjvhj/klkk/');

-- ----------------------------
-- Table structure for `bgm_copy`
-- ----------------------------
DROP TABLE IF EXISTS `bgm_copy`;
CREATE TABLE `bgm_copy` (
  `id` varchar(64) NOT NULL,
  `author` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='背景音乐表';

-- ----------------------------
-- Records of bgm_copy
-- ----------------------------

-- ----------------------------
-- Table structure for `topics`
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '发布者id',
  `topic_name` varchar(128) DEFAULT NULL COMMENT '话题名',
  `topic_desc` varchar(128) DEFAULT NULL COMMENT '话题描述',
  `cover_path` varchar(255) DEFAULT NULL COMMENT '话题封面图',
  `participation_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '参与话题的人员数量',
  `status` int(1) NOT NULL COMMENT '话题状态：\r\n1、发布成功\r\n2、撤销话题，管理员操作',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='话题信息表';

-- ----------------------------
-- Records of topics
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `face_image` varchar(255) DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `fans_counts` int(11) DEFAULT '0' COMMENT '我的粉丝数量',
  `follow_counts` int(11) DEFAULT '0' COMMENT '我关注的人总数',
  `receive_like_counts` int(11) DEFAULT '0' COMMENT '我接受到的赞美/收藏 的数量',
  `report_counts` int(11) DEFAULT '0' COMMENT '被举报的数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1001', '4654656', '456', '456', '0', '0', '0', '0');
INSERT INTO `users` VALUES ('1002', '15616516', '651651', '651', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `users_fans`
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '用户',
  `fan_id` varchar(64) NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`fan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户粉丝关联关系表';

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('453435433', '1002', '1001');

-- ----------------------------
-- Table structure for `users_like_videos`
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '用户',
  `video_id` varchar(64) NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_video_rel` (`user_id`,`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户喜欢的/赞过的视频';

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------

-- ----------------------------
-- Table structure for `users_report`
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report` (
  `id` varchar(64) NOT NULL,
  `deal_user_id` varchar(64) NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) NOT NULL,
  `title` varchar(128) NOT NULL COMMENT '类型标题，让用户选择，详情见 枚举',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `userid` varchar(64) NOT NULL COMMENT '举报人的id',
  `create_date` datetime NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报用户表';

-- ----------------------------
-- Records of users_report
-- ----------------------------

-- ----------------------------
-- Table structure for `videos`
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '发布者id',
  `audio_id` varchar(64) DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6,2) DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：\r\n1、发布成功\r\n2、禁止播放，管理员操作',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `click_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '视频点击量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频信息表';

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('6516156156156', '1002', '2001', 'sfadsfasfdadfafd', '/userid/video/jijiji.mp4', '0.00', '222', '222', '/userid/video_cover/444.jpg', '0', '0', '2018-09-19 08:26:46', '0');
INSERT INTO `videos` VALUES ('984651686161', '1001', '2001', 'baabbbdbgbksjbkg', '/userid/video/xxx.mp4', '0.00', '222', '222', '/userid/video_cover/sssss.jpg', '0', '0', '2018-09-19 08:23:35', '0');

-- ----------------------------
-- Table structure for `videos_topic`
-- ----------------------------
DROP TABLE IF EXISTS `videos_topic`;
CREATE TABLE `videos_topic` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '发布人的id',
  `topic_id` varchar(64) NOT NULL COMMENT '话题的id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='话题用户关联关系表';

-- ----------------------------
-- Records of videos_topic
-- ----------------------------
