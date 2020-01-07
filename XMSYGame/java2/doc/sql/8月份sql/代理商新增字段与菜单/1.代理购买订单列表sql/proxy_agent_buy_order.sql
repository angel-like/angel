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

 Date: 08/08/2019 14:58:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for proxy_agent_buy_order
-- ----------------------------
DROP TABLE IF EXISTS `proxy_agent_buy_order`;
CREATE TABLE `proxy_agent_buy_order`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(11) NULL DEFAULT NULL COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `proxy_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理账户',
  `proxy_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理名称',
  `buy_coins` bigint(22) NULL DEFAULT NULL COMMENT '购买金币',
  `discount` decimal(10, 8) NULL DEFAULT NULL COMMENT '折扣',
  `dis_gold_coins` bigint(22) NULL DEFAULT NULL COMMENT '优惠金币',
  `get_tatol_coins` bigint(22) NULL DEFAULT NULL COMMENT '获得的总金币',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代理购买订单列表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
