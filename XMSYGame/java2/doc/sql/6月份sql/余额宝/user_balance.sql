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

 Date: 23/06/2019 16:54:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_balance
-- ----------------------------
DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `user_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `money` bigint(22) NOT NULL DEFAULT 0 COMMENT '金额',
  `count_money` bigint(22) NULL DEFAULT 0 COMMENT '需要计算的钱',
  `unprofit_money` bigint(22) NULL DEFAULT 0 COMMENT '未计算收益的金额',
  `profit` bigint(22) NULL DEFAULT 0 COMMENT '总收益',
  `profit_yesterday` bigint(22) NULL DEFAULT 0 COMMENT '昨日收益',
  `count_day` datetime(0) NULL DEFAULT NULL COMMENT '计算时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户余额宝金额表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
