package com.xmsy.server.zxyy.webhome.modules.webim.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.entity.GameConfigUrlEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.service.GameConfigUrlService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserRecommendationRecordResult;
import com.xmsy.server.zxyy.webhome.modules.webim.user.result.WebimUserInfoResult;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

/**
 * 用户
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-06-20
 */
@RestController
@RequestMapping("webim/v1/user")
public class WebimUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserVipService userVipService;
	@Autowired
	private GameConfigUrlService gameConfigUrlService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	
	public static final String VIP_DEFAULT="VIP0";

	/**
	 * /** 退出登陆
	 */
	@GetMapping("/login-out")
	public R loginOut(@RequestHeader("token") String token) {
		userService.logout(token);
		return R.ok();
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping("/info")
	public R userInfo(@RequestHeader("token") String token) {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		WebimUserInfoResult info = new WebimUserInfoResult();
		info.setAccount(entity.getAccount());
		info.setMoney(entity.getMoney());
		info.setCoin(entity.getCoin());
		info.setPortrait(entity.getPortrait());
		UserVipEntity userVip=userVipService.selectById(entity.getVipId());
		info.setVip(userVip==null?VIP_DEFAULT:userVip.getName());
		List<GameConfigUrlEntity> list = gameConfigUrlService.selectList(new EntityWrapper<GameConfigUrlEntity>(null));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.GameErrorCode.GAMEURL_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEURL_IS_NULL.getCode());
		}
		String url = list.get(0).getUrl() + "?account=" + entity.getAccount() + "&token=" + token;
		info.setServiceUrl(url);
		info.setServiceName("游戏大厅");
		return R.ok().put("data", info);
	}

	/**
	 * 根据token获取用户推荐记录
	 * 
	 */
	@GetMapping("/recommendation")
	public R selectRecommendationListForUserId(@RequestHeader("token") String token, PageParam pageParam,
			@RequestParam(value = "account", required = false) String account) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (user == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserRecommendationEntity entity = userRecommendationService.selectRecommendationForUserId(userId);
		if (entity == null) {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.USER_RECOMMENDATION_ISNULL.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.USER_RECOMMENDATION_ISNULL.getCode());
		}
		Page<UserRecommendationRecordResult> pageList = userRecommendationRecordService
				.selectRecommendationListForUserId(userId, pageParam, account);
		return R.ok().put("data", pageList).put("recommendation", entity.getRecommendationCode()).put("commission",
				user.getCommission());
	}

}
