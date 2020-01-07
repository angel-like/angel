-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('634', '游戏记录-房卡斗地主', 'gamerecordfightlandlordsfangka/gamerecordfightlandlordsfangka', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:list,gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'gamerecordfightlandlordsfangka:gamerecordfightlandlordsfangka:delete', '2', null, '6';
