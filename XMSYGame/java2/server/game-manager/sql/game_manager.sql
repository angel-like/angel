/*
Navicat MySQL Data Transfer

Source Server         : (开发环境)192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : game_manager

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-06 09:33:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_config
-- ----------------------------
DROP TABLE IF EXISTS `game_config`;
CREATE TABLE `game_config` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(11) NOT NULL COMMENT '游戏id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
  `val` decimal(2,2) NOT NULL DEFAULT '0.00' COMMENT '游戏概率',
  `description` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性描述',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `gameid_name_uk` (`game_id`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='游戏配置（概率设置）';

-- ----------------------------
-- Records of game_config
-- ----------------------------
INSERT INTO `game_config` VALUES ('1', '3', 'visitorRate', '0.10', '游戏概率', '0', '0', '2018-10-19 17:50:34', '2019-02-27 10:55:56');
INSERT INTO `game_config` VALUES ('2', '1', 'normalRate', '0.10', '正常用户概率', '0', '0', '2018-10-19 18:59:59', '2018-10-19 18:59:59');
INSERT INTO `game_config` VALUES ('6', '1', 'visitorGuessingCardRate', '0.10', '游客猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-10-19 19:02:37');
INSERT INTO `game_config` VALUES ('7', '2', 'visitorRate', '0.10', '游戏概率', '0', '0', '2018-10-19 17:50:34', '2018-10-19 17:50:38');
INSERT INTO `game_config` VALUES ('11', '2', 'normalGuessingCardRate', '0.10', '正常猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-10-19 19:02:37');
INSERT INTO `game_config` VALUES ('16', '3', 'visitorFreeGameRate', '0.10', '游客小游戏概率', '0', '0', '2018-10-19 19:01:25', '2018-11-28 15:31:11');
INSERT INTO `game_config` VALUES ('17', '3', 'normalGuessingCardRate', '0.10', '正常猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-11-28 15:30:50');
INSERT INTO `game_config` VALUES ('18', '3', 'visitorGuessingCardRate', '0.10', '游客猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-11-28 15:31:20');
INSERT INTO `game_config` VALUES ('19', '4', 'visitorRate', '0.10', '游戏概率', '0', '0', '2018-10-19 17:50:34', '2018-10-19 17:50:38');
INSERT INTO `game_config` VALUES ('20', '4', 'normalRate', '0.10', '正常用户概率', '0', '0', '2018-10-19 18:59:59', '2018-10-19 18:59:59');
INSERT INTO `game_config` VALUES ('21', '2', 'normalFreeGameRate', '0.10', '正常小游戏概率', '0', '0', '2018-10-19 19:01:25', '2019-02-27 10:55:34');
INSERT INTO `game_config` VALUES ('22', '4', 'visitorFreeGameRate', '0.10', '游客小游戏概率', '0', '0', '2018-10-19 19:01:25', '2018-10-19 19:01:25');
INSERT INTO `game_config` VALUES ('23', '4', 'normalGuessingCardRate', '0.10', '正常猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-10-19 19:02:37');
INSERT INTO `game_config` VALUES ('24', '4', 'visitorGuessingCardRate', '0.10', '游客猜牌游戏概率', '0', '0', '2018-10-19 19:02:37', '2018-10-19 19:02:37');

-- ----------------------------
-- Table structure for game_grade
-- ----------------------------
DROP TABLE IF EXISTS `game_grade`;
CREATE TABLE `game_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '场次名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='游戏场次等级';

-- ----------------------------
-- Records of game_grade
-- ----------------------------
INSERT INTO `game_grade` VALUES ('1', '初级场', '2018-10-19 11:36:21', '2019-02-27 19:49:39', '0', '0');
INSERT INTO `game_grade` VALUES ('2', '中级场', '2018-10-19 11:36:21', '2019-02-28 09:56:21', '0', '0');
INSERT INTO `game_grade` VALUES ('3', '高级场', '2018-10-19 11:36:21', '2018-10-19 11:36:21', '0', '0');

DROP TABLE IF EXISTS `game_info`;
CREATE TABLE `game_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '游戏名字',
  `room_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间id',
  `grade_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏类型',
  `enable` bit(1) DEFAULT b'1' COMMENT '是否启用',
  `display` bit(1) DEFAULT b'1' COMMENT '是否显示',
  `sence` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '显示的scene页面',
  `version` int(11) DEFAULT '0' COMMENT '显示的scene页面',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `rate` decimal(10,2) DEFAULT '0.00' COMMENT '比率',
  `bscore` bigint(20) DEFAULT '0' COMMENT '游戏低分',
  `restrict` bigint(20) DEFAULT '0' COMMENT '入场限制',
  `max_times` bigint(20) DEFAULT '0' COMMENT '最高倍率',
  `max_lines` bigint(20) DEFAULT '0' COMMENT '线数',
  `delete_status` bigint(20) DEFAULT '0' COMMENT '删除状态',
  `maintenance` bit(1) DEFAULT b'1' COMMENT '是否维护中默认:不是维护中',
  `limit_lower` bigint(20) DEFAULT '0' COMMENT '限制到这个低点剔出去',
  `limit_high` bigint(20) DEFAULT NULL COMMENT '限高',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_game_id_room_id_grade_id` (`game_id`,`room_id`,`grade_id`) USING BTREE COMMENT '一个房间同一款游戏场次不能重复'
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_info
-- ----------------------------
INSERT INTO `game_info` VALUES ('1', '1', '斗地主', '1', '1', '', '', 'ddz', '0', '2018-10-18 16:16:04', '2019-04-07 21:25:19', '0.03', '10000', '200000', '8', '0', '0', '', '10', '20000000');
INSERT INTO `game_info` VALUES ('2', '1', '斗地主', '1', '2', '', '', 'ddz', '0', '2018-10-18 16:16:04', '2019-03-18 14:49:10', '0.03', '100000', '2000000', '16', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('3', '1', '斗地主', '1', '3', '', '', 'ddz', '0', '2018-10-18 16:16:04', '2019-03-21 10:08:42', '0.03', '1000000', '10000000', '32', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('4', '2', '抢庄牛牛', '1', '1', '', '', 'qznn', '0', '2018-10-18 16:16:04', '2019-03-18 14:49:52', '0.05', '10000', '200000', '10', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('5', '2', '抢庄牛牛', '1', '2', '', '', 'qznn', '0', '2018-10-18 16:16:04', '2019-03-18 14:50:22', '0.05', '100000', '2000000', '100', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('6', '2', '抢庄牛牛', '1', '3', '', '', 'qznn', '0', '2018-10-18 16:16:04', '2019-03-21 10:09:00', '0.05', '1000000', '10000000', '10000', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('7', '3', '通比牛牛', '1', '1', '', '', 'tbnn', '0', '2018-10-18 16:16:04', '2019-03-18 15:37:15', '0.05', '10000', '200000', '10', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('8', '3', '通比牛牛', '1', '2', '', '', 'tbnn', '0', '2018-10-18 16:16:04', '2019-03-18 15:37:52', '0.05', '100000', '2000000', '100', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('9', '3', '通比牛牛', '1', '3', '', '', 'tbnn', '0', '2018-10-18 16:16:04', '2019-03-21 10:09:08', '0.05', '1000000', '10000000', '10000', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('25', '9', '龙虎斗', '3', '1', '', '', 'lhd', '0', '2019-03-04 10:57:03', '2019-03-18 15:38:32', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('26', '9', '龙虎斗', '3', '2', '', '', 'lhd', '0', '2019-03-04 10:57:51', '2019-03-21 09:31:07', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('27', '9', '龙虎斗', '3', '3', '', '', 'lhd', '0', '2019-03-04 10:58:34', '2019-03-21 10:09:19', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('28', '10', '百家乐', '3', '1', '', '', 'bjl', '0', '2019-03-04 10:57:03', '2019-03-21 09:31:26', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('29', '10', '百家乐', '3', '2', '', '', 'bjl', '0', '2019-03-04 10:57:51', '2019-03-21 09:30:32', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('30', '10', '百家乐', '3', '3', '', '', 'bjl', '0', '2019-03-04 10:58:34', '2019-03-21 10:09:28', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('85', '4', '百人牛牛', '3', '1', '', '', 'brnn', '0', '2019-02-28 09:37:42', '2019-03-21 09:32:21', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('86', '4', '百人牛牛', '3', '2', '', '', 'brnn', '0', '2019-02-28 09:40:21', '2019-02-28 09:59:48', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('87', '4', '百人牛牛', '3', '3', '', '', 'brnn', '0', '2019-02-28 09:43:00', '2019-02-28 09:59:41', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('88', '5', '百人牌九', '3', '1', '', '', 'brpj', '0', '2019-02-28 09:43:52', '2019-03-21 09:32:38', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('90', '5', '百人牌九', '3', '2', '', '', 'brpj', '0', '2019-02-28 09:46:34', '2019-03-21 09:32:55', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('91', '5', '百人牌九', '3', '3', '', '', 'brpj', '0', '2019-02-28 09:47:44', '2019-03-21 09:33:30', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('92', '6', '百人炸金花  ', '3', '1', '', '', 'brzjh', '0', '2019-02-28 09:48:37', '2019-03-21 09:33:45', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('93', '6', '百人炸金花', '3', '2', '', '', 'brzjh', '0', '2019-02-28 09:50:49', '2019-03-21 09:33:58', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('94', '6', '百人炸金花', '3', '3', '', '', 'brzjh', '0', '2019-02-28 09:51:39', '2019-03-21 09:34:11', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('95', '7', '十三水', '1', '1', '', '', 'sss', '0', '2019-02-28 09:54:25', '2019-03-21 09:34:39', '0.05', '10000', '200000', '10', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('96', '7', '十三水', '1', '2', '', '', 'sss', '0', '2019-02-28 09:55:09', '2019-03-21 09:34:53', '0.05', '100000', '2000000', '100', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('97', '7', '十三水', '1', '3', '', '', 'sss', '0', '2019-02-28 09:56:04', '2019-03-21 10:09:38', '0.05', '1000000', '10000000', '1000', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('98', '8', '炸金花', '1', '1', '', '', 'zjh', '0', '2019-02-28 09:57:08', '2019-03-21 09:35:28', '0.05', '10000', '200000', '10', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('99', '8', '炸金花', '1', '2', '', '', 'zjh', '0', '2019-02-28 09:58:02', '2019-03-21 09:35:45', '0.05', '100000', '2000000', '100', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('100', '8', '炸金花', '1', '3', '', '', 'zjh', '0', '2019-02-28 09:58:30', '2019-03-21 09:35:59', '0.05', '1000000', '10000000', '1000', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('101', '11', '二八杠', '1', '1', '', '', 'ebg', '0', '2019-03-12 15:37:01', '2019-03-18 16:03:32', '0.05', '10000', '200000', '10', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('102', '11', '二八杠', '1', '2', '', '', 'ebg', '0', '2019-03-12 15:38:05', '2019-03-18 16:03:46', '0.05', '100000', '2000000', '100', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('103', '11', '二八杠', '1', '3', '', '', 'ebg', '0', '2019-03-12 15:38:51', '2019-03-21 10:09:47', '0.05', '1000000', '10000000', '1000', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('104', '12', '百人三公', '3', '1', '', '', 'brsg', '0', '2019-03-12 15:41:52', '2019-03-21 09:36:28', '0.00', '100', '200000', '0', '0', '0', '', '0', '20000000');
INSERT INTO `game_info` VALUES ('105', '12', '百人三公', '3', '2', '', '', 'brsg', '0', '2019-03-12 15:42:27', '2019-03-21 09:36:44', '0.00', '100', '2000000', '0', '0', '0', '', '0', '50000000');
INSERT INTO `game_info` VALUES ('106', '12', '百人三公', '3', '3', '', '', 'brsg', '0', '2019-03-12 15:43:04', '2019-03-21 09:36:59', '0.00', '100', '10000000', '0', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('107', '13', '房卡牛牛', '2', '1', '', '', 'fknn', '0', '2019-03-21 16:09:42', '2019-03-21 16:09:45', '0.03', '10000', '200000', '10', '0', '0', '', '0', '9223372036854775807');
INSERT INTO `game_info` VALUES ('110', '201', '海底宝藏', '4', '1', '', '', 'hdbz', '0', '2019-03-21 16:11:47', '2019-03-21 16:11:47', '0.00', '100', '1000', '10', '9', '0', '', '0', '1000000');
INSERT INTO `game_info` VALUES ('111', '201', '海底宝藏', '4', '2', '', '', 'hdbz', '0', '2019-03-21 16:11:47', '2019-03-21 16:11:47', '0.00', '10000', '100000', '10', '9', '0', '', '0', '100000000');
INSERT INTO `game_info` VALUES ('112', '201', '海底宝藏', '4', '3', '', '', 'hdbz', '0', '2019-03-21 16:11:47', '2019-03-21 16:11:47', '0.00', '100000', '1000000', '10', '9', '0', '', '0', '100000000000');
INSERT INTO `game_info` VALUES ('113', '14', '房卡十三水', '2', '1', '', '', 'fksss', '0', '2019-03-28 09:47:50', '2019-03-28 09:47:50', '0.10', '100', '1000', '10', '0', '0', '', '0', '10000000');
INSERT INTO `game_info` VALUES ('114', '14', '房卡十三水', '2', '2', '', '', 'fksss', '0', '2019-03-28 09:48:52', '2019-03-28 09:48:52', '0.10', '10000', '10000000', '100', '0', '0', '', '0', '1000000000');
INSERT INTO `game_info` VALUES ('115', '14', '房卡十三水', '2', '3', '', '', 'fksss', '0', '2019-03-28 09:49:42', '2019-03-28 09:49:42', '0.10', '100000', '100000000', '10000', '0', '0', '', '0', '10000000000000');

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '大厅名称',
  `room_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '房间id数组',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES ('1', 'APP大厅', '1,2,3,4,5', '0', '0', null, '2019-02-18 20:12:23');
INSERT INTO `hall` VALUES ('2', 'H5大厅', '1,2,3', '0', '0', null, '2019-02-18 20:14:16');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '房间名称',
  `enable` bit(1) DEFAULT b'1' COMMENT '是否可用',
  `display` bit(1) DEFAULT b'1' COMMENT '是否显示',
  `game_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '游戏id数组',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `delete_status` int(11) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('1', '匹配房间', '', '', '1,2,3,4,5,6,7,8,9,95,96,97,98,99,100', '0', '0', '2019-02-15 19:37:51', '2019-02-19 16:31:47');
INSERT INTO `room` VALUES ('2', '房卡房间', '', '', '107,113,114,115', '0', '0', '2019-02-16 10:49:04', '2019-02-18 19:27:10');
INSERT INTO `room` VALUES ('3', '百人房间', '', '', '25,26,27,28,29,30,87,86,85,88,90,91,92,93,94', '0', '0', '2019-02-18 14:25:02', '2019-02-18 19:27:32');
INSERT INTO `room` VALUES ('4', '拉霸房间', '', '', '113,114,115', '0', '0', '2019-02-21 10:49:38', '2019-02-21 10:49:38');
INSERT INTO `room` VALUES ('5', '捕鱼房间', '', '', '', '0', '0', '2019-02-21 10:51:34', '2019-02-21 10:51:34');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `parent_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级',
  `enable` bit(1) DEFAULT b'1' COMMENT '是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uk` (`code`,`parent_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='用户动态码';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('48', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', '001', '银行名称', '0', '');
INSERT INTO `sys_dictionary` VALUES ('49', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b001', '招商银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('50', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b002', '工商银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('51', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b003', '建设银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('52', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b004', '交通银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('53', '0', '2019-01-10 11:34:47', '2019-01-18 15:11:35', '0', '002', '用户类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('54', '0', '2019-01-10 11:36:11', '2019-01-18 15:11:35', '0', 'TRIAL', '试玩账号', '002', '');
INSERT INTO `sys_dictionary` VALUES ('55', '0', '2019-01-10 11:36:14', '2019-01-18 15:11:35', '0', 'USER', '普通会员', '002', '');
INSERT INTO `sys_dictionary` VALUES ('56', '0', '2019-01-10 11:36:18', '2019-01-18 15:11:35', '0', 'VIP', 'VIP', '002', '');
INSERT INTO `sys_dictionary` VALUES ('57', '0', '2019-01-16 15:41:58', '2019-01-18 15:11:35', '0', '003', '交易类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('58', '0', '2019-01-18 15:11:35', '2019-01-18 15:11:35', '0', '0', '存款', '003', '');
INSERT INTO `sys_dictionary` VALUES ('59', '0', '2019-01-18 15:11:46', '2019-01-18 15:11:35', '0', '1', '取款', '003', '');
INSERT INTO `sys_dictionary` VALUES ('60', '0', '2019-01-18 15:11:57', '2019-01-18 15:11:35', '0', '2', '冲销', '003', '');
INSERT INTO `sys_dictionary` VALUES ('61', '0', '2019-01-18 15:12:19', '2019-01-18 15:11:35', '0', '3', '返利', '003', '');
INSERT INTO `sys_dictionary` VALUES ('62', '0', '2019-01-18 15:12:49', '2019-01-18 15:11:35', '0', '004', '交易具体类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('63', '0', '2019-01-18 15:13:12', '2019-01-18 15:11:35', '0', '005', '订单状态', '0', '');
INSERT INTO `sys_dictionary` VALUES ('64', '0', '2019-01-18 15:13:33', '2019-01-18 15:11:35', '0', '006', '充值类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('65', '0', '2019-01-18 15:14:35', '2019-01-18 15:11:35', '0', 'BANK', '银行卡充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('66', '0', '2019-01-18 15:14:55', '2019-01-18 15:11:35', '0', 'THIRD', '第三方充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('67', '0', '2019-01-18 15:15:17', '2019-01-18 15:11:35', '0', 'ADMIN', '人工充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('68', '0', '2019-01-18 16:43:50', '2019-01-18 15:11:35', '0', '0', '未确认', '005', '');
INSERT INTO `sys_dictionary` VALUES ('69', '0', '2019-01-18 16:45:15', '2019-01-18 15:11:35', '0', '1', '审核失败', '005', '');
INSERT INTO `sys_dictionary` VALUES ('70', '0', '2019-01-18 16:45:25', '2019-01-18 15:11:35', '0', '2', '订单完成', '005', '');
INSERT INTO `sys_dictionary` VALUES ('71', '0', '2019-01-18 16:46:35', '2019-01-18 15:11:35', '0', '11', '银行充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('72', '0', '2019-01-18 16:46:49', '2019-01-18 15:11:35', '0', '12', '支付宝充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('73', '0', '2019-01-18 16:46:59', '2019-01-18 15:11:35', '0', '13', '微信充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('74', '0', '2019-01-18 16:47:10', '2019-01-18 15:11:35', '0', '14', '人工充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('75', '0', '2019-01-18 16:47:21', '2019-01-18 15:11:35', '0', '15', '佣金取款', '004', '');
INSERT INTO `sys_dictionary` VALUES ('76', '0', '2019-01-18 16:47:30', '2019-01-18 15:11:35', '0', '16', '账户取款', '004', '');
INSERT INTO `sys_dictionary` VALUES ('77', '0', '2019-01-18 16:47:39', '2019-01-18 15:11:35', '0', '17', '额度转换', '004', '');
INSERT INTO `sys_dictionary` VALUES ('78', '0', '2019-01-18 16:47:49', '2019-01-18 15:11:35', '0', '18', '签到返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('79', '0', '2019-01-18 16:47:58', '2019-01-18 15:11:35', '0', '19', '推荐返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('80', '1', '2019-01-25 17:27:36', '2019-01-18 15:11:35', '0', '007', '头部菜单类型', '0', '');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=522 DEFAULT CHARSET=utf8 COMMENT='系统日志';


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'sys/user', null, '1', 'admin', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'sys/role', null, '1', 'role', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('5', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('6', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('318', '0', '系统设置', null, null, '0', 'shezhi', '5');
INSERT INTO `sys_menu` VALUES ('319', '318', 'OTP管理', 'userotp/userotp', null, '1', 'config', '0');
INSERT INTO `sys_menu` VALUES ('320', '319', '新增', null, 'userotp:userotp:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('321', '319', '修改', null, 'userotp:userotp:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('322', '319', '删除', null, 'userotp:userotp:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('323', '319', '查询', null, 'userotp:userotp:list,userotp:userotp:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('324', '318', '网址绑定', 'domainmanagement/domainmanagement', null, '1', 'config', '0');
INSERT INTO `sys_menu` VALUES ('325', '324', '新增', null, 'domainmanagement:domainmanagement:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('326', '324', '修改', null, 'domainmanagement:domainmanagement:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('327', '324', '删除', null, 'domainmanagement:domainmanagement:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('328', '324', '查询', null, 'domainmanagement:domainmanagement:list,domainmanagement:domainmanagement:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('489', '0', '游戏设置', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('490', '489', '游戏大厅', 'hall/hall', null, '1', 'remen', '1');
INSERT INTO `sys_menu` VALUES ('491', '489', '游戏信息', 'info/info', null, '1', 'zonghe', '3');
INSERT INTO `sys_menu` VALUES ('494', '489', '游戏房间', 'room/room', null, '1', 'shouye', '2');
INSERT INTO `sys_menu` VALUES ('495', '490', '新增', null, 'hall:hall:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('496', '490', '修改', null, 'hall:hall:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('497', '490', '查询', null, 'hall:hall:list,hall:hall:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('498', '490', '删除', null, 'hall:hall:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('499', '491', '查询', null, 'info:info:list,info:info:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('500', '494', '新增', null, 'room:room:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('501', '494', '查询', null, 'room:room:list,room:room:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('502', '494', '删除', null, 'room:room:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('506', '494', '修改', null, 'room:room:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('508', '490', '下拉列表', null, 'gameinfo:gameinfo:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('509', '491', '新增', null, 'info:info:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('510', '491', '修改', null, 'info:info:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('511', '491', '删除', null, 'info:info:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('512', '489', '游戏场次', 'gamegrade/gamegrade', null, '1', 'tixing', '4');
INSERT INTO `sys_menu` VALUES ('513', '512', '新增', null, 'gamegrade:gamegrade:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('514', '512', '修改', null, 'gamegrade:gamegrade:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('515', '512', '查询', null, 'gamegrade:gamegrade:list,gamegrade:gamegrade:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('516', '512', '删除', null, 'gamegrade:gamegrade:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('520', '489', '游戏概率', 'gameconfig/gameconfig', null, '1', 'xiangqufill', '0');
INSERT INTO `sys_menu` VALUES ('521', '520', '查询', null, 'gameconfig:gameconfig:list,gameconfig:gameconfig:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('522', '520', '新增', null, 'gameconfig:gameconfig:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('523', '520', '修改', null, 'gameconfig:gameconfig:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('524', '520', '删除', null, 'gameconfig:gameconfig:delete', '2', null, '0');

-- ----------------------------
-- Table structure for sys_message_management
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_management`;
CREATE TABLE `sys_message_management` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `content_type` int(2) DEFAULT '1' COMMENT '类型(1：会员站内信 2:管理员站内信）',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `target_object` int(1) DEFAULT '3' COMMENT '目标对象（1：指定用户id 2:指定用户层次(角色) 3：所有用户）',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `hierarchy_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '层级ids',
  `user_account` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '用户账号集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='站内信';


-- ----------------------------
-- Table structure for sys_message_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_user`;
CREATE TABLE `sys_message_user` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `message_id` bigint(22) DEFAULT NULL COMMENT '消息id',
  `user_id` bigint(22) NOT NULL COMMENT '用户id',
  `user_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `status` int(2) NOT NULL COMMENT '状态（0：未读 1：已读）',
  `delete_message` bit(1) DEFAULT b'0' COMMENT '站内信删除状态（0：未删 1：已删）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='站内信用户关系表';


-- ----------------------------
-- Table structure for sys_notice_management
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_management`;
CREATE TABLE `sys_notice_management` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `target_object` int(2) DEFAULT '3' COMMENT '目标对象（1：指定用户id 2:指定用户层次(角色) 3：所有用户）',
  `hierarchy_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '层级id集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='公告管理';


-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_register_necessary
-- ----------------------------
DROP TABLE IF EXISTS `sys_register_necessary`;
CREATE TABLE `sys_register_necessary` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '显示名称',
  `show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `necessary` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否必填',
  `hints` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提示语',
  `order_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '序号',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注',
  `validation_rule` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '验证规则',
  `validation_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '验证描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='注册必填控制表';

-- ----------------------------
-- Records of sys_register_necessary
-- ----------------------------
INSERT INTO `sys_register_necessary` VALUES ('1', '2019-01-24 14:57:23', '2019-01-25 11:31:20', '0', '0', '身份证', '', '', '取款需要身份证验证', '1', 'userIdentity', '^[0-9]*$', '身份证错误！');
INSERT INTO `sys_register_necessary` VALUES ('2', '2019-01-24 14:57:23', '2019-01-25 11:31:29', '0', '0', '电话', '', '', '取款需要电话验证', '2', 'userPhone', '^[0-9]*$', '电话错误！');
INSERT INTO `sys_register_necessary` VALUES ('3', '2019-01-24 14:57:23', '2019-01-25 11:31:35', '0', '0', '邮箱', '', '', '取款需要邮箱验证', '3', 'userEmail', '^[0-9]*$', '游戏错误');
INSERT INTO `sys_register_necessary` VALUES ('4', '2019-01-24 14:57:23', '2019-01-24 17:35:33', '0', '0', '邮编', '', '', '取款需要邮编验证', '4', 'postCode', '/^0$|^[1-9]\\d*$/', '邮编错误！');
INSERT INTO `sys_register_necessary` VALUES ('5', '2019-01-24 14:57:23', '2019-01-24 17:34:12', '0', '0', '地址', '', '\0', '取款需要地址验证', '5', 'userAddress', '/^0$|^[1-9]\\d*$/', '地址错误！');
INSERT INTO `sys_register_necessary` VALUES ('6', '2019-01-24 14:57:23', '2019-01-24 17:34:23', '0', '0', 'QQ', '\0', '\0', '取款需要QQ验证', '6', 'userQq', '/^0$|^[1-9]\\d*$/', 'QQ显示错误');
INSERT INTO `sys_register_necessary` VALUES ('7', '2019-01-24 14:57:23', '2019-01-24 17:33:30', '0', '0', '生日', '\0', '\0', '取款需要生日验证', '7', 'userBirthday', '/^0$|^[1-9]\\d*$/', '生日错误');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员', '1', '2018-09-16 11:02:46');
INSERT INTO `sys_role` VALUES ('2', '财务管理员', '财务人员使用', '1', '2018-09-16 11:24:06');
INSERT INTO `sys_role` VALUES ('3', '代理商', '代理商', '1', '2018-09-17 10:30:08');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7240 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('49', '2', '44');
INSERT INTO `sys_role_menu` VALUES ('50', '3', '44');
INSERT INTO `sys_role_menu` VALUES ('51', '3', '68');
INSERT INTO `sys_role_menu` VALUES ('52', '3', '69');
INSERT INTO `sys_role_menu` VALUES ('53', '3', '71');
INSERT INTO `sys_role_menu` VALUES ('54', '3', '72');
INSERT INTO `sys_role_menu` VALUES ('55', '3', '-666666');
INSERT INTO `sys_role_menu` VALUES ('7078', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('7079', '4', '2');
INSERT INTO `sys_role_menu` VALUES ('7080', '4', '5');
INSERT INTO `sys_role_menu` VALUES ('7081', '4', '6');
INSERT INTO `sys_role_menu` VALUES ('7082', '4', '7');
INSERT INTO `sys_role_menu` VALUES ('7083', '4', '8');
INSERT INTO `sys_role_menu` VALUES ('7084', '4', '3');
INSERT INTO `sys_role_menu` VALUES ('7085', '4', '9');
INSERT INTO `sys_role_menu` VALUES ('7086', '4', '10');
INSERT INTO `sys_role_menu` VALUES ('7087', '4', '11');
INSERT INTO `sys_role_menu` VALUES ('7088', '4', '12');
INSERT INTO `sys_role_menu` VALUES ('7089', '4', '4');
INSERT INTO `sys_role_menu` VALUES ('7090', '4', '13');
INSERT INTO `sys_role_menu` VALUES ('7091', '4', '14');
INSERT INTO `sys_role_menu` VALUES ('7092', '4', '15');
INSERT INTO `sys_role_menu` VALUES ('7093', '4', '16');
INSERT INTO `sys_role_menu` VALUES ('7094', '4', '-666666');
INSERT INTO `sys_role_menu` VALUES ('7095', '13', '1');
INSERT INTO `sys_role_menu` VALUES ('7096', '13', '2');
INSERT INTO `sys_role_menu` VALUES ('7097', '13', '5');
INSERT INTO `sys_role_menu` VALUES ('7098', '13', '6');
INSERT INTO `sys_role_menu` VALUES ('7099', '13', '7');
INSERT INTO `sys_role_menu` VALUES ('7100', '13', '8');
INSERT INTO `sys_role_menu` VALUES ('7101', '13', '3');
INSERT INTO `sys_role_menu` VALUES ('7102', '13', '9');
INSERT INTO `sys_role_menu` VALUES ('7103', '13', '10');
INSERT INTO `sys_role_menu` VALUES ('7104', '13', '11');
INSERT INTO `sys_role_menu` VALUES ('7105', '13', '12');
INSERT INTO `sys_role_menu` VALUES ('7106', '13', '4');
INSERT INTO `sys_role_menu` VALUES ('7107', '13', '13');
INSERT INTO `sys_role_menu` VALUES ('7108', '13', '14');
INSERT INTO `sys_role_menu` VALUES ('7109', '13', '15');
INSERT INTO `sys_role_menu` VALUES ('7110', '13', '16');
INSERT INTO `sys_role_menu` VALUES ('7111', '13', '-666666');
INSERT INTO `sys_role_menu` VALUES ('7151', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('7152', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('7153', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('7154', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('7155', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('7156', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('7157', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('7158', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('7159', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('7160', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('7161', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('7162', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('7163', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('7164', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('7165', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('7166', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('7167', '1', '489');
INSERT INTO `sys_role_menu` VALUES ('7168', '1', '490');
INSERT INTO `sys_role_menu` VALUES ('7169', '1', '495');
INSERT INTO `sys_role_menu` VALUES ('7170', '1', '496');
INSERT INTO `sys_role_menu` VALUES ('7171', '1', '497');
INSERT INTO `sys_role_menu` VALUES ('7172', '1', '498');
INSERT INTO `sys_role_menu` VALUES ('7173', '1', '508');
INSERT INTO `sys_role_menu` VALUES ('7174', '1', '491');
INSERT INTO `sys_role_menu` VALUES ('7175', '1', '499');
INSERT INTO `sys_role_menu` VALUES ('7176', '1', '509');
INSERT INTO `sys_role_menu` VALUES ('7177', '1', '510');
INSERT INTO `sys_role_menu` VALUES ('7178', '1', '511');
INSERT INTO `sys_role_menu` VALUES ('7179', '1', '494');
INSERT INTO `sys_role_menu` VALUES ('7180', '1', '500');
INSERT INTO `sys_role_menu` VALUES ('7181', '1', '501');
INSERT INTO `sys_role_menu` VALUES ('7182', '1', '502');
INSERT INTO `sys_role_menu` VALUES ('7183', '1', '506');
INSERT INTO `sys_role_menu` VALUES ('7184', '1', '512');
INSERT INTO `sys_role_menu` VALUES ('7185', '1', '513');
INSERT INTO `sys_role_menu` VALUES ('7186', '1', '514');
INSERT INTO `sys_role_menu` VALUES ('7187', '1', '515');
INSERT INTO `sys_role_menu` VALUES ('7188', '1', '516');
INSERT INTO `sys_role_menu` VALUES ('7189', '1', '-666666');
INSERT INTO `sys_role_menu` VALUES ('7190', '15', '1');
INSERT INTO `sys_role_menu` VALUES ('7191', '15', '2');
INSERT INTO `sys_role_menu` VALUES ('7192', '15', '5');
INSERT INTO `sys_role_menu` VALUES ('7193', '15', '6');
INSERT INTO `sys_role_menu` VALUES ('7194', '15', '7');
INSERT INTO `sys_role_menu` VALUES ('7195', '15', '8');
INSERT INTO `sys_role_menu` VALUES ('7196', '15', '3');
INSERT INTO `sys_role_menu` VALUES ('7197', '15', '9');
INSERT INTO `sys_role_menu` VALUES ('7198', '15', '10');
INSERT INTO `sys_role_menu` VALUES ('7199', '15', '11');
INSERT INTO `sys_role_menu` VALUES ('7200', '15', '12');
INSERT INTO `sys_role_menu` VALUES ('7201', '15', '4');
INSERT INTO `sys_role_menu` VALUES ('7202', '15', '13');
INSERT INTO `sys_role_menu` VALUES ('7203', '15', '14');
INSERT INTO `sys_role_menu` VALUES ('7204', '15', '15');
INSERT INTO `sys_role_menu` VALUES ('7205', '15', '16');
INSERT INTO `sys_role_menu` VALUES ('7206', '15', '318');
INSERT INTO `sys_role_menu` VALUES ('7207', '15', '319');
INSERT INTO `sys_role_menu` VALUES ('7208', '15', '320');
INSERT INTO `sys_role_menu` VALUES ('7209', '15', '321');
INSERT INTO `sys_role_menu` VALUES ('7210', '15', '322');
INSERT INTO `sys_role_menu` VALUES ('7211', '15', '323');
INSERT INTO `sys_role_menu` VALUES ('7212', '15', '324');
INSERT INTO `sys_role_menu` VALUES ('7213', '15', '325');
INSERT INTO `sys_role_menu` VALUES ('7214', '15', '326');
INSERT INTO `sys_role_menu` VALUES ('7215', '15', '327');
INSERT INTO `sys_role_menu` VALUES ('7216', '15', '328');
INSERT INTO `sys_role_menu` VALUES ('7217', '15', '489');
INSERT INTO `sys_role_menu` VALUES ('7218', '15', '490');
INSERT INTO `sys_role_menu` VALUES ('7219', '15', '495');
INSERT INTO `sys_role_menu` VALUES ('7220', '15', '496');
INSERT INTO `sys_role_menu` VALUES ('7221', '15', '497');
INSERT INTO `sys_role_menu` VALUES ('7222', '15', '498');
INSERT INTO `sys_role_menu` VALUES ('7223', '15', '508');
INSERT INTO `sys_role_menu` VALUES ('7224', '15', '491');
INSERT INTO `sys_role_menu` VALUES ('7225', '15', '499');
INSERT INTO `sys_role_menu` VALUES ('7226', '15', '509');
INSERT INTO `sys_role_menu` VALUES ('7227', '15', '510');
INSERT INTO `sys_role_menu` VALUES ('7228', '15', '511');
INSERT INTO `sys_role_menu` VALUES ('7229', '15', '494');
INSERT INTO `sys_role_menu` VALUES ('7230', '15', '500');
INSERT INTO `sys_role_menu` VALUES ('7231', '15', '501');
INSERT INTO `sys_role_menu` VALUES ('7232', '15', '502');
INSERT INTO `sys_role_menu` VALUES ('7233', '15', '506');
INSERT INTO `sys_role_menu` VALUES ('7234', '15', '512');
INSERT INTO `sys_role_menu` VALUES ('7235', '15', '513');
INSERT INTO `sys_role_menu` VALUES ('7236', '15', '514');
INSERT INTO `sys_role_menu` VALUES ('7237', '15', '515');
INSERT INTO `sys_role_menu` VALUES ('7238', '15', '516');
INSERT INTO `sys_role_menu` VALUES ('7239', '15', '-666666');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ids` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色id（逗号分隔）',
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '代理商秘钥',
  `agency_rate` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  PRIMARY KEY (`user_id`,`delete_status`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@test.com', '13612345678', '1', '1', '2016-11-11 11:11:11', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('28', 'root', '07bcc24d01b1e9e9b86bb22e6d6fa77cf07425313285b6fc665abaca0a76b4c3', 'DmHtgapErql0DZ7hUgyP', 'root@qq.com', '12345545432', '1', '1', '2018-12-27 14:24:23', '13,4', null, null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('36', '1', '1');
INSERT INTO `sys_user_role` VALUES ('39', '28', '13');
INSERT INTO `sys_user_role` VALUES ('40', '28', '4');
INSERT INTO `sys_user_role` VALUES ('47', '30', '1');
INSERT INTO `sys_user_role` VALUES ('48', '30', '4');
INSERT INTO `sys_user_role` VALUES ('49', '30', '13');
INSERT INTO `sys_user_role` VALUES ('58', '29', '1');
INSERT INTO `sys_user_role` VALUES ('59', '31', '1');
INSERT INTO `sys_user_role` VALUES ('60', '32', '1');
INSERT INTO `sys_user_role` VALUES ('61', '32', '13');
INSERT INTO `sys_user_role` VALUES ('62', '32', '4');
INSERT INTO `sys_user_role` VALUES ('63', '34', '1');
INSERT INTO `sys_user_role` VALUES ('64', '34', '2');
INSERT INTO `sys_user_role` VALUES ('65', '34', '4');
INSERT INTO `sys_user_role` VALUES ('66', '34', '13');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '7fe311d012ca97498ad4aa1ac100fd2a', '2019-04-05 04:44:13', '2019-04-04 16:44:13');
INSERT INTO `sys_user_token` VALUES ('28', '6c337985cc1f63002f091d83c7ff1908', '2019-01-22 02:27:26', '2019-01-21 14:27:26');
INSERT INTO `sys_user_token` VALUES ('29', 'd351d06eb9646d04ad200a6a68c0f0a2', '2019-01-24 21:33:41', '2019-01-24 09:33:41');
INSERT INTO `sys_user_token` VALUES ('30', 'bc4681daafd40a623b2d63f7c3425982', '2019-01-31 21:31:30', '2019-01-31 09:31:30');
INSERT INTO `sys_user_token` VALUES ('31', '6fb06289a63b21994184d219448c0b03', '2019-01-29 21:29:23', '2019-01-29 09:29:23');
INSERT INTO `sys_user_token` VALUES ('32', '6dbc4a8f85f158aba6ad78efea3304e8', '2019-01-30 23:16:48', '2019-01-30 11:16:48');
INSERT INTO `sys_user_token` VALUES ('34', '5ed1c9baa96838c2642a3b79a1f95e01', '2019-01-30 22:34:17', '2019-01-30 10:34:17');
