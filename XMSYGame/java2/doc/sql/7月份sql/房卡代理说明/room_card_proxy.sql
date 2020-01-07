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

 Date: 02/07/2019 14:11:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for room_card_proxy
-- ----------------------------
DROP TABLE IF EXISTS `room_card_proxy`;
CREATE TABLE `room_card_proxy`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮名称',
  `enclosure_id` bigint(22) NULL DEFAULT NULL COMMENT '图片id',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文字',
  `type` int(10) NOT NULL COMMENT '类型(0:图片 1:文字）',
  `sort` int(10) NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房卡代理说明' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
