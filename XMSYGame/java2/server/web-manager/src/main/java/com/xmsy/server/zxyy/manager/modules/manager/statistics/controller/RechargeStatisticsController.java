package com.xmsy.server.zxyy.manager.modules.manager.statistics.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.manager.utils.DateUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 会员(用户统计)
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-07 19:11:23
 */
@RestController
@RequestMapping("rechargestatistics/rechargestatistics")
public class RechargeStatisticsController {
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;
//	@Autowired
//	private PayConfigService payConfigService;

	/**
	 * 充值报表统计
	 */
	@RequestMapping("/list")
	@RequiresPermissions("rechargestatistics:rechargestatistics:list")
	public R list(@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		if (!StringUtils.isEmpty(startTime)) {
			startTime += " 00:00:00";// 给开始日期拼接上当天开始时间
		}
		if (!StringUtils.isEmpty(endTime)) {
			endTime += " 23:59:59";// 结束时间拼接上当天最晚时间
		}
		RechargeReport rechargeReport = orderRechargeService.getRechargeReport(startTime, endTime);
		return R.ok().put("rechargeReport", rechargeReport);
	}


	private List<RechargeTableReport> searchRechargeTable(String startTime, String endTime) {
		List<RechargeTableReport> rechargeReportList = orderRechargeService.getRechargeTableReport(startTime, endTime);
		if (rechargeReportList != null && !rechargeReportList.isEmpty()) {
			BigDecimal unconfirmedAmount = BigDecimal.ZERO;
			BigDecimal cancelAmount = BigDecimal.ZERO;
			BigDecimal confirmedAmount = BigDecimal.ZERO;
			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal num = BigDecimal.ZERO;
			for (RechargeTableReport report : rechargeReportList) {
				if (report.getRechargeType() == Constant.RechargeType.ADMIN.getValue()) {
					report.setRechargeTypeName(Constant.RechargeType.ADMIN.getName());
				} else if (report.getRechargeType() == Constant.RechargeType.THIRD.getValue()) {
					report.setRechargeTypeName(Constant.RechargeType.THIRD.getName());
					if (!StringUtils.isEmpty(report.getRechargePlatform())) {
						report.setRechargePlatformName(report.getRechargePlatform()+"—"+report.getRechargeChannel());
					}
				}
				if (report.getRechargeType() == Constant.RechargeType.BANK.getValue()) {
					report.setRechargeTypeName(Constant.RechargeType.BANK.getName());
				}
				unconfirmedAmount = unconfirmedAmount.add(MathUtil.getBigDecimal(report.getUnconfirmedAmount()));
				cancelAmount = cancelAmount.add(MathUtil.getBigDecimal(report.getCancelAmount()));
				confirmedAmount = confirmedAmount.add(MathUtil.getBigDecimal(report.getConfirmedAmount()));
				discountAmount = discountAmount.add(MathUtil.getBigDecimal(report.getConfirmedDiscountAmount()));
				num = num.add(MathUtil.getBigDecimal(report.getUnconfirmedNum()))
						.add(MathUtil.getBigDecimal(report.getCancelNum()))
						.add(MathUtil.getBigDecimal(report.getConfirmedNum()));
				BigDecimal total = MathUtil.getBigDecimal(report.getUnconfirmedAmount())
						.add(MathUtil.getBigDecimal(report.getCancelAmount()))
						.add(MathUtil.getBigDecimal(report.getConfirmedAmount()));
				report.setTotalAmount(total);
			}
			RechargeTableReport totalReport = new RechargeTableReport();
			totalReport.setRechargeTypeName("总计");
			totalReport.setUnconfirmedAmount(unconfirmedAmount);
			totalReport.setCancelAmount(cancelAmount);
			totalReport.setConfirmedAmount(confirmedAmount);
			totalReport.setConfirmedDiscountAmount(discountAmount);
			totalReport.setConfirmedNum(num.intValue());
			totalReport.setCancelNum(0);
			totalReport.setUnconfirmedNum(0);
			totalReport.setTotalAmount(unconfirmedAmount.add(cancelAmount).add(confirmedAmount));
			rechargeReportList.add(totalReport);
		}

		return rechargeReportList;
	}

	/**
	 * 充值报表统计
	 */
	@RequestMapping("/tableList")
	@RequiresPermissions("rechargestatistics:rechargestatistics:list")
	public R tableList(@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		List<RechargeTableReport>  rechargeReportList = searchRechargeTable(startTime, endTime);
		return R.ok().put("rechargeReportList", rechargeReportList);
	}

	/**
	 * 充值报表统计导出
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportCSV")
	@RequiresPermissions("rechargestatistics:rechargestatistics:list")
	public void exportCSV(@RequestParam(value = "startTime", required = false) String startTime,
					   @RequestParam(value = "endTime", required = false) String endTime, HttpServletResponse response) {
		List<RechargeTableReport>  rechargeReportList = searchRechargeTable(startTime, endTime);

		String fileName = "recharge_"+System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getTrialHeadRowStr()); // 头信息
		for (RechargeTableReport recharegeTab : rechargeReportList) {
			writer.write(getTrialRowStr(recharegeTab)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	//=============================================  首页    ===========================================
	/**
	 * 总充值金额
	 */
	@RequestMapping("/sumRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumRechargeAmount")
	public R sumRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(null, null, null);
		return R.ok().put("data", map);
	}

	/**
	 * 第三方总充值金额
	 */
	@RequestMapping("/sumThirdRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumThirdRechargeAmount")
	public R sumThirdRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(null, null,
				Constant.RechargeType.THIRD.getValue());
		return R.ok().put("data", map);
	}

	/**
	 * 银行卡总充值金额
	 */
	@RequestMapping("/sumBankRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumBankRechargeAmount")
	public R sumBankRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(null, null,
				Constant.RechargeType.BANK.getValue());
		return R.ok().put("data", map);
	}

	/**
	 * 今日总充值金额
	 */
	@RequestMapping("/sumTodayRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumTodayRechargeAmount")
	public R sumTodayRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getToday()), null,
				null);
		return R.ok().put("data", map);
	}
	/**
	 * 今日线下银行充值总金额
	 */
	@RequestMapping("/sumBankTodayRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumTodayRechargeAmount")
	public R sumBankTodayRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getToday()), null,
				Constant.RechargeType.BANK.getValue());
		return R.ok().put("data", map);
	}
	/**
	 * 今日人工充值总金额
	 */
	@RequestMapping("/sumManTodayRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumTodayRechargeAmount")
	public R sumManTodayRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getToday()), null,
				Constant.RechargeType.ADMIN.getValue());
		return R.ok().put("data", map);
	}
	/**
	 * 今日第三方充值总金额
	 */
	@RequestMapping("/sumThirdTodayRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumTodayRechargeAmount")
	public R sumThirdTodayRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getToday()), null,
				Constant.RechargeType.THIRD.getValue());
		Map<String, Object> disAmount = userTransactionRecordService.getDisAmount(DateUtils.format(DateUtils.getToday()), null);
		if(map==null) {
			map = new HashMap<>();
		}
		if(disAmount!=null) {
			map.putAll(disAmount);
		}
		return R.ok().put("data", map);
	}
	/**
	 * 昨日总充值金额
	 */
	@RequestMapping("/sumYesterdayRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumYesterdayRechargeAmount")
	public R sumYesterdayRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getYesterday()),
				DateUtils.format(DateUtils.getYesterday()), null);
		Map<String, Object> disAmount = userTransactionRecordService.getDisAmount(DateUtils.format(DateUtils.getYesterday()),
				DateUtils.format(DateUtils.getYesterday()));
		if(map==null) {
			map = new HashMap<>();
		}
		if(disAmount!=null) {
			map.putAll(disAmount);
		}
		return R.ok().put("data", map);
	}

	/**
	 * 七日总充值金额
	 */
	@RequestMapping("/sumWeekRechargeAmount")
	@RequiresPermissions("rechargestatistics:rechargestatistics:sumWeekRechargeAmount")
	public R sumWeekRechargeAmount() {
		Map<String, Object> map = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getDay(-6)), null,
				null);
		Map<String, Object> disAmount = userTransactionRecordService.getDisAmount(DateUtils.format(DateUtils.getDay(-6)), null);
		if(map==null) {
			map = new HashMap<>();
		}
		if(disAmount!=null) {
			map.putAll(disAmount);
		}
		return R.ok().put("data", map);
	}

	// 获取头信息
	private String[] getTrialHeadRowStr() {
		String[] rowData = new String[6];
		rowData[0] = "存款类型";
		rowData[1] = "平台名称";
		rowData[2] = "存款总次数";
		rowData[3] = "存款完成次数";
		rowData[4] = "确认订单金额";
		rowData[5] = "优惠金额";

		return rowData;
	}

	// 获取行信息
	private String[] getTrialRowStr(RechargeTableReport recharegeTab) {
		String[] rowData = new String[6];

		rowData[0] = ExportCSVUtils.getString(recharegeTab.getRechargeTypeName());
		rowData[1] = ExportCSVUtils.getString(recharegeTab.getRechargePlatformName());
		rowData[2] = ExportCSVUtils.getString(recharegeTab.getUnconfirmedNum()+recharegeTab.getConfirmedNum()+recharegeTab.getCancelNum());
		rowData[3] = ExportCSVUtils.getString(recharegeTab.getConfirmedNum());
		rowData[4] = ExportCSVUtils.getString(recharegeTab.getConfirmedAmount());
		rowData[5] = ExportCSVUtils.getString(recharegeTab.getConfirmedDiscountAmount());

		return rowData;
	}

}
