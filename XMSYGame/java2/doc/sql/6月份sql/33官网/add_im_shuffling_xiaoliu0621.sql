/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-21 15:22:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_shuffling
-- ----------------------------
DROP TABLE IF EXISTS `im_shuffling`;
CREATE TABLE `im_shuffling` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `title` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片标题',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(启用，禁用)',
  `order_no` int(4) NOT NULL DEFAULT '0' COMMENT '排序号',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '跳转路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='33轮播图管理';


-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '33轮播图管理', 'imshuffling/imshuffling', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'imshuffling:imshuffling:list,imshuffling:imshuffling:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'imshuffling:imshuffling:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'imshuffling:imshuffling:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'imshuffling:imshuffling:delete', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '启/禁用', null, 'imshuffling:imshuffling:updateEnable', '2', null, '6';
