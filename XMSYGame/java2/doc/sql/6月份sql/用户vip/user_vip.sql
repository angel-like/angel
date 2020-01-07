/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-11 17:55:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_vip
-- ----------------------------
DROP TABLE IF EXISTS `user_vip`;
CREATE TABLE `user_vip` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'vip等级名称',
  `sort` int(10) DEFAULT '0' COMMENT '排序',
  `recharge_reached` bigint(22) NOT NULL DEFAULT '0' COMMENT '充值达到',
  `recharge_rate` decimal(3,2) NOT NULL DEFAULT '0.00' COMMENT '充值优惠比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户vip等级表';

