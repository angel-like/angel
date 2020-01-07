

#添加 人工充值 表的   字段属性
ALTER TABLE order_administrator_recharge add  `recharge_type` int(11)  COMMENT '充值类型';






#插入数据字典充值类型sql
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 15:30:29', '2019-10-31 16:41:01', 0, 'rechargeType', '人工充值类型', '0', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '0', '人工存入', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '1', '注册优惠', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '2', '充值优惠', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '3', '退佣优惠', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '4', '活动优惠', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '5', '其它优惠', 'rechargeType', b'1');
INSERT INTO `webhome`.`sys_dictionary`(`delete_status`, `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (0, '2019-10-31 16:41:35', '2019-10-31 16:41:35', 0, '6', '推广优惠', 'rechargeType', b'1');
