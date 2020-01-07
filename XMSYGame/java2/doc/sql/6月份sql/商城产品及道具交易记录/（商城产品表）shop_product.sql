CREATE TABLE `shop_product` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `sys_prop_id` bigint(22) NOT NULL COMMENT '道具名称id',
  `product_number` int(11) NOT NULL COMMENT '产品数量',
  `product_price` bigint(22) NOT NULL COMMENT '产品价格',
  `sell` bit(1) DEFAULT b'0' COMMENT '是否上架（0表示下架，1表示上架）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商城产品表';