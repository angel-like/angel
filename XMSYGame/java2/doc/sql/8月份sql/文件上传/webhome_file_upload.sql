/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.0.142
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 10.0.0.142:3306
 Source Schema         : webhome

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 26/08/2019 14:43:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for webhome_file_upload
-- ----------------------------
DROP TABLE IF EXISTS `webhome_file_upload`;
CREATE TABLE `webhome_file_upload`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `certificate_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证书别名',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  `platform` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台（ios,android）',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_package_time` datetime(0) NULL DEFAULT NULL COMMENT '打包时间',
  `p12_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'p12文件内容',
  `profile_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'profile文件内容',
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `file_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打包的文件地址',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `certificate_name_unique`(`certificate_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '苹果企业证书' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
