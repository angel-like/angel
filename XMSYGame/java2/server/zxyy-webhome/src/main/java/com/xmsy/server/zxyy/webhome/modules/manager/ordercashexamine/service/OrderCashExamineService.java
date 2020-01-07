package com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;

/**
 * 取款稽查表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 16:25:31
 */
public interface OrderCashExamineService extends IService<OrderCashExamineEntity> {

	// 创建稽查审核订单
	BigDecimal saveCashExamine(OrderRechargeEntity orderRechargeEntity, UserEntity user) throws Exception;
	//计算充值金额所需的打码量
	BigDecimal countUserNeedBet(BigDecimal amount ,Long hierarchyId) ;

	// 绑定用户信息赠送金币,生成稽查记录
	boolean bindUserinfoGiftCashExamine(UserEntity user, Long coin) throws Exception;
	// 绑定用户信息赠送金币,生成稽查记录
	boolean bindUserinfoGiftCashExamine(UserEntity user, Long coin,BigDecimal codeMultiple) throws Exception;

	/**
	 * 充值结算上个用户稽查记录
	 * 
	 * @param user
	 * @param hierarchyEntity
	 * 
	 * @return OrderCashExamineEntity
	 */
	OrderCashExamineEntity updateCashExamineForRecharge(UserEntity user, UserHierarchyEntity hierarchyEntity,
			boolean isClose);

	/**
	 * 用户取款查看稽查记录，更新有效打码返回最新稽查情况
	 * 
	 * @param user
	 * 
	 * @return OrderCashExamineEntity
	 */
	OrderCashExamineEntity updateCashExamineForQuery(UserEntity user);

	/**
	 * 用户提交取款订单更新稽查记录
	 * 
	 * @param user
	 * @param takeMoney
	 * 
	 * @return OrderCashExamineEntity
	 */
	OrderCashExamineEntity updateCashExamineForTake(UserEntity user, BigDecimal takeMoney);

	// 查询审核稽查审核订单
	public Page<OrderCashExamineEntity> findOrderCashExamines(PageParam pageParam,
			OrderCashExamineEntity orderCashExamineEntity, String startTime, String endTime);

	// 查询最近的一条稽查记录
	public OrderCashExamineEntity findRecentOrderCashExamine(Long userId);
}
