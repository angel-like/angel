package com.xmsy.server.zxyy.webhome.modules.app.recommend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.AppRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.service.AdChannelConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.appmoneystrategy.entity.AppMoneyStrategyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.appmoneystrategy.service.AppMoneyStrategyService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomeenclosure.entity.WebhomeEnclosureEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserRecommendationRecordResult;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

//import lombok.extern.slf4j.Slf4j;

/**
 * 推荐人
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 11:30:25
 */
@RestController
@RequestMapping("V1.0/App/Recommendation")
public class RecommendController {
	@Autowired
	private AppMoneyStrategyService appMoneyStrategyService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	@Autowired
    private AdChannelConfigService adChannelConfigService;

	/**
	 * 赚钱攻略
	 * 
	 */
	@GetMapping("/MakeMoneyStrategy")
	public R makeMoneyStrategy() {
		AppMoneyStrategyEntity param = new AppMoneyStrategyEntity();
		param.setAvailability(true);
		AppMoneyStrategyEntity moneyStrategy = appMoneyStrategyService
				.selectOne(new EntityWrapper<AppMoneyStrategyEntity>(param).orderBy("create_time", false));
		Map<String, Object> result = new HashMap<>();
		if (moneyStrategy != null) {
			result.put("name", moneyStrategy.getName());
			result.put("url", moneyStrategy.getUrl());
			result.put("createTime", moneyStrategy.getCreateTime());
			result.put("type", moneyStrategy.getType());
			result.put("md5", moneyStrategy.getMd5());
			if (moneyStrategy.getEnclosureId() != null && moneyStrategy.getEnclosureId() > 0) {
				WebhomeEnclosureEntity webhomeEnclosure = new WebhomeEnclosureEntity();
				webhomeEnclosure.setId(moneyStrategy.getEnclosureId());
				result.put("imgUrl", webhomeEnclosure.selectById().getUrl());
			}
		}
		return R.ok().put("data", result);
	}

	/**
	 * 开始赚钱
	 * 
	 */
	@GetMapping("/MakingMoney")
	public R makingMoney(@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		UserRecommenderResultParam userRecommendation = userRecommendationService.selectUserRecommendation(userId);
		userRecommendation.setCommission(user.getCommission());
		return R.ok().put("data", userRecommendation);
	}

	/**
	 * 开始赚钱(xiaoliu)
	 * 
	 */
	@GetMapping("/newMakingMoney")
	public R newMakingMoney(@RequestHeader("token") String token,String channelCode) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		AppRecommenderResultParam userRecommendation = userRecommendationService.selectUserRecommendationInfo(userId);
		//通过传过来的渠道码去查询发布地址
		String s="";
		if(channelCode==null) {
			channelCode=s;
		}
		AdChannelConfigEntity adChannelConfig = adChannelConfigService.findAdChannelByChannelCode(channelCode);
		if(adChannelConfig==null) {//如果没有设置空渠道码   默认查询第一个
			adChannelConfig = adChannelConfigService
					.selectOne(new EntityWrapper<AdChannelConfigEntity>(new AdChannelConfigEntity().setChannelCode(channelCode)));
		}

		if(adChannelConfig != null) {
			userRecommendation.setQrCode(adChannelConfig.getPublishUrl() + "?code="
					+ userRecommendation.getRecommendationCode() + "&channelCode=" + channelCode);
		}

		return R.ok().put("data", userRecommendation);
	}

	/**
	 * 玩家人数
	 * 
	 */
	@GetMapping("/subordinates")
	public R subordinate(@RequestHeader("token") String token,
			@RequestParam(value = "account", required = false) String account, PageParam pageParam) {
		//log.info("[获取用户推荐记录] token {} account {} ", token, token, account);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		Page<UserRecommendationRecordResult> pageList = userRecommendationRecordService
				.selectRecommendationListForUserId(userId, pageParam, account);
		return R.ok().put("data", pageList);
	}
}
