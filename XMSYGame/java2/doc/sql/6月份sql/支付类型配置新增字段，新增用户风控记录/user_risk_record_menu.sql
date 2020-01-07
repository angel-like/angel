INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '17', '用户风控记录表', 'userriskrecord/userriskrecord', NULL, '1', 'config', '6');
set @parentId = @@identity;
	
INSERT INTO `sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId,  '查看', NULL, 'userriskrecord:userriskrecord:list,userriskrecord:userriskrecord:info', '2', NULL, '6';