/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-22 15:49:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_download_manage
-- ----------------------------
DROP TABLE IF EXISTS `im_download_manage`;
CREATE TABLE `im_download_manage` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `download_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '下载地址',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(启用，禁用)',
  `order_no` int(4) NOT NULL DEFAULT '0' COMMENT '排序号',
  `type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型app/pc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='33推荐下载管理';



-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('975', '33推荐下载管理', 'imdownloadmanage/imdownloadmanage', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'imdownloadmanage:imdownloadmanage:list,imdownloadmanage:imdownloadmanage:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'imdownloadmanage:imdownloadmanage:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'imdownloadmanage:imdownloadmanage:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'imdownloadmanage:imdownloadmanage:delete', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '启/禁用', null, 'imdownloadmanage:imdownloadmanage:updateEnable', '2', null, '6';
