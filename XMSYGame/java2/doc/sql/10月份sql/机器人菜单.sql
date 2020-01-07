-- 机器人菜单设置  --  


INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
 VALUES ( '0', '机器人管理', NULL, NULL, '0', 'game', '10');
set @PPPparentId = @@identity;

--  机器人管理菜单
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
	SELECT @PPPparentId, '机器人', 'robot/robot', NULL, '1', 'admin', '0';
	
-- 父菜单ID
set @parentId = @@identity;
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '机器人回收', NULL, 'robot:robot:taskRecyclingRobot', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '禁用', NULL, 'robot:robot:disable', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '启用', NULL, 'robot:robot:enable', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '查询', NULL, 'robot:robot:list,robot:robot:info', '2', NULL, '0';


--  机器人游戏记录-1324为机器人管理菜单
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
	SELECT @PPPparentId, '游戏记录', 'robot/gamerecord', NULL, 1, 'tubiao', 0;
-- 父菜单ID
set @parentId = @@identity;
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId, '游戏记录', null, 'robotgamerecord:robotgamerecord:list', '2', null, '0';	
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId, '游戏下拉', null, 'gamerecord:gamerecord:gameSelect', '2', null, '0';
		
--  机器人游戏管理-1324为机器人管理菜单
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
	SELECT @PPPparentId,  '机器人游戏管理', 'robot/gameinfo', 'gameinfo:gameinfo:robotGameList', 1, 'game', 2;
-- 父菜单ID
set @parentId2 = @@identity;
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId2, '获取游戏机器人配置', null, 'gameinfo:gameinfo:robotGameConfig', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId2, '提交配置', null, 'gameinfo:gameinfo:saveConfig', '2', null, '0';	
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId2, '游戏场次统计', null, 'gameinfo:gameinfo:robotGradeList', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId2, '游戏统计', null, 'gameinfo:gameinfo:robotGameList', '2', null, '0';	
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId2, '房间下拉', null, 'gameinfo:gameinfo:roomSelect', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId2, '重置机器人数量', null, 'gameinfo:gameinfo:close', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId2, '强制回收机器人', null, 'gameinfo:gameinfo:forceTaskRobot', '2', null, '0';	
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId2, '还原机器人', null, 'gameinfo:gameinfo:forceOnTaskRobot', '2', null, '0';	
		
--  机器人盈利报表-1324为机器人管理菜单
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @PPPparentId,  '盈利报表', 'robot/robotprofitrecord', 'robotprofitrecord:robotprofitrecord:list', 1, 'tubiao', 3;
-- 父菜单ID
set @parentId3 = @@identity;
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId3, '总盈利', null, 'statistics:statistics:sumProfitForGame', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId3, '盈利列表', null, 'statistics:statistics:sumProfitForRecord', '2', null, '0';	
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
		SELECT @parentId3, '盈利详情', null, 'statistics:statistics:sumProfitForGradeRecord', '2', null, '0';
		
--  机器人统计结果-1324为机器人管理菜单
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @PPPparentId,  '统计结果', 'robot/robotprofitrecordresult', 'robotprofitrecordresult:robotprofitrecordresult:list', 1, 'shoucangfill', 4;
-- 父菜单ID
set @parentId4 = @@identity;
INSERT INTO `sys_menu`( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		SELECT @parentId4, '重新统计', null, 'robotprofitrecordresult:robotprofitrecordresult:sumrobotRecord', '2', null, '0';

