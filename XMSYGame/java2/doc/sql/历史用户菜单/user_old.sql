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

 Date: 27/06/2019 20:56:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_old
-- ----------------------------
DROP TABLE IF EXISTS `user_old`;
CREATE TABLE `user_old`  (
  `id` bigint(22) NOT NULL COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账号名称',
  `portrait` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `headframe_id` bigint(22) NULL DEFAULT 0 COMMENT '头像框id',
  `sex` bit(1) NOT NULL DEFAULT b'1' COMMENT '性别(0:女，1：男)',
  `user_type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号类型（试玩账号，普通会员，vip）',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token验证Id',
  `union_type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方平台',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方交互ID',
  `union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方ID',
  `superiors_id` bigint(22) NULL DEFAULT NULL COMMENT '上级ID',
  `forbidden_enable` bit(1) NULL DEFAULT b'0' COMMENT '禁用（正常）',
  `nobet_enable` bit(1) NULL DEFAULT b'0' COMMENT '停押（正常）',
  `frozen_enable` bit(1) NULL DEFAULT b'0' COMMENT '冻结(正常)',
  `abnormal_enable` bit(1) NULL DEFAULT b'0' COMMENT '是否异常',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT 0 COMMENT '层级id',
  `risk_hierarchy_id` bigint(22) NOT NULL DEFAULT 0 COMMENT '风控层级id',
  `vip_id` bigint(22) NULL DEFAULT 0 COMMENT 'VIP等级id',
  `coin` bigint(22) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户金币',
  `room_card` bigint(22) NULL DEFAULT 0 COMMENT '房卡',
  `commission` decimal(22, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '佣金',
  `money` decimal(22, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '金钱',
  `register_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '注册ip',
  `register_ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '注册ip地址',
  `register_device_code` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册机器码',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '真实姓名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `agent_enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（1：是代理。0：禁用代理）',
  `first_recharge` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否首冲过',
  `game_info_id` bigint(22) NOT NULL DEFAULT 0 COMMENT '在玩游戏信息id',
  `game_server_id` bigint(22) NOT NULL DEFAULT 0 COMMENT '游戏服务id',
  `no_scan` bit(1) NULL DEFAULT b'0' COMMENT '0:检索风控  1：不检索风控',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
