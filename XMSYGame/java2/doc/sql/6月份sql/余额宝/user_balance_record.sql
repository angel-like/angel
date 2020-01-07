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

 Date: 23/06/2019 16:54:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_balance_record
-- ----------------------------
DROP TABLE IF EXISTS `user_balance_record`;
CREATE TABLE `user_balance_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `money` bigint(22) NOT NULL DEFAULT 0 COMMENT '交易金额',
  `take_money` bigint(22) NULL DEFAULT 0 COMMENT '取出金额',
  `effect` bit(1) NULL DEFAULT b'0' COMMENT '是否生效',
  `type` int(1) NULL DEFAULT 0 COMMENT '类型 0:存入1:取出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 229 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账户金额存取记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
