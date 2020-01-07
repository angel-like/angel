package com.xmsy.server.zxyy.manager.modules.app.login;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.common.bean.message.RegisterMessage;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.network.sms.SendSMS;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.AppRegisteredRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.LoginRepeat;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.manager.modules.app.login.param.LoginParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.OAuth2Params;
import com.xmsy.server.zxyy.manager.modules.app.login.param.PhoneRegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.RegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.oauth2.OAuth2;
import com.xmsy.server.zxyy.manager.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.manager.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
	 * APP获取手机验证码
	 */
	@GetMapping("/verificationCode")
	public R phoneVerificationCode(@RequestParam("phoneNo") String phoneNo) {
		if (!VerificationUitl.phoneVerification(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
		}
		if (null != localContentCache.getPhoneVerificationCode(phoneNo)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_LIMIT_ERRO.getCode());
		}
		log.info("[APP获取手机验证码] phoneNo {}", phoneNo);
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
		String content = String.format(smsTemplate, randomCode);
		if (SendSMS.isSuccess(SendSMS.sendSMS(url, phoneNo, account, password, content, extno))) {
			localContentCache.putPhoneVerificationCode(phoneNo, randomCode);
			return R.ok().put("data", randomCode);
		}
		return R.error();
	}

	/**
	 * APP注册并登录
	 */
	@AppRegisteredRepeat("APP注册表单重复提交验证")
	@PostMapping("/register")
	public R appRegister(@RequestBody @Valid RegisterParams registerParams, HttpServletRequest httpServletRequest) {
		log.info("[APP注册] registerParams {}", registerParams);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		registerParams.setRegisterIp(ip);
		UserDetail userDetail = userService.appRegister(registerParams);
		RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
				registerParams.getDeviceType(), registerParams.getRegisterDeviceCode(),
				registerParams.getInvitationCode(), registerParams.getHallId(), registerParams.getEdition());
		message.setToken(userDetail.getToken());
		log.info("message {}", message);
		mqClient.registerPush(message);
		return R.ok().put("data", userDetail);
	}

	/**
	 * APP手机号注册并登录
	 */
	@AppRegisteredRepeat("APP手机注册表单重复提交验证")
	@PostMapping("/phoneRegister")
	public R appPhoneRegister(@RequestBody @Valid PhoneRegisterParams registerParams,
			HttpServletRequest httpServletRequest) {
		log.info("[APP手机注册] registerParams {}", registerParams);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		registerParams.setRegisterIp(ip);
		UserDetail userDetail = userService.appPhoneRegister(registerParams);
		RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
				registerParams.getDeviceType(), registerParams.getRegisterDeviceCode(), null,
				registerParams.getHallId(), registerParams.getEdition());
		message.setToken(userDetail.getToken());
		log.info("message {}", message);
		mqClient.registerPush(message);
		return R.ok().put("data", userDetail);
	}

	/**
	 * 第三方登陆
	 */
	@AppRegisteredRepeat("APP第三方登入注册表单重复提交验证")
	@PostMapping(value = "/oauth2Login")
	public R oauth2LoginWithPhone(@RequestBody OAuth2Params oauth2Param, HttpServletRequest httpServletRequest)
			throws Exception {
		log.info("oauth2Param {}", oauth2Param);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		OAuth2 oauth2 = oauth2Utils.getOAuth2(oauth2Param.getType());
		if (null == oauth2) {
			throw new RRException(ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_REGISTER_TYPE_ERRO.getCode());
		}
		String respone = oauth2.getAccessToken(oauth2Param.getCode());
		log.info("respone {}", respone);
		String access_token = JSON.parseObject(respone).getString("access_token");
		String openId = JSON.parseObject(respone).getString("openid");
		UserEntity entity = new UserEntity();
		entity.setOpenId(openId);
		if (StringUtils.isEmpty(openId)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_OAUTH_CODE_ERRO.getCode());
		}
		log.info("[oauth2LoginWithPhone] entity{}", entity);
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		if (null == user) {
			// 该第三方未登陆过，需要注册
			entity.setRegisterDeviceCode(oauth2Param.getDeviceCode());
			entity.setUnionType(oauth2Param.getType());
			entity.setRegisterIp(ip);
			String userinfo = oauth2.getUser(access_token, openId);
			JSONObject userResult = JSON.parseObject(userinfo);
			log.info("[oauth2LoginWithPhone] userinfo obj {}", userResult);
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
			UserDetail userDetail = userService.thirdPartyRegister(entity, userResult, oauth2Param);
			RegisterMessage message = new RegisterMessage(userDetail.getId(), userDetail.getAccount(), ip,
					oauth2Param.getDeviceType(), oauth2Param.getDeviceCode(), oauth2Param.getInvitationCode(),
					oauth2Param.getHallId(), oauth2Param.getEdition());
			message.setToken(userDetail.getToken());
			log.info("message {}", message);
			mqClient.registerPush(message);
			return R.ok().put("data", userDetail);
		}
		UserDetail result = null;
		try {
			result = userService.appThirdPartyLogin(user, oauth2Param, ip);
		} catch (Exception e) {
			String[] ipData = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(user.getId());
			userLogin.setHallId(oauth2Param.getHallId());
			userLogin.setIp(ip);
			try {
				if (ipData != null) {
					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1);
			}
			userLogin.setEdition(oauth2Param.getEdition());
			userLogin.setDeviceCode(oauth2Param.getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(oauth2Param.getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}
		return R.ok().put("data", result);
	}

	/**
	 * 登陆
	 */
	@LoginRepeat("重复登入校验")
	@PostMapping("/login")
	public R login(@RequestBody LoginParams loginParams, HttpServletRequest httpServletRequest)  {
		log.info("loginParams {}", loginParams);
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
			user = userService.selectOne(new EntityWrapper<>(entity));
		} else {
			user = userService.selectOne(new EntityWrapper<>(entity).orNew("phone={0}", entity.getAccount()));
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
		} catch (Exception e) {
			String[] ipData = IPQueryUtil.queryIp(ip);
			// 新增一条登陆失败记录
			UserLoginEntity userLogin = new UserLoginEntity();
			userLogin.setUserId(userId);
			userLogin.setHallId(loginParams.getHallId());
			userLogin.setIp(ip);
			try {
				if (ipData != null) {
					userLogin.setNation(ipData[0]);
					userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				}
			} catch (Exception e1) {
				log.error("[ip解析返回错误]", e1);
			}
			userLogin.setEdition(loginParams.getEdition());
			userLogin.setDeviceCode(loginParams.getDeviceCode());
			userLogin.setLoginStatus(SysConstant.FAIL);
			userLogin.setDeviceType(loginParams.getDeviceType());
			userLoginService.insert(userLogin);
			throw e;
		}
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

}
