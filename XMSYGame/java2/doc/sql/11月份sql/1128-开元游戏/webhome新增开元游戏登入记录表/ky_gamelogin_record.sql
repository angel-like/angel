/*
 Navicat MySQL Data Transfer

 Source Server         : 145
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 10.0.0.145:3306
 Source Schema         : webhome

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 29/11/2019 09:25:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ky_gamelogin_record
-- ----------------------------
DROP TABLE IF EXISTS `ky_gamelogin_record`;
CREATE TABLE `ky_gamelogin_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_id` bigint(20) NOT NULL COMMENT '订单号',
  `response_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应体',
  `game_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '游戏id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动分类' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
