/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.104
Source Server Version : 80016
Source Host           : 10.0.0.104:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-08-17 18:11:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ranking_list
-- ----------------------------
DROP TABLE IF EXISTS `ranking_list`;
CREATE TABLE `ranking_list` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `rank_list_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '排行榜名称',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `top_num` int(4) unsigned DEFAULT '20' COMMENT '榜单前几名参与抽奖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='排行榜';

-- ----------------------------
-- Records of ranking_list
-- ----------------------------
INSERT INTO `ranking_list` VALUES ('1', '0', '2019-08-12 16:04:08', '2019-08-16 15:13:33', '0', '分享榜', '\0', '20');
INSERT INTO `ranking_list` VALUES ('2', '0', '2019-08-12 16:04:22', '2019-08-17 18:05:41', '0', '日排行榜--按会员充值排行', '', '20');
INSERT INTO `ranking_list` VALUES ('3', '0', '2019-08-12 16:04:32', '2019-08-16 15:13:45', '0', '派奖榜', '\0', '20');
INSERT INTO `ranking_list` VALUES ('4', '0', '2019-08-12 16:04:41', '2019-08-17 18:06:33', '0', '用户排行--按会员财富排行', '', '20');
