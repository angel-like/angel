package com.xmsy.server.zxyy.webhome.modules.app.login;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.xmsy.server.zxyy.webhome.modules.app.login.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.ImmutableMap;
import com.xmsy.common.bean.message.LoginMessage;
import com.xmsy.common.bean.message.RegisterMessage;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.network.sms.SendSMS;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.annotation.AppRegisteredRepeat;
import com.xmsy.server.zxyy.webhome.common.annotation.LoginRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.webhome.modules.kaiyuan.service.KaiyuanGameService;
import com.xmsy.server.zxyy.webhome.modules.manager.asynchronous.UpdateUserAccountService;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.entity.KyGameloginRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.kygameloginrecord.service.KyGameloginRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.mq.MqClient;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.EntityValidateUtil;
import com.xmsy.server.zxyy.webhome.utils.IPQueryUtil;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;
import com.xmsy.server.zxyy.webhome.utils.PasswordVerification;
import com.xmsy.server.zxyy.webhome.utils.VerificationCodeUitl;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;

import lombok.extern.slf4j.Slf4j;

/**
 * 登陆
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/App/Login")
public class AppHomeLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private SysRegisterNecessaryService sysRegisterNecessaryService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private OAuth2Utils oauth2Utils;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private MqClient mqClient;
	@Autowired
	KyGameloginRecordService kyGameloginRecordService;
	@Autowired
	private KaiyuanGameService kaiyuanGameService;
	@Autowired
	private UpdateUserAccountService updateUserAccountService;
	@Autowired
	PushService pushService;
	@GetMapping("/code")
	public R getcode(String code) {
		System.out.println(code);
		return R.ok().put("code", code);
	}

	/**
	 * 验证邀请码是否存在
	 */
	@GetMapping("/verificationInvitationCode")
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
     * 获取注册手机号是否必填写
     * @return
     */
    @GetMapping("/register/phone/necessary")
    public R registerPhoneNecessary() {
    	//log.info("[APP手机注册] 获取手机号是否必填");
        SysRegisterNecessaryEntity sysRegisterNecessaryEntity = sysRegisterNecessaryService.selectById(SysConstant.REGISTER_PHONE);
        return R.ok().put("data", sysRegisterNecessaryEntity.getNecessary());
    }
	/**
	 * 验证账号是否存在，存在返回手机号码
	 */
	@GetMapping("/verificationAccount")
	public R verificationAccount(String account) {
		if (!PasswordVerification.regularVerification(account)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getCode());
		}
		UserEntity entity = new UserEntity();
		entity.setAccount(account);
		//log.info("[verificationAccount] entity{}", entity);
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		if (null == user) {
			return R.ok().put("data", ImmutableMap.of("exist", false));
		}
		if (!VerificationUitl.phoneVerification(user.getPhone())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_UNBIND_PHONE_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_UNBIND_PHONE_ERROR.getCode());
//			return R.ok().put("data", ImmutableMap.of("exist", true,
//					"phoneShow","","phone",""));
		}
		return R.ok().put("data", ImmutableMap.of("exist", true,
				"phoneShow",VerificationUitl.phoneNoShow(user.getPhone()),
				"phone",user.getPhone()));
	}

	/**
	 * APP获取手机验证码
	 */
	@GetMapping("/verificationCode")
	public R phoneVerificationCode(@RequestParam("phoneNo") String phoneNo) {
		if(sentSmsForPhone(phoneNo)) {
			return R.ok();
		}
		return R.error();
		
	}
	/**
	 * 发送短信验证码
	 * @param phoneNo
	 * @return
	 */
	private boolean sentSmsForPhone(String phoneNo) {
		if (!VerificationUitl.phoneVerification(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
		}
		if (null != localContentCache.getPhoneVerificationCode(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getCode());
		}
		//log.info("[APP获取手机验证码] phoneNo {}", phoneNo);
		String test = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, "test");
		if(null != test && "1".equals(test)) {
			localContentCache.putPhoneVerificationCode(phoneNo, "000000");
			return true;
		}
		String account = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, ThirdPartyDef.ACCOUNT);
		String password = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, ThirdPartyDef.PASSWORD);
		String url = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, ThirdPartyDef.URL);
		String extno = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, ThirdPartyDef.EXTNO);
		String smsTemplate = sysConfigService.selectByParamKey(ThirdPartyDef.BENSI_SMS, ThirdPartyDef.SMS_TEMPLATE);
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password) || StringUtils.isEmpty(url)
				|| StringUtils.isEmpty(extno) || StringUtils.isEmpty(smsTemplate)) {
			log.error("[APP获取手机验证码] url {},account {},password {},extno {},smsTemplate {}", url, account, password,
					extno, smsTemplate);
			throw new RRException(ErrorCode.ThirdPartyErrorCode.SMS_NETWORK_ERRO.getErrMsg(),
					ErrorCode.ThirdPartyErrorCode.SMS_NETWORK_ERRO.getCode());
		}
		String randomCode = VerificationCodeUitl.createVerificationCode();
		//log.info("randomCode:{}",randomCode);
		String content = String.format(smsTemplate, randomCode);
		if (SendSMS.isSuccess(SendSMS.sendSMS(url, phoneNo, account, password, content, extno))) {
			localContentCache.putPhoneVerificationCode(phoneNo, randomCode);
			return true;
		}
		return false;
	}
	/**
	 * 第三方第一次登录，绑定已经存在的账号
	 * @param registerParams
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/bindAccount")
	public R bingAccount(@RequestBody @Valid ThirdRegisterParams registerParams, HttpServletRequest httpServletRequest) {
		UserEntity entity = new UserEntity();
		entity.setAccount(registerParams.getAccount());
		//log.info("[verificationAccount] entity{}", entity);
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 验证码校验
		if (!registerParams.getCode().equals(localContentCache.getPhoneVerificationCode(user.getPhone()))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
				}
		user.setUnionType(registerParams.getUnionType());
		user.setOpenId(registerParams.getOpenId());
		user.setUnionId(registerParams.getUnionId());
		user.setSex(registerParams.getSex()==1);
		user.setNickName(Base64Util.base64Encoder(registerParams.getNickName()));
		userService.updateById(user);
		
		OAuth2Params oauth2Param = new OAuth2Params();
		oauth2Param.setCommonParam(registerParams.getCommonParam());
//		BaseLoginParam commonParam = new BaseLoginParam()
//		commonParam.setJpushRegId(registerParams.getJpushRegId())
//		oauth2Param.s.setJpushRegId(registerParams.getJpushRegId());
//		oauth2Param.setHallId(registerParams.getHallId());
//		oauth2Param.setDeviceType(registerParams.getDeviceType());
//		oauth2Param.setDeviceCode(registerParams.getDeviceCode());
//		oauth2Param.setEdition(registerParams.getEdition());
		UserDetail result = null;
		String ip = IpUtil.getIPAddress(httpServletRequest);
		try {
			result = userService.appThirdPartyLogin(user, oauth2Param, ip);
		} catch (Exception e) {
			String ipData[] = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(user.getId());
			userLogin.setHallId(1L);
			userLogin.setIp(ip);
			try {
				if (ipData != null) {
					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1);
			}
			userLogin.setEdition(oauth2Param.getCommonParam().getEdition());
			userLogin.setDeviceCode(oauth2Param.getCommonParam().getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(oauth2Param.getCommonParam().getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}
		return R.ok().put("data", result);
		
	}
	

	/**
	 * APP注册并登录
	 */
	@AppRegisteredRepeat("APP注册表单重复提交验证")
	@PostMapping("/register")
	public R appRegister(@RequestBody @Valid RegisterParams registerParams, HttpServletRequest httpServletRequest) {
		//log.info("[APP注册] registerParams {}", registerParams);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		registerParams.setRegisterIp(ip);
		UserDetail userDetail = userService.appRegister(registerParams);
		RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
				registerParams.getCommonParam().getDeviceType(), registerParams.getCommonParam().getDeviceCode(),
				registerParams.getCommonParam().getInvitationCode(), 1L, registerParams.getCommonParam().getEdition());
		message.setToken(userDetail.getToken());
		//log.info("message {}", message);
		mqClient.registerPush(message);
		return R.ok().put("data", userDetail);
	}


	/**
	 * 游客升级
	 */

	@PostMapping("/upgrade")
	public R upgrade(@RequestBody @Valid UpgradeParams upgradeParams,@RequestHeader("token") String token, HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getIPAddress(httpServletRequest);
		upgradeParams.setRegisterIp(ip);
        upgradeParams.setToken(token);
        UserDetail userDetail = userService.upgrade(upgradeParams);
        updateUserAccountService.UpdateUserAccountAsync(userDetail.getId(), userDetail.getAccount());
        UserInfoMessage userInfoMessage = new UserInfoMessage();
        userInfoMessage.setUid(userDetail.getId());
        userInfoMessage.setAccount(userDetail.getAccount());
        userInfoMessage.setNick(userDetail.getNickName());
        pushService.pushUserInfo(userInfoMessage);
		return R.ok().put("data", userDetail);
}


	/**
	 * APP手机号注册并登录
	 */
	@AppRegisteredRepeat("APP手机注册表单重复提交验证")
	@PostMapping("/phoneRegister")
	public R appPhoneRegister(@RequestBody @Valid PhoneRegisterParams registerParams,
			HttpServletRequest httpServletRequest) {
		//log.info("[APP手机注册] registerParams {}", registerParams);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		registerParams.setRegisterIp(ip);
		UserDetail userDetail = userService.appPhoneRegister(registerParams);
		RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
				registerParams.getCommonParam().getDeviceType(), registerParams.getCommonParam().getDeviceCode(), null,
				1L, registerParams.getCommonParam().getEdition());
		message.setToken(userDetail.getToken());
		//log.info("message {}", message);
		mqClient.registerPush(message);
		return R.ok().put("data", userDetail);
	}


	/**
	 * 第三方登陆
	 */
	@AppRegisteredRepeat("APP第三方登入注册表单重复提交验证")
	@PostMapping(value = "/oauth2Login")
	public R oauth2LoginWithPhone(@RequestBody @Valid OAuth2Params oauth2Param, HttpServletRequest httpServletRequest)
			throws Exception {
		//log.info("oauth2Param {}", oauth2Param);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		OAuth2 oauth2 = oauth2Utils.getOAuth2(oauth2Param.getType());
		if (null == oauth2) {
			throw new RRException(ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getCode());
		}
		String respone = oauth2.getAccessToken(oauth2Param.getCode());
		//log.info("respone {}", respone);
		String access_token = JSON.parseObject(respone).getString("access_token");
		String openId = JSON.parseObject(respone).getString("openid");
		UserEntity entity = new UserEntity();
		entity.setOpenId(openId);
		if (StringUtils.isEmpty(openId)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getCode());
		}
		//log.info("[oauth2LoginWithPhone] entity{}", entity);
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		if (null == user) {
//			// 该第三方未登陆过，需要注册
			entity.setRegisterDeviceCode(oauth2Param.getCommonParam().getDeviceCode());
			entity.setUnionType(oauth2Param.getType());
			entity.setRegisterIp(ip);
			entity.setChannelCode(oauth2Param.getCommonParam().getChannelCode());
			String userinfo = oauth2.getUser(access_token, openId);
			JSONObject userResult = JSON.parseObject(userinfo);
			//log.info("[oauth2LoginWithPhone] userinfo obj {}", userResult);
			if (userResult.getInteger("errcode") != null) {
				throw new RRException("第三方登陆出错");
			}
			String unionId = userResult.getString("unionid");
			int sex = userResult.getIntValue("sex");
			entity.setUnionId(unionId);
			if (sex == 0) {
				entity.setSex(false);
			} else if (sex == 1) {
				entity.setSex(true);
			}
			entity.setNickName(Base64Util.base64Encoder(userResult.getString("nickname")));
			UserDetail userDetail = userService.thirdPartyRegister(entity, userResult, oauth2Param);
			RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
					oauth2Param.getCommonParam().getDeviceType(), oauth2Param.getCommonParam().getDeviceCode(), oauth2Param.getCommonParam().getInvitationCode(),
					1L, oauth2Param.getCommonParam().getEdition());
			message.setToken(userDetail.getToken());
			//log.info("message {}", message);
			mqClient.registerPush(message);
			user=entity;
		}
		UserDetail result = null;
		try {
			result = userService.appThirdPartyLogin(user, oauth2Param, ip);
		} catch (Exception e) {
			log.error(e.getMessage());
			String ipData[] = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(user.getId());
			userLogin.setHallId(1L);
			userLogin.setIp(ip);
			try {
				if (ipData != null) {
					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1);
			}
			userLogin.setEdition(oauth2Param.getCommonParam().getEdition());
			userLogin.setDeviceCode(oauth2Param.getCommonParam().getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(oauth2Param.getCommonParam().getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}
		return R.ok().put("data", result);
	}
	/**
	 * 第三方登陆
	 */
	@AppRegisteredRepeat("APP第三方登入注册表单重复提交验证")
	@PostMapping(value = "/oauth2Valid")
	public R oauth2LoginWithPhone2(@RequestBody @Valid OAuth2Params oauth2Param, HttpServletRequest httpServletRequest)
			throws Exception {
		//log.info("oauth2Param {}", oauth2Param);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		log.info("oauth2Valid ip:{}", ip);
		OAuth2 oauth2 = oauth2Utils.getOAuth2(oauth2Param.getType());
		if (null == oauth2) {
			throw new RRException(ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getCode());
		}
		String respone = oauth2.getAccessToken(oauth2Param.getCode());
		//log.info("respone {}", respone);
		String access_token = JSON.parseObject(respone).getString("access_token");
		String openId = JSON.parseObject(respone).getString("openid");
		UserEntity entity = new UserEntity();
		entity.setOpenId(openId);
		if (StringUtils.isEmpty(openId)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getCode());
		}
		//log.info("[oauth2LoginWithPhone] entity{}", entity);
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		if (null == user) {
//			// 该第三方未登陆过，需要注册
			String userinfo = oauth2.getUser(access_token, openId);
			JSONObject userResult = JSON.parseObject(userinfo);
			//log.info("[oauth2LoginWithPhone] userinfo obj {}", userResult);
			if (userResult.getInteger("errcode") != null) {
				throw new RRException("第三方登陆出错");
			}
			ThirdRegisterParams param = new ThirdRegisterParams();
			param.setUnionId(userResult.getString("unionid"));
			param.setSex(userResult.getIntValue("sex"));
			param.setNickName(userResult.getString("nickname"));
			param.setOpenId(openId);
			param.setUnionType(oauth2Param.getType());
			return R.ok().put("data",ImmutableMap.of("isFirst", true,"user",param));
		}
		UserDetail result = null;
		try {
			result = userService.appThirdPartyLogin(user, oauth2Param, ip);
		} catch (Exception e) {
			String ipData[] = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(user.getId());
			userLogin.setHallId(1L);
			userLogin.setIp(ip);
			try {
				if (ipData != null) {
					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1);
			}
			userLogin.setEdition(oauth2Param.getCommonParam().getEdition());
			userLogin.setDeviceCode(oauth2Param.getCommonParam().getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(oauth2Param.getCommonParam().getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}
//		return R.ok().put("data", result);
		return R.ok().put("data",ImmutableMap.of("isFirst", false,"token",result.getToken()));
	}

	/**
	 * 登陆
	 */
	@LoginRepeat("重复登入校验")
	@PostMapping("/login")
	public R login(@RequestBody LoginParams loginParams, HttpServletRequest httpServletRequest) throws Exception {
//		log.info("loginParams {}", loginParams);
		// 校验参数
		String errorEesult = EntityValidateUtil.validateModel(loginParams);
		if (!StringUtils.isEmpty(errorEesult)) {
			throw new ParamInvalidException(errorEesult);
		}
		String ip = IpUtil.getIPAddress(httpServletRequest);
		// String ip = loginParams.getLoginIp();
		UserEntity entity = new UserEntity();
		entity.setAccount(loginParams.getAccount());
		UserEntity user = null;
		// 判断账号是不是手机号格式
		if (!VerificationUitl.phoneVerification(loginParams.getAccount())) {
			user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		} else {
			user = userService.selectOne(new EntityWrapper<UserEntity>(entity).orNew("phone={0}", entity.getAccount()));
		}
		// 查看账号是否输入正确
		if (user == null || user.getId() == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		Long userId = user.getId();
		if (StringUtils.isEmpty(ip)) {
			// IP为空
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getCode());
		}
		UserDetail result = null;
		try {
			result = userService.appLogin(user, loginParams, ip, true);
			LoginMessage message = new LoginMessage();
			message.setUserId(result.getId());
			message.setUserAccount(result.getAccount());
			mqClient.loginPush(message);
		} catch (Exception e) {
			String ipData[] = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(userId);
			userLogin.setHallId(1L);
			userLogin.setIp(ip);
			try {
				if (ipData != null) {

					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);

				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1.getMessage());
			}
			userLogin.setEdition(loginParams.getCommonParam().getEdition());
			userLogin.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(loginParams.getCommonParam().getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}

		//开元游戏短线重连

		//先查看用户在线状态。
//		JSONObject statusObj = kaiyuanGameService.getOnlineStatus(entity).getJSONObject("d");
//		if (statusObj.getIntValue("code") == 0 && statusObj.getIntValue("status") == 1){
//
//		}
		int code =0;
		JSONObject object = kaiyuanGameService.queryMoney(user).getJSONObject("d");
		code = object.getIntValue("code");
		if ( code == 0){
			int status =object.getIntValue("status");
			int gameStatus = object.getIntValue("gameStatus");
			if (status > -1){ //玩家账号存在。
				if (gameStatus >0){ //在游戏中
					List<KyGameloginRecordEntity> list = kyGameloginRecordService.selectList(new EntityWrapper<KyGameloginRecordEntity>(new KyGameloginRecordEntity().setUserId(userId)).last("limit 1"));
					if(list!= null && list.size() >0) {//
						OnlineInfo onlineInfo = new OnlineInfo();
						onlineInfo.setGameId(list.get(0).getGameId());
						onlineInfo.setUrl(list.get(0).getGameUrl());
						result.setOnlineInfo(onlineInfo);

					}


				}else {//不在游戏中
//					float totalMoney = object.getFloat("totalMoney");
					String freeMoney = (object.getString("freeMoney"));
					JSONObject clearmoney =  kaiyuanGameService.clearMoney(user,freeMoney).getJSONObject("d");
					code = clearmoney.getIntValue("code");
					if ( code == 0){
						UserEntity u = new UserEntity();
						u.setId(userId);
						u.setCoin(new BigDecimal(freeMoney).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
						u.setFreezeCoin(0L);
						result.setCoin(new BigDecimal(freeMoney).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
						result.setFreezeCoin(0L);
						userService.unfreezeUserCoin(u);

						UserEntity userUpdateParam = new UserEntity();
						userUpdateParam.setId(entity.getId());
						userUpdateParam.setCoin(u.getCoin());
						UserInfoMessage message = new UserInfoMessage(userUpdateParam,null);
						log.info("[进入开元 金币解冻-推送消息] message {}", message);
						pushService.pushUserInfo(message);
					}
//					else{
//						throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
//					}
				}
			}
		}
//		else{
//			throw new RRException(KyErrorCodeEnum.KyErrorRecordCodeEnum.getKyErrorCodeMsg(code),code);
//		}
		return R.ok().put("data", result);
	}

	/**
	 * /** 退出登陆
	 */
	@GetMapping("/loginOut")
	public R loginOut(@RequestHeader("token") String token) {
		userService.logout(token);
		return R.ok();
	}




	/**
	 * 试玩
	 */
	@GetMapping("/trialAccount")
	public R trialAccount(@RequestParam("hallId") Long hallId, @RequestParam("deviceType") String deviceType,
			HttpServletRequest httpServletRequest) {
		String ip = IpUtil.getIPAddress(httpServletRequest);
		UserDetail result = null;
		result = userService.appTrialAccount(ip, hallId, deviceType);
		return R.ok().put("data", result);
	}

	/**
	 * 游客登录
	 */
	@PostMapping("/touristLogin")
	public R touristLogin(@RequestBody TLoginParams loginParams, HttpServletRequest httpServletRequest) {
		String ip = IpUtil.getIPAddress(httpServletRequest);
		UserDetail result = null;
		UserEntity user = userService.getUserByTouristId(loginParams.getTouristId());

		if (user != null){ //游客账号已经存在
			try {
				//游客升级后，再次游客登录后提示使用账号密码登录
				if(SysConstant.USER.equals(user.getUserType())) {
					throw new RRException(ErrorCode.UserErrorCode.USER_TYPE_LOGIN_ERROR.getErrMsg(),
							ErrorCode.UserErrorCode.USER_TYPE_LOGIN_ERROR.getCode());
				}
				result = userService.touristLogin(user, loginParams, ip);
				LoginMessage message = new LoginMessage();
				message.setUserId(result.getId());
				message.setUserAccount(result.getAccount());
				mqClient.loginPush(message);
			} catch (Exception e) {
				String ipData[] = IPQueryUtil.queryIp(ip);
				// 新增一条登陆失败记录
				UserLoginEntity userLogin = new UserLoginEntity();
				userLogin.setUserId(user.getId());
				userLogin.setHallId(1L);
				userLogin.setIp(ip);
				try {
					if (ipData != null) {
						userLogin.setNation(ipData[0]);
						userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
					}
				} catch (Exception e1) {
					log.error("[ip解析返回错误]", e1.getMessage());
				}
				userLogin.setEdition(loginParams.getCommonParam().getEdition());
				userLogin.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
				userLogin.setLoginStatus(SysConstant.FAIL);
				userLogin.setDeviceType(loginParams.getCommonParam().getDeviceType());
				userLoginService.insert(userLogin);
				throw e;
			}
			return R.ok().put("data", result);
		}else{//游客注册
			loginParams.setLoginIp(ip);
			result = userService.touristRegister(loginParams);
			RegisterMessage message = new RegisterMessage(result.getId(), result.getAccount(), ip,
					loginParams.getCommonParam().getDeviceType(), loginParams.getCommonParam().getDeviceCode(),
					loginParams.getCommonParam().getInvitationCode(), 1L, loginParams.getCommonParam().getEdition());
			message.setToken(result.getToken());
			//log.info("message {}", message);
			mqClient.registerPush(message);
			return R.ok().put("data", result);

		}
	}


	/**
	 * APP手机号注册并登录
	 */

	@PostMapping("/phoneLogin")
	public R phoneLogin(@RequestBody @Valid PhoneLoginParams phoneLoginParams,
						   HttpServletRequest httpServletRequest) {
		//log.info("[APP手机注册] registerParams {}", registerParams);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		phoneLoginParams.setRegisterIp(ip);
		UserDetail result = null;
	    UserEntity user  = userService.getUserByPhone(phoneLoginParams.getAccount());
		if (user != null){ //账号已经存在
			try {
				result = userService.phoneLogin(user, phoneLoginParams, ip);
				LoginMessage message = new LoginMessage();
				message.setUserId(result.getId());
				message.setUserAccount(result.getAccount());
				mqClient.loginPush(message);
			} catch (Exception e) {
				String ipData[] = IPQueryUtil.queryIp(ip);
				// 新增一条登陆失败记录
				UserLoginEntity userLogin = new UserLoginEntity();
				userLogin.setUserId(user.getId());
				userLogin.setHallId(1L);
				userLogin.setIp(ip);
				try {
					if (ipData != null) {
						userLogin.setNation(ipData[0]);
						userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
					}
				} catch (Exception e1) {
					log.error("[ip解析返回错误]", e1.getMessage());
				}
				userLogin.setEdition(phoneLoginParams.getCommonParam().getEdition());
				userLogin.setDeviceCode(phoneLoginParams.getCommonParam().getDeviceCode());
				userLogin.setLoginStatus(SysConstant.FAIL);
				userLogin.setDeviceType(phoneLoginParams.getCommonParam().getDeviceType());
				userLoginService.insert(userLogin);
				throw e;
			}

			return R.ok().put("data", result);

	}else{
			result = userService.phoneRegister(phoneLoginParams);
			RegisterMessage message = new RegisterMessage(result.getId(), result.getAccount(), ip,
					phoneLoginParams.getCommonParam().getDeviceType(), phoneLoginParams.getCommonParam().getDeviceCode(),
					phoneLoginParams.getCommonParam().getInvitationCode(), 1L,
					phoneLoginParams.getCommonParam().getEdition());
			message.setToken(result.getToken());
			//log.info("message {}", message);
			mqClient.registerPush(message);
			return R.ok().put("data", result);

		}
	}
}
