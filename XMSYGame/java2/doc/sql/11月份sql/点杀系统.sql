#2019-11-21开始数据库修改
-- 会员表增加 点杀 字段       点杀名单也加个状态比较好
ALTER TABLE user add `point_kill_enable` bit(1) DEFAULT b'0' COMMENT '状态(0.正常,1:点杀）' AFTER`abnormal_enable`;
ALTER TABLE user add `point_kill_rate` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '点杀概率' AFTER`abnormal_enable`;
-- 会员信息表增加 是否在中国注册(0.否,1:是） 字段    
ALTER TABLE user_info add `user_address_status` bit(1) DEFAULT b'1' COMMENT '是否在中国注册(0.否,1:是）' AFTER`user_address`;
-- 修改除中国以外所有 国家状态
update user_info 
	set user_address_status =false 
	where user_address not like "%中国%" and user_address != '';

DROP TABLE IF EXISTS `user_point_kill`;
CREATE TABLE `user_point_kill` ( 
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
	`user_id` bigint(22) NOT NULL COMMENT '会员ID',
  `user_account` varchar(20) NOT NULL COMMENT '会员账号',
	`point_kill_rate` decimal(22,2) NOT NULL DEFAULT '0.60' COMMENT '点杀概率',
	`remove_amount` bigint(22) NOT NULL DEFAULT '0' COMMENT '解除退出金额',
	`remain_amount` bigint(22) NOT NULL DEFAULT '0' COMMENT '剩余解除退出金额',
	`sys_user_account` varchar(20) DEFAULT '' COMMENT '操作人',
	`operation_time` datetime DEFAULT NULL COMMENT '操作时间',
	`point_kill_enable` bit(1) DEFAULT b'0' COMMENT '状态(0.已被解除点杀,1:点杀）',
	`remake` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='点杀名单';

-- 点杀名单增加点杀操作人员  
ALTER TABLE user_point_kill add 	`point_kill_remake` varchar(255) DEFAULT '' COMMENT '点杀备注' AFTER`remain_amount`;
ALTER TABLE user_point_kill add 	`sys_user_point_kill` varchar(20) DEFAULT ''  COMMENT '点杀操作人' AFTER`remain_amount`;
-- 菜单SQL   会员系统下
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1143', '点杀管理', 'userpointkill/userpointkillmanager', NULL, '1', 'config', '6');
-- 按钮父菜单ID
set @parentId = @@identity;
-- 菜单下  点杀管理表SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	select  @parentId,'收益监管',null, 'userpointkill:tabs:userpointkillmanage', '1', 'config', '6';
-- 菜单下  点杀名单表SQL	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	select  @parentId,'点杀名单',null, 'userpointkill:tabs:userpointkill', '1', 'config', '6';

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'userpointkill:userpointkill:list,userpointkill:userpointkill:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '设置点杀', null, 'userpointkill:userpointkill:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '解除点杀', null, 'userpointkill:userpointkill:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'userpointkill:userpointkill:delete', '2', null, '6';