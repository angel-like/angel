package com.xmsy.server.zxyy.manager.modules.web.game;

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
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gameconfigurl.entity.GameConfigUrlEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gameconfigurl.service.GameConfigUrlService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;
import com.xmsy.server.zxyy.manager.utils.UserStatusVerificationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("webhome")
public class WebHomeGameController {
	@Autowired
	private GameConfigUrlService gameConfigUrlService;
	@Autowired
	private UserService userService;

	/**
	 * 热门游戏跳转
	 * 
	 */
	@GetMapping("/getWebMenuUrlPopularGames")
	public R getWebMenuUrlPopularGames(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "typeId", required = false) Long typeId, @RequestHeader("token") String token) {
		if (StringUtils.isEmpty(type)) {
			throw new RRException(ErrorCode.WebConfigErrorCode.URLTYPE_ISNULL_ERRO.getErrMsg(),
					ErrorCode.WebConfigErrorCode.URLTYPE_ISNULL_ERRO.getCode());
		} else {
			if (type.equals("game")) {
				return getWebMenuUrl(typeId, token);// 通过游戏Id跳转
			} else {
				throw new RRException(ErrorCode.WebConfigErrorCode.URLTYPE_IS_ERRO.getErrMsg(),
						ErrorCode.WebConfigErrorCode.URLTYPE_IS_ERRO.getCode());
			}
		}

	}

	/**
	 * 游戏跳转
	 * 
	 */
	@GetMapping("/getWebMenuUrl")
	public R getWebMenuUrl(@RequestParam(value = "gameId", required = false) Long gameId,
			@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被限制停压
		UserStatusVerificationUtil.userNobetValidate(user.getNobetEnable());
		if (gameId != null && gameId != 0) {
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
			String url = list.get(0).getUrl() + "?account=" + user.getAccount() + "&game=" + gameId + "&token=" + token;
			return R.ok().put("data", url);
		} else {
			throw new RRException(ErrorCode.WebConfigErrorCode.URL_ISNULL_ERRO.getErrMsg(),
					ErrorCode.WebConfigErrorCode.URL_ISNULL_ERRO.getCode());
		}
	}

}
