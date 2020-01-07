-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('297', '金币奖励配置', 'giftcoinconfig/giftcoinconfig', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'giftcoinconfig:giftcoinconfig:list,giftcoinconfig:giftcoinconfig:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'giftcoinconfig:giftcoinconfig:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'giftcoinconfig:giftcoinconfig:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'giftcoinconfig:giftcoinconfig:delete', '2', null, '6';
	
	
-- '分享记录菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('0', '分享记录管理', '', NULL, '0', 'xianxiachongzhi', '6');
    set @parentId1 = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	SELECT @parentId1, '分享记录', 'sharerecord/sharerecord', NULL, '1', 'xianxiachongzhi', '6';

-- 按钮父菜单ID
set @parentId2 = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId2, '查看', null, 'sharerecord:sharerecord:list,sharerecord:sharerecord:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId2, '新增', null, 'sharerecord:sharerecord:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId2, '修改', null, 'sharerecord:sharerecord:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId2, '删除', null, 'sharerecord:sharerecord:delete', '2', null, '6';
	
	
	
	-- 系统道具菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('297', '系统道具', 'sysprop/sysprop', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId3 = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId3, '查看', null, 'sysprop:sysprop:list,sysprop:sysprop:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId3, '新增', null, 'sysprop:sysprop:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId3, '修改', null, 'sysprop:sysprop:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId3, '删除', null, 'sysprop:sysprop:delete', '2', null, '6';

-- 站内信邮件模板菜单SQL	
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('297', '站内信邮件模板', 'sysmessagemodel/sysmessagemodel', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId4 = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '查看', null, 'sysmessagemodel:sysmessagemodel:list,sysmessagemodel:sysmessagemodel:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '新增', null, 'sysmessagemodel:sysmessagemodel:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '修改', null, 'sysmessagemodel:sysmessagemodel:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '删除', null, 'sysmessagemodel:sysmessagemodel:delete', '2', null, '6';
    
    -- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '查看道具列表', null, 'sysmessagemodelprop:sysmessagemodelprop:list,sysmessagemodelprop:sysmessagemodelprop:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '新增道具', null, 'sysmessagemodelprop:sysmessagemodelprop:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '修改道具', null, 'sysmessagemodelprop:sysmessagemodelprop:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId4, '删除道具', null, 'sysmessagemodelprop:sysmessagemodelprop:delete', '2', null, '6';
	
	
	
-- 会员道具领取记录菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('297', '会员道具领取记录', 'usergiftrecord/usergiftrecord', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId5 = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId5, '查看', null, 'usergiftrecord:usergiftrecord:list,usergiftrecord:usergiftrecord:info', '2', null, '6';



