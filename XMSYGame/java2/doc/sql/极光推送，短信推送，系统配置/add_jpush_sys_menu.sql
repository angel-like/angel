INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('541', 'APP推送', 'pushdispatchdetail/pushdispatchdetail', NULL, '1', 'web', '0');
	-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 SELECT @parentId, '查看', NULL, 'pushdispatchdetail:pushdispatchdetail:list,pushdispatchdetail:pushdispatchdetail:info', '2', NULL, '0';	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '新增', NULL, 'pushdispatchdetail:pushdispatchdetail:save', '2', NULL, '0';
	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '修改', NULL, 'pushdispatchdetail:pushdispatchdetail:update', '2', NULL, '0';
	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId,  '删除', NULL, 'pushdispatchdetail:pushdispatchdetail:delete', '2', NULL, '0';