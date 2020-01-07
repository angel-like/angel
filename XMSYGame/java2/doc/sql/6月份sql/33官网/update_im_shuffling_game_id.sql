ALTER TABLE `im_shuffling`
ADD COLUMN `game_id`  bigint(22) NULL DEFAULT NULL COMMENT '跳转路径' AFTER `url`;
