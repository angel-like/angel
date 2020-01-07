/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.145
Source Server Version : 50646
Source Host           : 10.0.0.145:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2019-12-27 15:31:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_stock_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `game_stock_operation_record`;
CREATE TABLE `game_stock_operation_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) NOT NULL COMMENT '房间id',
  `stock_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '库存id',
  `stock` decimal(22,2) NOT NULL COMMENT '实际有效库存',
  `stock_limit` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '库存上限',
  `tax` decimal(22,2) DEFAULT '0.00' COMMENT '抽税',
  `tax_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '抽税比例',
  `enable` bit(1) DEFAULT b'1' COMMENT '是否启动',
  `sys_user_id` bigint(11) DEFAULT '0' COMMENT '操作者id',
  `sys_user_name` varchar(255) DEFAULT '' COMMENT '操作者名称',
  `operation_content` varchar(600) DEFAULT '' COMMENT '操作内容',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1671 DEFAULT CHARSET=utf8 COMMENT='游戏库存操作记录';
