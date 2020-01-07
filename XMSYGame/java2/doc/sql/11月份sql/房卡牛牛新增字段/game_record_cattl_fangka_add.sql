ALTER TABLE `game_record_cattl_fangka`
MODIFY COLUMN `cards_int`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '牌型' AFTER `coins_after`,
ADD COLUMN `banker`  bit(1) NOT NULL DEFAULT 0 COMMENT '是否是庄家' AFTER `multiple`;
