DROP TABLE IF EXISTS `app_money_strategy`;
CREATE TABLE `app_money_strategy` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(255) DEFAULT '' COMMENT '路径',
  `enclosure_id` bigint(22) NOT NULL COMMENT '图片id',
  `type` bigint(10) NOT NULL DEFAULT '1' COMMENT '类型（1.不可点击，2：可跳转，3，跳游戏）',
  `md5` varchar(50) NOT NULL DEFAULT '' COMMENT 'md5',
  `availability` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='赚钱攻略表';