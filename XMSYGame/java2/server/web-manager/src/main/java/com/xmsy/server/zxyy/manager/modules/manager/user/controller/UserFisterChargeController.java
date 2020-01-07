package com.xmsy.server.zxyy.manager.modules.manager.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

/**
 * 用户首冲
 *
 * @author Administrator
 *
 */
@RestController
@RequestMapping("user/user")
public class UserFisterChargeController extends AbstractController{
	@Autowired
	private UserService userService;
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private RechargeChannelService rechargeChannelService;


	/**
	 * 列表
	 */
	@RequestMapping("/UserFisterCharge")
	@RequiresPermissions("orderrecharge:orderrecharge:list")
	public R list(OrderRechargeEntity OrderRechargeEntity, PageParam pageParam) {
		Page<OrderRechargeEntity> result = new Page<OrderRechargeEntity>(pageParam.getPage(), pageParam.getLimit());
		EntityWrapper<OrderRechargeEntity> entityWrapper = new EntityWrapper<OrderRechargeEntity>(
				OrderRechargeEntity.setFristrecharge(true));//设置首充条件
		entityWrapper.ge(!StringUtils.isEmpty(OrderRechargeEntity.getStartTime()), "create_time", // 首充时间查询
				OrderRechargeEntity.getStartTime()).
						le(!StringUtils.isEmpty(OrderRechargeEntity.getEndTime()), "create_time",
				OrderRechargeEntity.getEndTime());
		entityWrapper.orderBy("id", false);
		OrderRechargeEntity.selectPage(result, entityWrapper);
		List<OrderRechargeEntity> orderRechargeList = result.getRecords();
		for(OrderRechargeEntity orderRecharge:orderRechargeList) {
			if (userService.getSuperiorsName(orderRecharge.getUserId())!=null || userService.getSuperiorsName(orderRecharge.getUserId())!=""){
				orderRecharge.setSuperiorsName(userService.getSuperiorsName(orderRecharge.getUserId()));
			}
			orderRecharge.setHierarchyName(localContentCache.getHierarchyName(orderRecharge.getHierarchyId()));
			UserEntity user = userService.selectById(orderRecharge.getUserId());
			if(user!=null) {
				orderRecharge.setUserName(user.getUserName());
			}
			if(orderRecharge.getRechargeType()==2) {
				RechargeChannelEntity rechargeChannelByType = rechargeChannelService.getRechargeChannelByType(orderRecharge.getRechargeChannel());
				if(rechargeChannelByType!=null) {
					orderRecharge.setRechargePlatformName(orderRecharge.getRechargePlatform()+"-"+rechargeChannelByType.getName());
				}else {
					orderRecharge.setRechargePlatformName(orderRecharge.getRechargePlatform());
				}
				
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}
	/**
	 * 导出csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportUserFisterChargeCSVData")
	@RequiresPermissions("orderrecharge:orderrecharge:list")
	public void exportUserFisterChargeCSVData(OrderRechargeEntity OrderRechargeEntity, HttpServletResponse response) {
		OrderRechargeEntity.setFristrecharge(true);
		List<Map<String, Object>> list = orderRechargeService.selectListOrderRecharge(OrderRechargeEntity);
		for(Map<String, Object> map:list) {
			UserEntity user = userService.selectById(MathUtil.getBigDecimal(map.get("userId")).longValue());
			map.put("userName", user!=null ? user.getUserName() : "");
		}
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_UTF_8);
		// 按行写出
		writer.write(getTrialHeadRowStr()); // 头信息
		for (Map<String, Object> map : list) {
			writer.write(getTrialRowStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	// 获取行信息
		private String[] getTrialRowStr(Map<String, Object> map) {
			String[] rowData = new String[8];
			rowData[0] = ExportCSVUtils.getString(map.get("userId"));
			rowData[1] = ExportCSVUtils.getString(map.get("userName"));
			rowData[2] = ExportCSVUtils.getString(map.get("userAccount"));
			rowData[3] = getRechargeType(map);;
			rowData[4] = ExportCSVUtils.getString(map.get("depositType"));
			rowData[5] = ExportCSVUtils.getString(map.get("orderNo"));
			rowData[6] = ExportCSVUtils.getString(MathUtil.getBigDecimal(map.get("amount")).divide(MathUtil.getBigDecimal(100)));
			rowData[7] = ExportCSVUtils.getString(map.get("createTime"));
			return rowData;
		}
		//充值类型
		private String getRechargeType(Map<String, Object> data) {
			String getRechargeType = "";
			if (ExportCSVUtils.getString(data.get("rechargeType")).equals("1")) {
				getRechargeType += "后台人工充值";
			}else if(ExportCSVUtils.getString(data.get("rechargeType")).equals("2")){
				getRechargeType += "第三方支付";
			}else if(ExportCSVUtils.getString(data.get("rechargeType")).equals("3")) {
				getRechargeType += "线下银行卡打款";
			}
			return getRechargeType;
		}
		// 获取头信息
		private String[] getTrialHeadRowStr() {
			String[] rowData = new String[8];
			rowData[0] = "会员ID";
			rowData[1] = "会员名称";
			rowData[2] = "会员账号";
			rowData[3] = "充值类型";
			rowData[4] = "具体类型";
			rowData[5] = "订单号";
			rowData[6] = "资金变动";
			rowData[7] = "首充时间";
			return rowData;
		}
}
