ALTER TABLE `game_record_paodekuai`
ADD COLUMN `total_bomb_num`  int(11) NOT NULL DEFAULT 0 COMMENT '总炸弹数' AFTER `bomb_num`;