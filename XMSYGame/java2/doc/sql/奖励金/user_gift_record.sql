/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:37:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_gift_record
-- ----------------------------
DROP TABLE IF EXISTS `user_gift_record`;
CREATE TABLE `user_gift_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` int(5) NOT NULL DEFAULT '0' COMMENT '活动类型 1：奖励金',
  `detail_type` int(5) DEFAULT '0' COMMENT '具体类型 ',
  `num` int(9) NOT NULL DEFAULT '0' COMMENT '数量',
  `prop_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '道具id',
  `send_type` int(1) NOT NULL DEFAULT '0' COMMENT '发送模式 0：后台发放 1：客户领取',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号',
  `receive` bit(1) DEFAULT b'0' COMMENT '是否领取',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='用户道具发放记录';
