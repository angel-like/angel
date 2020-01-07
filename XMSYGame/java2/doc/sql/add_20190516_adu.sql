ALTER TABLE `user_hierarchy`
ADD COLUMN `hierarchy_type`  int(2) NOT NULL DEFAULT 0 COMMENT '层级类型0：正常  1：风控层级' AFTER `vip_enable`;


ALTER TABLE `user`
ADD COLUMN `risk_hierarchy_id`  bigint(22) NOT NULL DEFAULT 0 COMMENT '风控层级id' AFTER `hierarchy_id`;

ALTER TABLE `user`
ADD COLUMN `no_scan`  bit(1) NULL DEFAULT 0 COMMENT '0:检索风控  1：不检索风控' AFTER `game_server_id`;

INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 VALUES ( '17', '会员风控列表', 'account/riskuser', 'user:user:risklist', '1', 'admin', '1');
set @parentId1 = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 SELECT @parentId1, '修改风控层级', NULL, 'user:user:editRiskHierarchy', '2', NULL, '0';	


INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '345', '正常层级标签页', NULL, 'userhierarchy:tabs:normal', '2', NULL, '0');
INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '345', '风控层级标签页', NULL, 'userhierarchy:tabs:risk', '2', NULL, '0');

INSERT INTO `sys_dictionary` ( `delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`)
 VALUES ( '0', '2019-05-16 17:04:12', '2019-05-16 17:04:41', '0', 'moneyRisk', '未充值余额风控', 'riskConfig', '');



