package com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.ChannelStatisticsParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserAmountParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserChannelParam;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 渠道统计参数dao
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@Mapper
public interface ChannelStatisticsDao extends BaseMapper<ChannelStatisticsParam> {	
	/**
	 * 查询所有的渠道记录
	 * @return
	 */
	List<ChannelStatisticsParam> selectAllChannelData(Pagination page,@Param("channelStatistics") ChannelStatisticsParam channelStatisticsParam);
	/**
	 *  通过渠道码查询所有的会员数量
	 * @return
	 */
	Long getCountUserByChannelCode(@Param("channelStatistics") ChannelStatisticsParam channelStatistics);
	/**
	 * 通过渠道码获取所有会员id
	 * @param channelStatistics
	 * @return
	 */
	List<Long> selectUserIdByChannelCode(@Param("channelStatistics") ChannelStatisticsParam channelStatistics);
	
	/**
	 * 通过会员id集合查询打码量
	 * @param list
	 * @return
	 */
	Long getValitBetByUserId(@Param("list")List<Long> list);
	/**
	 * 通过会员id集合查询充值金额
	 * @param list
	 * @return
	 */
	Long getAmountByUserId(@Param("list")List<Long> list);
	
	/**
	 * 通过会员id集合查询取款金额
	 * @param list
	 * @return
	 */
	Long getDrawAmountByUserId(@Param("list")List<Long> list);
	
	
	
	/**
	 * 通过会员渠道码查询要展示的会员信息对象UserChannelParam
	 * @param channelStatistics
	 * @return
	 */
	List<UserChannelParam> getUserChannelParamListByChannelCode(Pagination page,@Param("channelStatistics") ChannelStatisticsParam channelStatistics);

	/**
	 * 通过会员渠道码查询要展示的会员信息对象UserChannelParam
	 * @param channelStatistics
	 * @return
	 */
	List<UserAmountParam> getUserAmountParamListByChannelCode(Pagination page, @Param("channelStatistics") ChannelStatisticsParam channelStatistics);


	/**
	 * 通过会员id查找会员打码量
	 * @param id
	 * @return
	 */
	Long getUserValidBetByUserId(@Param("id") Long id);

	/**
	 * 通过会员id查找会员充值金额
	 * @param id
	 * @return
	 */
	Long getUserAmountByUserId(@Param("id") Long id);
}
