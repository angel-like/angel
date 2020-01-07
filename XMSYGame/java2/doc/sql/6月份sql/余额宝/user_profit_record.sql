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

 Date: 23/06/2019 16:54:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_profit_record
-- ----------------------------
DROP TABLE IF EXISTS `user_profit_record`;
CREATE TABLE `user_profit_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `money` decimal(22, 2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `rate` decimal(20, 8) NULL DEFAULT 0.00000000 COMMENT '利率',
  `profit` decimal(22, 2) NULL DEFAULT 0.00 COMMENT '收益',
  `income_date` date NULL DEFAULT NULL COMMENT '收益日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `income_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账户金额收益记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
