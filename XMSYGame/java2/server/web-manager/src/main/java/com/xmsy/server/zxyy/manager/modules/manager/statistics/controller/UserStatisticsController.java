package com.xmsy.server.zxyy.manager.modules.manager.statistics.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.UserAnalysis;

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
    @Autowired
    private OrderRechargeService orderRechargeService;
    @Autowired
    private OrderTakeMoneyService orderTakeMoneyService;

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
    //@RequiresPermissions("userstatistics:userstatistics:getLoginCountByDeviceType")
    public R getLoginCountByDeviceType() {
        Map<String, Object> map = userLoginService.getLoginCountByDeviceType();
        Map<String, Object> map1 = orderRechargeService.sumRechargeAmount(DateUtils.format(DateUtils.getToday()), null,
                null);
        Map<String, Object> map2 = orderTakeMoneyService.sumTakeMoneyAmount(DateUtils.format(DateUtils.getToday()), null,
                null);
        map.putAll(map1);
        map.putAll(map2);
        return R.ok().put("data", map);
    }

    /**
     * 各个层级风控人数统计
     */
    @RequestMapping("/statisticsRiskNum")
    @RequiresPermissions("userstatistics:userstatistics:statisticsRiskNum")
    public R statisticsRiskNum() {
        List<Map<String, Object>> list = userService.statisticsRiskNum();
        return R.ok().put("list", list);
    }

    /**
     * 风控总数统计
     */
    @RequestMapping("/statisticsRiskTotalNum")
    @RequiresPermissions("userstatistics:userstatistics:statisticsRiskNum")
    public R statisticsRiskTotalNum() {
        Long riskTotalNum = userService.statisticsRiskTotalNum();
        return R.ok().put("data", riskTotalNum);
    }

    // ===============================会员分析===========================

    /**
     * 会员分析---随时间可变的曲线图
     */
    @RequestMapping("/UserNumberAnalysis")
    @RequiresPermissions("userstatistics:userstatistics:list")
    public R UserNumberAnalysis(UserAnalysis userAnalysis) {
        if ("".equals(userAnalysis.getStartTime()) || null == userAnalysis.getStartTime()) {
            throw new RRException(ErrorCode.SignErrorCode.TIME_ERRO.getErrMsg(),
                    ErrorCode.SignErrorCode.TIME_ERRO.getCode());
        }
        Map<String, Object> map = new HashMap<>();
        List<String> xData = new ArrayList<String>();// x轴表示每日日期
        List<String> yData = new ArrayList<String>();// y轴表示每日新增会员数量
        // 1.对于传过来的时间，遍历每一天，每天的统计去查询数据库。再把值放进xy里
        Date startDate = DateUtils.stringToDate(userAnalysis.getStartTime(), DateUtils.DATE_PATTERN);
        Date endDate = DateUtils.stringToDate(userAnalysis.getEndTime(), DateUtils.DATE_PATTERN);
        long betweenDate = (endDate.getTime() - startDate.getTime()) / (60 * 60 * 24 * 1000);//得到相差的天数
        if (betweenDate > 30) {
            throw new RRException(ErrorCode.SignErrorCode.TIMEOUT_ERRO.getErrMsg(),
                    ErrorCode.SignErrorCode.TIMEOUT_ERRO.getCode());
        }
        for (int i = 0; i <= betweenDate; i++) {
            map = new HashMap<>();
            String everyDate = DateUtils.format(DateUtils.addDateDays(startDate, i));//每天的天数去查询yyyy-MM-dd
            map = userService.sumUserForDate(everyDate, everyDate);
            xData.add(everyDate);
            yData.add(map.get("userNumber").toString());
            //System.out.println(map);
        }


        // 2.统计会员人数数据
        Map<String, Object> statisticsMap = userService.getStatisticsNumber();//统计会员数量，有效会员数量
        Map<String, Object> loginNumMap = userLoginService.getLoginCountByDeviceType();//计算设备的登录人数
        if (loginNumMap != null && !loginNumMap.isEmpty()) {
            statisticsMap.putAll(loginNumMap);
        }
        return R.ok().put("data", ImmutableMap.of("xaxisData", xData, "yaxislData", yData)).put("map", statisticsMap);
    }
}
