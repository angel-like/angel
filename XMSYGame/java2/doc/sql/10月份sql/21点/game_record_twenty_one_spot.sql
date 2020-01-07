/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.120
Source Server Version : 80012
Source Host           : 10.0.0.120:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-10-10 11:27:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_record_twenty_one_spot
-- ----------------------------
DROP TABLE IF EXISTS `game_record_twenty_one_spot`;
CREATE TABLE `game_record_twenty_one_spot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `grade_number` int(11) DEFAULT '0' COMMENT '场次编号',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `additional_score` bigint(20) NOT NULL DEFAULT '0' COMMENT '额外加注',
  `cards_prize` bigint(11) NOT NULL DEFAULT '0' COMMENT '牌型盈亏',
  `insurance_prize` bigint(11) NOT NULL DEFAULT '0' COMMENT '保险盈亏',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '总盈亏',
  `player_card` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '玩家牌型',
  `player_card_str` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '玩家牌型字符',
  `banker_card` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '庄家牌型',
  `banker_card_str` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '庄家牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
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
) ENGINE=InnoDB AUTO_INCREMENT=2855 DEFAULT CHARSET=utf8 COMMENT='游戏记录-21点';
