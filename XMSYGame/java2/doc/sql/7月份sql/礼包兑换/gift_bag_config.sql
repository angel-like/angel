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

 Date: 19/07/2019 16:21:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gift_bag_config
-- ----------------------------
DROP TABLE IF EXISTS `gift_bag_config`;
CREATE TABLE `gift_bag_config`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `exchange_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兑换码',
  `acount_money` decimal(22, 2) NOT NULL DEFAULT 0.00 COMMENT '兑换额度',
  `code_capacity` int(11) NULL DEFAULT 1 COMMENT '打码量',
  `password` int(8) NULL DEFAULT NULL COMMENT '密码',
  `exchange` bit(1) NOT NULL DEFAULT b'1' COMMENT '兑换码状态(0:已关闭，1：使用中)',
  `exchange_num` int(11) NULL DEFAULT 1 COMMENT '兑换次数',
  `exchange_account` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '指定兑换者账号（不填写则为不限制）',
  `start_time` datetime(0) NOT NULL COMMENT '生效时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `limit_times_user` bit(1) NOT NULL DEFAULT b'1' COMMENT '单个用户限制次数（0:单次，1:多次）',
  `period` int(11) NULL DEFAULT NULL COMMENT '周期（天）',
  `receive_times` int(5) NULL DEFAULT NULL COMMENT '周期内可以领取次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `exchange_code_unique`(`exchange_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '礼包配置表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
