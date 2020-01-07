/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-20 21:25:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_service_manager
-- ----------------------------
DROP TABLE IF EXISTS `im_service_manager`;
CREATE TABLE `im_service_manager` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '服务器名称',
  `service_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '服务器路径',
  `enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否启用：1启用',
  `order_no` int(5) NOT NULL DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='33娱乐服务器管理';


INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('975', '服务器管理', 'imservicemanager/imservicemanager', null, '1', 'tubiao', '1');
-- 按钮父菜单ID
set @parentId = @@identity;

INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'imservicemanager:imservicemanager:list,imservicemanager:imservicemanager:info', '2', null, '0';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '保存', null, 'imservicemanager:imservicemanager:save', '2', null, '0';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'imservicemanager:imservicemanager:update', '2', null, '0';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'imservicemanager:imservicemanager:delete', '2', null, '0';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改启用状态', null, 'imservicemanager:imservicemanager:updateEnable', '2', null, '0';



