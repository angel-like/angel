/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-03 16:36:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for webhome_alert_cofig
-- ----------------------------
DROP TABLE IF EXISTS `webhome_alert_cofig`;
CREATE TABLE `webhome_alert_cofig` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `Introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '介绍',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `url` varchar(50) NOT NULL DEFAULT '' COMMENT '跳转路径',
  `num` int(11) NOT NULL DEFAULT '1' COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='官网弹窗配置表\r\n';


INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '27', '弹窗广告管理', 'webhomealertcofig/webhomealertcofig', NULL, '1', 'editor', '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '742', '查看', NULL, 'webhomealertcofig:webhomealertcofig:list,webhomealertcofig:webhomealertcofig:info', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '742', '修改', NULL, 'webhomealertcofig:webhomealertcofig:update', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '742', '保存', NULL, 'webhomealertcofig:webhomealertcofig:save', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '742', '删除', NULL, 'webhomealertcofig:webhomealertcofig:delete', '2', NULL, '0');
