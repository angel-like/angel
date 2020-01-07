ALTER TABLE `order_recharge`
MODIFY COLUMN `order_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号' AFTER `version`;

ALTER TABLE `user_transaction_record`
MODIFY COLUMN `order_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '业务ID 订单编号' AFTER `type`;