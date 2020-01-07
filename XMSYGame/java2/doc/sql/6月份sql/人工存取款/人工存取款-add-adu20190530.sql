ALTER TABLE `order_administrator_recharge`
ADD COLUMN `coin`  bigint(22) NULL DEFAULT 0 COMMENT '金币' AFTER `amount`;

