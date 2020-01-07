package com.xmsy.server.zxyy.manager.modules.manager.userpassword.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserPasswordParam;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.entity.UserPasswordEntity;

/**
 * 用户密码表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserPasswordService extends IService<UserPasswordEntity> {

	PageUtils queryPage(Map<String, Object> params);

	// 新增密码
	void insertPassword(Long userId, String passWord, String takePassWord);

	/**
	 * 修改登录密码
	 * 
	 * @param UserPasswordParam
	 */
	void editLogin(UserPasswordParam param,String ip,String username);

	/**
	 * 修改取款密码
	 * 
	 * @param UserPasswordParam
	 */
	void editBank(UserPasswordParam param);

	// 验证登陆密码
	Boolean validateLoginPassword(String passWord, Long userId);

	// 修改登陆密码
	void updateLoginPassWord(String token, String verificationWord, String PassWord);

	// 修改取款密码
	void updateTakeMoneyPassWord(String token, String verificationWord, String PassWord);

	// 验证取款密码
	Boolean validateBankPassword(String passWord, Long userId);
}
