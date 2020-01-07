package com.xmsy.server.zxyy.manager.modules.web.login;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.common.bean.message.RegisterMessage;
import com.xmsy.common.define.exception.ParamInvalidException;
import com.xmsy.network.sms.SendSMS;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.annotation.LoginRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.WebRegisteredRepeat;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.common.validator.ValidatorUtils;
import com.xmsy.server.zxyy.manager.common.validator.group.AddGroup;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysCaptchaService;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.service.SysConfigService;
import com.xmsy.server.zxyy.manager.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.LoginEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.RegisterEntity;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.manager.utils.EntityValidateUtil;
import com.xmsy.server.zxyy.manager.utils.IpUtil;
import com.xmsy.server.zxyy.manager.utils.VerificationCodeUitl;
import com.xmsy.server.zxyy.manager.utils.VerificationUitl;

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
@RequestMapping("webhome/user")
public class WebHomeLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysRegisterNecessaryService sysRegisterNecessaryService;
	@Autowired
	private UserLoginService userLoginService;
	@Resource
	private OAuth2Utils oauth2Utils;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private MqClient mqClient;

	/**
	 * web获取手机验证码
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
	 * 注册验证
	 */
	@GetMapping("/registerMust")
	public R registerMust() {
		return R.ok().put("data", sysRegisterNecessaryService.getRegisterNecessary());
	}

	/**
	 * 获取在线人数
	 */
	@GetMapping("/onlineUsers")
	public R onlineUsers() {
		int num = 125810
				+ userService.selectCount(new EntityWrapper<UserEntity>(null).ne("user_type", SysConstant.USER));
		return R.ok().put("data", num);
	}

	/**
	 * web注册
	 */
	@WebRegisteredRepeat("官网注册表单重复提交验证")
	@PostMapping("/register")
	public R register(@RequestBody RegisterEntity registerEntity, HttpServletRequest httpServletRequest) {
		ValidatorUtils.validateEntity(registerEntity, AddGroup.class);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		registerEntity.setRegisterIp(ip);
		UserEntity user = userService.register(registerEntity);
		RegisterMessage message = new RegisterMessage(user.getId(), user.getAccount(), ip,
				registerEntity.getDeviceType(), registerEntity.getRegisterDeviceCode(),
				registerEntity.getInvitationCode(), registerEntity.getHallId(), registerEntity.getEdition());
		message.setToken(user.getToken());
		mqClient.registerPush(message);
		return R.ok().put("token", user.getToken());
	}

	/**
	 * web手机注册
	 */
	@WebRegisteredRepeat("官网注册表单重复提交验证")
	@PostMapping("/phoneRegister")
	public R phoneRegister(@RequestBody PhoneRegisterEntity phoneRegisterEntity,
			HttpServletRequest httpServletRequest) {
		ValidatorUtils.validateEntity(phoneRegisterEntity, AddGroup.class);
		String ip = IpUtil.getIPAddress(httpServletRequest);
		phoneRegisterEntity.setRegisterIp(ip);
		UserEntity user = userService.webPhoneRegister(phoneRegisterEntity);
		RegisterMessage message = new RegisterMessage(user.getId(), user.getAccount(), ip,
				phoneRegisterEntity.getDeviceType(), phoneRegisterEntity.getRegisterDeviceCode(),
				phoneRegisterEntity.getInvitationCode(), phoneRegisterEntity.getHallId(),
				phoneRegisterEntity.getEdition());
		message.setToken(user.getToken());
		mqClient.registerPush(message);
		return R.ok().put("token", user.getToken());
	}

	/**
	 * 登陆
	 */
	@LoginRepeat("重复登入校验")
	@PostMapping("/login")
	public R login(@RequestBody LoginEntity loginEntity, HttpServletRequest httpServletRequest) throws Exception {
		// 校验验证码
		if (!sysCaptchaService.validate(loginEntity.getCodeUuid(), loginEntity.getCode())) {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_ISNULL.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_ISNULL.getCode());
		}
		// 校验参数
		String errorEesult = EntityValidateUtil.validateModel(loginEntity);
		if (!StringUtils.isEmpty(errorEesult)) {
			throw new ParamInvalidException(errorEesult);
		}

		String token;
		String ip = IpUtil.getIPAddress(httpServletRequest);
		UserEntity entity = new UserEntity();
		entity.setAccount(loginEntity.getAccount());
		UserEntity user = null;
		// 判断账号是不是手机号格式
		if (!VerificationUitl.phoneVerification(loginEntity.getAccount())) {
			user = userService.selectOne(new EntityWrapper<UserEntity>(entity));
		} else {
			user = userService.selectOne(new EntityWrapper<UserEntity>(entity).orNew("phone={0}", entity.getAccount()));
		}
		// 查看账号是否输入正确
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
		}
		Long userId = user.getId();
		int i = 0;
		if (localContentCache.get(SysConstant.PASSWORD_ERROR + userId) != null) {
			i = (int) localContentCache.get(SysConstant.PASSWORD_ERROR + userId);// 输入错误密码次数
			if (i >= SysConstant.PASSWORD_ERROR_NUM) {
				throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_NUM_ERRO.getErrMsg(),
						ErrorCode.PasswordErrorCode.PASSWORD_NUM_ERRO.getCode());
			}
		}
		user.setId(userId);
		user.setToken(user.getToken());
		if (StringUtils.isEmpty(ip)) {
			// IP为空
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_ISNULL_ERRO.getCode());
		}

		try {
			token = userService.login(user, loginEntity, ip);
		} catch (Exception e) {
			// 新增一条登陆失败记录
			userLoginService.insert(userId, ip, loginEntity.getRegisterDeviceCode(), loginEntity.getDeviceType(), null,
					SysConstant.FAIL);
			// 新增一条密码错误记录
			localContentCache.put(SysConstant.PASSWORD_ERROR + userId, i + 1);
			throw e;
		}
		// JSONObject param = new JSONObject();
		// param.put("Uid", userId);
		// try {
		// HttpRequest.post(HallUrlConstant.getKICK_URL()).timeout(5000).body(param.toString()).execute().body();
		// } catch (Exception e) {
		// log.error("官网登入发送uid到大厅", e);
		// throw new RRException(ErrorCode.UserErrorCode.HALL_REQUEST_ERRO.getErrMsg(),
		// ErrorCode.UserErrorCode.HALL_REQUEST_ERRO.getCode());
		// }
		return R.ok().put("token", token);
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
	 * 试玩
	 */
	@GetMapping("/trialAccount")
	public R trialAccount(@RequestParam("deviceType") String deviceType, HttpServletRequest httpServletRequest) {
		String ip = IpUtil.getIPAddress(httpServletRequest);
		String token = userService.trialAccount(ip, deviceType);
		return R.ok().put("token", token);
	}

}
