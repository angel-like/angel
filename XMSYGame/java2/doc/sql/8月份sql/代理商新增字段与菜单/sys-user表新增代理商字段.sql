	alter table sys_user add COLUMN `proxy_balance` bigint(20) DEFAULT '0' COMMENT '代理商余额'
	
	alter table sys_user add COLUMN `proxy_sale_amount` bigint(20) DEFAULT '0' COMMENT '代理商售卖金额'

	alter table sys_user add COLUMN  `member_user_id` bigint(20) DEFAULT NULL COMMENT 'user表的id，用于会员绑定成为代理商'
	
	
	
	
	
