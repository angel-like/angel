-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('396', '派奖奖项明细表', 'pooldispatchtaskdetail/pooldispatchtaskdetail', NULL, '1', 'config', '3');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:list,pooldispatchtaskdetail:pooldispatchtaskdetail:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:delete', '2', null, '6';
