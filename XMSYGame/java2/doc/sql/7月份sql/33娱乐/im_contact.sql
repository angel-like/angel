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

 Date: 02/07/2019 18:03:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_contact
-- ----------------------------
DROP TABLE IF EXISTS `im_contact`;
CREATE TABLE `im_contact`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `enclosure_id` bigint(22) NULL DEFAULT NULL COMMENT '附件ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `type` int(10) NOT NULL COMMENT '信息类型(0:联系方式，1:网站信息）',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `sort` int(10) NULL DEFAULT 0 COMMENT '排序',
  `content_type` int(10) NOT NULL COMMENT '文本类型(0:文本，1:链接，2:图片）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '33联系方式' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
