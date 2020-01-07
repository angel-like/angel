INSERT INTO `webhome`.`sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		VALUES 	(541, '证书管理', 'webhomefileupload/iosfilemanager', '', 1, 'config', 6);

-- 修改文件上传菜单
update sys_menu SET parent_id='1246',perms='iosfileupload:tabs:normal',url=null  where name='文件上传'

-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	select  @parentId,'安装包',null, 'filepackage:tabs:record', '1', 'config', '6'


-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'webhomefilepackage:webhomefilepackage:list,webhomefilepackage:webhomefilepackage:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'webhomefilepackage:webhomefilepackage:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'webhomefilepackage:webhomefilepackage:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'webhomefilepackage:webhomefilepackage:delete', '2', null, '6';