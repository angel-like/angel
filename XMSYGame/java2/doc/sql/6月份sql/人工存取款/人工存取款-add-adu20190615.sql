ALTER TABLE `order_administrator_recharge`
ADD COLUMN `vip_discount`  decimal(22,2) NULL DEFAULT 0 COMMENT 'vip优惠金额' AFTER `discount_amount`;

