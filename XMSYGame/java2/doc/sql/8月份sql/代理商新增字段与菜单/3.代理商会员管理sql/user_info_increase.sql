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

 Date: 09/08/2019 15:14:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info_increase
-- ----------------------------
DROP TABLE IF EXISTS `user_info_increase`;
CREATE TABLE `user_info_increase`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '会员id',
  `proxy_id` bigint(22) NOT NULL COMMENT '代理商id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员信息补充表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
