/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.145
Source Server Version : 50646
Source Host           : 10.0.0.145:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-12-16 10:56:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_record_undersea_treasure
-- ----------------------------
DROP TABLE IF EXISTS `game_record_undersea_treasure`;
CREATE TABLE `game_record_undersea_treasure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT '' COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT '' COMMENT '房间名称',
  `pow` bigint(11) NOT NULL COMMENT '中奖倍数',
  `bet_lines` bigint(11) NOT NULL DEFAULT '0' COMMENT '压线数',
  `lines` varchar(255) NOT NULL DEFAULT '' COMMENT '中奖线情况',
  `scenes` varchar(255) NOT NULL DEFAULT '' COMMENT '图标情况',
  `bcoins` bigint(11) NOT NULL DEFAULT '0' COMMENT '底分',
  `bet_coins` bigint(11) NOT NULL,
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `mini_game_multiple` int(9) NOT NULL DEFAULT '1' COMMENT '小游戏倍数',
  `mini_game_rewards` bigint(20) NOT NULL DEFAULT '0' COMMENT '小游戏奖励',
  `mini_game` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否小游戏',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`,`mini_game`) USING BTREE,
  KEY `index_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16162 DEFAULT CHARSET=utf8 COMMENT='游戏记录-海底宝藏';
