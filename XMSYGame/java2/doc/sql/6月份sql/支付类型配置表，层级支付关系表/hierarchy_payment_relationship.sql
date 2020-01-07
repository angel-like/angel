/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-04 10:56:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hierarchy_payment_relationship
-- ----------------------------
DROP TABLE IF EXISTS `hierarchy_payment_relationship`;
CREATE TABLE `hierarchy_payment_relationship` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `payment_id` int(11) NOT NULL COMMENT '支付类型id',
  `hierarchy_id` int(11) NOT NULL COMMENT '层级id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_hierarchy_uk` (`payment_id`,`hierarchy_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='层级支付关系表';
