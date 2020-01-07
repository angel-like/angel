package com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.controller;

import java.util.List;

import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserAmountParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.ChannelStatisticsParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity.UserChannelParam;
import com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.service.ChannelStatisticsService;

/**
 * 渠道统计
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-03 17:05:09
 */
@RestController
@RequestMapping("channelstatistics/channelstatistics")
public class ChannelStatisticsController {
	@Autowired
	private ChannelStatisticsService channelStatisticsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("channelstatistics:channelstatistics:list")
	public R list(ChannelStatisticsParam channelStatisticsParam, PageParam pageParam) {
		Page<ChannelStatisticsParam> page = new Page<ChannelStatisticsParam>(pageParam.getPage(), pageParam.getLimit());
		// 获取所有渠道，通过记录去遍历一条条数据，整合出数据再set进当前实体类
		List<ChannelStatisticsParam> list = channelStatisticsService.selectAllChannelData(page, channelStatisticsParam);
		for (ChannelStatisticsParam channelstatistics : list) {
			if (StringUtils.isNotEmpty(channelStatisticsParam.getStartTime())
					&& StringUtils.isNotEmpty(channelStatisticsParam.getEndTime())) {
				channelstatistics.setStartTime(channelStatisticsParam.getStartTime());
				channelstatistics.setEndTime(channelStatisticsParam.getEndTime());
			}
			// 通过渠道码去获取当前会员数量
			Long coutUser = channelStatisticsService.getCountUserByChannelCode(channelstatistics);
			channelstatistics.setUserNum(coutUser);
			if(coutUser==0) {
				channelstatistics.setValidBet(0l);
				channelstatistics.setAmount(0l);
				continue;
			}
			
			// 通过渠道码获取所有会员id
			List<Long> listUserId = channelStatisticsService.selectUserIdByChannelCode(channelstatistics);
			Long validBet = 0L;
			Long amount = 0L;
			Long takeAmount = 0L;
			if (!CollectionUtils.isEmpty(listUserId)) {
				// 通过会员id集合查询打码量
				validBet = channelStatisticsService.getValitBetByUserId(listUserId);
				if (validBet == null) {
					validBet = 0L;
				}
				// 通过会员id集合查询充值总额
				amount = channelStatisticsService.getAmountByUserId(listUserId);
				if (amount == null) {
					amount = 0L;
				}
				
				// 通过会员id集合查询取款总额
				takeAmount = channelStatisticsService.getDrawAmountByUserId(listUserId);
				if (takeAmount == null) {
					takeAmount = 0L;
				}
			}
			channelstatistics.setValidBet(validBet);
			channelstatistics.setAmount(amount);
			channelstatistics.setTakeAmount(takeAmount);
		}
		page.setRecords(list);
		return R.ok().put("page",
				new PageUtils(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
	}

	/**
	 * 会员列表
	 */
	@RequestMapping("/listusernum")
	public R listUserNum(ChannelStatisticsParam channelStatisticsParam, PageParam pageParam) {
		Page<UserChannelParam> page = new Page<UserChannelParam>(pageParam.getPage(), pageParam.getLimit());
		// 通过渠道代码查询出UserChannelParam对象信息
		List<UserChannelParam> listUserChannelParam = channelStatisticsService
				.getUserChannelParamListByChannelCode(page, channelStatisticsParam);
		for (UserChannelParam userChannelParam : listUserChannelParam) {
			// 查询会员打码量
			Long userValidBet = channelStatisticsService.getUserValidBetByUserId(userChannelParam.getUserId());
			userChannelParam.setUservalidBet(userValidBet == null ? 0 : userValidBet);
		}
		page.setRecords(listUserChannelParam);
		return R.ok().put("page",
				new PageUtils(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
	}

	/**
	 * 会员充值金额列表
	 */
	@RequestMapping("/listamount")
	public R listAmount(ChannelStatisticsParam channelStatisticsParam, PageParam pageParam) {
		Page<UserAmountParam> page = new Page<UserAmountParam>(pageParam.getPage(), pageParam.getLimit());
		// 通过渠道代码查询出UserChannelParam对象信息
		List<UserAmountParam> listUserAmountParam = channelStatisticsService
				.getUserAmountParamListByChannelCode(page, channelStatisticsParam);
		for (UserAmountParam userAmountParam : listUserAmountParam) {
			// 查询会员充值金额
			Long userAmount = channelStatisticsService.getUserAmountByUserId(userAmountParam.getUserId());
			userAmountParam.setUserAmount(userAmount == null ? 0 : userAmount);
		}
		page.setRecords(listUserAmountParam);
		return R.ok().put("page",
				new PageUtils(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
	}

}
