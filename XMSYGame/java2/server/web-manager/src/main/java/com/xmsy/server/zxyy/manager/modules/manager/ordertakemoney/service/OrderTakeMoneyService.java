package com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyLockingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.TakeMoneyTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 取款记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
public interface OrderTakeMoneyService extends IService<OrderTakeMoneyEntity> {
	
	/**
	 * 查询单个会员取款记录
	 * 
	 */
	List<OrderTakeMoneyEntity> withdrawalRecord(UserParamFour user);

	/**
	 * 新增现金取款订单
	 * 
	 * @param takeMoneyRecordEntity
	 * @param UserStatisticsEntity
	 * @throws Exception
	 */
	void saveTakeMoneyOrder(OrderTakeMoneyEntity takeMoneyRecordEntity, UserEntity user) throws Exception;

	/**
	 * 新增佣金现金取款订单
	 * 
	 * @param takeMoneyRecordEntity
	 * @param UserStatisticsEntity
	 */
	void saveTakeCommissionOrder(OrderTakeMoneyEntity orderTakeMoneyEntity, UserEntity user);

	// 提款订单确认
	void orderTakeMoneyConfirm(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 提款订单取消
	void orderTakeMoneyCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 提款订单取消下注
	void orderTakeMoneyBetCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 查询用户取款记录
	Page<OrderTakeMoneyResult> getTakeMoneyRecord(PageParam pageParam, Long userId, String startTime, String endTime);

	// 取款表格报表
	List<TakeMoneyTableReport> getTakeMoneyTableReport( String startTime,
			 String endTime);

	boolean updateAll(OrderTakeMoneyEntity orderTakeMoneyEntity);

	int batchLocking(OrderTakeMoneyLockingEntity orderTakeMoneyEntity);
	
	/**
	 * 根据会员id统计总的取款金额 取款次数
	 * 
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatisticsTwo orderTakeTakeNum(Long userId);
	
	/**
	 * 获取最新一条取款中的金额和时间
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatisticsThree getLastTakeAmountByUserId(Long userId);
	/**
	 * 获取指定类型指定日期取款  订单总数量（orderNum） 成功的订单（successOrderNum） 订单总金额（takeAmount）
	 * @param startDate
	 * @param endDate
	 * @param accountType 取款类型(0：支付宝转账，1:银行转账，2：微信)
	 * @return
	 */
	Map<String, Object> sumTakeMoneyAmount( String startDate, String endDate,
			 Integer accountType);
	/**
	 * 为了获取csv下载数据而进行的查询
	 * @param orderTakeMoney
	 * @return
	 */
	List<Map<String,Object>> selectListOrderTakeMoney(OrderTakeMoneyEntity orderTakeMoney);
	
	//获取取款金额总数
    int takeMoneyNum( OrderTakeMoneyEntity orderTakeMoney);
	//获取已完成取款金额总数
	int conutStatus( OrderTakeMoneyEntity orderTakeMoney);
    
    
    /**
     * 获取取款金额总计
     * @param orderTakeMoney
     * @return
     */
    Long takeMoneytotal( OrderTakeMoneyEntity orderTakeMoney);
	/**
	 * 获取已完成取款金额总计
	 * @param orderTakeMoney
	 * @return
	 */
	Long sumStatus( OrderTakeMoneyEntity orderTakeMoney);

	/**
	 * 获取总手续费
	 * @param orderTakeMoney
	 * @return
	 */
	BigDecimal poundageTotal(OrderTakeMoneyEntity orderTakeMoney);
	
	/**
	 * 根据会员id统计当前会员 总的取款金额+行政费(包含未审核 跟已完成)
	 * 
	 * @param userId
	 * @return
	 */
	BigDecimal totalOrderTakeAmountTake(Long userId,String startTime,String endTime);
}
