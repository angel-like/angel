/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-25 14:33:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gift_coin_config
-- ----------------------------
DROP TABLE IF EXISTS `gift_coin_config`;
CREATE TABLE `gift_coin_config` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '奖励类型',
  `num` int(9) NOT NULL DEFAULT '0' COMMENT '条件次数|人数',
  `max_num` int(5) DEFAULT '0' COMMENT '当天最多次数',
  `coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '奖励的金币值',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（1.启用，0：禁用）',
  `send_type` int(1) NOT NULL DEFAULT '0' COMMENT '发送模式 0：邮件发送 1：客户领取',
  `cycle` int(3) NOT NULL DEFAULT '1' COMMENT '周期单位（天）',
  `template_id` bigint(22) NOT NULL COMMENT '邮件模板id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_num_uk` (`type`,`num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='金币奖励配置';

-- ----------------------------
-- Records of gift_coin_config
-- ----------------------------
INSERT INTO `gift_coin_config` VALUES ('1', '0', '2019-05-22 11:34:50', '2019-05-24 14:52:32', '0', '1', '10', '2', '10000', '', '0', '1', '4');
INSERT INTO `gift_coin_config` VALUES ('2', '0', '2019-05-22 11:35:19', '2019-05-25 14:24:09', '0', '3', '1', '10', '10000', '', '1', '1', '1');
INSERT INTO `gift_coin_config` VALUES ('3', '0', '2019-05-22 11:36:07', '2019-05-24 18:43:34', '0', '5', '1000', '1', '100000', '', '1', '1', '5');
INSERT INTO `gift_coin_config` VALUES ('4', '0', '2019-05-22 11:36:45', '2019-05-24 14:57:09', '0', '6', '3', '1', '10000', '', '1', '1', '6');
INSERT INTO `gift_coin_config` VALUES ('6', '0', '2019-05-22 17:04:09', '2019-05-24 15:00:24', '0', '1', '30', '1', '30000', '', '0', '1', '7');
INSERT INTO `gift_coin_config` VALUES ('7', '0', '2019-05-24 11:46:35', '2019-05-24 11:46:35', '0', '2', '1', '2', '100000', '', '0', '1', '2');
INSERT INTO `gift_coin_config` VALUES ('9', '1', '2019-05-24 15:05:58', '2019-05-24 15:05:58', '0', '5', '1', '2', '200000', '', '1', '1', '1');
