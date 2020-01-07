/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-20 09:44:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_headframe
-- ----------------------------
DROP TABLE IF EXISTS `user_headframe`;
CREATE TABLE `user_headframe` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '类型 ',
  `headframe_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '头像id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`user_id`,`headframe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户头像框关系表';
