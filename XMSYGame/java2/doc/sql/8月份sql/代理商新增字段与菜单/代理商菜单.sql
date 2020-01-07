INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (1142, '代理商', '', NULL, 0, 'huiyuan', 6);
	-- 父目录菜单ID
     set @parentId = @@identity;
	INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
	SELECT @parentId, '代理商管理', 'sys/proxy-agent', NULL, 1, 'config', 6;