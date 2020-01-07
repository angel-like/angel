/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-17 17:20:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for player_tasks
-- ----------------------------
DROP TABLE IF EXISTS `player_tasks`;
CREATE TABLE `player_tasks` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `room_id` bigint(22) NOT NULL COMMENT '房间类型',
  `game_id` bigint(22) NOT NULL COMMENT '游戏id',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `type` bigint(22) NOT NULL COMMENT '任务类型',
  `prop_id` bigint(22) NOT NULL COMMENT '道具id',
  `prop_num` int(11) NOT NULL COMMENT '道具数量',
  `condition` int(11) NOT NULL COMMENT '条件',
  `confrontation_requirement` bigint(22) NOT NULL COMMENT '对局要求',
  `cycle` int(11) NOT NULL,
  `sorts` int(11) NOT NULL COMMENT '排序',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='玩家任务表';
