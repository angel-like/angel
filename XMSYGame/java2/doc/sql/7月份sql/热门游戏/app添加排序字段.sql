ALTER TABLE `app_popular_games`
ADD COLUMN `sort`  int(10) NULL DEFAULT 0 COMMENT '排序' AFTER `game_id`;