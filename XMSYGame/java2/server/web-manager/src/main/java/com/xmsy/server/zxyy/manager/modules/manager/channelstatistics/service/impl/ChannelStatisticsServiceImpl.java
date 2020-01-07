package com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.service.impl;

import java.util.List;

import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserAmountParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.dao.ChannelStatisticsDao;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.ChannelStatisticsParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserChannelParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.service.ChannelStatisticsService;
@Service("channelStatisticsService")
public class ChannelStatisticsServiceImpl implements ChannelStatisticsService{
	@Autowired
	private ChannelStatisticsDao channelStatisticsDao;
	@Override
	public List<ChannelStatisticsParam> selectAllChannelData(Pagination page,
			ChannelStatisticsParam channelStatisticsParam) {
		return channelStatisticsDao.selectAllChannelData(page, channelStatisticsParam);
	}

	@Override
	public Long getCountUserByChannelCode(ChannelStatisticsParam channelStatistics) {
		return channelStatisticsDao.getCountUserByChannelCode(channelStatistics);
	}

	@Override
	public List<Long> selectUserIdByChannelCode(ChannelStatisticsParam channelStatistics) {
		return channelStatisticsDao.selectUserIdByChannelCode(channelStatistics);
	}

	@Override
	public Long getValitBetByUserId(List<Long> list) {
		return channelStatisticsDao.getValitBetByUserId(list);
	}

	@Override
	public Long getAmountByUserId(List<Long> list) {
		return channelStatisticsDao.getAmountByUserId(list);
	}

	@Override
	public List<UserChannelParam> getUserChannelParamListByChannelCode(Pagination page,
			ChannelStatisticsParam channelStatistics) {
		return channelStatisticsDao.getUserChannelParamListByChannelCode(page, channelStatistics);
	}

	@Override
	public List<UserAmountParam> getUserAmountParamListByChannelCode(Pagination page,
																	 ChannelStatisticsParam channelStatistics) {
		return channelStatisticsDao.getUserAmountParamListByChannelCode(page, channelStatistics);
	}

	@Override
	public Long getUserValidBetByUserId(Long id) {
		return channelStatisticsDao.getUserValidBetByUserId(id);
	}

	@Override
	public Long getUserAmountByUserId(Long id) {
		return channelStatisticsDao.getUserAmountByUserId(id);
	}

	@Override
	public Long getDrawAmountByUserId(List<Long> list) {
		return channelStatisticsDao.getDrawAmountByUserId(list);
	}

}
