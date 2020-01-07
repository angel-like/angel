/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-19 09:59:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for player_tasks_receive_record
-- ----------------------------
DROP TABLE IF EXISTS `player_tasks_receive_record`;
CREATE TABLE `player_tasks_receive_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号',
  `task_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '任务id',
  `finish_date` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_task_uk` (`user_id`,`task_id`,`finish_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='玩家任务领取记录表';
