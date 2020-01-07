package com.xmsy.server.zxyy.webhome.modules.webim.game;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.entity.GameConfigUrlEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gameconfigurl.service.GameConfigUrlService;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.dao.ImServiceManagerDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imservicemanager.entity.ImServiceManagerEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-06-20
 */
@Slf4j
@RestController
@RequestMapping("webim/v1/game")
public class WebimGameController {
	@Autowired
	private GameConfigUrlService gameConfigUrlService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImServiceManagerDao imServiceManagerDao;

	/**
	 * 游戏跳转
	 * 
	 */
	@GetMapping("/hall-path")
	public R hallPath(@RequestParam(value = "gameId", required = false) Long gameId,
			@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被限制停压
		UserStatusVerificationUtil.userNobetValidate(user.getNobetEnable());
		List<GameConfigUrlEntity> list = gameConfigUrlService.selectList(new EntityWrapper<GameConfigUrlEntity>(null));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.GameErrorCode.GAMEURL_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEURL_IS_NULL.getCode());
		}
		if (user.getMoney().compareTo(BigDecimal.ZERO) > 0) {
			// 如果余额大于0，那么就自动转换
			log.info("[余额转金币] : user {} param {}", user);
			Long exchangeCoin = user.getMoney().multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
			BigDecimal exchangeMoney = user.getMoney();
			userService.changer(user, exchangeMoney, exchangeCoin);
		}
		String url = list.get(0).getUrl() + "?account=" + user.getAccount() + "&token=" + token;
		if (null != gameId && 0 != gameId) {// 如果游戏ID不为空
			url = url + "&game=" + gameId;
		}
		return R.ok().put("data", url);
	}

	/**
	 * 游戏跳转
	 * 
	 */
	@GetMapping("/service-path")
	public R servicePath(@RequestParam("serviceId") Long serviceId, @RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被限制停压
		UserStatusVerificationUtil.userNobetValidate(user.getNobetEnable());
		if (user.getMoney().compareTo(BigDecimal.ZERO) > 0) {
			// 如果余额大于0，那么就自动转换
			log.info("[余额转金币] : user {} param {}", user);
			Long exchangeCoin = user.getMoney().multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
			BigDecimal exchangeMoney = user.getMoney();
			userService.changer(user, exchangeMoney, exchangeCoin);
		}
		ImServiceManagerEntity serviceEntity = imServiceManagerDao.selectById(serviceId);
		if (null == serviceEntity) {
			throw new RRException(ErrorCode.GameErrorCode.SERVICE_PATH_ERRO.getErrMsg(),
					ErrorCode.GameErrorCode.SERVICE_PATH_ERRO.getCode());
		}
		if (StringUtils.isEmpty(serviceEntity.getServiceUrl())) {
			throw new RRException(ErrorCode.GameErrorCode.SERVICE_PATH_IS_NULL.getErrMsg(),
					ErrorCode.GameErrorCode.SERVICE_PATH_IS_NULL.getCode());
		}
		String url = serviceEntity.getServiceUrl() + "?account=" + user.getAccount() + "&token=" + token;
		return R.ok().put("data", url);
	}

}
