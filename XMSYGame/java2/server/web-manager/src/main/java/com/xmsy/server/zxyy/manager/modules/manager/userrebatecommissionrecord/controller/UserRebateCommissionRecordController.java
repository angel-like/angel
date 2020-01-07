package com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDateDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.service.UserRebateCommissionRecordService;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;

/**
 * 用户佣金返利记录
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-12 14:38:44
 */
@RestController
@RequestMapping("userrebatecommissionrecord/userrebatecommissionrecord")
public class UserRebateCommissionRecordController  extends AbstractController{
	@Autowired
	private UserRebateCommissionRecordService userRebateCommissionRecordService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:list")
	public R list(UserRebateCommissionDetailsEntity userrebatecommissionrecord, PageParam pageParam) {
		Page<UserRebateCommissionDetailsEntity> result = userRebateCommissionRecordService
				.getDetailsList(userrebatecommissionrecord, pageParam);
		List<UserRebateCommissionDetailsEntity> list=result.getRecords();
		BigDecimal obtainCommission = BigDecimal.ZERO;
		BigDecimal commission = BigDecimal.ZERO;
		Long takeAmount = 0l;
    	for(UserRebateCommissionDetailsEntity data:list) {
    		if(data.getObtainCommission() !=null) {
    			obtainCommission = obtainCommission.add(MathUtil.getBigDecimal(data.getObtainCommission()));
    		}
    		if(data.getTakeAmount() !=null) {
    			takeAmount = takeAmount+data.getTakeAmount();
    		}
    		if(data.getCommission() !=null) {
    			commission = commission.add(MathUtil.getBigDecimal(data.getCommission()));
    		}

    	}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).
    			put("obtainCommission", obtainCommission).
    			put("takeAmount", takeAmount).
    			put("commission", commission);
	}

	/**
	 * 记录列表
	 */
	@RequestMapping("/detailList")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:detailList")
	public R detailList(UserRebateCommissionRecordEntity userrebatecommissionrecord, PageParam pageParam) {
		Page<UserRebateCommissionRecordEntity> result = new Page<UserRebateCommissionRecordEntity>(pageParam.getPage(),
				pageParam.getLimit());
		Wrapper<UserRebateCommissionRecordEntity> entityWrapper = new EntityWrapper<UserRebateCommissionRecordEntity>(
				userrebatecommissionrecord).eq(!StringUtils.isEmpty(userrebatecommissionrecord.getQueryDate()),
						"record_date", userrebatecommissionrecord.getQueryDate());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		userrebatecommissionrecord.selectPage(result, entityWrapper);
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages()));
	}

	/**
	 * 日期统计列表
	 */
	@RequestMapping("/dateList")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:dateList")
	public R dateList(UserRebateCommissionDateDetailsEntity userrebatecommissionrecord, PageParam pageParam) {
		Page<UserRebateCommissionDateDetailsEntity> result = userRebateCommissionRecordService
				.dateList(userrebatecommissionrecord, pageParam);
		BigDecimal commission = BigDecimal.ZERO;
		if (CollectionUtils.isEmpty(result.getRecords())) {
			return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
					result.getCurrent(), result.getPages())).put("commission", commission);
		}
		for (UserRebateCommissionDateDetailsEntity entity : result.getRecords()) {
			if (entity.getCommission() != null) {
				commission = commission.add(entity.getCommission());
			}
		}
		return R.ok().put("page", new PageUtils(result.getRecords(), result.getTotal(), result.getSize(),
				result.getCurrent(), result.getPages())).put("commission", commission);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:info")
	public R info(@PathVariable("id") Long id) {
		UserRebateCommissionRecordEntity userRebateCommissionRecord = userRebateCommissionRecordService.selectById(id);
		return R.ok().put("userrebatecommissionrecord", userRebateCommissionRecord);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:save")
	public R save(@RequestBody UserRebateCommissionRecordEntity userrebatecommissionrecord) {
		userRebateCommissionRecordService.insert(userrebatecommissionrecord);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:update")
	public R update(@RequestBody UserRebateCommissionRecordEntity userrebatecommissionrecord) {
		userRebateCommissionRecordService.updateById(userrebatecommissionrecord);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			userRebateCommissionRecordService.deleteById(id);
		}
		return R.ok();
	}
	/**
	 * 导推广记录csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportCSV")
	@RequiresPermissions("userrebatecommissionrecord:userrebatecommissionrecord:list")
	public void exportCSVData(UserRebateCommissionDetailsEntity userrebatecommissionrecord, HttpServletResponse response) {
		//获取数据
		List<Map<String, Object>> list = userRebateCommissionRecordService.getList();

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
		String[] rowData = new String[10];
		rowData[0] = ExportCSVUtils.getString(map.get("userId"));
		rowData[1] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[2] = ExportCSVUtils.getString(map.get("recommendationCode"));
		rowData[3] = ExportCSVUtils.getString(map.get("money"));
		rowData[4] = ExportCSVUtils.getString(map.get("num"));
		rowData[5] = ExportCSVUtils.getString(map.get("subordinateNum"));
		rowData[6] = "\t"+ExportCSVUtils.getString(map.get("betCoins"));
		rowData[7] = ExportCSVUtils.getString(map.get("obtainCommission"));
		rowData[8] = ExportCSVUtils.getString(map.get("takeMoney"));
		rowData[9] = ExportCSVUtils.getString(map.get("commission"));
		return rowData;
	}

	// 获取头信息
	private String[] getCommissionHeadRowStr() {
		String[] rowData = new String[10];
		rowData[0] = "用户ID";
		rowData[1] = "用户账号";
		rowData[2] = "邀请码";
		rowData[3] = "账户余额";
		rowData[4] = "下线总人数";
		rowData[5] = "直属下线人数";
		rowData[6] = "总打码";
		rowData[7] = "代理总佣金";
		rowData[8] = "佣金取款额";
		rowData[9] = "代理佣金余额";
		return rowData;
	}

}
