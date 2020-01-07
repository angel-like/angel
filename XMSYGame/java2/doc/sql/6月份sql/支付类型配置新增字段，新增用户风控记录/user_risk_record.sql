/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-06 16:18:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_risk_record
-- ----------------------------
DROP TABLE IF EXISTS `user_risk_record`;
CREATE TABLE `user_risk_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(100) DEFAULT '' COMMENT '用户类型',
  `risk_type` varchar(50) DEFAULT '' COMMENT '风控类型',
  `coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '金币值',
  `hierarchy_id` bigint(22) DEFAULT '0' COMMENT '层级id',
  `risk_val` decimal(22,2) DEFAULT '0.00' COMMENT '风控值',
  `recharge_val` decimal(22,2) DEFAULT '0.00' COMMENT '充值钱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='用户风控记录表';
