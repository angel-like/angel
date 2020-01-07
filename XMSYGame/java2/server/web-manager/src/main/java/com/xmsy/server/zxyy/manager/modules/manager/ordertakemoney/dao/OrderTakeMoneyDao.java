package com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatistics;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.TakeMoneyReport;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.TakeMoneyTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface OrderTakeMoneyDao extends BaseMapper<OrderTakeMoneyEntity> {
	// 充值报表
	TakeMoneyReport getTakeMoneyReport(@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 获取取款记录
	List<OrderTakeMoneyResult> getTakeMoneyRecord(Pagination page, @Param("userId") Long userId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 取款表格报表
	List<TakeMoneyTableReport> getTakeMoneyTableReport(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 根据会员id统计总的取款金额 取款次数
	 *
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatistics orderTakeStatistics(@Param("userId") Long userId);
	/**
	 * 根据会员id统计总的取款金额 取款次数
	 *
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatistics orderTakeStatisticsV2(@Param("userId") Long userId);

	/**
	 * 根据会员id统计总的取款金额 取款次数等
	 * @param userI
	 * @return
	 */
	OrderTakeMoneyStatisticsTwo orderTakeTakeNum(@Param("userId") Long userId);


	/**
	 * 根据会员id统计总的取款金额 取款次数
	 *
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyEntity getLastTakeMoney(@Param("userId") Long userId);

	boolean updateAll(@Param("entity") OrderTakeMoneyEntity orderTakeMoneyEntity);


	/**
	 * 获取最新一条取款中的金额和时间
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatisticsThree getLastTakeAmountByUserId(@Param("userId") Long userId);

	/**
	 * 获取指定类型指定日期取款  订单总数量（orderNum） 成功的订单（successOrderNum） 订单总金额（takeAmount）
	 * @param startDate
	 * @param endDate
	 * @param accountType 取款类型(0：支付宝转账，1:银行转账，2：微信)
	 * @return
	 */
	Map<String, Object> sumTakeMoneyAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("accountType") Integer accountType);
	Map<String, Object> sumTakeOnlineMoneyAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
										   @Param("accountType") Integer accountType);
	Map<String, Object> sumTakeAdmiMoneyAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
													   @Param("accountType") Integer accountType);
	/**
	 * 为了获取csv下载数据而进行的查询
	 * @param orderTakeMoney
	 * @return
	 */
	List<Map<String,Object>> selectListOrderTakeMoney(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);

	List<OrderTakeMoneyEntity> withdrawalRecord(@Param("user") UserParamFour user);

	//获取取款金额总数
    int takeMoneyNum(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);
	//获取已完成取款金额总数
	int conutStatus(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);


    /**
     * 获取取款金额总计
     * @param orderTakeMoney
     * @return
     */
    Long takeMoneytotal(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);
	/**
	 * 获取已完成金额总计
	 * @param orderTakeMoney
	 * @return
	 */
	Long sumStatus(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);
	/**
	 * 获取总手续费
	 */
	BigDecimal poundageTotal(@Param("orderTakeMoney") OrderTakeMoneyEntity orderTakeMoney);
	/**
	 * 统计
	 *
	 * @param id
	 */
	Map<String, Object> count(@Param("id") Long id);
	/**
	 * 根据会员id统计当前会员 总的取款金额+行政费(包含未审核 跟已完成)
	 * @param userId
	 * @return
	 */
	BigDecimal totalOrderTakeAmountTake(@Param("userId") Long userId,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
