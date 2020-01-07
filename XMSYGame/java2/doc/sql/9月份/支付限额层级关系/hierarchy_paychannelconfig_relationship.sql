/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.142
Source Server Version : 80012
Source Host           : 10.0.0.142:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-09-03 15:49:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hierarchy_paychannelconfig_relationship
-- ----------------------------
DROP TABLE IF EXISTS `hierarchy_paychannelconfig_relationship`;
CREATE TABLE `hierarchy_paychannelconfig_relationship` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `hierarchy_id` bigint(22) NOT NULL COMMENT '层级id',
  `paychannelconfig_id` bigint(22) NOT NULL COMMENT '支付限额id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pay` (`delete_status`,`hierarchy_id`,`paychannelconfig_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='层级支付限额关系表';
