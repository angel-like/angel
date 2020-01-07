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

 Date: 08/08/2019 15:02:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for proxy_order_transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `proxy_order_transaction_record`;
CREATE TABLE `proxy_order_transaction_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(11) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `proxy_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理账户',
  `type` int(22) NULL DEFAULT 0 COMMENT '交易类型',
  `order_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `amount` decimal(22, 2) NOT NULL COMMENT '交易额',
  `proxy_balance` decimal(22, 2) NOT NULL COMMENT '代理商余额',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代理交易订单表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
