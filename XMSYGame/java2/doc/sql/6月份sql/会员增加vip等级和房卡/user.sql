ALTER TABLE `user`
ADD COLUMN `vip_id`  bigint(22) NULL DEFAULT 0 COMMENT 'VIP等级id' AFTER `risk_hierarchy_id`,
ADD COLUMN `room_card`  bigint(22) NULL DEFAULT 0 COMMENT '房卡' AFTER `coin`;

ALTER TABLE `user`
ADD COLUMN `headframe_id`  bigint(22) NULL DEFAULT 0 COMMENT '头像框id' AFTER `portrait`;
