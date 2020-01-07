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

 Date: 26/08/2019 14:34:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for webhome_file_package
-- ----------------------------
DROP TABLE IF EXISTS `webhome_file_package`;
CREATE TABLE `webhome_file_package`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '数据版本',
  `proxy_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理编号',
  `proxy_alias` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理别名',
  `ios_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '苹果签名',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新地址',
  `ios_file_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '苹果文件打包地址',
  `android_file_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '安卓文件打包地址',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '安装包' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
