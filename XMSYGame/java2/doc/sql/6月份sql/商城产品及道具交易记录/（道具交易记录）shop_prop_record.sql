CREATE TABLE `shop_prop_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `sys_prop_id` bigint(22) NOT NULL COMMENT '道具名称id',
  `prop_number` int(11) NOT NULL COMMENT '道具数量',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '类型0：购买   1：使用',
  `produce_id` bigint(22) DEFAULT '0' COMMENT '产品id',
  `user_id` bigint(22) DEFAULT '0' COMMENT '会员id',
  `user_account` varchar(100) DEFAULT '' COMMENT '会员账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商城道具交易记录表';