ALTER TABLE `game_info`
ADD COLUMN `finished`  bit(1) NULL DEFAULT 0 COMMENT '是否完成（0否，1是）' AFTER `channel`;