 
 #用户账户金额存取记录表添加 user_account   字段属性
 ALTER TABLE `user_balance_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号';
	
	#用户账户金额收益记录表添加 user_account   字段属性
	 ALTER TABLE `user_profit_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号';
	
	#用户签到记录表添加 user_account   字段属性
	 ALTER TABLE `sign_user_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '用户账号';