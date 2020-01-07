package com.xmsy.server.zxyy.manager.modules.manager.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserCoinSumParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamThree;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamTwo;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserReportParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("user/usersingle")
public class UsersingleanalysisController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;
	@Autowired 
	private OrderRechargeService orderRechargeService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;


	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("user:usersingle:list")
	public R list(UserParamFour user, PageParam pageParam) {
		//查询前先输入会员账号和时间
		if(StringUtils.isEmpty(user.getAccount())) {
			return R.error(100,"请输入会员账号");
		}
		if(StringUtils.isEmpty(user.getEndTime())||StringUtils.isEmpty(user.getStartTime())) {
			return R.error(100,"请输入查询时间");
		}
		//判断会员是否存在
		UserEntity userEntity = new UserEntity();
		userEntity.setAccount(user.getAccount());
		UserEntity userWrapper = userService.selectOne(new EntityWrapper<UserEntity>(userEntity));
		if(userWrapper==null) {
			return R.error(100,"该会员不存在");
		}
		//查询单个会员信息
		UserParamThree userParam = userService.findSingleUserPage(user);
		List<UserParamThree> userParamList=new ArrayList<>();
		userParamList.add(userParam);
		//查询单个会员的取款信息
		List<OrderTakeMoneyEntity> orderTakeMoneyEntity = orderTakeMoneyService.withdrawalRecord(user);
		//查询单个会员的存款信息
		List<OrderRechargeEntity> orderRechargeEntities = orderRechargeService.depositRecord(user);
		//查询单个会员的登录日志
		List<UserLoginEntity> userLoginList = userService.logonLog(user);
		//查询会员的投注中奖信息
		List<UserCoinSumParam> getGameList = gameRecordService.selectCoinSum(user);
		//查询单个会员的充值提款总额等信息
		List<UserReportParam> getAmountSumList = userTransactionRecordService.getAmountSum(user);
		//循环getAmountSumList，gameMap。将gameMap的值赋值给getAmountSumList
		
		// 定义两个时间
		String stdate=user.getStartTime();
		String etdate=user.getEndTime();
		Date stdateStr = DateUtils.stringToDate(stdate,DateUtils.DATE_TIME_PATTERN);
		Date etdateStr = DateUtils.stringToDate(etdate,DateUtils.DATE_TIME_PATTERN);
		Date sdate = stdateStr;
		long forNum = getDatePoor(etdateStr,stdateStr);
		List<UserReportParam> resultList= new ArrayList<>();
		BigDecimal takeMoneySum = BigDecimal.ZERO;
	    BigDecimal orderRechargeSum = BigDecimal.ZERO;
		BigDecimal commissionSum = BigDecimal.ZERO;
	    BigDecimal commissionOutSum = BigDecimal.ZERO;
	    BigDecimal commissionEnterSum = BigDecimal.ZERO;
	    Long betCoins = 0l;
		Long prizeCoins = 0l;
		    
		for (int i=0;i<forNum;i++) {
			UserReportParam data1=getAmountSum(getAmountSumList, sdate);
			UserCoinSumParam data2 = getGameList(getGameList,sdate);
//			if(data1==null && data2==null) {
//				continue;
//			}
			if(data1==null) {
				data1=new UserReportParam(sdate);
			}
			if(data2==null) {
				data1.setBetCoins(0l);
				data1.setPrizeCoins(0l);
			}else {
				data1.setBetCoins(data2.getBetCoins());
				data1.setPrizeCoins(data2.getPrizeCoins());
			}
			sdate=DateUtils.addDateDays(sdate, 1);
			resultList.add(data1);
			takeMoneySum = takeMoneySum.add(MathUtil.getBigDecimal(data1.getTakeMoneySum()));
			orderRechargeSum = orderRechargeSum.add(MathUtil.getBigDecimal(data1.getOrderRechargeSum()));
			commissionSum = commissionSum.add(MathUtil.getBigDecimal(data1.getCommissionSum()));
			commissionOutSum = commissionOutSum.add(MathUtil.getBigDecimal(data1.getCommissionOutSum()));
			commissionEnterSum = commissionEnterSum.add(MathUtil.getBigDecimal(data1.getCommissionEnterSum()));
			betCoins += data1.getBetCoins();
			prizeCoins += data1.getPrizeCoins();
		}
		//new一个对象reportParamAll去接收getAmountSumList遍历出来的数据的总和
		UserReportParam reportParamAll = new UserReportParam();
		reportParamAll.setTakeMoneySum(takeMoneySum);
		reportParamAll.setOrderRechargeSum(orderRechargeSum);
		reportParamAll.setCommissionSum(commissionSum);
		reportParamAll.setCommissionEnterSum(commissionEnterSum);
		reportParamAll.setCommissionOutSum(commissionOutSum);
		reportParamAll.setBetCoins(betCoins);
		reportParamAll.setPrizeCoins(prizeCoins);
		return R.ok().put("userInfo", userParamList)
				.put("withdrawalRecord", orderTakeMoneyEntity)
				.put("depositRecord", orderRechargeEntities)
				.put("logonLog", userLoginList)
				.put("reportParamAll", reportParamAll)
				.put("mapList", resultList);
	}
	
	private UserReportParam getAmountSum(List<UserReportParam> getAmountSumList,Date date ) {
		for(UserReportParam para:getAmountSumList) {
			if(para.getCreateTime().equals(date) ) {
				return para;
			}
		}
		return null;
	}
	
	private UserCoinSumParam getGameList(List<UserCoinSumParam> gameList,Date date ) {
		for(UserCoinSumParam para:gameList) {
			if(para.getCreateTime().equals(date) ) {
				return para;
			}
		}
		return null;
	}
	
	//算出时间差（日）
	public static long getDatePoor(Date endDate, Date nowDate) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - nowDate.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	   
	    return day+1;
	}

	/**
	 * 列表
	 */
	@RequestMapping("/risklist")
	@RequiresPermissions("user:usersingle:risklist")
	public R risklist(UserParam user, PageParam pageParam) {
		return R.ok().put("page", userService.findRiskUserPage(user, pageParam));
	}
	
	/**
	 * 根据账号查询
	 */
	@RequestMapping("/queryByAccount")
	@RequiresPermissions("user:usersingle:list")
	public R queryByAccount(String account) {
		UserParamTwo entity = userService.queryByAccount(account);
		return R.ok().put("user", entity);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("user:usersingle:info")
	public R info(@PathVariable("id") Long id) {
		log.info("[info] id {}", id);
		UserEntity user = userService.selectById(id);
		log.info("[info] user {}", user);
		return R.ok().put("user", user);
	}

	/**
	 * 上级信息
	 */
	@RequestMapping("/surperinfo/{id}")
	@RequiresPermissions("user:usersingle:info")
	public R surperinfo(@PathVariable("id") Long id) {
		log.info("[surperinfo] id {}", id);
		Map<String, Object> userMap = new HashMap<>();
		UserEntity user = userService.selectById(id);
		log.info("[surperinfo] user {}", user);
		if (user != null) {
			userMap.put("id", user.getId());
			userMap.put("account", user.getAccount());
			if (user.getSuperiorsId() != null) {
				userMap.put("superiorsId", user.getSuperiorsId());
				user = userService.selectById(user.getSuperiorsId());
				if (user != null) {
					userMap.put("superiorsAccount", user.getAccount());
				}
			} else {
				userMap.put("superiorsId", 0);
			}
		}
		log.info("[surperinfo] userMap {}", userMap);
		return R.ok().put("user", userMap);
	}

	/**
	 * 基本信息
	 */
	@RequestMapping("/allinfo/{id}")
	@RequiresPermissions("user:usersingle:info")
	public R getUserAllinfo(@PathVariable("id") Long id) {
		log.info("[getUserAllinfo] id {}", id);
		UserEntity user = userService.selectById(id);
		log.info("[getUserAllinfo] user {}", user);
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(user.getId());
		userInfo = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>(userInfo));
		log.info("[userInfo] user {}", userInfo);
		return R.ok().put("user", user).put("userinfo", userInfo);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("user:usersingle:save")
	public R save(@RequestBody UserEntity user) {
		log.info("[save] user {}", user);
		userService.insert(user);
		return R.ok();
	}


}
