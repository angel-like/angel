/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:36:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_message_prop
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_prop`;
CREATE TABLE `sys_message_prop` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `message_id` bigint(22) DEFAULT '0' COMMENT '邮件id',
  `prop_id` bigint(22) DEFAULT '0' COMMENT '道具id',
  `prop_num` int(9) NOT NULL DEFAULT '0' COMMENT '道具数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='站内信-道具列表';
