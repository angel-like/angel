package com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

@RestController
@RequestMapping("ordertakemoney/ordertakemoney")
public class OrderTakeMoneyExportController extends AbstractController {

	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;

	/**
	 * 导出取款订单csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportCSVData")
	@RequiresPermissions("ordertakemoney:ordertakemoney:list")
	public void exportCSVData(OrderTakeMoneyEntity ordertakemoney, HttpServletResponse response) {

		List<Map<String, Object>> list = orderTakeMoneyService.selectListOrderTakeMoney(ordertakemoney);
		int takeMoneyNum = 0;
		Long takeMoneytotal = 0l;
		if (list != null && !list.isEmpty()) {
			// 获取取款笔数总计
			takeMoneyNum = orderTakeMoneyService.takeMoneyNum(ordertakemoney);
			// 获取取款金额总计
			takeMoneytotal = orderTakeMoneyService.takeMoneytotal(ordertakemoney);
		}
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("orderNo", "笔数总计:");
		totalMap.put("createTime", takeMoneyNum);
		totalMap.put("userAccount", "金额总计:");
		totalMap.put("takeAmount", takeMoneytotal);
		list.add(totalMap);
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getHeadRowStr()); // 头信息
		for (Map<String, Object> map : list) {
			writer.write(getRowStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	// 获取行信息
	private String[] getRowStr(Map<String, Object> map) {
		String[] rowData = new String[17];
		rowData[0] = ExportCSVUtils.getString(map.get("orderNo"));
		rowData[1] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[2] = ExportCSVUtils.getString(map.get("updateTime"));
		rowData[3] = ExportCSVUtils.getString(map.get("sysUserAccount"));
		rowData[4] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[5] = ExportCSVUtils.getString(map.get("takeAmount"));
		rowData[6] = ExportCSVUtils.getString(map.get("userNeedBet"));
		rowData[7] = ExportCSVUtils.getString(map.get("userValidBet"));
		rowData[8] = ExportCSVUtils.getString(map.get("poundage"));
		rowData[9] = ExportCSVUtils.getString(map.get("userSurplusCoin"));
		rowData[10] =  ExportCSVUtils.getString(map.get("incomeNo"));
		rowData[11] = ExportCSVUtils.getString(map.get("bankName"));
		rowData[12] = ExportCSVUtils.getString(map.get("accountName"));
		rowData[13] = ExportCSVUtils.getString(map.get("status"));
		rowData[14] = ExportCSVUtils.getString(map.get("accountType"));
		rowData[15] = ExportCSVUtils.getString(map.get("sysUserAccount"));
		rowData[16] = ExportCSVUtils.getString(map.get("betCancel"));
		return rowData;
	}

	// 获取头信息
	private String[] getHeadRowStr() {
		String[] rowData = new String[17];
		rowData[0] = "订单号";
		rowData[1] = "取款时间";
		rowData[2] = "操作时间";
		rowData[3] = "操作人";
		rowData[4] = "取款用户账号";
		rowData[5] = "取款金额";
		rowData[6] = "取款需要打码";
		rowData[7] = "用户有效打码";
		rowData[8] = "手续费";
		rowData[9] = "用户剩余金额";
		rowData[10] = "入款账号";
		rowData[11] = "银行名称";
		rowData[12] = "开户名";
		rowData[13] = "状态";
		rowData[14] = "取款类型";
		rowData[15] = "操作管理员";
		rowData[16] = "是否取消打码";
		return rowData;
	}

	// =============================导出佣金提现csv数据==========================
	/**
	 * 导出取款订单csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportCommissionCSVData")
	@RequiresPermissions("ordertakemoney:ordertakemoney:list")
	public void exportCommissionCSVData(OrderTakeMoneyEntity ordertakemoney, HttpServletResponse response) {

		List<Map<String, Object>> list = orderTakeMoneyService.selectListOrderTakeMoney(ordertakemoney);
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getCommissionHeadRowStr()); // 头信息
		for (Map<String, Object> map : list) {
			writer.write(getCommissionRowStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	// 获取行信息
	private String[] getCommissionRowStr(Map<String, Object> map) {
		String[] rowData = new String[12];
		rowData[0] = "\t"+ExportCSVUtils.getString(map.get("orderNo"));
		rowData[1] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[2] = ExportCSVUtils.getString(map.get("updateTime"));
		rowData[3] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[4] = ExportCSVUtils.getString(map.get("takeAmount"));
		rowData[5] = ExportCSVUtils.getString(map.get("poundage"));
		rowData[6] = ExportCSVUtils.getString(map.get("bankCart"));
		rowData[7] = ExportCSVUtils.getString(map.get("bankName"));
		rowData[8] = ExportCSVUtils.getString(map.get("accountName"));
		rowData[9] = ExportCSVUtils.getString(map.get("status"));
		rowData[10] = ExportCSVUtils.getString(map.get("accountType"));
		rowData[11] = ExportCSVUtils.getString(map.get("sysUserAccount"));
		return rowData;
	}

	// 获取头信息
	private String[] getCommissionHeadRowStr() {
		String[] rowData = new String[12];
		rowData[0] = "订单号";
		rowData[1] = "取款时间";
		rowData[2] = "操作时间";
		rowData[3] = "取款用户账号";
		rowData[4] = "取款金额";
		rowData[5] = "手续费";
		rowData[6] = "入款账号";
		rowData[7] = "银行名称";
		rowData[8] = "开户人";
		rowData[9] = "状态";
		rowData[10] = "取款类型";
		rowData[11] = "操作管理员";
		return rowData;
	}
}
