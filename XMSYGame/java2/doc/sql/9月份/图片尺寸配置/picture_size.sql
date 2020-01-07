/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.120
Source Server Version : 80012
Source Host           : 10.0.0.120:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-09-25 17:40:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for picture_size
-- ----------------------------
DROP TABLE IF EXISTS `picture_size`;
CREATE TABLE `picture_size` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type_id` int(11) NOT NULL DEFAULT '0' COMMENT '类型id（0：web，1：app）',
  `tips` varchar(220) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '提示语',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='图片尺寸配置';
