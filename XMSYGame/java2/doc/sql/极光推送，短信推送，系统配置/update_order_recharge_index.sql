ALTER TABLE `order_recharge`
DROP INDEX `index_create_time` ,
ADD INDEX `index_user_id` (`user_id`) USING BTREE ;