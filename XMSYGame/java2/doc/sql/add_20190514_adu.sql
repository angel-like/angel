ALTER TABLE `user_hierarchy`
ADD COLUMN `game_rate`  decimal(5,2) NOT NULL DEFAULT 0 COMMENT '游戏胜率' AFTER `vip_enable`;

ALTER TABLE `user_rebate`
ADD COLUMN `code_multiple`  decimal(5,2) NOT NULL DEFAULT 1 COMMENT '打码倍数' AFTER `water_rate`;



