/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.190
Source Server Version : 80015
Source Host           : 192.168.0.190:3306
Source Database       : robot_manager

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-04 16:37:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for game_record
-- ----------------------------
DROP TABLE IF EXISTS `game_record`;
CREATE TABLE `game_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `game_id` bigint(20) NOT NULL DEFAULT '0',
  `game_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) DEFAULT NULL COMMENT '场次id',
  `grade_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '房间名称',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注总金币',
  `prize_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '中奖金币',
  `user_type` int(1) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `mini` bit(1) DEFAULT b'0' COMMENT '是否小游戏',
  `profit_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '代理商盈利金币',
  `water_profit` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '抽水金额',
  `exchange_rate` decimal(2,2) NOT NULL DEFAULT '0.00' COMMENT '房卡和金币的兑换比例',
  `water_rate` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '抽水比例',
  `game_round_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '游戏局号',
  `valid_bet` bigint(20) NOT NULL DEFAULT '0' COMMENT '有效下注',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  PRIMARY KEY (`id`),
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='游戏记录';

-- ----------------------------
-- Records of game_record
-- ----------------------------

-- ----------------------------
-- Table structure for robot
-- ----------------------------
DROP TABLE IF EXISTS `robot`;
CREATE TABLE `robot` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '金币余额',
  `profit_coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '盈利金币额',
  `name` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '机器人名称',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '1启用/0禁用',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态(1游戏中,0空闲)',
  `sex` bit(1) NOT NULL COMMENT '性别(0:女，1：男)',
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `game_id` bigint(22) NOT NULL COMMENT '游戏ID',
  `grade_id` bigint(22) NOT NULL COMMENT '场次id',
  `token` varchar(255) DEFAULT NULL COMMENT 'token',
  `game_info_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '在玩游戏信息id',
  `game_server_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '游戏服务id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='机器人管理';

-- ----------------------------
-- Records of robot
-- ----------------------------

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=550 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员管理', 'sys/user', null, '1', 'admin', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色权限管理', 'sys/role', null, '1', 'role', '0');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('5', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('6', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '4', '权限菜单', null, 'sys:menu:rooleList', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('540', '0', '机器人管理', null, null, '0', null, '1');
INSERT INTO `sys_menu` VALUES ('541', '540', '机器人', 'robot/robot', 'robot:robot:list,robot:robot:info', '1', null, '1');
INSERT INTO `sys_menu` VALUES ('542', '541', '启用/禁用', null, 'robot:robot:enable,robot:robot:disable', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('545', '540', '机器人游戏管理', 'gameinfo/gameinfo', 'gameinfo:gameinfo:robotGameList', '1', null, '2');
INSERT INTO `sys_menu` VALUES ('546', '545', '获取游戏机器人配置', null, 'gameinfo:gameinfo:robotGameConfig', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('547', '545', '提交配置', null, 'gameinfo:gameinfo:saveConfig', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('548', '541', '回收', null, 'robot:robot:taskRecyclingRobot', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('549', '541', '获取游戏下拉', null, 'gameinfo:gameinfo:gameSelectForRobot', '2', null, '0');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员', '1', '2018-09-16 11:02:46');
INSERT INTO `sys_role` VALUES ('2', '财务管理员', '财务人员使用', '1', '2018-09-16 11:24:06');
INSERT INTO `sys_role` VALUES ('3', '厅主管理员', '厅主管理员', '1', '2018-09-17 10:30:08');
INSERT INTO `sys_role` VALUES ('4', '普通管理员', '普通管理员', '1', '2018-09-21 12:41:55');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16946 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('13631', '4', '2');
INSERT INTO `sys_role_menu` VALUES ('13632', '4', '5');
INSERT INTO `sys_role_menu` VALUES ('13633', '4', '6');
INSERT INTO `sys_role_menu` VALUES ('13634', '4', '7');
INSERT INTO `sys_role_menu` VALUES ('13635', '4', '8');
INSERT INTO `sys_role_menu` VALUES ('13636', '4', '362');
INSERT INTO `sys_role_menu` VALUES ('13637', '4', '363');
INSERT INTO `sys_role_menu` VALUES ('13638', '4', '364');
INSERT INTO `sys_role_menu` VALUES ('13639', '4', '365');
INSERT INTO `sys_role_menu` VALUES ('13640', '4', '366');
INSERT INTO `sys_role_menu` VALUES ('13641', '4', '17');
INSERT INTO `sys_role_menu` VALUES ('13642', '4', '18');
INSERT INTO `sys_role_menu` VALUES ('13643', '4', '19');
INSERT INTO `sys_role_menu` VALUES ('13644', '4', '20');
INSERT INTO `sys_role_menu` VALUES ('13645', '4', '21');
INSERT INTO `sys_role_menu` VALUES ('13646', '4', '22');
INSERT INTO `sys_role_menu` VALUES ('13647', '4', '23');
INSERT INTO `sys_role_menu` VALUES ('13648', '4', '38');
INSERT INTO `sys_role_menu` VALUES ('13649', '4', '39');
INSERT INTO `sys_role_menu` VALUES ('13650', '4', '40');
INSERT INTO `sys_role_menu` VALUES ('13651', '4', '41');
INSERT INTO `sys_role_menu` VALUES ('13652', '4', '42');
INSERT INTO `sys_role_menu` VALUES ('13653', '4', '315');
INSERT INTO `sys_role_menu` VALUES ('13654', '4', '316');
INSERT INTO `sys_role_menu` VALUES ('13655', '4', '317');
INSERT INTO `sys_role_menu` VALUES ('13656', '4', '351');
INSERT INTO `sys_role_menu` VALUES ('13657', '4', '352');
INSERT INTO `sys_role_menu` VALUES ('13658', '4', '358');
INSERT INTO `sys_role_menu` VALUES ('13659', '4', '359');
INSERT INTO `sys_role_menu` VALUES ('13660', '4', '360');
INSERT INTO `sys_role_menu` VALUES ('13661', '4', '361');
INSERT INTO `sys_role_menu` VALUES ('13662', '4', '470');
INSERT INTO `sys_role_menu` VALUES ('13663', '4', '471');
INSERT INTO `sys_role_menu` VALUES ('13664', '4', '24');
INSERT INTO `sys_role_menu` VALUES ('13665', '4', '25');
INSERT INTO `sys_role_menu` VALUES ('13666', '4', '26');
INSERT INTO `sys_role_menu` VALUES ('13667', '4', '345');
INSERT INTO `sys_role_menu` VALUES ('13668', '4', '346');
INSERT INTO `sys_role_menu` VALUES ('13669', '4', '347');
INSERT INTO `sys_role_menu` VALUES ('13670', '4', '348');
INSERT INTO `sys_role_menu` VALUES ('13671', '4', '349');
INSERT INTO `sys_role_menu` VALUES ('13672', '4', '350');
INSERT INTO `sys_role_menu` VALUES ('13673', '4', '353');
INSERT INTO `sys_role_menu` VALUES ('13674', '4', '354');
INSERT INTO `sys_role_menu` VALUES ('13675', '4', '391');
INSERT INTO `sys_role_menu` VALUES ('13676', '4', '367');
INSERT INTO `sys_role_menu` VALUES ('13677', '4', '368');
INSERT INTO `sys_role_menu` VALUES ('13678', '4', '369');
INSERT INTO `sys_role_menu` VALUES ('13679', '4', '370');
INSERT INTO `sys_role_menu` VALUES ('13680', '4', '392');
INSERT INTO `sys_role_menu` VALUES ('13681', '4', '393');
INSERT INTO `sys_role_menu` VALUES ('13682', '4', '394');
INSERT INTO `sys_role_menu` VALUES ('13683', '4', '395');
INSERT INTO `sys_role_menu` VALUES ('13684', '4', '465');
INSERT INTO `sys_role_menu` VALUES ('13685', '4', '466');
INSERT INTO `sys_role_menu` VALUES ('13686', '4', '467');
INSERT INTO `sys_role_menu` VALUES ('13687', '4', '468');
INSERT INTO `sys_role_menu` VALUES ('13688', '4', '469');
INSERT INTO `sys_role_menu` VALUES ('13689', '4', '480');
INSERT INTO `sys_role_menu` VALUES ('13690', '4', '481');
INSERT INTO `sys_role_menu` VALUES ('13691', '4', '503');
INSERT INTO `sys_role_menu` VALUES ('13692', '4', '297');
INSERT INTO `sys_role_menu` VALUES ('13693', '4', '298');
INSERT INTO `sys_role_menu` VALUES ('13694', '4', '299');
INSERT INTO `sys_role_menu` VALUES ('13695', '4', '300');
INSERT INTO `sys_role_menu` VALUES ('13696', '4', '301');
INSERT INTO `sys_role_menu` VALUES ('13697', '4', '302');
INSERT INTO `sys_role_menu` VALUES ('13698', '4', '308');
INSERT INTO `sys_role_menu` VALUES ('13699', '4', '309');
INSERT INTO `sys_role_menu` VALUES ('13700', '4', '310');
INSERT INTO `sys_role_menu` VALUES ('13701', '4', '311');
INSERT INTO `sys_role_menu` VALUES ('13702', '4', '312');
INSERT INTO `sys_role_menu` VALUES ('13703', '4', '313');
INSERT INTO `sys_role_menu` VALUES ('13704', '4', '314');
INSERT INTO `sys_role_menu` VALUES ('13705', '4', '428');
INSERT INTO `sys_role_menu` VALUES ('13706', '4', '429');
INSERT INTO `sys_role_menu` VALUES ('13707', '4', '430');
INSERT INTO `sys_role_menu` VALUES ('13708', '4', '431');
INSERT INTO `sys_role_menu` VALUES ('13709', '4', '499');
INSERT INTO `sys_role_menu` VALUES ('13710', '4', '500');
INSERT INTO `sys_role_menu` VALUES ('13711', '4', '472');
INSERT INTO `sys_role_menu` VALUES ('13712', '4', '473');
INSERT INTO `sys_role_menu` VALUES ('13713', '4', '474');
INSERT INTO `sys_role_menu` VALUES ('13714', '4', '475');
INSERT INTO `sys_role_menu` VALUES ('13715', '4', '494');
INSERT INTO `sys_role_menu` VALUES ('13716', '4', '476');
INSERT INTO `sys_role_menu` VALUES ('13717', '4', '477');
INSERT INTO `sys_role_menu` VALUES ('13718', '4', '478');
INSERT INTO `sys_role_menu` VALUES ('13719', '4', '479');
INSERT INTO `sys_role_menu` VALUES ('13720', '4', '502');
INSERT INTO `sys_role_menu` VALUES ('13721', '4', '318');
INSERT INTO `sys_role_menu` VALUES ('13722', '4', '319');
INSERT INTO `sys_role_menu` VALUES ('13723', '4', '320');
INSERT INTO `sys_role_menu` VALUES ('13724', '4', '321');
INSERT INTO `sys_role_menu` VALUES ('13725', '4', '322');
INSERT INTO `sys_role_menu` VALUES ('13726', '4', '323');
INSERT INTO `sys_role_menu` VALUES ('13727', '4', '324');
INSERT INTO `sys_role_menu` VALUES ('13728', '4', '325');
INSERT INTO `sys_role_menu` VALUES ('13729', '4', '326');
INSERT INTO `sys_role_menu` VALUES ('13730', '4', '327');
INSERT INTO `sys_role_menu` VALUES ('13731', '4', '328');
INSERT INTO `sys_role_menu` VALUES ('13732', '4', '329');
INSERT INTO `sys_role_menu` VALUES ('13733', '4', '415');
INSERT INTO `sys_role_menu` VALUES ('13734', '4', '416');
INSERT INTO `sys_role_menu` VALUES ('13735', '4', '419');
INSERT INTO `sys_role_menu` VALUES ('13736', '4', '420');
INSERT INTO `sys_role_menu` VALUES ('13737', '4', '421');
INSERT INTO `sys_role_menu` VALUES ('13738', '4', '422');
INSERT INTO `sys_role_menu` VALUES ('13739', '4', '496');
INSERT INTO `sys_role_menu` VALUES ('13740', '4', '334');
INSERT INTO `sys_role_menu` VALUES ('13741', '4', '337');
INSERT INTO `sys_role_menu` VALUES ('13742', '4', '338');
INSERT INTO `sys_role_menu` VALUES ('13743', '4', '339');
INSERT INTO `sys_role_menu` VALUES ('13744', '4', '340');
INSERT INTO `sys_role_menu` VALUES ('13745', '4', '341');
INSERT INTO `sys_role_menu` VALUES ('13746', '4', '504');
INSERT INTO `sys_role_menu` VALUES ('13747', '4', '505');
INSERT INTO `sys_role_menu` VALUES ('13748', '4', '506');
INSERT INTO `sys_role_menu` VALUES ('13749', '4', '507');
INSERT INTO `sys_role_menu` VALUES ('13750', '4', '508');
INSERT INTO `sys_role_menu` VALUES ('13766', '4', '396');
INSERT INTO `sys_role_menu` VALUES ('13767', '4', '397');
INSERT INTO `sys_role_menu` VALUES ('13768', '4', '398');
INSERT INTO `sys_role_menu` VALUES ('13769', '4', '399');
INSERT INTO `sys_role_menu` VALUES ('13770', '4', '400');
INSERT INTO `sys_role_menu` VALUES ('13771', '4', '401');
INSERT INTO `sys_role_menu` VALUES ('13772', '4', '402');
INSERT INTO `sys_role_menu` VALUES ('13773', '4', '403');
INSERT INTO `sys_role_menu` VALUES ('13774', '4', '404');
INSERT INTO `sys_role_menu` VALUES ('13775', '4', '405');
INSERT INTO `sys_role_menu` VALUES ('13776', '4', '406');
INSERT INTO `sys_role_menu` VALUES ('13777', '4', '440');
INSERT INTO `sys_role_menu` VALUES ('13778', '4', '441');
INSERT INTO `sys_role_menu` VALUES ('13779', '4', '442');
INSERT INTO `sys_role_menu` VALUES ('13780', '4', '443');
INSERT INTO `sys_role_menu` VALUES ('13781', '4', '444');
INSERT INTO `sys_role_menu` VALUES ('13782', '4', '445');
INSERT INTO `sys_role_menu` VALUES ('13783', '4', '446');
INSERT INTO `sys_role_menu` VALUES ('13784', '4', '447');
INSERT INTO `sys_role_menu` VALUES ('13785', '4', '448');
INSERT INTO `sys_role_menu` VALUES ('13786', '4', '449');
INSERT INTO `sys_role_menu` VALUES ('13787', '4', '450');
INSERT INTO `sys_role_menu` VALUES ('13788', '4', '451');
INSERT INTO `sys_role_menu` VALUES ('13789', '4', '452');
INSERT INTO `sys_role_menu` VALUES ('13790', '4', '453');
INSERT INTO `sys_role_menu` VALUES ('13791', '4', '454');
INSERT INTO `sys_role_menu` VALUES ('13792', '4', '455');
INSERT INTO `sys_role_menu` VALUES ('13793', '4', '456');
INSERT INTO `sys_role_menu` VALUES ('13794', '4', '457');
INSERT INTO `sys_role_menu` VALUES ('13795', '4', '458');
INSERT INTO `sys_role_menu` VALUES ('13796', '4', '459');
INSERT INTO `sys_role_menu` VALUES ('13797', '4', '460');
INSERT INTO `sys_role_menu` VALUES ('13798', '4', '461');
INSERT INTO `sys_role_menu` VALUES ('13799', '4', '462');
INSERT INTO `sys_role_menu` VALUES ('13800', '4', '463');
INSERT INTO `sys_role_menu` VALUES ('13801', '4', '464');
INSERT INTO `sys_role_menu` VALUES ('13802', '4', '489');
INSERT INTO `sys_role_menu` VALUES ('13803', '4', '490');
INSERT INTO `sys_role_menu` VALUES ('13804', '4', '491');
INSERT INTO `sys_role_menu` VALUES ('13805', '4', '492');
INSERT INTO `sys_role_menu` VALUES ('13806', '4', '493');
INSERT INTO `sys_role_menu` VALUES ('13807', '4', '495');
INSERT INTO `sys_role_menu` VALUES ('13808', '4', '497');
INSERT INTO `sys_role_menu` VALUES ('13809', '4', '498');
INSERT INTO `sys_role_menu` VALUES ('13810', '4', '501');
INSERT INTO `sys_role_menu` VALUES ('13811', '4', '-666666');
INSERT INTO `sys_role_menu` VALUES ('13812', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('15170', '2', '495');
INSERT INTO `sys_role_menu` VALUES ('15171', '2', '498');
INSERT INTO `sys_role_menu` VALUES ('15172', '2', '501');
INSERT INTO `sys_role_menu` VALUES ('15173', '2', '-666666');
INSERT INTO `sys_role_menu` VALUES ('15178', '3', '2');
INSERT INTO `sys_role_menu` VALUES ('15179', '3', '5');
INSERT INTO `sys_role_menu` VALUES ('15180', '3', '6');
INSERT INTO `sys_role_menu` VALUES ('15181', '3', '7');
INSERT INTO `sys_role_menu` VALUES ('15182', '3', '8');
INSERT INTO `sys_role_menu` VALUES ('15183', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('15184', '3', '9');
INSERT INTO `sys_role_menu` VALUES ('15185', '3', '10');
INSERT INTO `sys_role_menu` VALUES ('15186', '3', '11');
INSERT INTO `sys_role_menu` VALUES ('15187', '3', '12');
INSERT INTO `sys_role_menu` VALUES ('15188', '3', '362');
INSERT INTO `sys_role_menu` VALUES ('15189', '3', '363');
INSERT INTO `sys_role_menu` VALUES ('15190', '3', '364');
INSERT INTO `sys_role_menu` VALUES ('15191', '3', '365');
INSERT INTO `sys_role_menu` VALUES ('15192', '3', '366');
INSERT INTO `sys_role_menu` VALUES ('15193', '3', '531');
INSERT INTO `sys_role_menu` VALUES ('15194', '3', '17');
INSERT INTO `sys_role_menu` VALUES ('15195', '3', '18');
INSERT INTO `sys_role_menu` VALUES ('15196', '3', '19');
INSERT INTO `sys_role_menu` VALUES ('15197', '3', '20');
INSERT INTO `sys_role_menu` VALUES ('15198', '3', '21');
INSERT INTO `sys_role_menu` VALUES ('15199', '3', '22');
INSERT INTO `sys_role_menu` VALUES ('15200', '3', '23');
INSERT INTO `sys_role_menu` VALUES ('15201', '3', '38');
INSERT INTO `sys_role_menu` VALUES ('15202', '3', '39');
INSERT INTO `sys_role_menu` VALUES ('15203', '3', '40');
INSERT INTO `sys_role_menu` VALUES ('15204', '3', '41');
INSERT INTO `sys_role_menu` VALUES ('15205', '3', '42');
INSERT INTO `sys_role_menu` VALUES ('15206', '3', '315');
INSERT INTO `sys_role_menu` VALUES ('15207', '3', '316');
INSERT INTO `sys_role_menu` VALUES ('15208', '3', '317');
INSERT INTO `sys_role_menu` VALUES ('15209', '3', '351');
INSERT INTO `sys_role_menu` VALUES ('15210', '3', '352');
INSERT INTO `sys_role_menu` VALUES ('15211', '3', '358');
INSERT INTO `sys_role_menu` VALUES ('15212', '3', '359');
INSERT INTO `sys_role_menu` VALUES ('15213', '3', '360');
INSERT INTO `sys_role_menu` VALUES ('15214', '3', '361');
INSERT INTO `sys_role_menu` VALUES ('15215', '3', '470');
INSERT INTO `sys_role_menu` VALUES ('15216', '3', '471');
INSERT INTO `sys_role_menu` VALUES ('15217', '3', '24');
INSERT INTO `sys_role_menu` VALUES ('15218', '3', '25');
INSERT INTO `sys_role_menu` VALUES ('15219', '3', '26');
INSERT INTO `sys_role_menu` VALUES ('15220', '3', '345');
INSERT INTO `sys_role_menu` VALUES ('15221', '3', '346');
INSERT INTO `sys_role_menu` VALUES ('15222', '3', '347');
INSERT INTO `sys_role_menu` VALUES ('15223', '3', '348');
INSERT INTO `sys_role_menu` VALUES ('15224', '3', '349');
INSERT INTO `sys_role_menu` VALUES ('15225', '3', '350');
INSERT INTO `sys_role_menu` VALUES ('15226', '3', '353');
INSERT INTO `sys_role_menu` VALUES ('15227', '3', '354');
INSERT INTO `sys_role_menu` VALUES ('15228', '3', '391');
INSERT INTO `sys_role_menu` VALUES ('15229', '3', '367');
INSERT INTO `sys_role_menu` VALUES ('15230', '3', '368');
INSERT INTO `sys_role_menu` VALUES ('15231', '3', '369');
INSERT INTO `sys_role_menu` VALUES ('15232', '3', '370');
INSERT INTO `sys_role_menu` VALUES ('15233', '3', '392');
INSERT INTO `sys_role_menu` VALUES ('15234', '3', '393');
INSERT INTO `sys_role_menu` VALUES ('15235', '3', '394');
INSERT INTO `sys_role_menu` VALUES ('15236', '3', '395');
INSERT INTO `sys_role_menu` VALUES ('15237', '3', '465');
INSERT INTO `sys_role_menu` VALUES ('15238', '3', '466');
INSERT INTO `sys_role_menu` VALUES ('15239', '3', '467');
INSERT INTO `sys_role_menu` VALUES ('15240', '3', '468');
INSERT INTO `sys_role_menu` VALUES ('15241', '3', '469');
INSERT INTO `sys_role_menu` VALUES ('15242', '3', '480');
INSERT INTO `sys_role_menu` VALUES ('15243', '3', '481');
INSERT INTO `sys_role_menu` VALUES ('15244', '3', '497');
INSERT INTO `sys_role_menu` VALUES ('15245', '3', '503');
INSERT INTO `sys_role_menu` VALUES ('15246', '3', '28');
INSERT INTO `sys_role_menu` VALUES ('15247', '3', '29');
INSERT INTO `sys_role_menu` VALUES ('15248', '3', '30');
INSERT INTO `sys_role_menu` VALUES ('15249', '3', '31');
INSERT INTO `sys_role_menu` VALUES ('15250', '3', '32');
INSERT INTO `sys_role_menu` VALUES ('15251', '3', '437');
INSERT INTO `sys_role_menu` VALUES ('15252', '3', '533');
INSERT INTO `sys_role_menu` VALUES ('15253', '3', '33');
INSERT INTO `sys_role_menu` VALUES ('15254', '3', '34');
INSERT INTO `sys_role_menu` VALUES ('15255', '3', '35');
INSERT INTO `sys_role_menu` VALUES ('15256', '3', '36');
INSERT INTO `sys_role_menu` VALUES ('15257', '3', '37');
INSERT INTO `sys_role_menu` VALUES ('15258', '3', '330');
INSERT INTO `sys_role_menu` VALUES ('15259', '3', '331');
INSERT INTO `sys_role_menu` VALUES ('15260', '3', '332');
INSERT INTO `sys_role_menu` VALUES ('15261', '3', '333');
INSERT INTO `sys_role_menu` VALUES ('15262', '3', '297');
INSERT INTO `sys_role_menu` VALUES ('15263', '3', '298');
INSERT INTO `sys_role_menu` VALUES ('15264', '3', '299');
INSERT INTO `sys_role_menu` VALUES ('15265', '3', '300');
INSERT INTO `sys_role_menu` VALUES ('15266', '3', '301');
INSERT INTO `sys_role_menu` VALUES ('15267', '3', '302');
INSERT INTO `sys_role_menu` VALUES ('15268', '3', '308');
INSERT INTO `sys_role_menu` VALUES ('15269', '3', '309');
INSERT INTO `sys_role_menu` VALUES ('15270', '3', '310');
INSERT INTO `sys_role_menu` VALUES ('15271', '3', '311');
INSERT INTO `sys_role_menu` VALUES ('15272', '3', '312');
INSERT INTO `sys_role_menu` VALUES ('15273', '3', '313');
INSERT INTO `sys_role_menu` VALUES ('15274', '3', '314');
INSERT INTO `sys_role_menu` VALUES ('15275', '3', '428');
INSERT INTO `sys_role_menu` VALUES ('15276', '3', '429');
INSERT INTO `sys_role_menu` VALUES ('15277', '3', '430');
INSERT INTO `sys_role_menu` VALUES ('15278', '3', '431');
INSERT INTO `sys_role_menu` VALUES ('15279', '3', '499');
INSERT INTO `sys_role_menu` VALUES ('15280', '3', '500');
INSERT INTO `sys_role_menu` VALUES ('15281', '3', '472');
INSERT INTO `sys_role_menu` VALUES ('15282', '3', '473');
INSERT INTO `sys_role_menu` VALUES ('15283', '3', '474');
INSERT INTO `sys_role_menu` VALUES ('15284', '3', '475');
INSERT INTO `sys_role_menu` VALUES ('15285', '3', '494');
INSERT INTO `sys_role_menu` VALUES ('15286', '3', '476');
INSERT INTO `sys_role_menu` VALUES ('15287', '3', '477');
INSERT INTO `sys_role_menu` VALUES ('15288', '3', '478');
INSERT INTO `sys_role_menu` VALUES ('15289', '3', '479');
INSERT INTO `sys_role_menu` VALUES ('15290', '3', '502');
INSERT INTO `sys_role_menu` VALUES ('15291', '3', '318');
INSERT INTO `sys_role_menu` VALUES ('15292', '3', '319');
INSERT INTO `sys_role_menu` VALUES ('15293', '3', '320');
INSERT INTO `sys_role_menu` VALUES ('15294', '3', '321');
INSERT INTO `sys_role_menu` VALUES ('15295', '3', '322');
INSERT INTO `sys_role_menu` VALUES ('15296', '3', '323');
INSERT INTO `sys_role_menu` VALUES ('15297', '3', '324');
INSERT INTO `sys_role_menu` VALUES ('15298', '3', '325');
INSERT INTO `sys_role_menu` VALUES ('15299', '3', '326');
INSERT INTO `sys_role_menu` VALUES ('15300', '3', '327');
INSERT INTO `sys_role_menu` VALUES ('15301', '3', '328');
INSERT INTO `sys_role_menu` VALUES ('15302', '3', '329');
INSERT INTO `sys_role_menu` VALUES ('15303', '3', '415');
INSERT INTO `sys_role_menu` VALUES ('15304', '3', '416');
INSERT INTO `sys_role_menu` VALUES ('15305', '3', '419');
INSERT INTO `sys_role_menu` VALUES ('15306', '3', '420');
INSERT INTO `sys_role_menu` VALUES ('15307', '3', '421');
INSERT INTO `sys_role_menu` VALUES ('15308', '3', '422');
INSERT INTO `sys_role_menu` VALUES ('15309', '3', '496');
INSERT INTO `sys_role_menu` VALUES ('15310', '3', '334');
INSERT INTO `sys_role_menu` VALUES ('15311', '3', '337');
INSERT INTO `sys_role_menu` VALUES ('15312', '3', '338');
INSERT INTO `sys_role_menu` VALUES ('15313', '3', '339');
INSERT INTO `sys_role_menu` VALUES ('15314', '3', '340');
INSERT INTO `sys_role_menu` VALUES ('15315', '3', '341');
INSERT INTO `sys_role_menu` VALUES ('15316', '3', '504');
INSERT INTO `sys_role_menu` VALUES ('15317', '3', '505');
INSERT INTO `sys_role_menu` VALUES ('15318', '3', '506');
INSERT INTO `sys_role_menu` VALUES ('15319', '3', '507');
INSERT INTO `sys_role_menu` VALUES ('15320', '3', '508');
INSERT INTO `sys_role_menu` VALUES ('15321', '3', '532');
INSERT INTO `sys_role_menu` VALUES ('15322', '3', '396');
INSERT INTO `sys_role_menu` VALUES ('15323', '3', '397');
INSERT INTO `sys_role_menu` VALUES ('15324', '3', '398');
INSERT INTO `sys_role_menu` VALUES ('15325', '3', '399');
INSERT INTO `sys_role_menu` VALUES ('15326', '3', '400');
INSERT INTO `sys_role_menu` VALUES ('15327', '3', '401');
INSERT INTO `sys_role_menu` VALUES ('15328', '3', '402');
INSERT INTO `sys_role_menu` VALUES ('15329', '3', '403');
INSERT INTO `sys_role_menu` VALUES ('15330', '3', '404');
INSERT INTO `sys_role_menu` VALUES ('15331', '3', '405');
INSERT INTO `sys_role_menu` VALUES ('15332', '3', '406');
INSERT INTO `sys_role_menu` VALUES ('15333', '3', '440');
INSERT INTO `sys_role_menu` VALUES ('15334', '3', '441');
INSERT INTO `sys_role_menu` VALUES ('15335', '3', '442');
INSERT INTO `sys_role_menu` VALUES ('15336', '3', '443');
INSERT INTO `sys_role_menu` VALUES ('15337', '3', '444');
INSERT INTO `sys_role_menu` VALUES ('15338', '3', '445');
INSERT INTO `sys_role_menu` VALUES ('15339', '3', '446');
INSERT INTO `sys_role_menu` VALUES ('15340', '3', '447');
INSERT INTO `sys_role_menu` VALUES ('15341', '3', '448');
INSERT INTO `sys_role_menu` VALUES ('15342', '3', '449');
INSERT INTO `sys_role_menu` VALUES ('15343', '3', '450');
INSERT INTO `sys_role_menu` VALUES ('15344', '3', '451');
INSERT INTO `sys_role_menu` VALUES ('15345', '3', '452');
INSERT INTO `sys_role_menu` VALUES ('15346', '3', '453');
INSERT INTO `sys_role_menu` VALUES ('15347', '3', '454');
INSERT INTO `sys_role_menu` VALUES ('15348', '3', '455');
INSERT INTO `sys_role_menu` VALUES ('15349', '3', '456');
INSERT INTO `sys_role_menu` VALUES ('15350', '3', '457');
INSERT INTO `sys_role_menu` VALUES ('15351', '3', '458');
INSERT INTO `sys_role_menu` VALUES ('15352', '3', '459');
INSERT INTO `sys_role_menu` VALUES ('15353', '3', '460');
INSERT INTO `sys_role_menu` VALUES ('15354', '3', '461');
INSERT INTO `sys_role_menu` VALUES ('15355', '3', '462');
INSERT INTO `sys_role_menu` VALUES ('15356', '3', '463');
INSERT INTO `sys_role_menu` VALUES ('15357', '3', '464');
INSERT INTO `sys_role_menu` VALUES ('15358', '3', '489');
INSERT INTO `sys_role_menu` VALUES ('15359', '3', '490');
INSERT INTO `sys_role_menu` VALUES ('15360', '3', '491');
INSERT INTO `sys_role_menu` VALUES ('15361', '3', '492');
INSERT INTO `sys_role_menu` VALUES ('15362', '3', '493');
INSERT INTO `sys_role_menu` VALUES ('15363', '3', '495');
INSERT INTO `sys_role_menu` VALUES ('15364', '3', '498');
INSERT INTO `sys_role_menu` VALUES ('15365', '3', '501');
INSERT INTO `sys_role_menu` VALUES ('15366', '3', '-666666');
INSERT INTO `sys_role_menu` VALUES ('15367', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('15368', '3', '27');
INSERT INTO `sys_role_menu` VALUES ('16147', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('16148', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('16149', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('16150', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('16151', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('16152', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('16153', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('16154', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('16155', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('16156', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('16157', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('16158', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('16159', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('16160', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('16161', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('16162', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('16163', '1', '362');
INSERT INTO `sys_role_menu` VALUES ('16164', '1', '363');
INSERT INTO `sys_role_menu` VALUES ('16165', '1', '364');
INSERT INTO `sys_role_menu` VALUES ('16166', '1', '365');
INSERT INTO `sys_role_menu` VALUES ('16167', '1', '366');
INSERT INTO `sys_role_menu` VALUES ('16168', '1', '386');
INSERT INTO `sys_role_menu` VALUES ('16169', '1', '387');
INSERT INTO `sys_role_menu` VALUES ('16170', '1', '388');
INSERT INTO `sys_role_menu` VALUES ('16171', '1', '389');
INSERT INTO `sys_role_menu` VALUES ('16172', '1', '390');
INSERT INTO `sys_role_menu` VALUES ('16173', '1', '531');
INSERT INTO `sys_role_menu` VALUES ('16174', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('16175', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('16176', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('16177', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('16178', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('16179', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('16180', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('16181', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('16182', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('16183', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('16184', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('16185', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('16186', '1', '315');
INSERT INTO `sys_role_menu` VALUES ('16187', '1', '316');
INSERT INTO `sys_role_menu` VALUES ('16188', '1', '317');
INSERT INTO `sys_role_menu` VALUES ('16189', '1', '351');
INSERT INTO `sys_role_menu` VALUES ('16190', '1', '352');
INSERT INTO `sys_role_menu` VALUES ('16191', '1', '358');
INSERT INTO `sys_role_menu` VALUES ('16192', '1', '359');
INSERT INTO `sys_role_menu` VALUES ('16193', '1', '360');
INSERT INTO `sys_role_menu` VALUES ('16194', '1', '361');
INSERT INTO `sys_role_menu` VALUES ('16195', '1', '470');
INSERT INTO `sys_role_menu` VALUES ('16196', '1', '471');
INSERT INTO `sys_role_menu` VALUES ('16197', '1', '540');
INSERT INTO `sys_role_menu` VALUES ('16198', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('16199', '1', '25');
INSERT INTO `sys_role_menu` VALUES ('16200', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('16201', '1', '345');
INSERT INTO `sys_role_menu` VALUES ('16202', '1', '346');
INSERT INTO `sys_role_menu` VALUES ('16203', '1', '347');
INSERT INTO `sys_role_menu` VALUES ('16204', '1', '348');
INSERT INTO `sys_role_menu` VALUES ('16205', '1', '349');
INSERT INTO `sys_role_menu` VALUES ('16206', '1', '350');
INSERT INTO `sys_role_menu` VALUES ('16207', '1', '353');
INSERT INTO `sys_role_menu` VALUES ('16208', '1', '354');
INSERT INTO `sys_role_menu` VALUES ('16209', '1', '391');
INSERT INTO `sys_role_menu` VALUES ('16210', '1', '549');
INSERT INTO `sys_role_menu` VALUES ('16211', '1', '367');
INSERT INTO `sys_role_menu` VALUES ('16212', '1', '368');
INSERT INTO `sys_role_menu` VALUES ('16213', '1', '369');
INSERT INTO `sys_role_menu` VALUES ('16214', '1', '370');
INSERT INTO `sys_role_menu` VALUES ('16215', '1', '392');
INSERT INTO `sys_role_menu` VALUES ('16216', '1', '393');
INSERT INTO `sys_role_menu` VALUES ('16217', '1', '394');
INSERT INTO `sys_role_menu` VALUES ('16218', '1', '395');
INSERT INTO `sys_role_menu` VALUES ('16219', '1', '534');
INSERT INTO `sys_role_menu` VALUES ('16220', '1', '465');
INSERT INTO `sys_role_menu` VALUES ('16221', '1', '466');
INSERT INTO `sys_role_menu` VALUES ('16222', '1', '467');
INSERT INTO `sys_role_menu` VALUES ('16223', '1', '468');
INSERT INTO `sys_role_menu` VALUES ('16224', '1', '469');
INSERT INTO `sys_role_menu` VALUES ('16225', '1', '480');
INSERT INTO `sys_role_menu` VALUES ('16226', '1', '481');
INSERT INTO `sys_role_menu` VALUES ('16227', '1', '497');
INSERT INTO `sys_role_menu` VALUES ('16228', '1', '503');
INSERT INTO `sys_role_menu` VALUES ('16229', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('16230', '1', '28');
INSERT INTO `sys_role_menu` VALUES ('16231', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('16232', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('16233', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('16234', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('16235', '1', '437');
INSERT INTO `sys_role_menu` VALUES ('16236', '1', '533');
INSERT INTO `sys_role_menu` VALUES ('16237', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('16238', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('16239', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('16240', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('16241', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('16242', '1', '294');
INSERT INTO `sys_role_menu` VALUES ('16243', '1', '295');
INSERT INTO `sys_role_menu` VALUES ('16244', '1', '296');
INSERT INTO `sys_role_menu` VALUES ('16245', '1', '417');
INSERT INTO `sys_role_menu` VALUES ('16246', '1', '486');
INSERT INTO `sys_role_menu` VALUES ('16247', '1', '488');
INSERT INTO `sys_role_menu` VALUES ('16248', '1', '303');
INSERT INTO `sys_role_menu` VALUES ('16249', '1', '304');
INSERT INTO `sys_role_menu` VALUES ('16250', '1', '305');
INSERT INTO `sys_role_menu` VALUES ('16251', '1', '306');
INSERT INTO `sys_role_menu` VALUES ('16252', '1', '307');
INSERT INTO `sys_role_menu` VALUES ('16253', '1', '330');
INSERT INTO `sys_role_menu` VALUES ('16254', '1', '331');
INSERT INTO `sys_role_menu` VALUES ('16255', '1', '332');
INSERT INTO `sys_role_menu` VALUES ('16256', '1', '333');
INSERT INTO `sys_role_menu` VALUES ('16257', '1', '551');
INSERT INTO `sys_role_menu` VALUES ('16258', '1', '423');
INSERT INTO `sys_role_menu` VALUES ('16259', '1', '424');
INSERT INTO `sys_role_menu` VALUES ('16260', '1', '425');
INSERT INTO `sys_role_menu` VALUES ('16261', '1', '426');
INSERT INTO `sys_role_menu` VALUES ('16262', '1', '427');
INSERT INTO `sys_role_menu` VALUES ('16263', '1', '482');
INSERT INTO `sys_role_menu` VALUES ('16264', '1', '483');
INSERT INTO `sys_role_menu` VALUES ('16265', '1', '484');
INSERT INTO `sys_role_menu` VALUES ('16266', '1', '485');
INSERT INTO `sys_role_menu` VALUES ('16267', '1', '550');
INSERT INTO `sys_role_menu` VALUES ('16268', '1', '297');
INSERT INTO `sys_role_menu` VALUES ('16269', '1', '298');
INSERT INTO `sys_role_menu` VALUES ('16270', '1', '299');
INSERT INTO `sys_role_menu` VALUES ('16271', '1', '300');
INSERT INTO `sys_role_menu` VALUES ('16272', '1', '301');
INSERT INTO `sys_role_menu` VALUES ('16273', '1', '302');
INSERT INTO `sys_role_menu` VALUES ('16274', '1', '308');
INSERT INTO `sys_role_menu` VALUES ('16275', '1', '309');
INSERT INTO `sys_role_menu` VALUES ('16276', '1', '310');
INSERT INTO `sys_role_menu` VALUES ('16277', '1', '311');
INSERT INTO `sys_role_menu` VALUES ('16278', '1', '312');
INSERT INTO `sys_role_menu` VALUES ('16279', '1', '313');
INSERT INTO `sys_role_menu` VALUES ('16280', '1', '314');
INSERT INTO `sys_role_menu` VALUES ('16281', '1', '428');
INSERT INTO `sys_role_menu` VALUES ('16282', '1', '429');
INSERT INTO `sys_role_menu` VALUES ('16283', '1', '430');
INSERT INTO `sys_role_menu` VALUES ('16284', '1', '431');
INSERT INTO `sys_role_menu` VALUES ('16285', '1', '499');
INSERT INTO `sys_role_menu` VALUES ('16286', '1', '500');
INSERT INTO `sys_role_menu` VALUES ('16287', '1', '472');
INSERT INTO `sys_role_menu` VALUES ('16288', '1', '473');
INSERT INTO `sys_role_menu` VALUES ('16289', '1', '474');
INSERT INTO `sys_role_menu` VALUES ('16290', '1', '475');
INSERT INTO `sys_role_menu` VALUES ('16291', '1', '494');
INSERT INTO `sys_role_menu` VALUES ('16292', '1', '476');
INSERT INTO `sys_role_menu` VALUES ('16293', '1', '477');
INSERT INTO `sys_role_menu` VALUES ('16294', '1', '478');
INSERT INTO `sys_role_menu` VALUES ('16295', '1', '479');
INSERT INTO `sys_role_menu` VALUES ('16296', '1', '502');
INSERT INTO `sys_role_menu` VALUES ('16297', '1', '318');
INSERT INTO `sys_role_menu` VALUES ('16298', '1', '319');
INSERT INTO `sys_role_menu` VALUES ('16299', '1', '320');
INSERT INTO `sys_role_menu` VALUES ('16300', '1', '321');
INSERT INTO `sys_role_menu` VALUES ('16301', '1', '322');
INSERT INTO `sys_role_menu` VALUES ('16302', '1', '323');
INSERT INTO `sys_role_menu` VALUES ('16303', '1', '324');
INSERT INTO `sys_role_menu` VALUES ('16304', '1', '325');
INSERT INTO `sys_role_menu` VALUES ('16305', '1', '326');
INSERT INTO `sys_role_menu` VALUES ('16306', '1', '327');
INSERT INTO `sys_role_menu` VALUES ('16307', '1', '328');
INSERT INTO `sys_role_menu` VALUES ('16308', '1', '329');
INSERT INTO `sys_role_menu` VALUES ('16309', '1', '415');
INSERT INTO `sys_role_menu` VALUES ('16310', '1', '416');
INSERT INTO `sys_role_menu` VALUES ('16311', '1', '419');
INSERT INTO `sys_role_menu` VALUES ('16312', '1', '420');
INSERT INTO `sys_role_menu` VALUES ('16313', '1', '421');
INSERT INTO `sys_role_menu` VALUES ('16314', '1', '422');
INSERT INTO `sys_role_menu` VALUES ('16315', '1', '496');
INSERT INTO `sys_role_menu` VALUES ('16316', '1', '334');
INSERT INTO `sys_role_menu` VALUES ('16317', '1', '337');
INSERT INTO `sys_role_menu` VALUES ('16318', '1', '338');
INSERT INTO `sys_role_menu` VALUES ('16319', '1', '339');
INSERT INTO `sys_role_menu` VALUES ('16320', '1', '340');
INSERT INTO `sys_role_menu` VALUES ('16321', '1', '341');
INSERT INTO `sys_role_menu` VALUES ('16322', '1', '504');
INSERT INTO `sys_role_menu` VALUES ('16323', '1', '505');
INSERT INTO `sys_role_menu` VALUES ('16324', '1', '506');
INSERT INTO `sys_role_menu` VALUES ('16325', '1', '507');
INSERT INTO `sys_role_menu` VALUES ('16326', '1', '508');
INSERT INTO `sys_role_menu` VALUES ('16327', '1', '532');
INSERT INTO `sys_role_menu` VALUES ('16328', '1', '375');
INSERT INTO `sys_role_menu` VALUES ('16329', '1', '376');
INSERT INTO `sys_role_menu` VALUES ('16330', '1', '378');
INSERT INTO `sys_role_menu` VALUES ('16331', '1', '379');
INSERT INTO `sys_role_menu` VALUES ('16332', '1', '380');
INSERT INTO `sys_role_menu` VALUES ('16333', '1', '381');
INSERT INTO `sys_role_menu` VALUES ('16334', '1', '377');
INSERT INTO `sys_role_menu` VALUES ('16335', '1', '382');
INSERT INTO `sys_role_menu` VALUES ('16336', '1', '383');
INSERT INTO `sys_role_menu` VALUES ('16337', '1', '384');
INSERT INTO `sys_role_menu` VALUES ('16338', '1', '385');
INSERT INTO `sys_role_menu` VALUES ('16339', '1', '487');
INSERT INTO `sys_role_menu` VALUES ('16340', '1', '407');
INSERT INTO `sys_role_menu` VALUES ('16341', '1', '408');
INSERT INTO `sys_role_menu` VALUES ('16342', '1', '410');
INSERT INTO `sys_role_menu` VALUES ('16343', '1', '411');
INSERT INTO `sys_role_menu` VALUES ('16344', '1', '412');
INSERT INTO `sys_role_menu` VALUES ('16345', '1', '413');
INSERT INTO `sys_role_menu` VALUES ('16346', '1', '414');
INSERT INTO `sys_role_menu` VALUES ('16347', '1', '432');
INSERT INTO `sys_role_menu` VALUES ('16348', '1', '433');
INSERT INTO `sys_role_menu` VALUES ('16349', '1', '434');
INSERT INTO `sys_role_menu` VALUES ('16350', '1', '435');
INSERT INTO `sys_role_menu` VALUES ('16351', '1', '436');
INSERT INTO `sys_role_menu` VALUES ('16352', '1', '526');
INSERT INTO `sys_role_menu` VALUES ('16353', '1', '527');
INSERT INTO `sys_role_menu` VALUES ('16354', '1', '528');
INSERT INTO `sys_role_menu` VALUES ('16355', '1', '529');
INSERT INTO `sys_role_menu` VALUES ('16356', '1', '530');
INSERT INTO `sys_role_menu` VALUES ('16357', '1', '535');
INSERT INTO `sys_role_menu` VALUES ('16358', '1', '536');
INSERT INTO `sys_role_menu` VALUES ('16359', '1', '537');
INSERT INTO `sys_role_menu` VALUES ('16360', '1', '538');
INSERT INTO `sys_role_menu` VALUES ('16361', '1', '539');
INSERT INTO `sys_role_menu` VALUES ('16362', '1', '396');
INSERT INTO `sys_role_menu` VALUES ('16363', '1', '397');
INSERT INTO `sys_role_menu` VALUES ('16364', '1', '398');
INSERT INTO `sys_role_menu` VALUES ('16365', '1', '399');
INSERT INTO `sys_role_menu` VALUES ('16366', '1', '400');
INSERT INTO `sys_role_menu` VALUES ('16367', '1', '401');
INSERT INTO `sys_role_menu` VALUES ('16368', '1', '402');
INSERT INTO `sys_role_menu` VALUES ('16369', '1', '403');
INSERT INTO `sys_role_menu` VALUES ('16370', '1', '404');
INSERT INTO `sys_role_menu` VALUES ('16371', '1', '405');
INSERT INTO `sys_role_menu` VALUES ('16372', '1', '406');
INSERT INTO `sys_role_menu` VALUES ('16373', '1', '440');
INSERT INTO `sys_role_menu` VALUES ('16374', '1', '441');
INSERT INTO `sys_role_menu` VALUES ('16375', '1', '442');
INSERT INTO `sys_role_menu` VALUES ('16376', '1', '443');
INSERT INTO `sys_role_menu` VALUES ('16377', '1', '444');
INSERT INTO `sys_role_menu` VALUES ('16378', '1', '445');
INSERT INTO `sys_role_menu` VALUES ('16379', '1', '446');
INSERT INTO `sys_role_menu` VALUES ('16380', '1', '447');
INSERT INTO `sys_role_menu` VALUES ('16381', '1', '448');
INSERT INTO `sys_role_menu` VALUES ('16382', '1', '449');
INSERT INTO `sys_role_menu` VALUES ('16383', '1', '450');
INSERT INTO `sys_role_menu` VALUES ('16384', '1', '451');
INSERT INTO `sys_role_menu` VALUES ('16385', '1', '452');
INSERT INTO `sys_role_menu` VALUES ('16386', '1', '453');
INSERT INTO `sys_role_menu` VALUES ('16387', '1', '454');
INSERT INTO `sys_role_menu` VALUES ('16388', '1', '455');
INSERT INTO `sys_role_menu` VALUES ('16389', '1', '456');
INSERT INTO `sys_role_menu` VALUES ('16390', '1', '457');
INSERT INTO `sys_role_menu` VALUES ('16391', '1', '458');
INSERT INTO `sys_role_menu` VALUES ('16392', '1', '459');
INSERT INTO `sys_role_menu` VALUES ('16393', '1', '460');
INSERT INTO `sys_role_menu` VALUES ('16394', '1', '461');
INSERT INTO `sys_role_menu` VALUES ('16395', '1', '462');
INSERT INTO `sys_role_menu` VALUES ('16396', '1', '463');
INSERT INTO `sys_role_menu` VALUES ('16397', '1', '464');
INSERT INTO `sys_role_menu` VALUES ('16398', '1', '489');
INSERT INTO `sys_role_menu` VALUES ('16399', '1', '490');
INSERT INTO `sys_role_menu` VALUES ('16400', '1', '491');
INSERT INTO `sys_role_menu` VALUES ('16401', '1', '492');
INSERT INTO `sys_role_menu` VALUES ('16402', '1', '493');
INSERT INTO `sys_role_menu` VALUES ('16403', '1', '495');
INSERT INTO `sys_role_menu` VALUES ('16404', '1', '498');
INSERT INTO `sys_role_menu` VALUES ('16405', '1', '501');
INSERT INTO `sys_role_menu` VALUES ('16406', '1', '541');
INSERT INTO `sys_role_menu` VALUES ('16407', '1', '542');
INSERT INTO `sys_role_menu` VALUES ('16409', '1', '548');
INSERT INTO `sys_role_menu` VALUES ('16411', '1', '545');
INSERT INTO `sys_role_menu` VALUES ('16412', '1', '546');
INSERT INTO `sys_role_menu` VALUES ('16413', '1', '547');
INSERT INTO `sys_role_menu` VALUES ('16414', '1', '-666666');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ids` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色id（逗号分隔）',
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '代理商秘钥',
  `agency_rate` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  PRIMARY KEY (`user_id`,`delete_status`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@test.com', '13612345678', '1', '1', '2016-11-11 11:11:11', '1', null, null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('36', '1', '1');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '117620706191903b14d749baea1fcba9', '2019-04-04 07:01:43', '2019-04-03 19:01:43');
