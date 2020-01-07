CREATE TABLE `push_dispatch_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '标题',
  `operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '操作人',
  `recipient` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '接收者账号',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `execute_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '是否删除',
  `status` int(1) DEFAULT '0' COMMENT '状态 0：未执行 1：成功 2：失败',
  `scope` int(1) DEFAULT '1' COMMENT '范围 1:广播 2: 指定层级 3: 指定用户',
  `type` int(1) DEFAULT '1' COMMENT '类型 1:即时 2: 定时, 3 循环定时',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '发送内容',
  `fail_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '原因',
  `hierarchy_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '层级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;