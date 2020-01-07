-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('17', '会员道具交易记录', 'shopproprecord/shopproprecord', NULL, '1', 'config', '11');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'shopproprecord:shopproprecord:list,shopproprecord:shopproprecord:info', '2', null, '6';
