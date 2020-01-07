ALTER TABLE `recharge_channel`
ADD COLUMN `alias`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '支付类型别名' AFTER `md5`;

update recharge_channel r set r.alias='alipay' where id='12';
update recharge_channel r set r.alias='weixin' where id='13';
update recharge_channel r set r.alias='qqpay' where id='29';
update recharge_channel r set r.alias='jindongpay' where id='30';
update recharge_channel r set r.alias='quickpay' where id='31';
update recharge_channel r set r.alias='unionpay' where id='32';