

INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '0', '商城', NULL, NULL, '0', 'chongzhizonge', '4');
-- 按钮父菜单ID
set @parentId1 = @@identity;
-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	SELECT @parentId1,'商城产品表', 'shopproduct/shopproduct', NULL, '1', 'config', '1';

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'shopproduct:shopproduct:list,shopproduct:shopproduct:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'shopproduct:shopproduct:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'shopproduct:shopproduct:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'shopproduct:shopproduct:delete', '2', null, '6';
