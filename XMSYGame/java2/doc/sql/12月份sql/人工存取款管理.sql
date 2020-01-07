-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '人工存取款管理', 'orderrecharge/admin', NULL, '1', 'config', '6');

-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId,  '人工存款', NULL, 'order:tabs:adminenter,orderadministratorrecharge:orderadministratorrecharge:accessMoney,orderadministratorrecharge:orderadministratorrecharge:info,messagemanagement:messagemanagement:info,orderadministratorrecharge:orderadministratorrecharge:save,orderadministratorrecharge:orderadministratorrecharge:create,orderadministratorrecharge:orderadministratorrecharge:getUser', 2, NULL, 1;
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId,  '人工取款', NULL, 'order:tabs:adminout,orderadministratorrecharge:orderadministratorrecharge:accessMoney,orderadministratorrecharge:orderadministratorrecharge:getUser,orderadministratorrecharge:orderadministratorrecharge:save', 2, NULL, 1;
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
SELECT @parentId, '人工存取历史记录', NULL, 'order:tabs:adminhistory,orderadministratorrecharge:orderadministratorrecharge:orderList', 2, NULL, 1;
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
   SELECT @parentId, '修改打码量', NULL, 'order:tabs:ordercashrecord,ordercashrecord:ordercashrecord:save,orderadministratorrecharge:orderadministratorrecharge:getUser', 2, NULL, 1;
    UPDATE sys_menu a
	        INNER JOIN (
	       SELECT menu_id
	       FROM sys_menu
	       WHERE  NAME='资金系统'
	        )b
	      SET a.parent_id = b.menu_id
	   WHERE    a.`name` = '人工存取款管理';