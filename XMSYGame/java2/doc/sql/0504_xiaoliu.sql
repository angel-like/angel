/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-04 15:31:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_popular_games
-- ----------------------------
DROP TABLE IF EXISTS `app_popular_games`;
CREATE TABLE `app_popular_games` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `game_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '游戏ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COMMENT='APP热门游戏';

-- ----------------------------
-- Records of app_popular_games
-- ----------------------------
INSERT INTO `app_popular_games` VALUES ('186', '0', '2019-05-04 11:53:30', '2019-05-04 11:53:30', '0', '1');
INSERT INTO `app_popular_games` VALUES ('187', '0', '2019-05-04 11:53:36', '2019-05-04 11:53:36', '0', '2');
INSERT INTO `app_popular_games` VALUES ('188', '0', '2019-05-04 11:53:41', '2019-05-04 11:53:41', '0', '3');


INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('747', '541', '热门游戏管理', 'apppopulargames/apppopulargames', NULL, '1', 'remen', '6');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('748', '747', '查看', NULL, 'apppopulargames:apppopulargames:list,apppopulargames:apppopulargames:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('749', '747', '保存', NULL, 'apppopulargames:apppopulargames:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('750', '747', '修改', NULL, 'apppopulargames:apppopulargames:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('751', '747', '删除', NULL, 'apppopulargames:apppopulargames:delete', '2', NULL, '0');


DROP TABLE IF EXISTS `app_hot_promotions`;
CREATE TABLE `app_hot_promotions` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` bigint(22) DEFAULT '0' COMMENT '属性',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='APP热门活动';

-- ----------------------------
-- Records of app_hot_promotions
-- ----------------------------
INSERT INTO `app_hot_promotions` VALUES ('105', '0', '2019-05-04 16:11:16', '2019-05-04 16:11:16', '0', '1', '签到', 'https://wap.abqp0.cc/m/mobile/IndexActivity/zh');
INSERT INTO `app_hot_promotions` VALUES ('106', '0', '2019-05-04 16:11:39', '2019-05-04 16:21:30', '0', '2', '热门活动', 'https://wap.abqp0.cc/m/mobile/IndexActivity/zh');
INSERT INTO `app_hot_promotions` VALUES ('107', '1', '2019-05-04 16:25:34', '2019-05-04 16:25:34', '0', '0', 'test', '2131312');


INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('752', '541', '热门活动管理', 'apphotpromotions/apphotpromotions', NULL, '1', 'shoucangfill', '7');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('753', '752', '查看', NULL, 'apphotpromotions:apphotpromotions:list,apphotpromotions:apphotpromotions:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('754', '752', '保存', NULL, 'apphotpromotions:apphotpromotions:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('755', '752', '修改', NULL, 'apphotpromotions:apphotpromotions:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('756', '752', '删除', NULL, 'apphotpromotions:apphotpromotions:delete', '2', NULL, '0');

