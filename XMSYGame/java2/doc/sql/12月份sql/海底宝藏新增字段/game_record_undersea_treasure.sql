ALTER TABLE `game_record_undersea_treasure`
ADD COLUMN `mini_game_multiple`  int(9) NOT NULL DEFAULT 1 COMMENT '小游戏倍数' AFTER `coins_after`,
ADD COLUMN `mini_game_rewards`  bigint(20) NOT NULL DEFAULT 0 COMMENT '小游戏奖励' AFTER `mini_game_multiple`;