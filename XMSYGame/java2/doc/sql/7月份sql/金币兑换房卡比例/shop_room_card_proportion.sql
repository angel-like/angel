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

 Date: 05/07/2019 17:19:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shop_room_card_proportion
-- ----------------------------
DROP TABLE IF EXISTS `shop_room_card_proportion`;
CREATE TABLE `shop_room_card_proportion`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `proportion` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '比例',
  `effect_date` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `effect_date_ed`(`effect_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '金币兑换房卡比例表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
