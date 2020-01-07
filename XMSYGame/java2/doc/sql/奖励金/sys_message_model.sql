/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:35:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_message_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_model`;
CREATE TABLE `sys_message_model` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) DEFAULT '' COMMENT '伪标题',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `content_type` int(2) DEFAULT '1' COMMENT '类型(1：会员站内信 2:管理员站内信）',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `readonly` bit(1) DEFAULT b'0' COMMENT '是否只读:1：是 0：否',
  `effect_time` int(11) DEFAULT '0' COMMENT '有效期限（天），0是无限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='站内信模板';

-- ----------------------------
-- Records of sys_message_model
-- ----------------------------
INSERT INTO `sys_message_model` VALUES ('1', '0', '2019-05-22 16:37:31', '2019-05-25 12:39:39', '0', '分享app-金币100-客户领取', '分享app奖励通知', '分享奖励通知;您有效分享app 1次，赠送您金币: 100, 请查收', '1', '', '\0', '7');
INSERT INTO `sys_message_model` VALUES ('2', '0', '2019-05-22 17:28:51', '2019-05-22 17:28:51', '0', '邀请好友', '邀请好友', '邀请好友', '1', '', '\0', '2');
INSERT INTO `sys_message_model` VALUES ('4', '0', '2019-05-24 14:42:37', '2019-05-24 18:18:16', '0', '用户对局数满10奖励-100金币-后台发放', '用户对局数满奖励通知', '用户对局数满奖励通知;您今日已经玩了10局游戏，赠送您金币 :100, 请查收', '1', '', '', '7');
INSERT INTO `sys_message_model` VALUES ('5', '0', '2019-05-24 14:47:49', '2019-05-24 16:23:39', '0', '救济金发放-1000金币-客户领取', '救济金发放通知', '救济金发放通知;赠送您金币 :1000, 请查收', '1', '', '\0', '7');
INSERT INTO `sys_message_model` VALUES ('6', '0', '2019-05-24 14:50:39', '2019-05-24 16:23:53', '0', '鼓励金发放-100金币-客户领取', '鼓励金发放通知', '鼓励金发放通知;赠送您金币 :100, 请查收', '1', '', '\0', '7');
INSERT INTO `sys_message_model` VALUES ('7', '0', '2019-05-24 14:59:15', '2019-05-24 16:24:00', '0', '用户对局数满30奖励-300金币-后台发放', '用户对局数满奖励通知', '用户对局数满奖励通知;您今日已经玩了30局游戏，赠送您金币 :300, 请查收', '1', '', '', '7');
