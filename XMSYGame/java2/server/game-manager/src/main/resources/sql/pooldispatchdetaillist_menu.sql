-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('396', '派奖明细记录表', 'pooldispatchdetaillist/pooldispatchdetaillist', NULL, '1', 'config', '4');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'pooldispatchdetaillist:pooldispatchdetaillist:list,pooldispatchdetaillist:pooldispatchdetaillist:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'pooldispatchdetaillist:pooldispatchdetaillist:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'pooldispatchdetaillist:pooldispatchdetaillist:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'pooldispatchdetaillist:pooldispatchdetaillist:delete', '2', null, '6';
