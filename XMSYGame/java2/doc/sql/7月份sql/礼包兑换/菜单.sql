-- 目录SQL：兑换码管理
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
    VALUES (1145, '兑换码管理', NULL, NULL, 0, 'config', 6);
	-- 按菜单父类目录id
 set @parentId = @@identity;
	-- 菜单SQL：兑换码配置页面以及兑换码兑换记录页面
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    select @parentId,  '兑换码管理', 'giftbagconfig/giftbagmanager', NULL, '1', 'config', '6'; 
    -- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '兑换码配置表', null, 'giftbagconfig:tabs:normal', '2', null, '0';
    
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '兑换码兑换记录', null, 'giftbagexchangerecord:tabs:record', '2', null, '0';


INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'giftbagconfig:giftbagconfig:list,giftbagconfig:giftbagconfig:info,giftbagexchangerecord:giftbagexchangerecord:list,giftbagexchangerecord:giftbagexchangerecord:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'giftbagconfig:giftbagconfig:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'giftbagconfig:giftbagconfig:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'giftbagconfig:giftbagconfig:delete', '2', null, '6';