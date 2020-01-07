ALTER TABLE `webhome`.`order_take_money` 
ADD COLUMN `user_need_bet` decimal(22, 2) NOT NULL COMMENT '总需要打码数(等级正常打码数+等级优惠打码数+上次剩余打码-等级放宽打码)' AFTER `take_amount`,
ADD COLUMN `user_valid_bet` decimal(22, 2) NOT NULL COMMENT '当前用户有效打码' AFTER `user_need_bet`;