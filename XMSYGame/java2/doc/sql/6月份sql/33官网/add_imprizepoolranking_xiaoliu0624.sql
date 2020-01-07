/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-24 20:00:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_prize_pool_ranking
-- ----------------------------
DROP TABLE IF EXISTS `im_prize_pool_ranking`;
CREATE TABLE `im_prize_pool_ranking` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '内容',
  `prize` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '奖金',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(启用，禁用)',
  `order_no` int(4) NOT NULL DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='33推荐热门游戏排行';

-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('975', '游戏奖池排行', 'imprizepoolranking/imprizepoolranking', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'imprizepoolranking:imprizepoolranking:list,imprizepoolranking:imprizepoolranking:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'imprizepoolranking:imprizepoolranking:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'imprizepoolranking:imprizepoolranking:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'imprizepoolranking:imprizepoolranking:delete', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '启/禁用', null, 'imprizepoolranking:imprizepoolranking:updateEnable', '2', null, '6';
