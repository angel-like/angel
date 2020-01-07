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

 Date: 08/08/2019 15:03:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for proxy_transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `proxy_transfer_record`;
CREATE TABLE `proxy_transfer_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(11) NOT NULL DEFAULT 0 COMMENT '会员ID',
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '会员账户',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '会员名称',
  `order_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '业务ID 划拨编号',
  `transfer_coin` decimal(22, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '划拨金币',
  `proxy_id` bigint(11) NOT NULL DEFAULT 0 COMMENT '代理商ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no`) USING BTREE COMMENT '订单号唯一',
  INDEX `index_user_account`(`user_account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8165 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '划拨记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
