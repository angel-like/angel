package com.xmsy.server.zxyy.manager.modules.manager.userbalance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;

/**
 * 用户余额宝金额表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@Mapper
public interface UserBalanceDao extends BaseMapper<UserBalanceEntity> {
	/**
	 * 增加或者减去 传入实体类用户里的金额
	 * @param entity
	 * @return
	 */
	Integer updateUserBalanceByUserId(UserBalanceEntity entity);
	/**
	 * 通过id集合查找相关用户的用户余额
	 * @param idList
	 * @return
	 */
	List<UserBalanceEntity> findUserBalanceByUserId(@Param("idList")List<Long> idList);
	/**
	 * 统计
	 *
	 * @param id
	 */
	Map<String, Object> count(@Param("id") Long id);

	/**
	 * 计算总金额
	 */
	Long AllMoney(@Param("userBalance") UserBalanceEntity userBalance);
	/**
	 * 计算总收益
	 */
	Long AllPrize(@Param("userBalance") UserBalanceEntity userBalance);
	/**
	 * 计算昨日总收益
	 */
	Long AllProfitYesterday(@Param("userBalance") UserBalanceEntity userBalance);
}
