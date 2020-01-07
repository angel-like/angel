/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:34:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for share_record
-- ----------------------------
DROP TABLE IF EXISTS `share_record`;
CREATE TABLE `share_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `client_type` varchar(100) NOT NULL DEFAULT '' COMMENT '客户端类型 ',
  `share_to` varchar(100) NOT NULL DEFAULT '' COMMENT '分享到',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='分享记录';
