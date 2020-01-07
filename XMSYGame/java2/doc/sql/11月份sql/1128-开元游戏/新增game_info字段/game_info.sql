# game_info表添加channel  字段属性
	 ALTER TABLE `game_info`
  ADD COLUMN`channel` bigint(22) NOT NULL DEFAULT 0 COMMENT '渠道 0-本地 1-开元';