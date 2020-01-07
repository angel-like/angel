INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('0', '33娱乐', NULL, NULL, '0', 'web', '7');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单SQL
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	 SELECT @parentId, '素材管理', 'immaterial/immaterial', NULL, '1', 'menu', '0';
	 
-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'immaterial:immaterial:list,immaterial:immaterial:info', '2', null, '6';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'immaterial:immaterial:save', '2', null, '6';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'immaterial:immaterial:update', '2', null, '6';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'immaterial:immaterial:delete', '2', null, '6';