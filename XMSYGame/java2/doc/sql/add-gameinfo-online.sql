ALTER TABLE `game_info`
ADD COLUMN `online_min`  bigint(20) NULL DEFAULT 0 COMMENT '最小在线人数' AFTER `limit_lower`,
ADD COLUMN `online_max`  bigint(20) NULL DEFAULT 0 COMMENT '最大在线人数' AFTER `online_min`;