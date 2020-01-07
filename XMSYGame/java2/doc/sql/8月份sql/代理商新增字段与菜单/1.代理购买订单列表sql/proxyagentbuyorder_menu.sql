
 -- 代理模块
   INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES
   (0, '代理模块', NULL, NULL, 0, 'huiyuan', 6);
 -- 菜单父目录ID
   set @parentId = @@identity;
-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 SELECT  @parentId, '代理购买订单表', 'proxyagentbuyorder/proxyagentbuyorder', NULL, '1', 'config', '6';
 
-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'proxyagentbuyorder:proxyagentbuyorder:list,proxyagentbuyorder:proxyagentbuyorder:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '新增', null, 'proxyagentbuyorder:proxyagentbuyorder:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '修改', null, 'proxyagentbuyorder:proxyagentbuyorder:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '删除', null, 'proxyagentbuyorder:proxyagentbuyorder:delete', '2', null, '6';

