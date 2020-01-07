ALTER TABLE `game_record`
  ADD COLUMN`platform_code` bigint(22) NOT NULL DEFAULT 0 COMMENT '平台 0-自营游戏 1-开元 2-ig';
UPDATE   game_record   a   SET a.platform_code =1 WHERE a.game_id in(920,740,620,650,1370,1690,1610,1860,630,1960,1970,1940,600);
INSERT INTO sys_schedule (create_time,update_time,schedule_name) VALUES(now(),now(),'report_provider_daily_schedule');

CREATE TABLE `report_provider_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `provider_code` bigint(20) DEFAULT '0' COMMENT '运营商编码',
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
  UNIQUE KEY `user_uk` (`count_day`,`provider_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8 COMMENT='每日平台投入产出报表';
