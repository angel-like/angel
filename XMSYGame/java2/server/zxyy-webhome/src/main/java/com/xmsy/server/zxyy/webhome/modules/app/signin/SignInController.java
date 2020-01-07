package com.xmsy.server.zxyy.webhome.modules.app.signin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.service.SignRewardContinuousEveryDayService;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.SignUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class SignInController {
	
	@Autowired
	private SignRewardContinuousEveryDayService signRewardContinuousEveryDayService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SignUserRecordService signUserRecordService;
	
	/**
	 * 获取签到奖励和已经签到天数接口
	 * 
	 * @throws Exception
	 */
	@GetMapping("/getSignDetails")
	public R signIn(@RequestHeader("token") String token)
			throws Exception {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		List<SignRewardContinuousEveryDayEntity> signList = signRewardContinuousEveryDayService.selectSignIn(entity.getVipId());
		Date startDate = DateUtils.getWeekStartAndEnd(0)[0];
		Integer continueSignDay = signUserRecordService.continuousNum(entity.getId(), startDate);
		Integer signInToday =  signUserRecordService.todaySign(entity.getId(), DateUtils.format(new Date()));
		//log.info("addDayZeroPoint {}",DateUtils.addDayZeroPoint(new Date(),0));
		JSONObject obj = new JSONObject();
		obj.put("signDayRewards", signList);
		obj.put("signInToday", signInToday>0);
		obj.put("continueSignDay", continueSignDay);
		//log.info("[getSignDetails===》获取签到列表] obj {} ", obj);
		return R.ok().put("data", obj);
	}
	
	
	/**
	 * 签到接口
	 * 
	 * @throws Exception
	 */
	@PostMapping("/userSign")
	public R signInCoin(@RequestHeader("token") String token)
			throws Exception {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		JSONObject obj = signUserRecordService.insertRecordTwo(entity);
		return R.ok().put("data", obj);
	}
	/**
	 * VIP签到奖励说明接口
	 * @return
	 */
	@GetMapping("/getSignRewardEveryDay")
	public R signReward(@RequestHeader("token") String token) {
		List<SignRewardContinuousEveryDayEntity> listSignReward = signRewardContinuousEveryDayService.selectList(new EntityWrapper<SignRewardContinuousEveryDayEntity>().orderBy("vip_id,day_num"));
		List<Map<String, Object>> list = signRewardContinuousEveryDayService.getSignRewardData(listSignReward);
		return R.ok().put("data", list);
	}
}
