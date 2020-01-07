-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('634', '房卡血战麻将', 'gamerecordxuezhanmajiangfaka/gamerecordxuezhanmajiangfaka', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:list,gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'gamerecordxuezhanmajiangfaka:gamerecordxuezhanmajiangfaka:delete', '2', null, '6';
