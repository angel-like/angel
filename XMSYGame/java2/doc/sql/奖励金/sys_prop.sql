/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:36:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_prop
-- ----------------------------
DROP TABLE IF EXISTS `sys_prop`;
CREATE TABLE `sys_prop` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '道具名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统道具';

-- ----------------------------
-- Records of sys_prop
-- ----------------------------
INSERT INTO `sys_prop` VALUES ('1', '0', '2019-05-22 17:59:42', '2019-05-22 17:59:42', '0', '金币');
INSERT INTO `sys_prop` VALUES ('2', '0', '2019-05-22 18:00:39', '2019-05-22 18:00:39', '0', '记牌器');
