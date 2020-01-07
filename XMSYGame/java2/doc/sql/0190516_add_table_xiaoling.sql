DROP TABLE IF EXISTS `report_data_daily`;
CREATE TABLE `report_data_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `recharge_total` decimal(30,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '充值总额',
  `recharge_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '充值人数',
  `registere_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '注册用户数',
  `active_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '活跃人数',
  `win_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '赢钱人数',
  `lose_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '输钱人数',
  `investment_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总投入',
  `output_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总产出',
  `win_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总输赢',
  `count_day` date DEFAULT NULL COMMENT '结算日期',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`count_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='每日平台数据报表';

DROP TABLE IF EXISTS `report_game_daily`;
CREATE TABLE `report_game_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `participate_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '参与人数',
  `investment_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总投入',
  `output_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总产出',
  `win_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总输赢',
  `count_day` date DEFAULT NULL COMMENT '结算日期',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`count_day`,`game_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='每日游戏投入产出报表';

DROP TABLE IF EXISTS `report_game_grade_daily`;
CREATE TABLE `report_game_grade_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `participate_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '参与人数',
  `investment_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总投入',
  `output_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总产出',
  `win_total` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '总输赢',
  `count_day` date DEFAULT NULL COMMENT '结算日期',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`count_day`,`game_id`,`grade_id`,`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='每日每个游戏场次投入产出报表';

DROP TABLE IF EXISTS `report_player_game_daily`;
CREATE TABLE `report_player_game_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账号名称',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '游戏名称',
  `grade_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `grade_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '房间名称',
  `investment_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总投入',
  `output_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总产出',
  `win_total` bigint(22) NOT NULL DEFAULT '0' COMMENT '总输赢',
  `count_day` date DEFAULT NULL COMMENT '结算日期',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`count_day`,`user_id`,`game_id`,`grade_id`,`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='每日玩家游戏投入产出报表';