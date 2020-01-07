/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:36:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_message_model_prop
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_model_prop`;
CREATE TABLE `sys_message_model_prop` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `message_id` bigint(22) DEFAULT '0' COMMENT '模板邮件id',
  `prop_id` bigint(22) DEFAULT '0' COMMENT '道具id',
  `prop_num` int(9) NOT NULL DEFAULT '0' COMMENT '道具数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='站内信模板-道具列表';

-- ----------------------------
-- Records of sys_message_model_prop
-- ----------------------------
INSERT INTO `sys_message_model_prop` VALUES ('16', '0', '2019-05-23 17:10:25', '2019-05-24 14:39:07', '0', '1', '1', '100');
INSERT INTO `sys_message_model_prop` VALUES ('18', '0', '2019-05-23 17:11:26', '2019-05-23 17:11:26', '0', '2', '1', '3');
INSERT INTO `sys_message_model_prop` VALUES ('20', '0', '2019-05-24 14:51:03', '2019-05-24 14:51:03', '0', '6', '1', '100');
INSERT INTO `sys_message_model_prop` VALUES ('21', '0', '2019-05-24 14:51:24', '2019-05-24 14:51:24', '0', '5', '1', '1000');
INSERT INTO `sys_message_model_prop` VALUES ('22', '0', '2019-05-24 14:51:48', '2019-05-24 14:51:48', '0', '4', '1', '100');
INSERT INTO `sys_message_model_prop` VALUES ('23', '0', '2019-05-24 14:59:55', '2019-05-24 14:59:55', '0', '7', '1', '300');
