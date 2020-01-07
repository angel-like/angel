INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( 1388, '天降财神活动记录', NULL, 'useractivitiesmanager:tabs:fortune', 2, NULL, 0);

INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (297, '天降财神活动配置', 'fortuneactivitymanager/fortuneactiviconfig', 'useractivitysettings:tabs:fortune', 2, 'config', 6);
-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '删除', NULL, 'fortuneactiviconfig:fortuneactiviconfig:delete', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '修改', NULL, 'fortuneactiviconfig:fortuneactiviconfig:update', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '新增', NULL, 'fortuneactiviconfig:fortuneactiviconfig:save', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '查看', NULL, 'fortuneactiviconfig:fortuneactiviconfig:list,fortuneactiviconfig:fortuneactiviconfig:info', 2, NULL, 6);

INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( 297, '红包任务后台配置', 'fortuneactivitymanager/envelopetaskconfig', NULL, 1, 'config', 6);
-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '删除', NULL, 'envelopetaskconfig:envelopetaskconfig:delete', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '修改', NULL, 'envelopetaskconfig:envelopetaskconfig:update', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '新增', NULL, 'envelopetaskconfig:envelopetaskconfig:save', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '查看', NULL, 'envelopetaskconfig:envelopetaskconfig:list,envelopetaskconfig:envelopetaskconfig:info', 2, NULL, 6);

INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( 297, '天降财神红包记录', 'fortuneactivitymanager/enveloperecord', NULL, 1, 'config', 6);
-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '删除', NULL, 'enveloperecord:enveloperecord:delete', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (@parentId, '修改', NULL, 'enveloperecord:enveloperecord:update', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '新增', NULL, 'enveloperecord:enveloperecord:save', 2, NULL, 6);
INSERT INTO `webhome`.`sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( @parentId, '查看', NULL, 'enveloperecord:enveloperecord:list,enveloperecord:enveloperecord:info', 2, NULL, 6);