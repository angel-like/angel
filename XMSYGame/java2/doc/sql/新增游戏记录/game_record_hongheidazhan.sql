/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.142
Source Server Version : 80012
Source Host           : 10.0.0.142:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-08-30 16:04:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_record_hongheidazhan
-- ----------------------------
DROP TABLE IF EXISTS `game_record_hongheidazhan`;
CREATE TABLE `game_record_hongheidazhan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `grade_number` int(11) NOT NULL DEFAULT '0' COMMENT '场次编号',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `red_card` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '红方牌型',
  `red_card_str` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '红方牌型字符',
  `black_card` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '黑方牌型',
  `black_card_str` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '黑方牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3364 DEFAULT CHARSET=utf8 COMMENT='游戏记录-红黑大战';
