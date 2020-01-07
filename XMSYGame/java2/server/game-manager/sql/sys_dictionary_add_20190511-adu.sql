/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : game_manager

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-11 17:58:12
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', '0', '2019-05-11 16:26:55', '2019-05-11 16:26:55', '0', 'gameconfig', '游戏配置', '0', '');
INSERT INTO `sys_dictionary` VALUES ('2', '0', '2019-05-11 16:36:14', '2019-05-11 16:36:23', '0', 'initialStock', '初始库存', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('3', '0', '2019-05-11 16:36:41', '2019-05-11 16:36:41', '0', 'currentStock', '当前库存', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('4', '0', '2019-05-11 16:36:53', '2019-05-11 16:36:53', '0', 'goalStock', '目标库存', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('5', '0', '2019-05-11 16:37:16', '2019-05-11 16:37:16', '0', 'intervalGameRate', '库存区间游戏概率', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('6', '0', '2019-05-11 16:37:31', '2019-05-11 16:37:31', '0', 'maxRobot', '最多机器人数量', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('7', '0', '2019-05-11 16:37:42', '2019-05-11 16:37:42', '0', 'miniRate', '小游戏胜率', 'gameconfig', '');
INSERT INTO `sys_dictionary` VALUES ('8', '0', '2019-05-11 16:37:54', '2019-05-11 16:37:54', '0', 'robotWait', '机器人等待时间', 'gameconfig', '');
