package com.xmsy.server.zxyy.webhome.modules.manager.statistics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.UserAnalysis;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

/**
 * 会员(用户统计)
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-02-07 19:11:23
 */
@RestController
@RequestMapping("userstatistics/userstatistics")
public class UserStatisticsController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginService userLoginService;

	/**
	 * 用户(会员)统计
	 */
	@RequestMapping("/list")
	@RequiresPermissions("userstatistics:userstatistics:list")
	public R list() {
		Map<String, Object> map = userService.getStatisticsNumber();
		Map<String, Object> loginNumMap = userLoginService.getLoginCountByDeviceType();
		if (map == null) {
			map = new HashMap<>();
		}
		if (loginNumMap != null && !loginNumMap.isEmpty()) {
			map.putAll(loginNumMap);
		}
		return R.ok().put("data", map);
	}

	/**
	 * 7日新增会员(会员)统计(每日)
	 */
	@RequestMapping("/getInsertUserNumber")
	@RequiresPermissions("userstatistics:userstatistics:getInsertUserNumber")
	public R getInsertUserNumber() {
		List<Map<String, Object>> list = userService.getInsertUserNumber();
		return R.ok().put("list", list);
	}

	// ========================================================首页================================================
	/**
	 * 新增会员(会员)统计(总数)，有效会员
	 */
	@RequestMapping("/sumInsertUserNumber")
	@RequiresPermissions("userstatistics:userstatistics:sumInsertUserNumber")
	public R sumInsertUserNumber() {
		Map<String, Object> map = userService.sumUserForDate(null, null);
		return R.ok().put("data", map);
	}

	/**
	 * 7日新增会员(会员)统计(总数)，有效会员 前6天+今日
	 */
	@RequestMapping("/weekInsertUserNumber")
	@RequiresPermissions("userstatistics:userstatistics:weekInsertUserNumber")
	public R weekInsertUserNumber() {
		Map<String, Object> map = userService.sumUserForDate(DateUtils.format(DateUtils.getDay(-6)), null);
		return R.ok().put("data", map);
	}

	/**
	 * 昨日新增会员(会员)统计(总数)，有效会员
	 */
	@RequestMapping("/yesterdayInsertUserNumber")
	@RequiresPermissions("userstatistics:userstatistics:yesterdayInsertUserNumber")
	public R yesterdayInsertUserNumber() {
		Map<String, Object> map = userService.sumUserForDate(DateUtils.format(DateUtils.getYesterday()),
				DateUtils.format(DateUtils.getYesterday()));
		return R.ok().put("data", map);
	}

	/**
	 * 今日新增会员(会员)统计(总数)，有效会员
	 */
	@RequestMapping("/todayInsertUserNumber")
	@RequiresPermissions("userstatistics:userstatistics:todayInsertUserNumber")
	public R todayInsertUserNumber() {
		Map<String, Object> map = userService.sumUserForDate(DateUtils.format(DateUtils.getToday()), null);
		return R.ok().put("data", map);
	}

	/**
	 * 在线人数
	 */
	@RequestMapping("/getLoginCountByDeviceType")
	@RequiresPermissions("userstatistics:userstatistics:getLoginCountByDeviceType")
	public R getLoginCountByDeviceType() {
		Map<String, Object> map = userLoginService.getLoginCountByDeviceType();
		return R.ok().put("data", map);
	}

	/**
	 * 风控人数
	 */
	@RequestMapping("/statisticsRiskNum")
	@RequiresPermissions("userstatistics:userstatistics:statisticsRiskNum")
	public R statisticsRiskNum() {
		Long riskNum = userService.statisticsRiskNum();
		return R.ok().put("riskNum", riskNum);
	}
	
	//===============================会员分析===========================
	/**
	 * 会员分析---随时间可变的曲线图
	 */
	@RequestMapping("/UserNumberAnalysis")
	//@RequiresPermissions("userstatistics:userstatistics:sumInsertUserNumber")
	public R UserNumberAnalysis(UserAnalysis userAnalysis) {
		Map<String, Object> map = userService.sumUserForDate(userAnalysis.getStartTime(), userAnalysis.getEndTime());
		//1.对于传过来的时间，遍历每一天，每天的统计去查询数据库。再把值放进xy里
		List<String> xData = new ArrayList<String>();//x轴表示每日日期
		List<Integer> yData = new ArrayList<Integer>();//y轴表示每日新增会员数量
		
		//2.计算设备的登录人数
		Map<String, Object> loginNumMap = userLoginService.getLoginCountByDeviceType();
		if (map == null) {
			map = new HashMap<>();
		}
		if (loginNumMap != null && !loginNumMap.isEmpty()) {
			map.putAll(loginNumMap);
		}
		return R.ok().put("data",ImmutableMap.of("xaxisData",xData ,"yaxislData",yData )).put("map", map);
	}

}
