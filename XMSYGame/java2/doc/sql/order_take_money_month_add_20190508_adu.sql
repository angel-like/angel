/*
Navicat MySQL Data Transfer

Source Server         : 85.208.56.202
Source Server Version : 80015
Source Host           : 85.208.56.202:3508
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-08 17:43:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_take_money_month
-- ----------------------------
DROP TABLE IF EXISTS `order_take_money_month`;
CREATE TABLE `order_take_money_month` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `take_type` int(9) DEFAULT '0' COMMENT '提现类型',
  `take_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '提现类型名称',
  `unconfirmed_amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '未确认提现总额',
  `cancel_amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '取消提现总额',
  `confirmed_amount` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '确认提现总额',
  `unconfirmed_poundage` decimal(22,2) DEFAULT '0.00' COMMENT '待确认手续总额',
  `cancel_poundage` decimal(22,2) DEFAULT '0.00' COMMENT '取消手续总额',
  `confirmed_poundage` decimal(22,2) DEFAULT '0.00' COMMENT '确认手续总额',
  `unconfirmed_obtain_amount` decimal(22,2) DEFAULT '0.00' COMMENT '未确认实际金额',
  `cancel_obtain_amount` decimal(22,2) DEFAULT '0.00' COMMENT '取消实际金额',
  `confirmed_obtain_amount` decimal(22,2) DEFAULT '0.00' COMMENT '确认实际金额',
  `unconfirmed_num` int(11) DEFAULT '0' COMMENT '未确认订单数',
  `cancel_num` int(11) DEFAULT '0' COMMENT '取消的订单数',
  `confirmed_num` int(11) DEFAULT '0' COMMENT '确认的订单数',
  `count_month` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统计的月份',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_order_no` (`count_month`,`take_type`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE,
  KEY `index_count_month` (`count_month`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='取款订单月统计表';
