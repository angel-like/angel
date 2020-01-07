ALTER TABLE `order_administrator_recharge`
ADD COLUMN `discount_amount`  decimal(22,2) NULL DEFAULT 0 COMMENT '优惠金额' AFTER `designated`;

