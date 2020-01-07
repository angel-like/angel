ALTER TABLE `game_record`
ADD COLUMN `grade_number`  int(11) NOT NULL DEFAULT 0 COMMENT '场次编号' AFTER `grade_name`;