package com.xmsy.server.zxyy.webhome.modules.app.user;

import java.math.BigDecimal;
//import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.util.securitytools.MD5Util;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.UserUpdateRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.EnterGameParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.ExchangeParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.RecommenderParam;
//import com.xmsy.server.zxyy.webhome.modules.app.user.param.SignSignDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateRoomCardParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateUserParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.CoinExchange;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.CommissionExchange;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.MoneyExchange;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.UserAlipay;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.UserBank;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.UserName;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.UserPortrait;
import com.xmsy.server.zxyy.webhome.modules.app.user.validate.UserWalletPassword;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
//import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.entity.SignRewardContinuousEntity;
//import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.service.SignRewardContinuousService;
//import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.dao.SignRewardContinuousEveryDayDao;
//import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
//import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.SignUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.entity.SysDictionaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.UserVipService;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.LoginPassWordEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.TakeMoneyPassWordEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserRecommendationRecordResult;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2Utils;
//import com.xmsy.server.zxyy.webhome.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.PasswordVerification;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;

//import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-02-14 17:18:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App")
public class AppUserController {
	@Resource
	private OAuth2Utils oauth2Utils;
	@Autowired
	private UserService userService;
	@Autowired
	private PushService pushService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserInfoService userInfoService;
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
	private DomainManagementService domainManagementService;
//	@Autowired
//	private SignRewardContinuousEveryDayDao signRewardContinuousEveryDayDao;
//	@Autowired
//	private SignRewardContinuousService signRewardContinuousService;
//	@Autowired
//	private SignUserRecordService signUserRecordService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private UserVipService userVipService;
	@Autowired
	private OrderRechargeService orderRechargeService;

	/**
	 * 验证邀请码是否存在
	 */
	@GetMapping("/VerificationInvitationCode")
	public R verificationInvitationCode(String invitationCode) {
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(invitationCode);
		List<UserRecommendationEntity> list = userRecommendationService
				.selectList(new EntityWrapper<UserRecommendationEntity>(entity));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getCode());
		}
		return R.ok();
	}

	/**
	 * 验证取款密码是否正确
	 */
	@PostMapping("/VerificationBankPassword")
	public R VerificationBankPassword(@RequestHeader("token") String token, @RequestBody String password) {
		//log.debug("[验证取款密码是否正确] token {} password {} ", token, password);
		JSONObject json = JSON.parseObject(password);
		if (StringUtils.isEmpty(json.getString("password"))) {
			throw new RRException(ErrorCode.PasswordErrorCode.BANK_PASSWORD_ISNULL_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.BANK_PASSWORD_ISNULL_ERRO.getCode());
		}
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		boolean result = userPasswordService.validateBankPassword(json.getString("password"), entity.getId());
		log.debug("[验证取款密码是否正确] result {} ", result);
		return R.ok().put("data", result);
	}

	/**
	 * 手机号绑定
	 */
	@PostMapping("/phone")
	public R appPhoneBind(@RequestHeader("token") String token, @RequestParam("phoneNo") String phoneNo) {
		//log.debug("[手机号绑定 appPhoneBind] token {} phoneNo {} ", token, phoneNo);
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
	 * 获取用户信息
	 */
	@GetMapping("/UserInfo")
	public R getUserInfo(@RequestHeader("token") String token) {
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserPasswordEntity userPassword = new UserPasswordEntity();
		userPassword.setUserId(entity.getId());
		UserPasswordEntity result = userPasswordService.selectOne(new EntityWrapper<UserPasswordEntity>(userPassword));
		if (null == result && !entity.getUserType().equals(SysConstant.TRIAL)) {
			log.error("[app大厅获取用户信息接口：getUserInfo] UserPasswordEntity {} ", result);
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(1);
		String hierachyid = entity.getHierarchyId() == null ? "" : entity.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(entity.getId());
		requestVo.setUserAccount(entity.getAccount());
		requestVo.setRegisterDate(entity.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		entity.setUnreadNum(unreadNum);
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(entity.getId());// 用户ID
		UserInfoEntity userinfo = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>(userInfo));
		// 是否绑定银行卡密码
		boolean bindPassword = result == null ? false : !StringUtils.isEmpty(result.getBankPassword());
		UserDetail userDetial = new UserDetail(entity, userinfo == null ? new UserInfoEntity() : userinfo,
				bindPassword);
		int sort=0;
		if(userDetial.getVipId()>0) {
			UserVipEntity selectOne=userVipService.selectById(userDetial.getVipId());
			sort=selectOne.getSort();
			
		}
		//查询当前充值总额
		Long currentRechargeAmount = orderRechargeService.selectAllAmount(userDetial.getId());
		//查询下个vip信息
		UserVipEntity userVipEntity=userVipService.selectNextVip(sort);
		if(userVipEntity==null) {
			userDetial.setNextVipId(-1l);
		}else {
			userDetial.setNextVipName(userVipEntity.getName());
			userDetial.setNextVipId(userVipEntity.getId());
			userDetial.setNextRechargeAmount(userVipEntity.getRechargeReached());
		}
		if(currentRechargeAmount==null||currentRechargeAmount==0){
			userDetial.setCurrentRechargeAmount(0l);
		}else {
			userDetial.setCurrentRechargeAmount(currentRechargeAmount);
		}
		//log.debug("[app获取用户信息] userDetial {}", userDetial);
//		userDetial.setProviderCode(HallUrlConstant.getPROVIDER_CODE());
		UserLoginEntity userLogin = userLoginService.selectOne(new EntityWrapper<UserLoginEntity>(new UserLoginEntity().setToken(token)
				.setLoginStatus("success")).orderBy("id", false));
		if(userLogin!=null) {
			if(userLogin.getIpAddress() != null && userLogin.getNation() != null) {
				userDetial.setAddress(userLogin.getIpAddress().replace(userLogin.getNation(), ""));
			}
		}

		log.debug("[app获取用户信息] userDetial {}", userDetial);
		return R.ok().put("data", userDetial);
	}

	/**
	 * 修改用户真实姓名
	 */
	@PutMapping("/User/username")
	public R updateUsername(@RequestHeader("token") String token,
			@Validated(UserName.class) @RequestBody UpdateUserParam param) {
		//log.debug("[修改用户真实姓名] token {} param {}", token, param);
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		if ( entity.getUserType().equals(SysConstant.USER_TOURIST)) {//是游客  提示升级为正式会员
			throw new RRException(ErrorCode.UserErrorCode.USER_TYPE_TOURIST_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_TYPE_TOURIST_ERROR.getCode());
		}
		if (!StringUtils.isEmpty(entity.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USERNAME_CANNOT_UPDATE.getErrMsg(),
					ErrorCode.UserErrorCode.USERNAME_CANNOT_UPDATE.getCode());
		}
		if (!VerificationUitl.userNameVerification(param.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NAME_ERRO.getCode());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(param.getUserName());
//		if (!CollectionUtils.isEmpty(userService.selectList(new EntityWrapper<UserEntity>(userEntity)))) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getCode());
//		}
		entity.setUserName(param.getUserName());
		entity.updateById();
		UserEntity pushMessage = new UserEntity();
		pushMessage.setId(entity.getId());
		pushMessage.setUserName(param.getUserName());
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.debug("[修改用户真实姓名-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		return R.ok();
	}

	/**
	 * 修改用户头像
	 */
	@PutMapping("/User/portrait")
	public R portrait(@RequestHeader("token") String token,
			@Validated(UserPortrait.class) @RequestBody UpdateUserParam param) {
		//log.debug("[修改用户头像] token {} param {}", token, param);
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		if (!VerificationUitl.portraitVerification(param.getPortrait())) {
			throw new RRException(ErrorCode.UserErrorCode.HEAD_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.HEAD_ERRO.getCode());
		}
		entity.setPortrait(param.getPortrait());
		entity.updateById();
		// ================推送消息====================================
		UserEntity pushMessage = new UserEntity();
		pushMessage.setId(entity.getId());
		pushMessage.setPortrait(param.getPortrait());
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		//log.debug("[修改用户头像-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		return R.ok();
	}

	/**
	 * 修改用户银行卡
	 * 
	 * @throws Exception
	 */
	@UserUpdateRepeat("用户重复更新信息校验")
	@PutMapping("/User/bank")
	public R updateUserBank(@RequestHeader("token") String token,
			@Validated(UserBank.class) @RequestBody UpdateUserParam param) throws Exception {
		//log.debug("[修改用户银行卡] token {} param {}", token, param);
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		if (StringUtils.isEmpty(entity.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USERNAME_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USERNAME_IS_NULL.getCode());
		}
		SysDictionaryEntity sysDictionary = sysDictionaryService.selectById(param.getBank());
		if (sysDictionary == null) {
			throw new RRException(ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getErrMsg(),
					ErrorCode.OrderErrorCode.BANK_ISNULL_ERRO.getCode());
		}
		if (!VerificationUitl.bankCardVerification(param.getBankCard())) {
			throw new RRException(ErrorCode.UserErrorCode.BANK_CARD_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.BANK_CARD_ERRO.getCode());
		}
		// 校验用户是否已经绑定银行卡
		UserInfoEntity userInfoEntity = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(entity.getId())));
		if (!StringUtils.isEmpty(userInfoEntity.getBankCard())) {
			throw new RRException(ErrorCode.UserErrorCode.BANKCARD_CANNOT_UPDATE.getErrMsg(),
					ErrorCode.UserErrorCode.BANKCARD_CANNOT_UPDATE.getCode());
		}
		// 校验银行卡是否已经被绑定
		UserInfoEntity userInfo = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setBankCard(param.getBankCard())));
		if (null != userInfo) {
			throw new RRException(ErrorCode.UserErrorCode.BANKCARD_REPEAT_UPDATE.getErrMsg(),
					ErrorCode.UserErrorCode.BANKCARD_REPEAT_UPDATE.getCode());
		}
		userInfoEntity.setBankName(sysDictionary.getName());
		userInfoEntity.setBankAddress(param.getBankAddress());
		userInfoEntity.setBankCard(param.getBankCard());
		// 银行卡和支付宝都没有的话就是首次绑定，返利
		// if (StringUtils.isEmpty(userInfoEntity.getAlipayAccount())) {
		// userService.updateUserInfoAndRabate(userInfoEntity, entity);
		// // 用户有银行卡信息就不返利
		// } else {
		// userInfoEntity.updateById();
		// }
		userService.updateUserInfoAndRabate(userInfoEntity, entity);
		// ================推送消息====================================
		UserEntity userEntity = new UserEntity();
		userEntity.setId(entity.getId());
		UserInfoEntity pushMessage = new UserInfoEntity();
		pushMessage.setBankCard(param.getBankCard());
		UserInfoMessage message = new UserInfoMessage(userEntity, pushMessage);
		log.debug("[修改用户银行卡-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		return R.ok();
	}

	/**
	 * 修改用户取款密码
	 */
	@PutMapping("/User/WalletPassword")
	public R updateUserWalletPassword(@RequestHeader("token") String token,
			@Validated(UserWalletPassword.class) @RequestBody UpdateUserParam param) {
		//log.debug("[修改用户取款密码] token {} param {}", token, param);
		if (!PasswordVerification.regularVerification(param.getWalletPassword())) {
			log.error("[修改用户取款密码：updateUserWalletPassword] 密码格式不正确  param {}", param);
			throw new RRException(ErrorCode.UserErrorCode.USER_WALLET_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_WALLET_PASSWORD_ERRO.getCode());
		}
		if (!param.getWalletPassword().equals(param.getConfirmWalletPassword())) {
			log.error("[修改用户取款密码：] updateUserWalletPassword 两次输入密码不一致 param {}", param);
			throw new RRException(ErrorCode.UserErrorCode.USER_CONFIRM_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_CONFIRM_PASSWORD_ERRO.getCode());
		}
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		UserPasswordEntity userPassword = new UserPasswordEntity();
		userPassword.setUserId(entity.getId());
		UserPasswordEntity result = userPasswordService.selectOne(new EntityWrapper<UserPasswordEntity>(userPassword));
		if (null == result) {
			log.error("[修改用户取款密码：updateUserWalletPassword] UserPasswordEntity {}", result);
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (!StringUtils.isEmpty(result.getBankPassword())) {
			log.error("[修改用户取款密码：updateUserWalletPassword] param {}", param);
			throw new RRException(ErrorCode.UserErrorCode.USER_PASSWORD_UNMODIFY_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PASSWORD_UNMODIFY_ERRO.getCode());
		}
		result.setBankPassword(MD5Util.generate(param.getWalletPassword()));
		userPasswordService.updateById(result);
		UserEntity pushMessage = new UserEntity();
		pushMessage.setId(entity.getId());
		UserInfoMessage message = new UserInfoMessage(pushMessage, null, true);
		log.debug("[修改用户取款密码-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		return R.ok();
	}

	/**
	 * 修改用户支付宝
	 * 
	 * @throws Exception
	 */
	@PutMapping("/User/alipay")
	public R updateUserAlipay(@RequestHeader("token") String token,
			@Validated(UserAlipay.class) @RequestBody UpdateUserParam param) throws Exception {
		//log.debug("[修改用户支付宝] token {} param {}", token, param);
		UserEntity entity = userService.selectUserForToken(token);
		if (entity == null || entity.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.UserErrorCode.TOKEN_INVALID_LOSE.getCode());
		}
		if (StringUtils.isEmpty(entity.getUserName())) {
			throw new RRException(ErrorCode.UserErrorCode.USERNAME_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USERNAME_IS_NULL.getCode());
		}
		UserInfoEntity userInfoEntity = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(entity.getId())));
		if (!StringUtils.isEmpty(userInfoEntity.getAlipayAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.ALIPAY_CANNOT_UPDATE.getErrMsg(),
					ErrorCode.UserErrorCode.ALIPAY_CANNOT_UPDATE.getCode());
		}
		if (!VerificationUitl.alipayAccountVerification(param.getAlipay())) {
			throw new RRException(ErrorCode.UserErrorCode.ALIPAY_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.ALIPAY_ACCOUNT_ERRO.getCode());
		}
		userInfoEntity.setAlipayAccount(param.getAlipay());
		userInfoEntity.updateById();
		// ================推送消息====================================
		UserEntity userEntity = new UserEntity();
		userEntity.setId(entity.getId());
		UserInfoEntity pushMessage = new UserInfoEntity();
		pushMessage.setAlipayAccount(param.getAlipay());
		UserInfoMessage message = new UserInfoMessage(userEntity, pushMessage);
		log.debug("[修改用户支付宝-推送消息] message {}", message);
		pushService.pushUserInfo(message);
		return R.ok();
	}

	/**
	 * 根据token获取用户下推广链接
	 * 
	 */
	@GetMapping("/PromotionLink")
	public R PromotionLink(@RequestHeader("token") String token, PageParam pageParam) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserRecommendationEntity entity = userRecommendationService.selectRecommendationForUserId(userId);
		String officialUrl = domainManagementService.getOfficalDomain() + "?code=" + entity.getRecommendationCode();
		return R.ok().put("data", ImmutableMap.of("url", officialUrl));
	}

	/**
	 * 用户归集
	 * 
	 * @throws Exception
	 */
	@PostMapping("/Collect")
	public R exchange(@RequestHeader("token") String token) throws Exception {
		//log.debug("[exchange] 用户归集 token {} ", token);
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
		if (null != user.getGameServerId() && Constant.DEFAULT != user.getGameServerId()) {
			throw new RRException(ErrorCode.UserErrorCode.PLAYING_GAME_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.PLAYING_GAME_LIMIT_ERRO.getCode());
		}
		//log.debug("用户资金归集 : user {}", user);
		if (user.getCoin() < Constant.CLIENT_COIN_RATE) {
			return R.ok();
		}
		Long exchangeCoin = getExchangeCoin(user.getCoin());
		BigDecimal exchangeMoney = new BigDecimal(exchangeCoin).divide(new BigDecimal(Constant.COIN_RATE)).setScale(2,
				BigDecimal.ROUND_DOWN);
		userService.integrate(user, exchangeMoney, exchangeCoin, TransactionDetailType.INTEGRATE);
		// ================推送消息====================================
		UserEntity pushMessage = new UserEntity();
		pushMessage.setCoin(0 - exchangeCoin);
		pushMessage.setId(user.getId());
		pushMessage.setMoney(exchangeMoney);
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.debug("[用户归集-推送消息] message {}", message);
		pushService.pushExchange(message);
		return R.ok();
	}

	/**
	 * 金币转余额
	 * 
	 * @throws Exception
	 */
	@PostMapping("/Coin/Exchange")
	public R coinExchange(@RequestHeader("token") String token,
			@Validated(CoinExchange.class) @RequestBody ExchangeParam param) throws Exception {
		log.debug("[coinExchange] 金币转余额 token {}  param {}", token, param);
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
		if (new BigDecimal(user.getCoin())
				.compareTo(new BigDecimal(param.getCoin()).multiply(new BigDecimal(Constant.DB_COIN_RATE))) < 0) {
			throw new RRException(ErrorCode.UserErrorCode.COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.COIN_NOT_ENOUGH.getCode());
		}
		if (null != user.getGameServerId() && Constant.DEFAULT != user.getGameServerId()) {
			throw new RRException(ErrorCode.UserErrorCode.PLAYING_GAME_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.PLAYING_GAME_LIMIT_ERRO.getCode());
		}
		log.debug("[用户金币转余额] : user {} param {}", user, param);
		Long exchangeCoin = new BigDecimal(param.getCoin()).multiply(new BigDecimal(Constant.DB_COIN_RATE)).longValue();
		BigDecimal exchangeMoney = new BigDecimal(param.getCoin()).divide(new BigDecimal(Constant.CLIENT_COIN_RATE))
				.setScale(2, BigDecimal.ROUND_DOWN);
		userService.integrate(user, exchangeMoney, exchangeCoin, TransactionDetailType.COINEXCHAGE);
		// ================推送消息====================================
		UserEntity pushMessage = new UserEntity();
		pushMessage.setCoin(0 - exchangeCoin);
		pushMessage.setId(user.getId());
		pushMessage.setMoney(exchangeMoney);
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.debug("[用户归集-推送消息] message {}", message);
		pushService.pushExchange(message);
		return R.ok();
	}

	/**
	 * 余额转金币
	 * 
	 * @throws Exception
	 */
	@PostMapping("/Money/Exchange")
	public R moneyExchange(@RequestHeader("token") String token,
			@Validated(MoneyExchange.class) @RequestBody ExchangeParam param) throws Exception {
		//log.debug("[moneyExchange] 余额转金币 token {}  param {}", token, param);
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
		if (user.getMoney().compareTo(param.getMoney()) < 0) {
			throw new RRException(ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getCode());
		}
		//log.debug("[余额转金币] : user {} param {}", user, param);
		Long exchangeCoin = param.getMoney().multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
		BigDecimal exchangeMoney = param.getMoney();
		userService.changer(user, exchangeMoney, exchangeCoin);
		// ================推送消息====================================
		UserEntity pushMessage = new UserEntity();
		pushMessage.setCoin(exchangeCoin);
		pushMessage.setId(user.getId());
		pushMessage.setMoney(BigDecimal.ZERO.subtract(exchangeMoney));
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.debug("[余额转金币-推送消息] message {}", message);
		pushService.pushExchange(message);
		return R.ok();
	}

	/**
	 * 佣金转金币
	 * 
	 * @throws Exception
	 */
	@PostMapping("/Commission/Exchange")
	public R commissionExchange(@RequestHeader("token") String token,
			@Validated(CommissionExchange.class) @RequestBody ExchangeParam param) throws Exception {
		//log.debug("[commissionExchange] 佣金转金币 token {}  param {}", token, param);
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
		if (user.getCommission().compareTo(param.getCommission()) < 0) {
			throw new RRException(ErrorCode.UserErrorCode.COMMISSION_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.COMMISSION_NOT_ENOUGH.getCode());
		}
		//log.debug("[佣金转金币] : user {} param {}", user, param);
		Long exchangeCoin = param.getCommission().multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
		BigDecimal exchangeCommission = param.getCommission();
		userService.exchangeCommission(user, exchangeCommission, exchangeCoin);
		// ================推送消息====================================
		UserEntity pushMessage = new UserEntity();
		pushMessage.setCoin(exchangeCoin);
		pushMessage.setId(user.getId());
		pushMessage.setCommission(BigDecimal.ZERO.subtract(exchangeCommission));
		UserInfoMessage message = new UserInfoMessage(pushMessage, null);
		log.debug("[佣金转金币-推送消息] message {}", message);
		pushService.pushExchange(message);
		return R.ok();
	}

	/**
	 * 根据token获取用户下线人数和佣金
	 * 
	 */
	@GetMapping("/RecommendationNumAndCommission")
	public R selectRecommendationForUserId(@RequestHeader("token") String token) {
		//log.debug("[selectRecommendationForUserId] 根据token获取用户下线人数和佣金 token {} ", token);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserRecommendationEntity entity = userRecommendationService.selectRecommendationForUserId(userId);
		return R.ok().put("data",
				ImmutableMap.of("recommendationNumber", entity.getNum(), "commission", entity.getAmount()));
	}

	/**
	 * 绑定推荐人
	 * 
	 * @param token
	 * @param invitationCode
	 * @return
	 */
	@PostMapping("/BindRecommendation")
	public R BindRecommendation(@RequestHeader("token") String token, @RequestBody RecommenderParam recommenderParam) {
		//log.debug("[BindRecommendation] 绑定推荐人 token {} recommenderParam {}", token, recommenderParam);
		userService.bindRecommender(token, recommenderParam);
		return R.ok();
	}

	/**
	 * 进入游戏
	 * 
	 * @param token
	 * @param invitationCode
	 * @return
	 */
	@PostMapping("/EnterGame")
	public R EnterGame(@RequestBody @Valid EnterGameParam enterGameParam) {
		log.debug("[EnterGame] 进入游戏 enterGameParam {}", enterGameParam);
		userService.enterGame(enterGameParam);
		return R.ok();
	}

	/**
	 * 修改用户金币
	 * 
	 * @param token
	 * @param updateCoinParam
	 * @return
	 */
	@PostMapping("/UserCoin")
	public R UserCoin(@RequestBody @Valid UpdateCoinParam updateCoinParam) {
		log.debug("UserCoin 修改用户金币请求参数 {}", updateCoinParam);
		userService.updateUserCoin(updateCoinParam);
		return R.ok();
	}

	/**
	 * 修改用户房卡
	 * 
	 * @param token
	 * @param updateCoinParam
	 * @return
	 */
	@PostMapping("/UseRoomCard")
	public R UseRoomCard(@RequestBody @Valid UpdateRoomCardParam updateParam) {
		log.debug("UseRoomCard 修改用户房卡请求参数 {}", updateParam);
		userService.updateUserRoomCard(updateParam);
		return R.ok();
	}

	/**
	 * 绑定用户银行卡支付宝和真实姓名
	 * 
	 * @throws Exception
	 */
	@PutMapping("/UserInformation")
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
	@GetMapping("/UserBalance")
	public R getUserBalance(@RequestHeader("token") String token) {
		UserAccountResult entity = userService.getUserBalance(token);
		return R.ok().put("data", entity);
	}

	/**
	 * 根据token获取登陆记录
	 * 
	 */
	@GetMapping("/LoginRecordForToken")
	public R getLoginRecordForToken(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader("token");
		List<UserLoginEntity> list = userLoginService.getLoginRecordForToken(token);
		return R.ok().put("data", list);
	}

	/**
	 * 获取交易类型下拉
	 * 
	 */
	@GetMapping("/TransactionRecordType")
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
	@GetMapping("/TransactionRecordForToken")
	public R getTransactionRecordForToken(@RequestHeader("token") String token,
			@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
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
		log.debug("修改登陆密码 LoginPassWordEntity:{}", loginPassWordEntity);
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
		//log.debug("[获取用户下级] token {}", token);
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
		//log.debug("[获取游戏记录] token {} startTime {} endTime {}", token, startTime, endTime);
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
	@GetMapping("/recommendationListForUserId")
	public R recommendationListForUserId(@RequestHeader("token") String token,
			@RequestParam(value = "account", required = false) String account, PageParam pageParam) {
		//log.debug("[获取用户推荐记录] token {} account {} ", token, token, account);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = userService.selectById(userId);
		UserRecommendationEntity entity = userRecommendationService.selectRecommendationForUserId(userId);
		Page<UserRecommendationRecordResult> pageList = userRecommendationRecordService
				.selectRecommendationListForUserId(userId, pageParam, account);
		return R.ok().put("data", pageList).put("commission", user.getCommission()).put("recommendation",
				entity.getRecommendationCode());

	}

	/**
	 * 邀请码二维码
	 * 
	 */
	@GetMapping("/recommendationQrCode")
	public R recommendationQrCode(@RequestHeader("token") String token) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserRecommenderResultParam userRecommendation = userRecommendationService.selectUserRecommendation(userId);
		return R.ok().put("data", userRecommendation);
	}

	// 获取金币
	private Long getExchangeCoin(Long coin) {
		BigDecimal exchangeCoinDecimal = new BigDecimal(coin);
		exchangeCoinDecimal = exchangeCoinDecimal.divide(new BigDecimal(100));
		return exchangeCoinDecimal.longValue() * 100;
	}

	// 获取签到页面
//	@GetMapping("/getSignDetails")
//	public R getSignDetails(@RequestHeader("token") String token) {
//		log.debug("[getSignDetails===》获取签到列表] token {}", token);
//		Long userId = Long.valueOf(JwtUtil.getUserId(token));
//		Date date = DateUtils.getWeekStart(0);// 获取本周开始时间
//		log.debug("[getSignDetails===》获取签到列表] userId {},date {}", userId, date);
//		List<SignSignDetailEntity> signDetailsList = signRewardContinuousEveryDayDao.getSignDetails(userId,
//				DateUtils.format(date), DateUtils.format(DateUtils.getToday()));
//		if (CollectionUtil.isEmpty(signDetailsList) || signDetailsList.size() < 7) {
//			log.debug("[getSignDetails===》获取签到列表] 配置数量小于7 {}", signDetailsList.size());
//			throw new RRException(ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getErrMsg(),
//					ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getCode());
//		}
//		int i = DateUtils.dayOfWeek(DateUtils.getToday());// 当前星期几
//		if (null != signDetailsList.get(i - 1)
//				&& signDetailsList.get(i - 1).getStatus() == Constant.signStatus.NOT_ALLOWED_SIGN.getValue()) {
//			log.debug("[getSignDetails===》获取签到列表] 今日签到状态 {}", signDetailsList.get(i - 1).getStatus());
//			signDetailsList.get(i - 1).setStatus(Constant.signStatus.ALLOW_SIGN.getValue());// 如果今天没签到，将今天设为可签
//		}
//		// 累计签到奖励列表
//		List<SignRewardContinuousEntity> continuousList = signRewardContinuousService
//				.selectList(new EntityWrapper<SignRewardContinuousEntity>(new SignRewardContinuousEntity()));
//		List<SignUserRecordEntity> recordList = signUserRecordService
//				.selectList(new EntityWrapper<SignUserRecordEntity>(new SignUserRecordEntity().setUserId(userId))
//						.orderBy("create_time", false));
//		int num = 0;
//		if (!CollectionUtil.isEmpty(recordList)) {
//			num = recordList.get(0).getContinuousNum();
//		}
//		JSONObject obj = new JSONObject();
//		obj.put("signDayRewards", signDetailsList);
//		obj.put("continueSignRewards", continuousList);
//		obj.put("continueSignDay", num);
//		log.debug("[getSignDetails===》获取签到列表] obj {} ", obj);
//		return R.ok().put("data", obj);
//	}

	// 用户签到
//	@PostMapping("/userSign")
//	public R userSign(@RequestHeader("token") String token) throws Exception {
//		log.debug("[userSign===》用户签到] token {}", token);
//		Long userId = Long.valueOf(JwtUtil.getUserId(token));
//		log.debug("[userSign===》用户签到] userId {}", userId);
//		JSONObject obj = signUserRecordService.insertRecord(userId);
//		return R.ok().put("data", obj);
//	}
}
