/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-17 17:20:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sign_reward_continuous
-- ----------------------------
DROP TABLE IF EXISTS `sign_reward_continuous`;
CREATE TABLE `sign_reward_continuous` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `day_num` int(10) NOT NULL DEFAULT '0' COMMENT '连续登陆天数',
  `reward` bigint(22) NOT NULL DEFAULT '0' COMMENT '奖励',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4066 DEFAULT CHARSET=utf8 COMMENT='签到连续奖励';

-- ----------------------------
-- Records of sign_reward_continuous
-- ----------------------------
INSERT INTO `sign_reward_continuous` VALUES ('4062', '0', '2019-05-13 16:10:57', '2019-05-14 11:45:06', '0', '3', '10000');
INSERT INTO `sign_reward_continuous` VALUES ('4063', '0', '2019-05-13 16:11:11', '2019-05-14 11:45:12', '0', '5', '20000');
INSERT INTO `sign_reward_continuous` VALUES ('4064', '0', '2019-05-13 16:11:22', '2019-05-14 11:45:19', '0', '8', '50000');
INSERT INTO `sign_reward_continuous` VALUES ('4065', '0', '2019-05-13 16:11:39', '2019-05-14 11:45:25', '0', '10', '200000');

-- ----------------------------
-- Table structure for sign_reward_continuous_every_day
-- ----------------------------
DROP TABLE IF EXISTS `sign_reward_continuous_every_day`;
CREATE TABLE `sign_reward_continuous_every_day` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `day_name` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '签到名称',
  `reward` bigint(22) NOT NULL DEFAULT '0' COMMENT '奖励',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4069 DEFAULT CHARSET=utf8 COMMENT='每天签到奖励';

-- ----------------------------
-- Records of sign_reward_continuous_every_day
-- ----------------------------
INSERT INTO `sign_reward_continuous_every_day` VALUES ('1', '0', '2019-05-13 16:12:17', '2019-05-14 11:47:32', '0', '周一', '10000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('2', '0', '2019-05-13 16:12:27', '2019-05-14 11:47:36', '0', '周二', '30000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('3', '0', '2019-05-13 16:12:37', '2019-05-14 11:47:40', '0', '周三', '50000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('4', '0', '2019-05-13 16:12:48', '2019-05-14 11:47:45', '0', '周四', '60000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('5', '0', '2019-05-13 16:13:02', '2019-05-14 11:47:49', '0', '周五', '80000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('6', '0', '2019-05-13 16:13:22', '2019-05-14 11:47:54', '0', '周六', '100000');
INSERT INTO `sign_reward_continuous_every_day` VALUES ('7', '0', '2019-05-13 16:13:32', '2019-05-14 11:48:00', '0', '周日', '200000');

-- ----------------------------
-- Table structure for sign_user_record
-- ----------------------------
DROP TABLE IF EXISTS `sign_user_record`;
CREATE TABLE `sign_user_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `day_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '签到奖励ID',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `reward` bigint(22) NOT NULL DEFAULT '0' COMMENT '奖励',
  `continuous_num` int(11) NOT NULL COMMENT '连续签到天数',
  `sign_date` date DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_record` (`sign_date`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4076 DEFAULT CHARSET=utf8 COMMENT='用户签到记录表';

-- ----------------------------
-- Records of sign_user_record
-- ----------------------------



INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('810', '297', '连续签到奖励', 'signrewardcontinuous/signrewardcontinuous', NULL, '1', 'bianji', '6');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('811', '810', '新增', NULL, 'signrewardcontinuous:signrewardcontinuous:list,signrewardcontinuous:signrewardcontinuous:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('812', '810', '新增', NULL, 'signrewardcontinuous:signrewardcontinuous:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('813', '810', '修改', NULL, 'signrewardcontinuous:signrewardcontinuous:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('814', '810', '删除', NULL, 'signrewardcontinuous:signrewardcontinuous:delete', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('815', '297', '日常签到奖励', 'signrewardcontinuouseveryday/signrewardcontinuouseveryday', NULL, '1', 'config', '7');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('816', '815', '查看', NULL, 'signrewardcontinuouseveryday:signrewardcontinuouseveryday:list,signrewardcontinuouseveryday:signrewardcontinuouseveryday:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('817', '815', '新增', NULL, 'signrewardcontinuouseveryday:signrewardcontinuouseveryday:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('818', '815', '修改', NULL, 'signrewardcontinuouseveryday:signrewardcontinuouseveryday:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('819', '815', '删除', NULL, 'signrewardcontinuouseveryday:signrewardcontinuouseveryday:delete', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('820', '297', '签到记录', 'signuserrecord/signuserrecord', NULL, '1', 'daohang', '8');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('821', '820', '查看', NULL, 'signuserrecord:signuserrecord:list,signuserrecord:signuserrecord:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('822', '820', '保存', NULL, 'signuserrecord:signuserrecord:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('823', '820', '修改', NULL, 'signuserrecord:signuserrecord:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('824', '820', '删除', NULL, 'signuserrecord:signuserrecord:delete', '2', NULL, '0');


ALTER TABLE `user_hierarchy`
ADD COLUMN `hierarchy_type`  int(2) NOT NULL DEFAULT 0 COMMENT '层级类型0：正常  1：风控层级' AFTER `game_rate`;

INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('821', '18', '重置密码错误次数', NULL, 'user:user:deletePassWordNum', '2', NULL, '0');



