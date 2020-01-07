package com.xmsy.server.zxyy.manager.modules.web.user;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.UserUpdateRepeat;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.LoginPassWordEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.TakeMoneyPassWordEntity;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserOrderRechargeExchangeParam;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserRecommendationRecordResult;
import com.xmsy.server.zxyy.manager.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;
import com.xmsy.server.zxyy.manager.utils.PasswordVerification;
import com.xmsy.server.zxyy.manager.utils.UserStatusVerificationUtil;
import com.xmsy.server.zxyy.manager.utils.VerificationUitl;

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
public class WebHomeUserController {
	@Resource
	private OAuth2Utils oauth2Utils;
	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;
	@Autowired
	private UserPasswordService userPasswordService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Resource
	private MessageManagementService messageManagementService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private LocalContentCache localContentCache;

	/**
	 * /** 退出登陆
	 */
	@GetMapping("/loginOut")
	public R loginOut(@RequestHeader("token") String token) {
		userService.logout(token);
		return R.ok();
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping("/getUserInfo")
	public R getUserInfo(@RequestHeader("token") String token) {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
		String hierachyid = entity.getHierarchyId() == null ? "" : entity.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(entity.getId());
		requestVo.setUserAccount(entity.getAccount());
		requestVo.setRegisterDate(entity.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		entity.setUnreadNum(unreadNum);
		return R.ok().put("data", entity);
	}

	/**
	 * 获取用户基本信息
	 */
	@GetMapping("/getUserInformation")
	public R getUserInformation(@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserInfoResult entity = userService.getUserInformation(userId);
		return R.ok().put("data", entity);
	}

	/**
	 * 手机号绑定
	 */
	@PostMapping("/user/phone")
	public R webPhoneBind(@RequestHeader("token") String token, @RequestParam("phoneNo") String phoneNo) {
		log.info("[web端手机号绑定 webPhoneBind] token {} phoneNo {} ", token, phoneNo);
		if (!VerificationUitl.phoneVerification(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
		}
		if (null != localContentCache.getPhoneVerificationCode(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getCode());
		}
		Long id = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = userService.selectById(id);
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		if (!StringUtils.isEmpty(entity.getPhone())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ALREADY_BIND_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ALREADY_BIND_ERRO.getCode());
		}
		entity.setPhone(phoneNo);
		userService.updateById(entity);
		return R.ok();
	}

	/**
	 * 修改用户基本信息
	 * 
	 * @throws Exception
	 */
	@UserUpdateRepeat("用户重复更新信息校验")
	@PostMapping("/updateUserInformation")
	public R updateUserInformation(@RequestHeader("token") String token, @RequestBody UserInfoResult entity)
			throws Exception {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		entity.setUserId(userId);
		userService.updateUserInformation(entity);
		return R.ok();
	}

	/**
	 * 获取用户账户余额
	 */
	@GetMapping("/getUserBalance")
	public R getUserBalance(@RequestHeader("token") String token) {
		UserAccountResult entity = userService.getUserBalance(token);
		return R.ok().put("data", entity);
	}

	/**
	 * 用户归集
	 * 
	 * @throws Exception
	 */

	@PostMapping("/user/balance/exchange")
	public R exchange(@RequestHeader("token") String token) throws Exception {
		UserEntity user = userService.selectUserForToken(token);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
		if (user.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		log.info("用户归集 : user {}", user);
		Long exchangeCoin = getExchangeCoin(user.getCoin());
		BigDecimal exchangeMoney = new BigDecimal(exchangeCoin).divide(new BigDecimal(Constant.COIN_RATE)).setScale(2,
				BigDecimal.ROUND_DOWN);
		userService.integrate(user, exchangeMoney, exchangeCoin, TransactionDetailType.INTEGRATE);
		return R.ok();
	}

	/**
	 * 用户佣金转金币
	 * 
	 * @throws Exception
	 */

	@PostMapping("/user/balance/commission/exchange")
	public R exchangeCommission(@RequestHeader("token") String token, @RequestParam("commission") BigDecimal commission)
			throws Exception {
		if (null == commission || commission.compareTo(BigDecimal.ZERO) < 1) {
			throw new RRException(ErrorCode.UserErrorCode.COMMISSION_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.COMMISSION_INVALID.getCode());
		}
		UserEntity user = userService.selectUserForToken(token);
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被冻结
		UserStatusVerificationUtil.userFrozenValidate(user.getFrozenEnable());
		if (user.getUserType().equals(SysConstant.TRIAL)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TRIAL_ERRO.getCode());
		}
		if (user.getCommission().compareTo(commission) < 0) {
			throw new RRException(ErrorCode.UserErrorCode.COMMISSION_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.COMMISSION_NOT_ENOUGH.getCode());
		}
		log.info(" 用户佣金转余额: user {}", user);
		userService.exchangeCommission(user, commission,
				commission.multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
		return R.ok();
	}

	/**
	 * 根据token获取登陆记录
	 * 
	 */
	@GetMapping("/getLoginRecordForToken")
	public R getLoginRecordForToken(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		List<UserLoginEntity> list = userLoginService.getLoginRecordForToken(token);
		return R.ok().put("data", list);
	}

	/**
	 * 获取交易类型下拉
	 * 
	 */
	@GetMapping("/getTransactionRecordType")
	public R getTransactionRecordType() {
		SysDictionaryEntity entity = new SysDictionaryEntity();
		entity.setParentCode("004");
		entity.setEnable(SysConstant.ENABLE_TRUE);
		List<SysDictionaryEntity> list = sysDictionaryService
				.selectList(new EntityWrapper<SysDictionaryEntity>(entity));
		return R.ok().put("data", list);
	}

	/**
	 * 根据token获取交易记录
	 * 
	 */
	@GetMapping("/getTransactionRecordForToken")
	public R getTransactionRecordForToken(@RequestHeader("token") String token,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "type", required = false) String type, PageParam pageParam) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserTransactionRecordEntity entity = new UserTransactionRecordEntity();
		entity.setUserId(userId);
		Page<UserTransactionRecordEntity> pageList = userTransactionRecordService.getTransactionRecords(entity,
				pageParam, startTime, endTime, type);
		return R.ok().put("data", pageList);
	}

	/**
	 * 修改登陆密码
	 * 
	 */
	@PostMapping("/updateLoginPsaaword")
	public R updateLoginPsaaword(@RequestBody LoginPassWordEntity loginPassWordEntity,
			HttpServletRequest httpServletRequest) {
		if (StringUtils.isEmpty(loginPassWordEntity.getVerificationWord())
				|| StringUtils.isEmpty(loginPassWordEntity.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getCode());
		}
		// 验证新密码格式
		if (!PasswordVerification.regularVerification(loginPassWordEntity.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());

		}
		// 判断新旧密码是否一样
		if (loginPassWordEntity.getVerificationWord().equals(loginPassWordEntity.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ERRO.getCode());

		}
		String token = httpServletRequest.getHeader("token");
		userPasswordService.updateLoginPassWord(token, loginPassWordEntity.getVerificationWord(),
				loginPassWordEntity.getLoginPassWord());
		return R.ok();
	}

	/**
	 * 修改取款密码
	 * 
	 */
	@PostMapping("/updateTakeMoneyPassWord")
	public R updateTakeMoneyPassWord(@RequestBody TakeMoneyPassWordEntity userEntity,
			HttpServletRequest httpServletRequest) {
		if (StringUtils.isEmpty(userEntity.getVerificationWord())
				|| StringUtils.isEmpty(userEntity.getTakeMoneyPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISNULL_ERRO.getCode());
		}
		// 验证新密码格式
		if (!PasswordVerification.regularVerification(userEntity.getTakeMoneyPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getCode());

		}
		// 判断新旧密码是否一样
		if (userEntity.getVerificationWord().equals(userEntity.getTakeMoneyPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.USER_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.USER_PASSWORD_ERRO.getCode());
		}

		String token = httpServletRequest.getHeader("token");
		userPasswordService.updateTakeMoneyPassWord(token, userEntity.getVerificationWord(),
				userEntity.getTakeMoneyPassWord());
		return R.ok();
	}

	/**
	 * 根据token获取下级信息
	 * 
	 */
	@GetMapping("/getSubordinateUser")
	public R getSubordinateUser(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserRecommendationRecordEntity entity = new UserRecommendationRecordEntity();
		entity.setPromoterId(userId);// 查询推荐人为“我”的用户
		List<UserRecommendationRecordEntity> list = userRecommendationRecordService
				.selectList(new EntityWrapper<UserRecommendationRecordEntity>(entity));
		return R.ok().put("data", list);
	}

	/**
	 * 根据token获取用户游戏记录
	 * 
	 */
	@GetMapping("/getGameRecord")
	public R getGameRecord(@RequestHeader("token") String token, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime, PageParam pageParam) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		GameRecordEntity entity = new GameRecordEntity();
		entity.setUserId(userId);
		Page<GameRecordEntity> pageList = gameRecordService.getGameRecords(entity, pageParam, startTime, endTime);
		return R.ok().put("data", pageList);
	}

	/**
	 * 根据token获取用户推荐记录
	 * 
	 */
	@GetMapping("/selectRecommendationListForUserId")
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

	/**
	 * 根据token获取用户存款记录
	 * 
	 */
	@GetMapping("/rechargeExamineList")
	public R rechargeExamineList(@RequestHeader("token") String token, PageParam pageParam,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {

		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		Page<UserOrderRechargeExchangeParam> pageList = orderRechargeService.rechargeExamineList(userId, pageParam,
				startTime, endTime);
		return R.ok().put("data", pageList);
	}

	// 获取金币
	private Long getExchangeCoin(Long coin) {
		BigDecimal exchangeCoinDecimal = new BigDecimal(coin);
		exchangeCoinDecimal = exchangeCoinDecimal.divide(new BigDecimal(100));
		return exchangeCoinDecimal.longValue() * 100;
	}
}
