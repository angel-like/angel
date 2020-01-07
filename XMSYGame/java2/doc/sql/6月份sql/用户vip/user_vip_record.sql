/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-15 17:59:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_vip_record
-- ----------------------------
DROP TABLE IF EXISTS `user_vip_record`;
CREATE TABLE `user_vip_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `vip_id` bigint(22) NOT NULL DEFAULT '0' COMMENT 'vip等级id',
  `recharge_amount` bigint(22) DEFAULT '0' COMMENT '已经充值',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户vip等级记录表';
