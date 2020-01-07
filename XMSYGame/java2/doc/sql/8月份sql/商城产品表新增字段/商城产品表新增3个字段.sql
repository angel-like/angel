ALTER TABLE shop_product add  `product_one_price` bigint(22) DEFAULT NULL COMMENT '产品单价';
ALTER TABLE shop_product add  `product_total_price` bigint(22) DEFAULT NULL COMMENT '产品总价';
ALTER TABLE shop_product add  `discount` decimal(10,8) DEFAULT NULL COMMENT '折扣';