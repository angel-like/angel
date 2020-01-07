ALTER TABLE `user_rebate_commission_record`
ADD COLUMN `record_date`  date NULL DEFAULT NULL COMMENT '记录日期' AFTER `provide_user_account`;
ALTER TABLE `user_rebate_commission_record`
MODIFY COLUMN `record_date`  date NOT NULL COMMENT '记录日期' AFTER `provide_user_account`,
DROP INDEX `only` ,
ADD UNIQUE INDEX `only` (`record_date`, `user_id`, `provide_user_id`) USING BTREE COMMENT '一个下级一天只能为一个用户提供佣金一次';


INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('329', '代理明细', 'userrebatecommissionrecord/userrebatecommissionrecorddetail', null, '1', 'tubiao', '3');
-- 按钮父菜单ID
set @parentId = @@identity;

INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '查看', null, 'userrebatecommissionrecord:userrebatecommissionrecord:list,userrebatecommissionrecord:userrebatecommissionrecord:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '详情列表', null, 'userrebatecommissionrecord:userrebatecommissionrecord:detailList', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '日期详情', null, 'userrebatecommissionrecord:userrebatecommissionrecord:dateList', '2', null, '6';
	INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId, '下级列表', null, 'agent:agent:SubordinateListForCommission', '2', null, '6';