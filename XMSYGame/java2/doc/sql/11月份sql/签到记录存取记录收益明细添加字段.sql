 
 #�û��˻�����ȡ��¼����� user_account   �ֶ�����
 ALTER TABLE `user_balance_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '�û��˺�';
	
	#�û��˻���������¼����� user_account   �ֶ�����
	 ALTER TABLE `user_profit_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '�û��˺�';
	
	#�û�ǩ����¼����� user_account   �ֶ�����
	 ALTER TABLE `sign_user_record`
  ADD COLUMN`user_account` varchar(100) NOT NULL DEFAULT '' COMMENT '�û��˺�';