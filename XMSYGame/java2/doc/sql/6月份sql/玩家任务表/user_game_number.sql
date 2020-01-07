

-- ----------------------------
-- Table structure for user_game_number
-- ----------------------------
DROP TABLE IF EXISTS `user_game_number`;
CREATE TABLE `user_game_number` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `room_id` bigint(22) DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(50) DEFAULT '' COMMENT '房间名称',
  `game_id` bigint(22) DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(50) DEFAULT '' COMMENT '游戏名称',
  `count_date` date DEFAULT NULL COMMENT '统计日期',
  `game_num` int(11) DEFAULT '0' COMMENT '游戏次数',
  `win_num` int(11) DEFAULT '0' COMMENT '胜局次数',
  `lose_num` int(11) DEFAULT '0' COMMENT '输局次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id_uk` (`user_id`,`room_id`,`game_id`,`count_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户每日游戏次数统计表';
