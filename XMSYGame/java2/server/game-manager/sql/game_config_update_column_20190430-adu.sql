ALTER TABLE `game_config`
MODIFY COLUMN `val`  decimal(22,2) NOT NULL DEFAULT 0.00 COMMENT '游戏概率' AFTER `name`;

ALTER TABLE `game_config`
ADD COLUMN `start_val`  bigint(22) NOT NULL DEFAULT 0 COMMENT '开始值' AFTER `name`,
ADD COLUMN `end_val`  bigint(22) NOT NULL DEFAULT 0 COMMENT '结束值' AFTER `start_val`,
DROP INDEX `gameid_name_uk` ,
ADD UNIQUE INDEX `gameid_name_uk` (`game_id`, `name`, `start_val`, `end_val`) USING BTREE ;