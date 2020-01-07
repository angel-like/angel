ALTER TABLE `game_record_undersea_treasure`
ADD COLUMN `mini_game`  bit(1) NOT NULL DEFAULT 0 COMMENT '是否小游戏' AFTER `mini_game_rewards`;
