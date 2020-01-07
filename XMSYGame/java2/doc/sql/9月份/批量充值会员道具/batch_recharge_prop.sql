/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.120
Source Server Version : 80012
Source Host           : 10.0.0.120:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-09-29 10:14:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for batch_recharge_prop
-- ----------------------------
DROP TABLE IF EXISTS `batch_recharge_prop`;
CREATE TABLE `batch_recharge_prop` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `order_no` varchar(22) DEFAULT '' COMMENT '订单号',
  `sys_user_account` varchar(255) DEFAULT '' COMMENT '操作人用户名',
  `sys_user_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `prop_id` bigint(20) DEFAULT NULL COMMENT '道具id',
  `prop_num` bigint(20) unsigned DEFAULT '0' COMMENT '道具数量',
  `account` varchar(500) DEFAULT '' COMMENT '支付人账号',
  `hierarchy_id` bigint(20) DEFAULT NULL COMMENT '层级id',
  `vip_id` bigint(20) DEFAULT NULL COMMENT 'vip_id',
  `designated` bit(1) DEFAULT b'0' COMMENT '是否指定',
  `send_message` bit(1) DEFAULT b'0' COMMENT '是否发送站内信',
  `message_title` varchar(255) DEFAULT '' COMMENT '站内信标题',
  `message_content` varchar(255) DEFAULT '' COMMENT '站内信内容',
  `effective_date` int(11) DEFAULT '0' COMMENT '站内信有效期限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_uk` (`order_no`) USING BTREE,
  KEY `index_account` (`account`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=utf8 COMMENT='批量充值会员道具';
