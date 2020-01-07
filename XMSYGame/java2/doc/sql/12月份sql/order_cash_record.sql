/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.145_3306
Source Server Version : 50646
Source Host           : 10.0.0.145:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2020-01-02 12:53:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_cash_record
-- ----------------------------
DROP TABLE IF EXISTS `order_cash_record`;
CREATE TABLE `order_cash_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` int(11) DEFAULT '0' COMMENT '操作类型',
  `user_id` bigint(22) NOT NULL COMMENT '用户id',
  `amount` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '操作金额',
  `sys_user_account` varchar(20) DEFAULT '' COMMENT '操作人用户名',
  `sys_user_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `user_account` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='用户打码量记录表';
