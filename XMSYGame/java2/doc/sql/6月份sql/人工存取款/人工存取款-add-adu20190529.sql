ALTER TABLE `order_administrator_recharge`
ADD COLUMN `operation_type`  int(1) NULL DEFAULT 0 COMMENT '操作类型 0：存款   1：取款' AFTER `hierarchy_id`,
ADD COLUMN `designated`  bit(1) NULL DEFAULT 0 COMMENT '是否指定' AFTER `operation_type`;

ALTER TABLE `order_take_money`
ADD COLUMN `admin_order_no`  varchar(55) NULL DEFAULT '' COMMENT '人工取款主订单号' AFTER `order_no`;

INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '392', '订单详情', NULL, 'orderadministratorrecharge:orderadministratorrecharge:orderDetail', '2', NULL, '0');
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
VALUES ( '17', '人工存取款', 'orderadministrator/orderadministrator', NULL, '1', 'shezhi', '6');
-- 按钮父菜单ID
set @parentId = @@identity;
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '订单详情', NULL, 'orderadministratorrecharge:orderadministratorrecharge:orderDetail', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '获取会员信息', NULL, 'orderadministratorrecharge:orderadministratorrecharge:getUser', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
SELECT @parentId, '创建人工存取款', NULL, 'orderadministratorrecharge:orderadministratorrecharge:accessMoney', '2', NULL, '0';
INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) 
 SELECT @parentId, '查询', NULL, 'orderadministratorrecharge:orderadministratorrecharge:orderList', '2', NULL, '0';