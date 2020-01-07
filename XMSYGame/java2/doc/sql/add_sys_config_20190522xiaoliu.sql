/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-22 10:02:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'value',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '类型',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '备注0为最上级',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '七牛云', 'qiniuyun', '1', '0', '0', '2019-05-21 16:20:48', '2019-05-21 16:20:48', '0');
INSERT INTO `sys_config` VALUES ('2', 'url', 'https://image.99dongshi.com/', '1', '1', '0', '2019-05-21 19:42:10', '2019-05-21 19:42:10', '0');
INSERT INTO `sys_config` VALUES ('3', 'publickey', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl1AoVCLeyVcqKi9uLF5qlG2JWQWTuARg3AhVFE3Z1gPH2oqArljDRP86vyAOdY+9vbA5J7r9gkOEBjnqhxzsOl47eTEwf5HxSonzMO0zfVPm0mrsgFN6SBgJepigajiU2MdyIxYMq/DFnbFXyJ4e5poBLgOzQgv4hN1tm3E315Ayn67Rxs+WjU7MgZxgEuzCwC47+7Hj37/TjMGQvwrqyXuphGKaKyvrVdvY3L5DoIn6D/sj+dsltoDi9CNBPtvt8a2znHOK8Xm4/wsQ4UfWU7rRDuFdoKExpkoA6jZYMXI5nJE03u5P5CPVohDKiYdd04l+elPAwkvq5w/TtVrZBQIDAQAB', '1', '1', '0', '2019-05-21 19:43:01', '2019-05-21 19:43:01', '0');
INSERT INTO `sys_config` VALUES ('4', 'access_key', 's7slQ_ulkPH96YKbIkmzRwqhXsq74K-CtA2lOQQW', '1', '1', '0', '2019-05-21 20:37:14', '2019-05-21 20:37:14', '0');
INSERT INTO `sys_config` VALUES ('5', 'secret_key', 'EiK-tnVVJ_XoEcPgfXpzzKv5XExFU3UzIBumGjjO', '1', '1', '0', '2019-05-21 20:41:48', '2019-05-21 20:41:48', '0');
INSERT INTO `sys_config` VALUES ('6', 'bucket_public', 'zxyy-website', '1', '1', '0', '2019-05-21 20:42:07', '2019-05-21 20:42:07', '0');
INSERT INTO `sys_config` VALUES ('7', '微信', 'weChat', '', '0', '0', '2019-05-22 09:50:40', '2019-05-22 09:50:40', '0');
INSERT INTO `sys_config` VALUES ('8', 'appId', 'wxdde407c976bbcb9a', '', '7', '0', '2019-05-22 09:52:21', '2019-05-22 09:52:21', '0');
INSERT INTO `sys_config` VALUES ('9', 'appSecret', 'ae36bb597fd1d5a0a7f3027bfec1f5ac', '', '7', '0', '2019-05-22 09:52:35', '2019-05-22 09:52:35', '0');






INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('318', '系统配置', 'sysconfig/sysconfig', NULL, '1', 'shezhi', '2');
	-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 SELECT @parentId, '查看', NULL, 'sysconfig:sysconfig:list,sysconfig:sysconfig:info', '2', NULL, '0';	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '新增', NULL, 'sysconfig:sysconfig:save', '2', NULL, '0';
	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '修改', NULL, 'sysconfig:sysconfig:update', '2', NULL, '0';
	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '删除', NULL, 'sysconfig:sysconfig:delete', '2', NULL, '0';
	

