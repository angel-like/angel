/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.109
Source Server Version : 80012
Source Host           : 10.0.0.109:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-04 10:57:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_type_configuration
-- ----------------------------
DROP TABLE IF EXISTS `payment_type_configuration`;
CREATE TABLE `payment_type_configuration` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='支付类型配置表';

-- ----------------------------
-- Records of payment_type_configuration
-- ----------------------------
INSERT INTO `payment_type_configuration` VALUES ('21', '0', '2019-06-03 14:51:23', '2019-06-03 14:51:23', '0', '银行卡存款');
INSERT INTO `payment_type_configuration` VALUES ('22', '0', '2019-06-03 14:51:44', '2019-06-03 14:51:44', '0', 'AB存款');
INSERT INTO `payment_type_configuration` VALUES ('23', '0', '2019-06-03 14:51:59', '2019-06-03 14:51:59', '0', '第三方存款');
INSERT INTO `payment_type_configuration` VALUES ('24', '0', '2019-06-03 14:53:00', '2019-06-03 14:53:00', '0', '人工存款');
