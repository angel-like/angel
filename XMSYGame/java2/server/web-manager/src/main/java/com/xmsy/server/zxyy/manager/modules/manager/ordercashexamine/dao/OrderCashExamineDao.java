package com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;

/**
 * 取款稽查表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 16:25:31
 */
@Mapper
public interface OrderCashExamineDao extends BaseMapper<OrderCashExamineEntity> {

	// 获取稽查记录
	public List<OrderCashExamineEntity> findOrderCashExamines(
			@Param("orderCashExamineEntity") OrderCashExamineEntity orderCashExamineEntity,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 获取最近一条稽查记录
	public OrderCashExamineEntity findRecentOrderCashExamine(@Param("userId") Long userId);

	/**
	 * 查找最近一条稽查记录 不管状态
	 * 
	 * @param userId
	 * @return
	 */
	public OrderCashExamineEntity findRecentOneOrderCashExamine(@Param("userId") Long userId, @Param("id") Long id);

	/**
	 * 
	 * .修改用户的需要打码数量
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateOrderCashExamineUserNeedBet(@Param("id") Long id,
			@Param("userNeedBet") BigDecimal userNeedBet);
    public  boolean  updateCashBet (@Param("userId") Long userId,@Param("userValidBet") BigDecimal userValidBet);
	public  boolean  updateValidCashBet (@Param("userId") Long userId);
	boolean    updateCashBetAndTime(@Param("userId") Long userId,@Param("userValidBet") BigDecimal userValidBet,@Param("updateTime") Date updateTime);

}
