package com.xmsy.server.zxyy.webhome.modules.manager.statistics.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.dao.OrderTakeMoneyDao;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.TakeMoneyReport;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.TakeMoneyTableReport;

/**
 * 取款统计
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-07 19:11:23
 */
@RestController
@RequestMapping("takemoneystatistics/takemoneystatistics")
public class TakeMoneyStatisticsController {
	@Autowired
	private OrderTakeMoneyDao orderTakeMoneyDao;
	
	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;

	/**
	 * 取款报表统计
	 */
	@RequestMapping("/list")
	@RequiresPermissions("takemoneystatistics:takemoneystatistics:list")
	public R list(@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		if(!StringUtils.isEmpty(startTime)) {
			startTime+=" 00:00:00";//给开始日期拼接上当天开始时间
		}
		if(!StringUtils.isEmpty(endTime)) {
			endTime+=" 23:59:59";//结束时间拼接上当天最晚时间
		}
		TakeMoneyReport takeMoneyReport = orderTakeMoneyDao.getTakeMoneyReport(startTime, endTime);
		return R.ok().put("takeMoneyReport", takeMoneyReport);
	}

	/**
	 * 充值报表统计
	 */
	@RequestMapping("/tableList")
	@RequiresPermissions("takemoneystatistics:takemoneystatistics:list")
	public R tableList(@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		if(!StringUtils.isEmpty(startTime)) {
			startTime+=" 00:00:00";//给开始日期拼接上当天开始时间
		}
		if(!StringUtils.isEmpty(endTime)) {
			endTime+=" 23:59:59";//结束时间拼接上当天最晚时间
		}
		List<TakeMoneyTableReport> takeMoneyReportList = orderTakeMoneyService.getTakeMoneyTableReport(startTime, endTime);
		if(takeMoneyReportList != null && !takeMoneyReportList.isEmpty()) {
			BigDecimal moneyWaitAmount = BigDecimal.ZERO;
			BigDecimal moneyCancelAmount = BigDecimal.ZERO;
			BigDecimal moneySuccessAmount = BigDecimal.ZERO;
			BigDecimal poundage = BigDecimal.ZERO;
			BigDecimal obtainAmount = BigDecimal.ZERO;
			BigDecimal num = BigDecimal.ZERO;
			for(TakeMoneyTableReport report : takeMoneyReportList) {
				if(report.getType()==Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
					report.setTypeName("余额取款");
				}
				else if(report.getType()==Constant.TakeMoneyType.COMMISSION.getValue()) {
					report.setTypeName("佣金取款");
				}
				
				moneyWaitAmount=moneyWaitAmount.add(MathUtil.getBigDecimal(report.getMoneyWaitAmount()));
				moneyCancelAmount=moneyCancelAmount.add(MathUtil.getBigDecimal(report.getMoneyCancelAmount()));
				moneySuccessAmount=moneySuccessAmount.add(MathUtil.getBigDecimal(report.getMoneySuccessAmount()));
				poundage=poundage.add(MathUtil.getBigDecimal(report.getConfirmedPoundage()));
				obtainAmount=obtainAmount.add(MathUtil.getBigDecimal(report.getObtainAmount()));
				num=num.add(MathUtil.getBigDecimal(report.getUnconfirmedNum()))
						.add(MathUtil.getBigDecimal(report.getCancelNum()))
						.add(MathUtil.getBigDecimal(report.getConfirmedNum()));
				BigDecimal total = MathUtil.getBigDecimal(report.getMoneyWaitAmount())
						.add(MathUtil.getBigDecimal(report.getMoneyCancelAmount()))
						.add(MathUtil.getBigDecimal(report.getMoneySuccessAmount()));
				report.setTotalAmount(total);
			}
			TakeMoneyTableReport totalReport = new TakeMoneyTableReport();
			totalReport.setTypeName("总计");
			totalReport.setMoneyWaitAmount(moneyWaitAmount);
			totalReport.setMoneyCancelAmount(moneyCancelAmount);
			totalReport.setMoneySuccessAmount(moneySuccessAmount);
			totalReport.setConfirmedPoundage(poundage);
			totalReport.setObtainAmount(obtainAmount);
			totalReport.setConfirmedNum(num.intValue());
			totalReport.setCancelNum(0);
			totalReport.setUnconfirmedNum(0);
			totalReport.setTotalAmount(moneyWaitAmount.add(moneyCancelAmount).add(moneySuccessAmount));
			takeMoneyReportList.add(totalReport);
		}
		return R.ok().put("takeMoneyReportList", takeMoneyReportList);
	}
}
