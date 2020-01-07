#2019-11-28开始数据库修改
-- 理财产品表
DROP TABLE IF EXISTS `user_balance_product`;
CREATE TABLE `user_balance_product` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `product_name` varchar(100) NOT NULL DEFAULT '' COMMENT '方案名称',
  `settlement_type` int(11) DEFAULT '0' COMMENT '结算类型（0:循环结算，1本金结算）',
  `min_money` bigint(22) NOT NULL DEFAULT '0' COMMENT '最低金额',
  `max_money` bigint(22) NOT NULL DEFAULT '0' COMMENT '最高金额',
  `rate` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '利率',
  `rate_money_max` bigint(22) NOT NULL DEFAULT '0' COMMENT '利息上限',
  `remaind_buy_num` bigint(22) DEFAULT '0' COMMENT '剩余可买金额',
  `bet_multiple` int(11) DEFAULT '1' COMMENT '打码倍数',
  `user_today_buy_num` int(11) DEFAULT '1' COMMENT '会员当天可购买次数',
  `issue_num` bigint(22) NOT NULL DEFAULT '0' COMMENT '发行金额',
  `issue_time` datetime DEFAULT NULL COMMENT '结算时间',
  `settlement_cycle` int(11) DEFAULT '1' COMMENT '结算周期',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(0:禁用，1:启用)',
  `order_num` int(4) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='理财产品';
INSERT INTO user_balance_product(`id`, `delete_status`, `create_time`, `update_time`, `version`, `product_name`, `settlement_type`, `min_money`, `max_money`, `rate`, `rate_money_max`, `remaind_buy_num`, `bet_multiple`, `user_today_buy_num`, `issue_num`, `issue_time`, `settlement_cycle`, `enable`, `order_num`) VALUES (1, 0, '2019-11-28 12:04:19', '2019-12-05 12:03:13', 0, '余额宝A', 0, 10000, 1000000, 0.00072000, 88800, 100000000, 2, 3, 100000000, '2019-11-28 00:00:00', 1, b'0', 1);
-- 关联余额宝表增加理财产品id字段
ALTER TABLE user_balance add 	`user_balance_product_id` int(11) NOT NULL DEFAULT '1' COMMENT '理财产品id' AFTER`user_account`;
ALTER TABLE user_balance_record add 	`user_balance_product_id` int(11) NOT NULL DEFAULT '1' COMMENT '理财产品id' AFTER`user_account`;
ALTER TABLE user_profit_record add 	`user_balance_product_id` int(11) NOT NULL DEFAULT '1' COMMENT '理财产品id' AFTER`user_account`;


#  user_balance删除唯一约束
ALTER TABLE user_balance DROP INDEX user_id;
# 添加唯一约束 XXX
ALTER TABLE user_balance ADD  UNIQUE key(user_id,user_balance_product_id);


-- 理财管理目录SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('0', '理财管理', null, NULL, '0', 'disanfang', '6');
-- 按钮父菜单ID
set @parentIdtotal = @@identity;

-- 理财产品菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		select  @parentIdtotal,'理财产品','userbalanceproduct/userbalanceproduct',NULL, '1', 'config', '6';
-- 按钮父菜单ID
set @parentId11 = @@identity;
-- 菜单对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId11, '查看', null, 'userbalanceproduct:userbalanceproduct:list,userbalanceproduct:userbalanceproduct:info', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId11, '新增', null, 'userbalanceproduct:userbalanceproduct:save', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId11, '修改', null, 'userbalanceproduct:userbalanceproduct:update', '2', null, '6';
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId11, '删除', null, 'userbalanceproduct:userbalanceproduct:delete', '2', null, '6';

-- 余额宝菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		select  @parentIdtotal, '余额宝', 'userbalance/userbalance', NULL, '1', 'config', '6';
-- 按钮父菜单ID
set @parentId1 = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId1, '查看', null, 'userbalance:userbalance:list,userbalance:userbalance:info', '2', null, '6';
-- 存取记录菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
	select @parentIdtotal, '存取记录', 'userbalance/userbalancerecord', NULL, '1', 'config', '6';
-- 按钮父菜单ID
set @parentId2 = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId2, '查看', null, 'userbalancerecord:userbalancerecord:list,userbalancerecord:userbalancerecord:info', '2', null, '6';
-- 会员收益菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
		select  @parentIdtotal, '会员收益', 'userbalance/userprofitrecord1', NULL, '1', 'config', '6';
-- 按钮父菜单ID
set @parentId1 = @@identity;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    SELECT @parentId1, '查看', null, 'userprofitrecord:userprofitrecord:list', '2', null, '6';