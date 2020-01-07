/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.145
Source Server Version : 50646
Source Host           : 10.0.0.145:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-12-31 15:12:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_record_dezhoupuke
-- ----------------------------
DROP TABLE IF EXISTS `game_record_dezhoupuke`;
CREATE TABLE `game_record_dezhoupuke` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `grade_number` int(11) NOT NULL DEFAULT '1' COMMENT '场次编号',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `big_base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分(大盲注)',
  `small_base_score` bigint(20) NOT NULL DEFAULT '0' COMMENT '底分(小盲注)',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `public_cards_int` varchar(100) DEFAULT '' COMMENT '公共牌型',
  `public_cards_str` varchar(200) DEFAULT '' COMMENT '公共牌型',
  `play_cards_int` varchar(600) NOT NULL COMMENT '玩家牌型',
  `play_cards_str` varchar(600) NOT NULL COMMENT '玩家牌型',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `banker` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否庄家',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5208 DEFAULT CHARSET=utf8 COMMENT='游戏记录-德州扑克';
