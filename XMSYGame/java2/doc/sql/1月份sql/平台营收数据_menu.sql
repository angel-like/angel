-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1', '平台营业数据', 'platformdatareport/reportproviderdaily', 'reportproviderdaily:reportproviderdaily:list,reportproviderdaily:reportproviderdaily:info', '1', 'config', '6');
     UPDATE sys_menu a
	        INNER JOIN (
	       SELECT menu_id
	       FROM sys_menu
	       WHERE  NAME='平台数据报表'
	        )b
	      SET a.parent_id = b.menu_id
	   WHERE    a.`name` = '平台营业数据';
