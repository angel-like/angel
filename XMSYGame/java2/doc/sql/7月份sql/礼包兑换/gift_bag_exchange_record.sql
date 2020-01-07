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

 Date: 19/07/2019 16:21:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gift_bag_exchange_record
-- ----------------------------
DROP TABLE IF EXISTS `gift_bag_exchange_record`;
CREATE TABLE `gift_bag_exchange_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '兑换用户id',
  `user_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账户',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名称',
  `exchange_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兑换码',
  `exchange_time` datetime(0) NOT NULL COMMENT '兑换时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '礼包兑换记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
