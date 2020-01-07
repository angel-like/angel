package com.xmsy.server.zxyy.manager.modules.manager.userbalance.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;


/**
 * 用户余额宝金额表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
public interface UserBalanceService extends IService<UserBalanceEntity> {
	/**
	 * 通过id集合查找相关用户的用户余额
	 * @param idList
	 * @return
	 */
	List<UserBalanceEntity> findUserBalanceByUserId(@Param("idList")List<Long> idList);

	/**
	 * 总金额
	 * @return
	 */
	Long AllPrize(UserBalanceEntity userBalance);
	/**
	 * 总收益
	 * @return
	 */
	Long AllMoney(UserBalanceEntity userBalance);
	/**
	 * 昨日总收益
	 * @return
	 */
	Long AllProfitYesterday(UserBalanceEntity userBalance);
}

