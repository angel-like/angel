/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-13 15:37:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_bet_record
-- ----------------------------
DROP TABLE IF EXISTS `user_bet_record`;
CREATE TABLE `user_bet_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `user_account` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '会员账号',
  `bet_coins` bigint(22) NOT NULL DEFAULT '0' COMMENT '有效下注',
  `old_bet` bigint(22) NOT NULL DEFAULT '0' COMMENT '历史有效下注，在充值时的有效下注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `only` (`create_time`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户每日有效下注';

-- ----------------------------
-- Records of user_bet_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_rebate_commission_record
-- ----------------------------
DROP TABLE IF EXISTS `user_rebate_commission_record`;
CREATE TABLE `user_rebate_commission_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `commission` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '佣金',
  `provide_user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '供应佣金的用户ID',
  `provide_user_account` varchar(50) NOT NULL COMMENT '供应佣金的用户账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `only` (`create_time`,`user_id`,`provide_user_id`) USING BTREE COMMENT '一个下级一天只能为一个用户提供佣金一次'
) ENGINE=InnoDB AUTO_INCREMENT=11291 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户佣金返利记录';

-- ----------------------------
-- Records of user_rebate_commission_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_recommendation_grade
-- ----------------------------
DROP TABLE IF EXISTS `user_recommendation_grade`;
CREATE TABLE `user_recommendation_grade` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `mini_value` bigint(22) NOT NULL DEFAULT '0' COMMENT '最小值',
  `max_value` bigint(22) NOT NULL DEFAULT '0' COMMENT '最大值',
  `reward_rate` decimal(22,4) NOT NULL COMMENT '返利优惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户推荐等级';

-- ----------------------------
-- Records of user_recommendation_grade
-- ----------------------------
INSERT INTO `user_recommendation_grade` VALUES ('11', '2019-06-06 15:54:53', '2019-06-13 15:34:38', '0', '0', '实习生', '0', '10000', '0.0050');
INSERT INTO `user_recommendation_grade` VALUES ('12', '2019-06-06 16:00:22', '2019-06-06 16:00:22', '0', '0', '学员', '10000', '50000', '0.0060');
INSERT INTO `user_recommendation_grade` VALUES ('13', '2019-06-06 16:21:48', '2019-06-06 16:21:48', '0', '0', '市场专员', '50000', '100000', '0.0070');
INSERT INTO `user_recommendation_grade` VALUES ('14', '2019-06-06 16:23:35', '2019-06-06 16:23:35', '0', '0', '代理', '100000', '300000', '0.0080');
INSERT INTO `user_recommendation_grade` VALUES ('15', '2019-06-06 16:25:26', '2019-06-06 16:25:26', '0', '0', '区域代理', '300000', '600000', '0.0090');
INSERT INTO `user_recommendation_grade` VALUES ('16', '2019-06-06 16:42:20', '2019-06-06 16:43:45', '0', '0', '总代理', '600000', '1000000', '0.0100');
INSERT INTO `user_recommendation_grade` VALUES ('17', '2019-06-06 16:46:10', '2019-06-06 16:46:56', '0', '0', '助理', '1000000', '2000000', '0.0120');
INSERT INTO `user_recommendation_grade` VALUES ('18', '2019-06-06 18:51:26', '2019-06-06 18:51:26', '0', '0', '经理', '2000000', '4000000', '0.0140');
INSERT INTO `user_recommendation_grade` VALUES ('19', '2019-06-06 18:51:57', '2019-06-06 18:51:57', '0', '0', '监事', '4000000', '6000000', '0.0160');
INSERT INTO `user_recommendation_grade` VALUES ('20', '2019-06-06 18:52:23', '2019-06-06 18:52:23', '0', '0', '总监', '6000000', '8000000', '0.0180');
INSERT INTO `user_recommendation_grade` VALUES ('21', '2019-06-06 18:52:52', '2019-06-06 18:52:52', '0', '0', '股东', '8000000', '10000000', '0.0200');
INSERT INTO `user_recommendation_grade` VALUES ('22', '2019-06-06 18:53:10', '2019-06-06 18:53:25', '0', '0', '合伙人', '10000000', '20000000', '0.0220');
INSERT INTO `user_recommendation_grade` VALUES ('23', '2019-06-06 18:54:13', '2019-06-06 18:57:09', '0', '0', '董事', '20000000', '0', '0.0250');

-- ----------------------------
-- Table structure for user_recommendation_tree
-- ----------------------------
DROP TABLE IF EXISTS `user_recommendation_tree`;
CREATE TABLE `user_recommendation_tree` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '被推荐人id',
  `parant_user_id` bigint(22) NOT NULL COMMENT '上级id',
  `user_parant_distance` int(22) NOT NULL DEFAULT '0' COMMENT '等级差',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_parent` (`user_id`,`parant_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户推荐关系表';

-- ----------------------------
-- Records of user_recommendation_tree
-- ----------------------------


INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('329', '代理等级', 'userrecommendationgrade/userrecommendationgrade', 'userrecommendationgrade:userrecommendationgrade:list,userrecommendationgrade:userrecommendationgrade:info', '1', 'tubiao', '3');
-- 按钮父菜单ID
set @parentId = @@identity;

INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'userrecommendationgrade:userrecommendationgrade:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'userrecommendationgrade:userrecommendationgrade:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'userrecommendationgrade:userrecommendationgrade:delete', '2', null, '6';




INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '415', '开启/关闭团队权限', NULL, 'agent:agent:updateTeamEnable', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '415', '团队列表', NULL, 'agent:agent:teamList', '2', NULL, '0');

ALTER TABLE `user_recommendation`
ADD COLUMN `recommendation_hierarchy_id`  bigint(22) NOT NULL COMMENT '推荐等级ID' AFTER `agent_hierarchy_id`,
ADD COLUMN `team_enable`  bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启团队' AFTER `recommendation_hierarchy_id`;

ALTER TABLE `user_recommendation`
MODIFY COLUMN `agent_hierarchy_id`  bigint(22) NOT NULL DEFAULT 0 COMMENT '代理等级' AFTER `coin`,
MODIFY COLUMN `recommendation_hierarchy_id`  bigint(22) NULL COMMENT '推荐等级ID' AFTER `agent_hierarchy_id`;

