-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('630', '百人经典牛牛', 'gamerecordbairenjingdianniuniu/gamerecordbairenjingdianniuniu', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:list,gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'gamerecordbairenjingdianniuniu:gamerecordbairenjingdianniuniu:delete', '2', null, '6';
