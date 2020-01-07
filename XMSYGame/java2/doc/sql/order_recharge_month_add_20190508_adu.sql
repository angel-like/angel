/*
Navicat MySQL Data Transfer

Source Server         : 85.208.56.202
Source Server Version : 80015
Source Host           : 85.208.56.202:3508
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-08 17:43:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_recharge_month
-- ----------------------------
DROP TABLE IF EXISTS `order_recharge_month`;
CREATE TABLE `order_recharge_month` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `recharge_platform` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '充值平台',
  `recharge_type` int(9) DEFAULT '0' COMMENT '充值类型',
  `recharge_platform_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '充值平台名称',
  `recharge_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '充值类型名称',
  `unconfirmed_amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '未确认充值总额',
  `cancel_amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '取消充值总额',
  `confirmed_amount` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '确认充值总额',
  `unconfirmed_discount_amount` decimal(22,2) DEFAULT '0.00' COMMENT '待确认优惠总额',
  `cancel_discount_amount` decimal(22,2) DEFAULT '0.00' COMMENT '取消优惠总额',
  `confirmed_discount_amount` decimal(22,2) DEFAULT '0.00' COMMENT '确认优惠总额',
  `unconfirmed_num` int(11) DEFAULT '0' COMMENT '未确认订单数',
  `cancel_num` int(11) DEFAULT '0' COMMENT '取消的订单数',
  `confirmed_num` int(11) DEFAULT '0' COMMENT '确认的订单数',
  `count_month` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统计的月份',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_order_no` (`recharge_platform`,`count_month`,`recharge_type`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE,
  KEY `index_count_month` (`count_month`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='充值订单月统计表';
