package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.app.ranking.param.AppRechargeRankingParam;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatistics;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserOrderRechargeExchangeParam;

/**
 * 充值订单表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
@Mapper
public interface OrderRechargeDao extends BaseMapper<OrderRechargeEntity> {

	// 更新充值订单
	Integer updateOrderRecharge(@Param("merchantOrderNo") String merchantOrderNo, @Param("orderNo") String orderNo,
			@Param("completed") Integer completed, @Param("uncomfirmed") Integer uncomfirmed,
			@Param("rechargeTime") String rechargeTime);

	// 充值报表
	RechargeReport getRechargeReport(@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 充值表格报表
	List<RechargeTableReport> getRechargeTableReport(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 按照日期范围统计充值的金额
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<RankingListDayEntity> statisticsRechargeByDay(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 周排行榜，前7日，前20名
	 */
	List<AppRechargeRankingParam> weeklyRanking();

	/**
	 * 日排行榜，昨日，前20名
	 */
	List<AppRechargeRankingParam> dayRanking();

	/**
	 * 按照用户id统计充值的金额
	 *
	 * @param userId
	 * @return
	 */
	OrderRechargeStatistics statisticsRechargeByUserId(@Param("userId") Long userId);
	/**
	 * 按照用户id统计充值的金额
	 *
	 * @param userId
	 * @return
	 */
	OrderRechargeStatistics statisticsRechargeByUserIdV2(@Param("userId") Long userId);
	BigDecimal statisticsRechargeByUserIdV3(@Param("userId") Long userId);


	//
	/**
	 * 根据userid取得最后一次充值记录
	 *
	 * @param userId
	 * @return
	 */
	OrderRechargeEntity getLastRechargeByUserId(@Param("userId") Long userId);
	BigDecimal getLastRechargeByUser(@Param("userId") Long userId);


	List<UserOrderRechargeExchangeParam> rechargeExamineList(Pagination page, @Param("userId") Long userId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 获取指定类型指定日期充值总额
	 */
	int sumAmountForDate(@Param("date") String date, @Param("rechargeType") Integer rechargeType);

	/**
	 * 获取指定类型指定日期充值总额和总数
	 */
	Map<String, Object> sumRechargeAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("rechargeType") Integer rechargeType);

	boolean updateAll(@Param("orderRecharge") OrderRechargeEntity orderRecharge);

	/**
	 * 获取最新一条取款中的金额和时间
	 * @param userId
	 * @return
	 */
	OrderRechargeStatisticsThree getLastRechargeAmountByUserId(@Param("userId") Long userId);

	/**
	 * 根据会员id统计总的取款金额 取款次数等
	 * @param userI
	 * @return
	 */
	OrderRechargeStatisticsTwo orderRechargeNum(@Param("userId") Long userId);
	/**
	 * 为了获取csv下载数据而进行的查询
	 * @param orderRecharge
	 * @return
	 */
	List<Map<String,Object>> selectListOrderRecharge(@Param("orderRecharge") OrderRechargeEntity orderRecharge);

	/**
	 * 单个会员存款记录
	 * @param user
	 * @return
	 */
	List<OrderRechargeEntity> depositRecord(@Param("user") UserParamFour user);
	/**
	 * 统计
	 *
	 * @param id
	 */
	Map<String, Object> count(@Param("id") Long id);
}
