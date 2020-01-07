package com.xmsy.server.zxyy.calculate.modules.manager.user.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.param.UpdateCoinParam;

/**
 * 用户信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserService extends IService<UserEntity> {


	/**
	 * 用户充值的同时直接额度转换
	 */
	void rechargeAndChanger(UserEntity user, BigDecimal money, Long coin);
	
	/**
	 * 修改是否首冲状态
	 */
	void updateUserFirstRecharge(Long id);
	/**
	 * 撤销充值余额
	 */
	void revokeMoney(OrderRechargeEntity orderRecharge)throws Exception;
	
	void updateAgentCommission(Long userId,UserEntity agentUser,BigDecimal commission);

	/**
	 * 修改用户金币
	 * 
	 * @param enterGameParam
	 */
	void updateUserCoin(UpdateCoinParam updateCoinParam);
	
	void updateUserCoin(UserEntity user, Long coin, int type, int detailType);
	/**
	 * 根据用户ID修改用户金币，余额，佣金等
	 * 
	 * @param user
	 */
	void updateUserWalletByUserId(UserEntity user);
}
