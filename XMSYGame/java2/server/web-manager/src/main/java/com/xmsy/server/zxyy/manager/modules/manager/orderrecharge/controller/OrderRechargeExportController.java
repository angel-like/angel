package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

@RestController
@RequestMapping("orderrecharge/orderrecharge")
public class OrderRechargeExportController extends AbstractController {

	@Autowired
	private OrderRechargeService orderRechargeService;

	/**
	 * 导出银行卡订单csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportBankCSVData")
	@RequiresPermissions("orderrecharge:orderrecharge:list")
	public void exportCSVData(OrderRechargeEntity orderrecharge, HttpServletResponse response) {
		List<Map<String, Object>> list = orderRechargeService.selectListOrderRecharge(orderrecharge);
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
		String[] rowData = new String[15];
		rowData[0] = ExportCSVUtils.getString(map.get("orderNo"));
		rowData[1] = ExportCSVUtils.getString(map.get("depositDate"));
		rowData[2] = ExportCSVUtils.getString(map.get("rechargeTime"));
		rowData[3] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[4] = ExportCSVUtils.getString(map.get("sysUserAccount"));
		rowData[5] = ExportCSVUtils.getString(map.get("amount"));
		rowData[6] = ExportCSVUtils.getString(map.get("depositName"));
		rowData[7] = ExportCSVUtils.getString(map.get("depositBank"));
		rowData[8] = ExportCSVUtils.getString(map.get("incomeBank"));
		rowData[9] = ExportCSVUtils.getString(map.get("incomeBankAccount"));
		rowData[10] = ExportCSVUtils.getString(map.get("payee"));
		rowData[11] = ExportCSVUtils.getString(map.get("status"));
		rowData[12] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[13] = ExportCSVUtils.getString(map.get("fristrecharge"));
		rowData[14] = ExportCSVUtils.getString(map.get("discountAmount"));
		return rowData;
	}

	// 获取头信息
	private String[] getHeadRowStr() {
		String[] rowData = new String[15];
		rowData[0] = "订单号";
		rowData[1] = "提交时间";
		rowData[2] = "充值时间";
		rowData[3] = "存款时间";
		rowData[4] = "操作人";
		rowData[5] = "金额";
		rowData[6] = "存款人";
		rowData[7] = "存款方式";
		rowData[8] = "收款银行";
		rowData[9] = "收款账号";
		rowData[10] = "收款人";
		rowData[11] = "订单状态";
		rowData[12] = "支付人账号";
		rowData[13] = "是否首充";
		rowData[14] = "优惠金额";
		return rowData;
	}

	// ================================导出第三方支付订单CSV=======================
	/**
	 * 导出银行卡订单csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportThirdCSVData")
	@RequiresPermissions("orderrecharge:orderrecharge:list")
	public void exportThirdCSVData(OrderRechargeEntity orderrecharge, HttpServletResponse response) {
		List<Map<String, Object>> list = orderRechargeService.selectListOrderRecharge(orderrecharge);
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getHeadThirdRowStr()); // 头信息
		for (Map<String, Object> map : list) {
			writer.write(getRowThirdStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	// 获取行信息
	private String[] getRowThirdStr(Map<String, Object> map) {
		String[] rowData = new String[14];
		rowData[0] = ExportCSVUtils.getString(map.get("orderNo"));
		rowData[1] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[2] = ExportCSVUtils.getString(map.get("rechargeTime"));
		rowData[3] = ExportCSVUtils.getString(map.get("sysUserAccount"));
		rowData[4] = ExportCSVUtils.getString(map.get("amount"));
		rowData[5] = ExportCSVUtils.getString(map.get("status"));
		rowData[6] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[7] = ExportCSVUtils.getString(map.get("fristrecharge"));
		rowData[8] = ExportCSVUtils.getString(map.get("merchantOrderNo"));
		rowData[9] = ExportCSVUtils.getString(map.get("rechargePlatform"));
		rowData[10] = ExportCSVUtils.getString(map.get("rechargeTerminal"));
		rowData[11] = ExportCSVUtils.getString(map.get("preMoney"));
		rowData[12] = ExportCSVUtils.getString(map.get("discountAmount"));
		rowData[13] = ExportCSVUtils.getString(map.get("rechargeChannelName"));
		return rowData;
	}

	// 获取头信息
	private String[] getHeadThirdRowStr() {
		String[] rowData = new String[14];
		rowData[0] = "订单号";
		rowData[1] = "存款时间";
		rowData[2] = "完成时间";
		rowData[3] = "操作人";
		rowData[4] = "金额";
		rowData[5] = "订单状态";
		rowData[6] = "支付人账号";
		rowData[7] = "是否首充";
		rowData[8] = "第三方支付平台订单号";
		rowData[9] = "第三方支付平台";
		rowData[10] = "充值终端WEB,PC";
		rowData[11] = "充值前主账户金额";
		rowData[12] = "优惠金额";
		rowData[13] = "充值渠道";
		return rowData;
	}
}
