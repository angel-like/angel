CREATE TABLE `shop_prop_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT 'ɾ��״̬',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` datetime DEFAULT NULL COMMENT '�޸�ʱ��',
  `version` int(11) DEFAULT '0' COMMENT '���ݰ汾',
  `sys_prop_id` bigint(22) NOT NULL COMMENT '��������id',
  `prop_number` int(11) NOT NULL COMMENT '��������',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '����0������   1��ʹ��',
  `produce_id` bigint(22) DEFAULT '0' COMMENT '��Ʒid',
  `user_id` bigint(22) DEFAULT '0' COMMENT '��Աid',
  `user_account` varchar(100) DEFAULT '' COMMENT '��Ա�˺�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='�̳ǵ��߽��׼�¼��';