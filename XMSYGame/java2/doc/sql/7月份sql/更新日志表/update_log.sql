/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.0.109
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 10.0.0.109:3306
 Source Schema         : webhome

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 05/08/2019 09:44:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for update_log
-- ----------------------------
DROP TABLE IF EXISTS `update_log`;
CREATE TABLE `update_log`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目名',
  `type` int(10) NOT NULL COMMENT '类型(0:后台 1:前台 2：游戏管理服）',
  `expect_update_time` datetime(0) NULL DEFAULT NULL COMMENT '预计更新时间',
  `update_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否更新(0:否，1：是)',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '更新日志' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
