/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-20 09:43:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for headframe
-- ----------------------------
DROP TABLE IF EXISTS `headframe`;
CREATE TABLE `headframe` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型 ',
  `type_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '类型id',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '标题',
  `enable` bit(1) DEFAULT b'0' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='头像框表';
