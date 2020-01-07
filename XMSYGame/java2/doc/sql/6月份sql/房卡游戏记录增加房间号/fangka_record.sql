ALTER TABLE `game_record`
ADD COLUMN `room_no`  varchar(100) NULL DEFAULT '' COMMENT '房间号--针对房卡游戏' AFTER `robot`;

ALTER TABLE `game_record_shisanshui_fangka`
ADD COLUMN `room_no`  varchar(100) NULL DEFAULT '' COMMENT '房间号' AFTER `round`;

ALTER TABLE `game_record_cattl_fangka`
ADD COLUMN `room_no`  varchar(100) NULL DEFAULT '' COMMENT '房间号' AFTER `round`;
s