package com.xmsy.server.zxyy.schedule.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.xmsy.server.zxyy.schedule.server.OrderMonthService;
import com.xmsy.server.zxyy.schedule.server.RechargeOrderService;
import com.xmsy.server.zxyy.schedule.server.TakeMoneyOrderService;
import com.xmsy.server.zxyy.schedule.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单月统计 定时任务
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class OrderMonthScheduleTask {

	@Autowired
	private RechargeOrderService rechargeOrderService;
	@Autowired
	private TakeMoneyOrderService takeMoneyOrderService;
	@Autowired
	private OrderMonthService orderMonthService;

	//
	@Scheduled(cron = "0 0 6 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void rechargeByMonth() {
		if (DateUtils.getDayOfMonth(new Date()) != 1) {// 不是号
			log.warn("【rechargeByMonth】充值月统计是每月1号");
			return;
		}
		JSONArray dataList = null;
		try {
			dataList = rechargeOrderService.statisticsRechargeByMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dataList == null || dataList.isEmpty()) {
			log.warn("【rechargeByMonth】月统计的充值：无记录");
			return;
		}
		orderMonthService.saveRechargeOrderMonth(dataList);
	}

	@Scheduled(cron = "0 0 1 * * ? ")
	// @Scheduled(fixedRate = 60000 * 5)
	public void takeMoneyByMonth() {
		if (DateUtils.getDayOfMonth(new Date()) != 1) {// 不是号
			log.warn("【takeMoneyByMonth】提现月统计是每月1号");
			return;
		}
		JSONArray dataList = null;
		try {
			dataList = takeMoneyOrderService.statisticsTakeMoneyByMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dataList == null || dataList.isEmpty()) {
			log.warn("【takeMoneyByMonth】月统计的取现订单：无记录");
			return;
		}
		orderMonthService.saveTakeMoneyOrderMonth(dataList);
	}

}
