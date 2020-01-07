package com.xmsy.server.zxyy.webhome.modules.manager.user.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.util.securitytools.MD5Util;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.dao.EnvelopeRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service.FortuneActiviConfigService;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.cache.LocalContentCache;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ArrayUtil;
import com.xmsy.server.zxyy.webhome.common.utils.ConfigConstant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.balance.param.SwitchParam;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.BindingPhone;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.LoginParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.OAuth2Params;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.PhoneLoginParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.PhoneRegisterParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.PhoneRegisterParamsExt;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.RegisterParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.TLoginParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.UpgradeParams;
import com.xmsy.server.zxyy.webhome.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.EnterGameParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.RecommenderParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateRoomCardParam;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;
import com.xmsy.server.zxyy.webhome.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.webhome.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeStatistics;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.dao.OrderTakeMoneyDao;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatistics;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproprecord.entity.ShopPropRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysdictionary.service.SysDictionaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.entity.SysRegisterNecessaryEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.sysregisternecessary.service.SysRegisterNecessaryService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.dao.UserAgentHierarchyDao;
import com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.entity.UserBlacklistEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userblacklist.service.UserBlacklistService;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebate.entity.UserRebateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebate.service.UserRebateService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.dao.UserRecommendationDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.dao.UserRecommendationRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.entity.UserTrialConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertrialconfig.service.UserTrialConfigService;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.LoginEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.RegisterEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserPasswordResult;
import com.xmsy.server.zxyy.webhome.modules.webim.logon.entity.WebimPhoneRegisterEntity;
import com.xmsy.server.zxyy.webhome.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.webhome.utils.AccountVerification;
import com.xmsy.server.zxyy.webhome.utils.Base64Util;
import com.xmsy.server.zxyy.webhome.utils.ByteUtil;
import com.xmsy.server.zxyy.webhome.utils.IPQueryUtil;
import com.xmsy.server.zxyy.webhome.utils.InviteCode;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;
import com.xmsy.server.zxyy.webhome.utils.PasswordVerification;
import com.xmsy.server.zxyy.webhome.utils.UserStatusVerificationUtil;
import com.xmsy.server.zxyy.webhome.utils.VerificationUitl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private UserRecommendationService userRecommendationService;
	@Autowired
	private UserPasswordService userPasswordService;
	@Autowired
	private UserRecommendationRecordService userRecommendationRecordService;
	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;
	@Autowired
	private UserTrialConfigService userTrialConfigService;
	@Autowired
	private UserAgentHierarchyService userAgentHierarchyService;
	@Autowired
	private UserRebateService userRebateService;
	@Autowired
	private UserBlacklistService userBlacklistService;
	@Autowired
	private  MessageUserService  messageUserService;
	@Autowired
	private MessageManagementService  messageManagementService;
	@Autowired
	private DomainManagementService domainManagementService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private OrderRechargeDao orderRechargeDao;
	@Autowired
	private OrderTakeMoneyDao orderTakeMoneyDao;
	@Autowired
	private PushService pushService;
	@Autowired
	private UserAgentHierarchyDao userAgentHierarchyDao;
	@Autowired
	private UserRecommendationDao userRecommendationDao;
	@Autowired
	private UserRecommendationRecordDao userRecommendationRecordDao;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysRegisterNecessaryService sysRegisterNecessaryService;
	@Autowired
	private FortuneActiviConfigService fortuneActiviConfigService;
	@Autowired
	private EnvelopeRecordDao envelopeRecordDao;

	public static final String TITLE = "实名返利通知";
	public static final String SUCCUSS_CONTENT = "用户实名返利; 金币 :%s, 请查收";
	public static final String INVITE_TITLE = "邀请奖励通知";
	public static final String INVITE_SUCCUSS_CONTENT = "邀请奖励; 金币 :%s, 请查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户

	@Override
	public PageUtils findUserPage(UserParam userParam, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		//log.debug("[findUserPage] params {}", userParam);
		if (userParam.getStatus() != null && !"".equals(userParam.getStatus().trim())) {
			String[] statusArr = userParam.getStatus().trim().split(",");
			boolean unforbidden = ArrayUtil.useLoopSearch(statusArr, "1");
			boolean forbidden = ArrayUtil.useLoopSearch(statusArr, "2");
			boolean unfrozen = ArrayUtil.useLoopSearch(statusArr, "3");
			boolean frozen = ArrayUtil.useLoopSearch(statusArr, "4");
			boolean unnobet = ArrayUtil.useLoopSearch(statusArr, "5");
			boolean nobet = ArrayUtil.useLoopSearch(statusArr, "6");
			boolean normalEnable = ArrayUtil.useLoopSearch(statusArr, "7");
			boolean abnormalEnable = ArrayUtil.useLoopSearch(statusArr, "8");
			if (!unforbidden && forbidden) {
				userParam.setForbiddenEnable(true);
			} else if (unforbidden && !forbidden) {
				userParam.setForbiddenEnable(false);
			}
			if (!unfrozen && frozen) {
				userParam.setFrozenEnable(true);
			} else if (unfrozen && !frozen) {
				userParam.setFrozenEnable(false);
			}
			if (!unnobet && nobet) {
				userParam.setNobetEnable(true);
			} else if (unnobet && !nobet) {
				userParam.setNobetEnable(false);
			}
			if (!normalEnable && abnormalEnable) {
				userParam.setAbnormalEnable(true);
			} else if (normalEnable && !abnormalEnable) {
				userParam.setAbnormalEnable(false);
			}
		}
		if (("recharge_date").equals(userParam.getSortOption())) {// 如果是按照最近充值排序
			page.setRecords(this.baseMapper.findUserPageForRecharge(userParam, page));
		} else if (("takemoney_date").equals(userParam.getSortOption())) {
			page.setRecords(this.baseMapper.findUserPageForTakeMoney(userParam, page));
		} else if (("recharge_num").equals(userParam.getSortOption())) {
			page.setRecords(this.baseMapper.findUserPageForRechargeNumOrder(userParam, page));
		} else if (("takemoney_num").equals(userParam.getSortOption())) {
			page.setRecords(this.baseMapper.findUserPageForTakeMoneyOrderNum(userParam, page));
		} else {
			page.setRecords(this.baseMapper.findUserPage(userParam, page));
		}

		List<Map<String, Object>> userList = page.getRecords();
		getUserOtherData(userList, userParam.getTrial() != null && userParam.getTrial() == 1);
		// log.debug("[findUserPage] userList {}", userList);
		return new PageUtils(page);
	}

	public PageUtils findRiskUserPage(UserParam userParam, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		page.setRecords(this.baseMapper.findRiskUserPage(userParam, page));
		return new PageUtils(page);
	}

	@Override
	public List<Map<String, Object>> findUserList(UserParam user) {
		List<Map<String, Object>> userList = this.baseMapper.findUserList(user);
		getUserOtherData(userList, user.getTrial() != null && user.getTrial() == 1);
		return userList;
	}

	private void getUserOtherData(List<Map<String, Object>> userList, boolean trial) {
		Map<String, Object> superUserMap = new HashMap<>();
		// 非试玩账号
		if (!trial) {
			Set<Long> superiorsIdList = new HashSet<>();
			for (Map<String, Object> user : userList) {
				if (user.get("superiorsId") != null) {
					superiorsIdList.add(Long.parseLong(user.get("superiorsId").toString()));
				}
				Integer gameInfoId = Integer.valueOf(user.get("gameInfoId").toString());
				if (0 != gameInfoId) {
					user.put("roomId", ByteUtil.toInt(ByteUtil.highEight(gameInfoId)));
					user.put("gameId", ByteUtil.toInt(ByteUtil.lowFourteen(gameInfoId)));
					user.put("gradeId", ByteUtil.toInt(ByteUtil.middleEight(gameInfoId)));
					user.put("gameName", GameInfoInterface.getGameName(Long.valueOf(user.get("gameId").toString())));
				}
			}
			List<UserEntity> superUser = null;
			if (!superiorsIdList.isEmpty()) {
				superUser = this.baseMapper.findUserListByIdList(superiorsIdList);
			}
			if (superUser != null && !superUser.isEmpty()) {
				for (UserEntity user : superUser) {
					superUserMap.put(user.getId() + "", user.getAccount());
				}
			}
		}
		for (Map<String, Object> user : userList) {
			if (user.get("coin") != null) {
				user.put("coin", Long.valueOf(user.get("coin").toString()) / 100);
			}
			if (MathUtil.getBigDecimal(user.get("gameInfoId")).longValue() > 0) {
				user.put("online", true);
			} else {
				user.put("online", false);
			}
			// 非试玩账号
			if (!trial) {
				// 直接上级
				Long userId = Long.parseLong(user.get("id").toString());
				if (user.get("superiorsId") != null) {
					user.put("superiorsAccount", superUserMap.get(user.get("superiorsId").toString()));
				}
				// 取款
				OrderTakeMoneyStatistics orderTakeMoneyStatistics = orderTakeMoneyDao.orderTakeStatistics(userId);
				if (orderTakeMoneyStatistics != null) {
					user.put("takeAmount", orderTakeMoneyStatistics.getTakeAmount());
					user.put("takeNum", orderTakeMoneyStatistics.getTakeNum());
				} else {
					user.put("takeAmount", 0);
					user.put("takeNum", 0);
				}
				// 最后一次取款
				OrderTakeMoneyEntity lastTakeMoney = orderTakeMoneyDao.getLastTakeMoney(userId);
				if (lastTakeMoney != null) {
					user.put("lastTakeAmount", MathUtil.getBigDecimal(lastTakeMoney.getTakeAmount()));
					user.put("lastTakeAmountDate", lastTakeMoney.getCreateTime());
				} else {
					user.put("lastTakeAmount", 0);
					user.put("lastTakeAmountDate", "");
				}
				// 存款
				OrderRechargeStatistics orderRechargeStatistics = orderRechargeDao.statisticsRechargeByUserId(userId);
				if (orderRechargeStatistics != null) {
					user.put("rechargeAmount", orderRechargeStatistics.getAmount());
					user.put("discountAmount", orderRechargeStatistics.getDiscountAmount());
					user.put("rechargeNum", orderRechargeStatistics.getRechargeNum());
				} else {
					user.put("rechargeAmount", 0);
					user.put("discountAmount", 0);
					user.put("rechargeNum", 0);
				}
				// 最后一次存款
				OrderRechargeEntity lastRecharge = orderRechargeDao.getLastRechargeByUserId(userId);
				if (lastRecharge != null) {
					user.put("lastRechargeAmount", MathUtil.getBigDecimal(lastRecharge.getAmount()));
					user.put("lastRechargeAmountDate", lastRecharge.getCreateTime());
				} else {
					user.put("lastRechargeAmount", 0);
					user.put("lastRechargeAmountDate", "");
				}

				// 打码量
				OrderCashExamineEntity orderCashExamine = orderCashExamineService.findRecentOrderCashExamine(userId);
				if (orderCashExamine != null) {
					user.put("userNeedBet", orderCashExamine.getUserNeedBet());
					user.put("userValidBet", orderCashExamine.getUserValidBet());
					user.put("isSatisfy",
							orderCashExamine.getUserValidBet().compareTo(orderCashExamine.getUserNeedBet()) > 0);
				} else {
					user.put("userNeedBet", 0);
					user.put("userValidBet", 0);
					user.put("isSatisfy", true);
				}
			}
		}
	}

	/**
	 * web端注册
	 */
	@Override
	public UserEntity register(RegisterEntity registerEntity) {
		//log.debug("[register] registerEntity {}", registerEntity);
		// 验证账号及密码的合法性
		if (!registerEntity.getLoginPassWord().equals(registerEntity.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerEntity.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerEntity.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerEntity.getTakeMoneyPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getCode());
		}
		// 验证账号是否重复
		if (validateAccountRepeat(registerEntity.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(registerEntity.getUserName());
		if (!CollectionUtils.isEmpty(baseMapper.selectList(new EntityWrapper<UserEntity>(userEntity)))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getCode());
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// // 一个ip限制注册5个账号
		// List<UserEntity> userList = baseMapper.selectList(
		// new EntityWrapper<UserEntity>(new
		// UserEntity().setRegisterIp(registerEntity.getRegisterIp())));
		// if (!CollectionUtils.isEmpty(userList) && userList.size() >
		// Constant.REGISTER_IP_LIMIT) {
		// throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
		// ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
		// } // 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(registerEntity.getAccount());// 账号
		user.setUserName(registerEntity.getUserName());// 真实姓名
		user.setPortrait(registerEntity.getPortrait());// 用户头像
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerEntity.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerEntity.getRegisterDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
		user.setSex(MathUtil.getRandomSex());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		int ran =(int)(Math.random()*8998)+1000+1;
		user.setNickName(Base64Util.base64Encoder("玩家"+ran));
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		userInfo.setUserIdentity(registerEntity.getUserIdentity());// 身份证号码
		userInfo.setUserPhone(registerEntity.getUserPhone());// 电话
		userInfo.setUserEmail(registerEntity.getUserEmail());// 电子邮箱
		userInfo.setPostCode(registerEntity.getPostCode());// 邮编
		userInfo.setUserAddress(registerEntity.getUserAddress());// 地址
		userInfo.setUserQq(registerEntity.getUserQq());// QQ
		userInfo.setUserBirthday(registerEntity.getUserBirthday());// 生日
		userInfo.setBankName(registerEntity.getBankName());// 银行名称
		userInfo.setBankAddress(registerEntity.getBankAddress());// 银行开户地址
		userInfo.setBankCard(registerEntity.getBankCard());// 银行卡号
		userInfo.setBankAccountName(registerEntity.getBankAccountName());// 银行卡开户名
		userInfoService.insert(userInfo);// 新增用户信息

		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerEntity.getLoginPassWord());// 登陆密码
		String takePassWord = MD5Util.generate(registerEntity.getTakeMoneyPassWord());// 取款密码
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		// 获取系统默认代理等级
		UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService.getDefaultHierarchy();
		if (null == userAgentHierarchy) {
			throw new RRException(ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getErrMsg(),
					ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getCode());
		}
		String token = JwtUtil.createJWT(userId.toString());
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		user.setToken(token);
		//log.debug("[registerLogin] token {} user {}", token, user);
		return user;
	}

	/**
	 * web端手机注册
	 */
	@Override
	public UserEntity webPhoneRegister(PhoneRegisterEntity registerEntity) {
		//log.debug("[webPhoneRegister] PhoneRegisterEntity {}", registerEntity);
		// 验证账号及密码的合法性
		if (!registerEntity.getLoginPassWord().equals(registerEntity.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!VerificationUitl.phoneVerification(registerEntity.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerEntity.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerEntity.getTakeMoneyPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.TAKE_PASSWORD_ERRO.getCode());
		}
		// 验证码校验
		if (!registerEntity.getCode().equals(localContentCache.getPhoneVerificationCode(registerEntity.getAccount()))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
		}
		// 验证账号是否重复
		if (validatePhoneRepeat(registerEntity.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getCode());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(registerEntity.getUserName());
		if (!CollectionUtils.isEmpty(baseMapper.selectList(new EntityWrapper<UserEntity>(userEntity)))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getCode());
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// 一个ip限制注册5个账号
//		List<UserEntity> userList = baseMapper.selectList(
//				new EntityWrapper<UserEntity>(new UserEntity().setRegisterIp(registerEntity.getRegisterIp())));
//		if (!CollectionUtils.isEmpty(userList) && userList.size() > Constant.REGISTER_IP_LIMIT) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
//		}
		// 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(registerEntity.getAccount());// 账号
		user.setUserName(registerEntity.getUserName());// 真实姓名
		user.setPortrait(registerEntity.getPortrait());// 用户头像
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerEntity.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerEntity.getRegisterDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
		user.setSex(MathUtil.getRandomSex());
		user.setPhone(registerEntity.getAccount());
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		userInfo.setUserIdentity(registerEntity.getUserIdentity());// 身份证号码
		userInfo.setUserPhone(registerEntity.getUserPhone());// 电话
		userInfo.setUserEmail(registerEntity.getUserEmail());// 电子邮箱
		userInfo.setPostCode(registerEntity.getPostCode());// 邮编
		userInfo.setUserAddress(registerEntity.getUserAddress());// 地址
		userInfo.setUserQq(registerEntity.getUserQq());// QQ
		userInfo.setUserBirthday(registerEntity.getUserBirthday());// 生日
		userInfo.setBankName(registerEntity.getBankName());// 银行名称
		userInfo.setBankAddress(registerEntity.getBankAddress());// 银行开户地址
		userInfo.setBankCard(registerEntity.getBankCard());// 银行卡号
		userInfo.setBankAccountName(registerEntity.getBankAccountName());// 银行卡开户名
		userInfoService.insert(userInfo);// 新增用户信息

		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerEntity.getLoginPassWord());// 登陆密码
		String takePassWord = MD5Util.generate(registerEntity.getTakeMoneyPassWord());// 取款密码
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		// 获取系统默认代理等级
		UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService.getDefaultHierarchy();
		if (null == userAgentHierarchy) {
			throw new RRException(ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getErrMsg(),
					ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getCode());
		}
		String token = JwtUtil.createJWT(userId.toString());
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		user.setToken(token);
		//log.debug("[registerLogin] token {} user {}", token, user);
		return user;
	}

	/**
	 * 33web端手机注册
	 */
	@Override
	public UserEntity webimPhoneRegister(WebimPhoneRegisterEntity registerEntity) {
		//log.debug("[webPhoneRegister] PhoneRegisterEntity {}", registerEntity);
		// 验证登入密码和确认密码是否相同
		if (!registerEntity.getLoginPassWord().equals(registerEntity.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		 //手机号验证码是否需要校验
        SysRegisterNecessaryEntity sysRegisterNecessaryEntity = sysRegisterNecessaryService.selectById(SysConstant.REGISTER_PHONE);
        if (sysRegisterNecessaryEntity != null && sysRegisterNecessaryEntity.getNecessary()) {
            if (!VerificationUitl.phoneVerification(registerEntity.getPhone())) {
                throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
            }
            // 验证码校验
            if (!registerEntity.getCode().equals(localContentCache.getPhoneVerificationCode(registerEntity.getPhone()))) {
                throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
            }
        }
        if("".equals(registerEntity.getPhone())) {
        	registerEntity.setPhone(null);
        }
		// 验证账号是否重复,手机号是否已经绑定
		validateAccountAndPhoneRepeat(registerEntity.getAccount(), registerEntity.getPhone());
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// 一个ip限制注册5个账号
//		List<UserEntity> userList = baseMapper.selectList(
//				new EntityWrapper<UserEntity>(new UserEntity().setRegisterIp(registerEntity.getRegisterIp())));
//		if (!CollectionUtils.isEmpty(userList) && userList.size() > Constant.REGISTER_IP_LIMIT) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
//		}
		// 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(registerEntity.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerEntity.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerEntity.getRegisterDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setPortrait(MathUtil.getRandomPortrait(MathUtil.getRandomSex()));// 用户头像
		user.setSex(MathUtil.getRandomSex());
		user.setPhone(registerEntity.getPhone());
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		userInfoService.insert(userInfo);// 新增用户信息

		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerEntity.getLoginPassWord());// 登陆密码
		userPasswordService.insertPassword(userId, passWord, null);
		// 获取系统默认代理等级
		UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService.getDefaultHierarchy();
		if (null == userAgentHierarchy) {
			throw new RRException(ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getErrMsg(),
					ErrorCode.HierarchyErrorCode.AGENTHIERARCHY_ISNULL_ERRO.getCode());
		}
		String token = JwtUtil.createJWT(userId.toString());
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		user.setToken(token);
		//log.debug("[registerLogin] token {} user {}", token, user);
		return user;
	}

	/**
	 * app端注册
	 */
	@Override
	public UserDetail appRegister(RegisterParams registerParams) {
		//log.debug("[register] registerParams {}", registerParams);
		// 验证账号及密码的合法性
		if (!registerParams.getLoginPassWord().equals(registerParams.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerParams.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		if (!AccountVerification.regularVerification(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getCode());
		}
		// 验证账号是否重复
		if (validatePhoneRepeat(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
		}
		  //手机号验证码是否需要校验
        SysRegisterNecessaryEntity sysRegisterNecessaryEntity = sysRegisterNecessaryService.selectById(SysConstant.REGISTER_PHONE);
        if (sysRegisterNecessaryEntity != null && sysRegisterNecessaryEntity.getNecessary()) {
            if (!VerificationUitl.phoneVerification(registerParams.getUserPhone())) {
                throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
            }
            // 验证码校验
            if (!registerParams.getCode().equals(localContentCache.getPhoneVerificationCode(registerParams.getUserPhone()))) {
                throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
            }
        }
        if(registerParams.getUserPhone()!=null&&!"".equals(registerParams.getUserPhone())){
			Map<String, Object> map = new HashMap<>();
			map.put("phone",registerParams.getUserPhone());
			List<UserEntity> userEntities = baseMapper.selectByMap(map);
			if(userEntities.size()>0){
				throw new RRException(ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getCode());
			}
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// // 一个设备码只能注册2个账户
		// if (!StringUtils.isEmpty(registerParams.getRegisterDeviceCode())) {
		// int userCount = baseMapper.selectCount(new EntityWrapper<UserEntity>(
		// new
		// UserEntity().setRegisterDeviceCode(registerParams.getRegisterDeviceCode())));
		// if (userCount > Constant.USER_DEVICE_CODE_LIMIT) {
		// throw new
		// RRException(ErrorCode.UserErrorCode.USER_DEVICE_CODE_LIMIT_ERRO.getErrMsg(),
		// ErrorCode.UserErrorCode.USER_DEVICE_CODE_LIMIT_ERRO.getCode());
		// }
		// }
		 // 一个ip限制注册5个账号
//		 List<UserEntity> userList = baseMapper.selectList(
//				 new EntityWrapper<UserEntity>(new
//						 UserEntity().setRegisterIp(registerParams.getRegisterIp())));
//		 if (!CollectionUtils.isEmpty(userList) && userList.size() >
//		 	Constant.REGISTER_IP_LIMIT) {
//			 throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
//					 	ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
//		 } // 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		int ran =(int)(Math.random()*8998)+1000+1;
		user.setNickName(Base64Util.base64Encoder("玩家"+ran));
		user.setUnionType(registerParams.getUnionType());
		user.setUnionId(registerParams.getUnionId());
		user.setAccount(registerParams.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerParams.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerParams.getCommonParam().getDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setPhone(registerParams.getUserPhone());
		user.setCoin(0L);
		user.setRoomCard(0L);
		user.setChannelCode(registerParams.getCommonParam().getChannelCode());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(registerParams.getSex()!=null?registerParams.getSex()==1:MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setUserName(registerParams.getUserName() == null ? "" : registerParams.getUserName());
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		/**
		 * 获取用户未读信息
		 */
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		requestVo.setRegisterDate(user.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		user.setUnreadNum(unreadNum);
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		if (!StringUtils.isEmpty(registerParams.getCommonParam().getJpushRegId())) {
			userInfo.setJpushRegId(registerParams.getCommonParam().getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerParams.getLoginPassWord());// 登陆密码
		String takePassWord = "";// 取款密码
		if (registerParams.getTakeMoneyPassWord() != null && !"".equals(registerParams.getTakeMoneyPassWord())) {
			takePassWord = MD5Util.generate(registerParams.getTakeMoneyPassWord());
		}
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		//log.debug("[login] 用户是否登陆过 {}", StringUtils.isEmpty(user.getToken()));
		if (!StringUtils.isEmpty(user.getToken())) {
			// token不为空，根据token获取人员信息
			UserEntity cacheUser = (UserEntity) localContentCache.getToken(user.getToken());
			//log.debug("[login] 用户是否是登陆状态 {}", cacheUser != null);
			if (cacheUser != null) {
				// 如果不为空表示已登陆,将账号强制退出
				localContentCache.remove(user.getToken());
			}
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);

		String ipData[] = IPQueryUtil.queryIp(registerParams.getRegisterIp());
		String address =null;
		try {
			if (ipData != null) {

				address=ipData[1] + ipData[2];
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		setHallIp(result, 1L);
		result.setAddress(address);
		//log.debug("[login] result {}", result);
		return result;
	}



	/**
	 * 游客升级
	 */
	@Override
	public UserDetail upgrade(UpgradeParams upgradeParams) {
		//log.debug("[register] registerParams {}", registerParams);
		// 验证账号及密码的合法性
		long stime=System.currentTimeMillis();
		if (!upgradeParams.getLoginPassWord().equals(upgradeParams.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(upgradeParams.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		if (!AccountVerification.regularVerification(upgradeParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getCode());
		}
		 Long userId = Long.valueOf(JwtUtil.getUserId(upgradeParams.getToken()));
		 UserEntity user = this.baseMapper.selectById(userId);
		 log.debug("查询user time1:{}",System.currentTimeMillis()-stime);
		 stime=System.currentTimeMillis();
        // 判断用户是否获取失败
        if (user == null) {
            throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
        }
		// 验证账号是否重复
		if (validatePhoneRepeat(upgradeParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
		}
		log.debug("验证账号是否重复 time2:{}",System.currentTimeMillis()-stime);
		 stime=System.currentTimeMillis();

		String account = user.getAccount();
		int ran =(int)(Math.random()*8998)+1000+1;
		String nickName = "玩家"+ran;
		user.setNickName(Base64Util.base64Encoder(nickName));
		user.setAccount(upgradeParams.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
//		user.setTouristId("0"); 2019-12-27 升级后不清除，再次游客登录后提示使用账号密码登录
		user.setUpdateTime(new Date());
		user.setSex(upgradeParams.getSex()!=null?upgradeParams.getSex()==1:MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
        user.setRemark("（"+account+"）升级："+DateUtils.format(new Date(), "yyyy-MM-dd"));
		//log.debug("[register] insertGetId {}", user);
		baseMapper.updateById(user);
		log.debug("修改账号 time3:{}",System.currentTimeMillis()-stime);
		 stime=System.currentTimeMillis();
//		// 新增用户信息
//		UserInfoEntity userInfo = new UserInfoEntity();
		userPasswordService.updateLoginPassWord2(upgradeParams.getToken(),upgradeParams.getVerificationWord(),upgradeParams.getLoginPassWord());
		log.debug("更新密码 time4:{}",System.currentTimeMillis()-stime);
		 stime=System.currentTimeMillis();
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		//log.debug("[login] 用户是否登陆过 {}", StringUtils.isEmpty(user.getToken()));
//		if (!StringUtils.isEmpty(user.getToken())) {
//			// token不为空，根据token获取人员信息
//			UserEntity cacheUser = (UserEntity) localContentCache.getToken(user.getToken());
//			//log.debug("[login] 用户是否是登陆状态 {}", cacheUser != null);
//			if (cacheUser != null) {
//				// 如果不为空表示已登陆,将账号强制退出
//				localContentCache.remove(user.getToken());
//			}
//		}
		// 生成一个新的token，并加入缓存中
//		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
//		user.setToken(token);
		//log.debug("[login] token {} ", token);
//		localContentCache.putToken(token, user);
//		localContentCache.put(String.valueOf(user.getId()), token);
//		String officialUrl = domainManagementService.getOfficalDomain();
//		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
//		
		//log.debug("[login] result {}", result);
		UserDetail result = new UserDetail();
		result.setId(user.getId());
		result.setAccount(user.getAccount());
		result.setToken(upgradeParams.getToken());
		result.setNickName(nickName);
		setHallIp(result, upgradeParams.getHallId());
		//批量更新用户账号信息
//         this.batchUpgradeUserAccount(user.getId(),user.getAccount());
//         log.info("批量更新用户账号 time5:{}",System.currentTimeMillis()-stime);
		return result;
	}


	/**
	 * 游客注册
	 */
	@Override
	public UserDetail touristRegister(TLoginParams loginParams) {

		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID

		UserEntity user = new UserEntity();// 用户

        int ran =(int)(Math.random()*8998)+1000+1;
		user.setAccount("游客"+ran);// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.TOURIST);// 用户类型(默认为普通用户)
		user.setRegisterIp(loginParams.getLoginIp());// 注册IP
		user.setRegisterDeviceCode(loginParams.getCommonParam().getDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setChannelCode(loginParams.getCommonParam().getChannelCode());
		user.setCoin(0L);
		user.setRoomCard(0L);

		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
        user.setTouristId(loginParams.getTouristId());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(true));// 用户头像

		user.setNickName(Base64Util.base64Encoder("游客"+ran));
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		/**
		 * 获取用户未读信息
		 */
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		requestVo.setRegisterDate(user.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		user.setUnreadNum(unreadNum);
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		if (!StringUtils.isEmpty(loginParams.getCommonParam().getJpushRegId())) {
			userInfo.setJpushRegId(loginParams.getCommonParam().getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		userPasswordService.insertPassword(userId, "", "");
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		//log.debug("[login] 用户是否登陆过 {}", StringUtils.isEmpty(user.getToken()));
		if (!StringUtils.isEmpty(user.getToken())) {
			// token不为空，根据token获取人员信息
			UserEntity cacheUser = (UserEntity) localContentCache.getToken(user.getToken());
			//log.debug("[login] 用户是否是登陆状态 {}", cacheUser != null);
			if (cacheUser != null) {
				// 如果不为空表示已登陆,将账号强制退出
				localContentCache.remove(user.getToken());
			}
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		String ipData[] = IPQueryUtil.queryIp(loginParams.getLoginIp());
		String address =null;
		try {
			if (ipData != null) {

				address=ipData[1] + ipData[2];
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		result.setAddress(address);
		setHallIp(result, 1L);
		//log.debug("[login] result {}", result);
		return result;
	}

	/**
	 * app端手机注册
	 */
	@Override
	public UserDetail appPhoneRegister(PhoneRegisterParams registerParams) {
		//log.debug("[register] registerParams {}", registerParams);
		// 验证账号及密码的合法性
		if (!registerParams.getLoginPassWord().equals(registerParams.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!VerificationUitl.phoneVerification(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerParams.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		// 验证账号是否重复
		if (validatePhoneRepeat(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getCode());
		}
		// 验证码校验
		if (!registerParams.getCode().equals(localContentCache.getPhoneVerificationCode(registerParams.getAccount()))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(registerParams.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerParams.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerParams.getCommonParam().getDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setRoomCard(0L);
		user.setChannelCode(registerParams.getCommonParam().getChannelCode());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		user.setPhone(registerParams.getAccount());
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		/**
		 * 获取用户未读信息
		 */
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		requestVo.setRegisterDate(user.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		user.setUnreadNum(unreadNum);
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		if (!StringUtils.isEmpty(registerParams.getCommonParam().getJpushRegId())) {
			userInfo.setJpushRegId(registerParams.getCommonParam().getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerParams.getLoginPassWord());// 登陆密码
		String takePassWord = "";// 取款密码
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, 1L);
		log.debug("[login] result {}", result);
		return result;
	}

	/**
     * app端手机注册
     */
    @Override
    public UserDetail appPhoneRegister(PhoneRegisterParamsExt registerParams) {
        log.debug("[appPhoneRegisterExt] registerParams {}", registerParams);
        //手机号验证码是否需要校验
        SysRegisterNecessaryEntity sysRegisterNecessaryEntity = sysRegisterNecessaryService.selectById(SysConstant.REGISTER_PHONE);
        if (sysRegisterNecessaryEntity.getNecessary()) {
            if (!VerificationUitl.phoneVerification(registerParams.getPhone())) {
                throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
            }
            // 验证码校验
            if (!registerParams.getCode().equals(localContentCache.getPhoneVerificationCode(registerParams.getPhone()))) {
                throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
                        ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
            }
        }
        // 验证账号及密码的合法性
        if (!registerParams.getLoginPassWord().equals(registerParams.getVerificationWord())) {
            throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
                    ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
        }
        // 验证账号是否重复
        if (validatePhoneRepeat(registerParams.getAccount())) {
            throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
                    ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
        }
        // 获取默认层级ID
        UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
        Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
        // 新增用户并返回用户ID
        UserEntity user = new UserEntity();// 用户
        user.setAccount(registerParams.getAccount());// 账号
        user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
        user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
        user.setRegisterIp(registerParams.getRegisterIp());// 注册IP
        user.setRegisterDeviceCode(registerParams.getRegisterDeviceCode());// 注册设备码
        user.setHierarchyId(hierarchyId);// 用户所属层级
        user.setCoin(0L);
        user.setRoomCard(0L);
        user.setChannelCode(registerParams.getCommonParam().getChannelCode());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setSex(MathUtil.getRandomSex());
        user.setPhone(registerParams.getPhone());
        user.setCommission(BigDecimal.ZERO);
        user.setMoney(BigDecimal.ZERO);
        user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
        user.setForbiddenEnable(false);
        user.setFrozenEnable(false);
        user.setPhone(registerParams.getAccount());
        //log.debug("[register] insertGetId {}", user);
        baseMapper.insertGetId(user);
        Long userId = user.getId();// 新增的用户ID
        /**
         * 获取用户未读信息
         */
        MessageRequestVo requestVo = new MessageRequestVo();
        requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
        String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
        requestVo.setHierarchyIds(hierachyid.split(","));
        requestVo.setUserId(user.getId());
        requestVo.setUserAccount(user.getAccount());
        requestVo.setRegisterDate(user.getCreateTime());
        int unreadNum = messageManagementService.countUnreadNumber(requestVo);
        user.setUnreadNum(unreadNum);
        // 新增用户信息
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setUserId(userId);// 用户ID
        if (!StringUtils.isEmpty(registerParams.getCommonParam().getJpushRegId())) {
            userInfo.setJpushRegId(registerParams.getCommonParam().getJpushRegId());
        }
        userInfoService.insert(userInfo);// 新增用户信息
        // 对密码进行加密并新增用户密码
        String passWord = MD5Util.generate(registerParams.getLoginPassWord());// 登陆密码
        String takePassWord = "";// 取款密码
        userPasswordService.insertPassword(userId, passWord, takePassWord);
        // 生成一个新的token，并加入缓存中
        String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
        user.setToken(token);
        //log.debug("[login] token {} ", token);
        localContentCache.putToken(token, user);
        localContentCache.put(String.valueOf(user.getId()), token);
        String officialUrl = domainManagementService.getOfficalDomain();
        UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
        setHallIp(result, 1L);
        //log.debug("[login] result {}", result);
        return result;
    }

	public UserDetail setHallIp(UserDetail result, Long hallId) {
		boolean isOk = false;
		//获取当前时间 在 活动时间内   （只有一个活动，但有多个事件）
		Wrapper<FortuneActiviConfigEntity> entityWrapper = new EntityWrapper<FortuneActiviConfigEntity>();
		entityWrapper.le("start_time", new Date());
		entityWrapper.ge("end_time", new Date());
		FortuneActiviConfigEntity fortuneActiviConfig = fortuneActiviConfigService.selectOne(entityWrapper);
		// 查询活动期间未开启红包个数
		Integer unEnvelopeNum = 0;
		if (fortuneActiviConfig != null) {
			Wrapper<EnvelopeRecordEntity> wrapper = new EntityWrapper<EnvelopeRecordEntity>(new EnvelopeRecordEntity()
					.setUserId(result.getId()).setActivityId(fortuneActiviConfig.getId()).setStatus(0));
			wrapper.ge("create_time", fortuneActiviConfig.getStartTime());
			wrapper.le("create_time", fortuneActiviConfig.getEndTime());
			unEnvelopeNum = envelopeRecordDao.selectCount(wrapper);
		}
		result.setUnEnvelopeNum(unEnvelopeNum);
		try {
			if ("localhost".equals(HallUrlConstant.getHALL_URL())) {
				result.setHallIp("192.168.0.182");
				result.setHallPort(8500);
				return result;
			}
			String url = HallUrlConstant.getHALL_URL() + hallId;
			String body = HttpRequest.get(url).timeout(30000).execute().body();
			JSONObject robj = JSON.parseObject(body);
			if (robj.getInteger("Result") == 0) {
				String hallIp = robj.getString("HallIp");
				String hallIpArr[] = hallIp.split(":");
				if (hallIpArr.length == 1) {
					result.setHallIp(hallIpArr[0]);
					result.setHallPort(null);
					isOk = true;
				} else if (hallIpArr.length == 2) {
					result.setHallIp(hallIpArr[0]);
					result.setHallPort(Integer.parseInt(hallIpArr[1]));
					isOk = true;
				}
			}
		} catch (Exception e) {
			log.error("获取大厅ip失败 error {}", e);
//			log.debug("大厅ip 缓存的map {}", SysConstant.hallAddressMap);
//			String hallIp = SysConstant.hallAddressMap.get(hallId.intValue());
			String hallIp = localContentCache.getHallAddress(hallId);
			if (hallIp != null) {
				String hallIpArr[] = hallIp.split(":");
				if (hallIpArr.length == 1) {
					result.setHallIp(hallIpArr[0]);
					result.setHallPort(null);
					isOk = true;
				} else if (hallIpArr.length == 2) {
					result.setHallIp(hallIpArr[0]);
					result.setHallPort(Integer.parseInt(hallIpArr[1]));
					isOk = true;
				}
			}
		}
		if (!isOk) {
			// throw new
			// RRException(ErrorCode.UserErrorCode.USER_LOGIN_GET_HALL_IP_ERRO.getErrMsg(),
			// ErrorCode.UserErrorCode.USER_LOGIN_GET_HALL_IP_ERRO.getCode());
		}
		return result;

	}

	/**
	 * 第三方注册
	 *
	 */
	@Override
	public UserDetail thirdPartyRegister(UserEntity user, JSONObject userResult, OAuth2Params oAuth2Params) {
		// 自动生成密码和账号
		String newAccount = autogenerationAccount(user.getUnionType());
		user.setAccount(newAccount);
		user.setPortrait(((Math.random() * 9 + 1) * 100000) + "");// 随机数（头像）
		// 默认属性
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		user.setHierarchyId(hierarchyId);// 将用户加入默认层级
		// 新增用户信息,并获取到用户ID
		user.setCreateTime(new Date());
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理

		user.setUserType(SysConstant.TOURIST);// 用户类型(默认为普通用户)
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setRoomCard(0L);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		//log.debug("[thirdPartyRegister] user {}", user);
		insertGetId(user);// 新增用户
		Long userId = user.getId();// 新增的用户ID
		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(InviteCode.create());// 随机生成登陆密码
		String takePassWord = "";// 取款密码
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		if (OAuth2Utils.WECHAT.equals(oAuth2Params.getType())) {
			// userWechatInfoService
			// .insert(new
			// UserExtendInfoEntity().setUserId(userId).setProvince(userResult.getString("province"))
			// .setCity(userResult.getString("city")).setCountry(userResult.getString("country")));
		}
		/**
		 * 获取用户未读信息
		 */
//		MessageRequestVo requestVo = new MessageRequestVo();
//		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
//		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
//		requestVo.setHierarchyIds(hierachyid.split(","));
//		requestVo.setUserId(user.getId());
//		requestVo.setUserAccount(user.getAccount());
//		requestVo.setRegisterDate(user.getCreateTime());
//		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
//		user.setUnreadNum(unreadNum);
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		if (!StringUtils.isEmpty(oAuth2Params.getCommonParam().getJpushRegId())) {
			userInfo.setJpushRegId(oAuth2Params.getCommonParam().getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, 1L);
		//log.debug("[login] result {}", result);
		return result;
	}

	/**
	 * 绑定推荐人
	 * 
	 * @param token
	 * @param recommenderParam
	 */
	@Override
	@Transactional
	public void bindRecommender(String token, RecommenderParam recommenderParam) {
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.baseMapper.selectById(userId);
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (user.getSuperiorsId() != null && user.getSuperiorsId() > 0) {
			throw new RRException("已经绑定推荐人",
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getCode());
		}
		// 验证填写的邀请码是否存在,存在就获取上级ID及账号
		Long superiorsId = null;// 上级ID
		if (!StringUtils.isEmpty(recommenderParam.getInvitationCode())) {
			List<UserRecommendationEntity> list = validateRecommender(recommenderParam.getInvitationCode());
			if (!CollectionUtils.isEmpty(list)) {
				UserEntity superiorsEntity = baseMapper.selectById(list.get(0).getUserId());
				//log.debug("[register] 上级代理状态是否正常 {}", superiorsEntity.getAgentEnable());
				if (superiorsEntity.getAgentEnable() == SysConstant.ENABLE_TRUE) {// 如果上级是正常代理，那么就有上级ID
					superiorsId = list.get(0).getUserId();
				} else {
					throw new RRException("该推荐人的代理状态有误",
							ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getCode());
				}
			} else {
				throw new RRException(
						ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getErrMsg(),
						ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getCode());
			}
		} else {
			throw new RRException(ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getErrMsg(),
					ErrorCode.InvitationCodeErrorCode.RECOMMENDATION_CODE_VERIFICATION_ERRO.getCode());
		}
		user.setSuperiorsId(superiorsId);
		this.updateById(user);
		userRecommendationService.updateSuperiors(superiorsId);

	}

	@Override
	@Transactional
	public void enterGame(EnterGameParam enterGameParam) {
		UserEntity user = this.baseMapper.selectById(enterGameParam.getUserId());
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 校验用户是否被禁用
		UserStatusVerificationUtil.userForbiddenValidate(user.getForbiddenEnable());
		// 校验用户是否被限制停压
		UserStatusVerificationUtil.userNobetValidate(user.getNobetEnable());
		user.setGameInfoId(enterGameParam.getGameInfoId());
		user.setGameServerId(enterGameParam.getGameServerId());
		this.updateById(user);

	}

	@Override
	@Transactional
	public void updateUserCoin(UpdateCoinParam updateCoinParam) {
		UserEntity user = this.baseMapper.selectById(updateCoinParam.getUserId());
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (user.getCoin() + updateCoinParam.getCoin() < 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		UserEntity updateUser = new UserEntity();
		updateUser.setCoin(updateCoinParam.getCoin());
		updateUser.setId(updateCoinParam.getUserId());
//		this.baseMapper.updateUserWalletByUserId(updateUser);
		this.updateUserWalletByUserId(updateUser);
		

	}

	@Override
	@Transactional
	public void updateUserRoomCard(UpdateRoomCardParam updateParam) {
		UserEntity user = this.baseMapper.selectById(updateParam.getUserId());
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		if (user.getRoomCard() - updateParam.getRoomCard() < 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ROOMCARD_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ROOMCARD_NOT_ENOUGH.getCode());
		}
		UserEntity updateUser = new UserEntity();
		updateUser.setRoomCard(updateParam.getRoomCard());
		updateUser.setId(updateParam.getUserId());
		
		this.updateUserWalletByUserId(updateUser);
//		if (this.baseMapper.updateUserWalletByUserId(updateUser) > 0) {
			ShopPropRecordEntity shopPropRecordEntity = new ShopPropRecordEntity();
			shopPropRecordEntity.setPropNumber(updateParam.getRoomCard().intValue());
			shopPropRecordEntity.setSysPropId(Constant.ROOMCART_PROP_ID);
			shopPropRecordEntity.setType(Constant.PROP_USE_TYPE_1);
			shopPropRecordEntity.setUserId(user.getId());
			shopPropRecordEntity.setUserAccount(user.getAccount());
			shopPropRecordEntity.insert();
//		} else {
//			throw new RRException(ErrorCode.UserErrorCode.USER_ROOMCARD_NOT_ENOUGH.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_ROOMCARD_NOT_ENOUGH.getCode());
//		}

	}

	/**
	 * 登陆
	 */
	@Override
	public String login(UserEntity user, LoginEntity loginEntity, String ip) {
		//log.debug("[login] user {} ,loginEntity {}, ip {}", user, loginEntity, ip);
		// String account = loginEntity.getAccount();
		String passWord = loginEntity.getLoginPassWord();
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
		// 验证密码
		Boolean result = userPasswordService.validateLoginPassword(passWord, userId);
		if (!result) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
		} else {
			// 清除缓存中用户输错密码次数
			localContentCache.remove(SysConstant.PASSWORD_ERROR + userId);
		}
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		UserLoginEntity entity = new UserLoginEntity();
		entity.setUserId(userId);
		entity.setIp(ip);
		entity.setDeviceCode(loginEntity.getRegisterDeviceCode());
		entity.setLoginStatus(SysConstant.SUCCESS);
		entity.setDeviceType(loginEntity.getDeviceType());
		entity.setHallId(loginEntity.getHallId());
		entity.setEdition(loginEntity.getEdition());
		entity.setToken(token);
		String ipData[] = IPQueryUtil.queryIp(ip);
		try {
			if (ipData != null) {
				entity.setNation(ipData[0]);
				entity.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}

		if (null != resultEntity) {
			entity.setLastDeviceCode(resultEntity.getDeviceCode());
			entity.setLastDeviceType(resultEntity.getDeviceType());
			entity.setLastEdition(resultEntity.getEdition());
			entity.setLastHallId(resultEntity.getHallId());
			entity.setLastIp(resultEntity.getIp());
			entity.setLastIpAddress(resultEntity.getIpAddress());
			entity.setLastNation(resultEntity.getNation());
			entity.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(entity);
		log.debug("[login] entity {}", entity);
		return token;
	}

	/**
	 * app登陆
	 */
	@Override
	public UserDetail appLogin(UserEntity user, LoginParams loginParams, String Ip, boolean VerifyPassword) {
		//log.debug("[login] user {} ,loginParams {},ip {}", user, loginParams, Ip);
		// String account = loginParams.getAccount();
		String passWord = loginParams.getLoginPassWord();
		// 根据账号获取人员信息
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
		int passWordErrorLimit = SysConstant.PASSWORD_ERROR_NUM;
		String passWordErrorLimitStr = sysDictionaryService.getName(SysConstant.LOGON,
				SysConstant.PASSWORD_ERROR_LIMIT);
		if (!StringUtils.isEmpty(passWordErrorLimitStr)) {
			try {
				passWordErrorLimit = Integer.parseInt(passWordErrorLimitStr);
			} catch (Exception e) {
				log.error("passWordErrorLimit:", e);
			}
		}
		if (VerifyPassword) {// 验证密码
			// 验证输入密码错误是否超出默认次数
			int i = 0;
			if (localContentCache.get(SysConstant.PASSWORD_ERROR + userId) != null) {
				i = (int) localContentCache.get(SysConstant.PASSWORD_ERROR + userId);//
				// 输入错误密码次数
				if (i >= passWordErrorLimit) {
					throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_NUM_ERRO.getErrMsg(),
							ErrorCode.PasswordErrorCode.PASSWORD_NUM_ERRO.getCode());
				}
			}
			Boolean result = userPasswordService.validateLoginPassword(passWord, userId);
			if (!result) {
				localContentCache.put(SysConstant.PASSWORD_ERROR + userId, i + 1);
				throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getErrMsg(),
						ErrorCode.PasswordErrorCode.PASSWORD_INVALID_ERRO.getCode());
			} else {
				// 清除缓存中用户输错密码次数
				localContentCache.remove(SysConstant.PASSWORD_ERROR + userId);
			}
		}

		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(loginParams.getCommonParam().getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(loginParams.getCommonParam().getJpushRegId()));
		}
		// 获取上一条登入记录
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		// 新增一条登陆成功记录
		UserLoginEntity userLogin = new UserLoginEntity();
		userLogin.setUserId(userId);
		userLogin.setIp(Ip);
		String ipData[] = IPQueryUtil.queryIp(Ip);
		  String address =null;
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				address=ipData[1] + ipData[2];
			}

		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(1l);
		userLogin.setEdition(loginParams.getCommonParam().getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(loginParams.getCommonParam().getDeviceType());
		if (null != resultEntity) {
			userLogin.setLastDeviceCode(resultEntity.getDeviceCode());
			userLogin.setLastDeviceType(resultEntity.getDeviceType());
			userLogin.setLastEdition(resultEntity.getEdition());
			userLogin.setLastHallId(resultEntity.getHallId());
			userLogin.setLastIp(resultEntity.getIp());
			userLogin.setLastIpAddress(resultEntity.getIpAddress());
			userLogin.setLastNation(resultEntity.getNation());
			userLogin.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(userLogin);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
		result.setAddress(address);
		setHallIp(result,1L);
		log.debug("[login] result {}", result);
		return result;
	}


	/**
	 * app登陆
	 */
	@Override
	public UserDetail touristLogin(UserEntity user,  TLoginParams loginParams,String Ip) {
		//log.debug("[login] user {} ,loginParams {},ip {}", user, loginParams, Ip);
		// String account = loginParams.getAccount();

		// 根据账号获取人员信息
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
//		int passWordErrorLimit = SysConstant.PASSWORD_ERROR_NUM;
//		String passWordErrorLimitStr = sysDictionaryService.getName(SysConstant.LOGON,
//				SysConstant.PASSWORD_ERROR_LIMIT);
//		if (!StringUtils.isEmpty(passWordErrorLimitStr)) {
//			try {
//				passWordErrorLimit = Integer.parseInt(passWordErrorLimitStr);
//			} catch (Exception e) {
//				log.error("passWordErrorLimit:", e);
//			}
//		}

		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(loginParams.getCommonParam().getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(loginParams.getCommonParam().getJpushRegId()));
		}
		// 获取上一条登入记录
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		// 新增一条登陆成功记录
		UserLoginEntity userLogin = new UserLoginEntity();
		userLogin.setUserId(userId);
		userLogin.setIp(Ip);
		String ipData[] = IPQueryUtil.queryIp(Ip);
		String address =null;
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				address=ipData[1] + ipData[2];
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(1L);
		userLogin.setEdition(loginParams.getCommonParam().getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(loginParams.getCommonParam().getDeviceType());
		if (null != resultEntity) {
			userLogin.setLastDeviceCode(resultEntity.getDeviceCode());
			userLogin.setLastDeviceType(resultEntity.getDeviceType());
			userLogin.setLastEdition(resultEntity.getEdition());
			userLogin.setLastHallId(resultEntity.getHallId());
			userLogin.setLastIp(resultEntity.getIp());
			userLogin.setLastIpAddress(resultEntity.getIpAddress());
			userLogin.setLastNation(resultEntity.getNation());
			userLogin.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(userLogin);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
		result.setAddress(address);
		setHallIp(result, 1L);
		log.debug("[login] result {}", result);
		return result;
	}
	/**
	 * app第三方登陆
	 */
	@Override
	public UserDetail appThirdPartyLogin(UserEntity user, OAuth2Params loginParams, String ip) {
		//log.debug("[appThirdPartyLogin] user {} ,loginParams {},ip {}", user, loginParams, ip);
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[appThirdPartyLogin] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(loginParams.getCommonParam().getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(loginParams.getCommonParam().getJpushRegId()));
		}
		// 获取上一条登入记录
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		// 新增一条登陆成功记录
		UserLoginEntity userLogin = new UserLoginEntity();
		userLogin.setUserId(userId);
		userLogin.setIp(ip);
		String ipData[] = IPQueryUtil.queryIp(ip);
		String address =null;
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				address=ipData[1] + ipData[2];
			}
			
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(1L);
		userLogin.setEdition(loginParams.getCommonParam().getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(loginParams.getCommonParam().getDeviceType());
		if (null != resultEntity) {
			userLogin.setLastDeviceCode(resultEntity.getDeviceCode());
			userLogin.setLastDeviceType(resultEntity.getDeviceType());
			userLogin.setLastEdition(resultEntity.getEdition());
			userLogin.setLastHallId(resultEntity.getHallId());
			userLogin.setLastIp(resultEntity.getIp());
			userLogin.setLastIpAddress(resultEntity.getIpAddress());
			userLogin.setLastNation(resultEntity.getNation());
			userLogin.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(userLogin);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
		result.setAddress(address);
		setHallIp(result,1L);
		log.debug("[appThirdPartyLogin] result {}", result);
		return result;
	}

	/**
	 * web第三方登陆
	 */
	@Override
	public String webThirdPartyLogin(UserEntity user, OAuth2Params loginParams, String ip) {
		//log.debug("[appThirdPartyLogin] user {} ,loginParams {},ip {}", user, loginParams, ip);
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[appThirdPartyLogin] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		UserLoginEntity entity = new UserLoginEntity();
		entity.setUserId(userId);
		entity.setIp(ip);
		entity.setDeviceCode(loginParams.getCommonParam().getDeviceCode());
		entity.setLoginStatus(SysConstant.SUCCESS);
		entity.setDeviceType(loginParams.getCommonParam().getDeviceType());
		entity.setHallId(1L);
		entity.setEdition(loginParams.getCommonParam().getEdition());
		entity.setToken(token);
		String ipData[] = IPQueryUtil.queryIp(ip);
		try {
			if (ipData != null) {
				entity.setNation(ipData[0]);
				entity.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}

		if (null != resultEntity) {
			entity.setLastDeviceCode(resultEntity.getDeviceCode());
			entity.setLastDeviceType(resultEntity.getDeviceType());
			entity.setLastEdition(resultEntity.getEdition());
			entity.setLastHallId(resultEntity.getHallId());
			entity.setLastIp(resultEntity.getIp());
			entity.setLastIpAddress(resultEntity.getIpAddress());
			entity.setLastNation(resultEntity.getNation());
			entity.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(entity);
		log.debug("[login] entity {}", entity);
		return token;
	}

	/**
	 * 新增用户并返回ID
	 */
	@Override
	public UserEntity insertGetId(UserEntity userEntity) {
		//log.debug("[insertGetId] userEntity {}", userEntity);
		baseMapper.insertGetId(userEntity);
		return userEntity;
	}

	// 通过推荐码获取人员ID
	public List<UserRecommendationEntity> validateRecommender(String recommender) {
		//log.debug("[validateRecommender] recommender {}", recommender);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(recommender);
		List<UserRecommendationEntity> list = userRecommendationService
				.selectList(new EntityWrapper<UserRecommendationEntity>(entity));
		//log.debug("[validateRecommender] list {}", list);
		return list;
	}

	// 判断推荐码是否存在(大于0存在)
	public int validateRecommenderRepeat(String recommender) {
		//log.debug("[validateRecommenderRepeat] recommender {}", recommender);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(recommender);
		int num = userRecommendationService.selectCount(new EntityWrapper<UserRecommendationEntity>(entity));
		//log.debug("[validateRecommenderRepeat] num {}", num);
		return num;
	}

	/**
	 * 以下为用户验证方法
	 * 
	 * @param account
	 * @return
	 */

	// 验证账号是否重复(true:重复)
	public Boolean validateAccountRepeat(String account) {
		//log.debug("[validateAccountRepeat] account {}", account);
		Boolean result = false;
		UserEntity userEntity = new UserEntity();
		userEntity.setAccount(account);
		int num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity));
		if (num > 0) {
			return result = true;
		}
		//log.debug("[validateAccountRepeat] result {}", result);
		return result;
	}

	/**
	 * 以下为用户验证方法
	 * 
	 * @param account
	 * @return
	 */

	// 验证账号是否重复(true:重复)
	public Boolean validatePhoneRepeat(String account) {
		//log.debug("[validatePhoneRepeat] account {}", account);
		Boolean result = false;
		UserEntity userEntity = new UserEntity();
		userEntity.setAccount(account);
		int num = 0;
		// 判断账号是不是手机号格式
		if (!VerificationUitl.phoneVerification(account)) {
			num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity));
		} else {
			num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity).orNew("phone={0}", account));
		}
		if (num > 0) {
			return result = true;
		}
		//log.debug("[validateAccountRepeat] result {}", result);
		return result;
	}
	
	// 验证账号是否重复(true:重复)
	public Boolean validateUserPhoneRepeat(String phone) {
		//log.debug("[validateUserPhoneRepeat] phone {}", phone);
		Boolean result = false;
		UserEntity userEntity = new UserEntity();
		userEntity.setPhone(phone);
		int num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity));
		if (num > 0) {
			return result = true;
		}
		//log.debug("[validateUserPhoneRepeat] result {}", result);
		return result;
	}

	/**
	 * 退出登陆
	 */
	@Override
	public void logout(String token) {
		log.debug("[logout] token {}", token);
		localContentCache.remove(token);// 清除缓存，退出登陆
	}

	/**
	 * 根据token获取人员基本信息及账户余额
	 */
	@Override
	public UserEntity selectUserForToken(String token) {
		Long id = Long.valueOf(JwtUtil.getUserId(token));
		//log.debug("[selectUserForToken] token {} id {} ", token, id);
		UserEntity entity = baseMapper.selectById(id);
		UserPasswordEntity passwordEntity = userPasswordService
				.selectOne(new EntityWrapper<UserPasswordEntity>(new UserPasswordEntity().setUserId(id)));
		if (null == passwordEntity || StringUtils.isEmpty(passwordEntity.getBankPassword())) {
			entity.setTakemoney(false);
		} else {
			entity.setTakemoney(true);
		}
		//log.debug("[selectUserForToken] entity {}", entity);
		return entity;
	}

	/**
	 * 提供前缀自动生成账号 不重复
	 */
	public String autogenerationAccount(String name) {
		//log.debug("[autogenerationAccount] name {}", name);
		UserEntity userEntity = new UserEntity();
		//// 自动生成账号信息
		String str;
		String s;
		int i;
		String account;
		while (true) {
			str = (char) (Math.random() * 26 + 'a') + "";
			s = (char) (Math.random() * 26 + 'a') + "";
			i = 1 + (int) (Math.random() * 100);
			account = name + str + i + s;
			userEntity.setAccount(account);
			List<UserEntity> list = baseMapper.selectList(new EntityWrapper<UserEntity>(userEntity));
			if (CollectionUtils.isEmpty(list)) {
				break;
			}
		}
		//log.debug("[autogenerationAccount] account {}", account);
		return account;
	}

	@Override
	public Integer deleteBankInfo(Long userId) {
		//log.debug("[deleteBankInfo] userId {}", userId);
		return this.baseMapper.deleteBankInfo(userId);
	}

	@Override
	public void updateUserBaseInfo(UserEntity userEntity) {
		//log.debug("[updateUserBaseInfo] userEntity {}", userEntity);
		this.baseMapper.updateUserBaseInfo(userEntity);
	}

	@Override
	public Page<Agent> getAgentList(UserEntity user, PageParam pageParam) {
		//log.debug("[getAgentList] user {} ,pageParam {}", user, pageParam);
		Page<Agent> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getAgentList(page, user));
	}

	/**
	 * 根据用户Id修改用户金币，余额，佣金
	 */
	@Override
	public void updateUserWalletByUserId(UserEntity user) {
		//log.debug("[updateUserWalletByUserId] user {} ", user);
		int i=0;
		try {
			i=baseMapper.updateUserWalletByUserId(user);
		} catch (Exception e) {
			log.error("updateUserWalletByUserId error",e);
		}
		if(i>0) {
			return;
		}
		throw new RRException(ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getErrMsg(),
				ErrorCode.UserErrorCode.MONEY_NOT_ENOUGH.getCode());
	}


	/**
	 * 根据用户Id修改用户金币，余额，佣金
	 */
	@Override
	public void freezeUserCoinByUserId(UserEntity user) {
		//log.debug("[updateUserWalletByUserId] user {} ", user);
		int i=0;
		try {
			i=baseMapper.freezeUserCoinByUserId(user);
		} catch (Exception e) {
			log.error("updateUserWalletByUserId error",e);
		}
		if(i>0) {
			return;
		}
	}

	@Override
	public Page<UserEntity> getAgentSubordinateList(Long userId, String account, PageParam pageParam) {
		//log.debug("[getAgentSubordinateList] userId {} ,account {} ,pageParam {}", userId, account, pageParam);
		Page<UserEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getAgentSubordinateList(page, account, userId));
	}

	@Override
	public List<UserEntity> findUserListByIdList(Collection<Long> idList) {
		//log.debug("[findUserListByIdList] idList {} ", idList);
		return this.baseMapper.findUserListByIdList(idList);
	}

	@Override
	public String trialAccount(String ip, String deviceType) {
		String token = null;

		// 同一ip在规定时间内如果重复使用试玩，返回同一个账户
		List<UserEntity> ipList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL).eq("register_ip", ip));
		if (!CollectionUtils.isEmpty(ipList)) {
			for (UserEntity entity : ipList) {
				if (StringUtils.isEmpty(entity.getToken())) {
					continue;
				}
				if (localContentCache.getToken(entity.getToken()) != null) {
					return entity.getToken();
				}

			}
		}
		List<UserEntity> list = baseMapper
				.selectList(new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL));
		if (!CollectionUtils.isEmpty(list)) {
			for (UserEntity entity : list) {
				if (StringUtils.isEmpty(entity.getToken())) {// 如果这个试玩账号没登陆过
					token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
					localContentCache.putToken(token, entity);// 将用户加入缓存
					updateUserAndLogin(ip, token, deviceType);
					return token;
				} else {// 如果登陆过，查看该用户是否在线
					if (localContentCache.getToken(entity.getToken()) == null) {// 查看该用户是否在线
						token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
						localContentCache.putToken(token, entity);// 将用户加入缓存
						updateUserAndLogin(ip, token, deviceType);
						return token;
					}
				}
			}
		}
		throw new RRException(ErrorCode.UserErrorCode.TRIAL_ACCOUNT_ISNULL_ERRO.getErrMsg(),
				ErrorCode.UserErrorCode.TRIAL_ACCOUNT_ISNULL_ERRO.getCode());
	}

	@Override
	public UserPasswordResult getUserPasswordInfo(long id) {
		//log.debug("[getUserPasswordInfo] id {} ", id);
		return this.baseMapper.getUserPasswordInfo(id);
	}

	@Override
	public UserAccountResult getUserBalance(String token) {
		//log.debug("[getUserBalance] token {} ", token);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = baseMapper.selectById(userId);
		UserAccountResult result = new UserAccountResult();
		result.setCoin(entity.getCoin());
		result.setId(entity.getId());
		result.setMoney(entity.getMoney());
		//log.debug("[getUserBalance] result {} ", result);
		return result;
	}

	/**
	 * 根据修改用户token 新增一条登陆记录 获取系统设置试玩账号金币====刷新金币 (用于试玩账号登陆) xiaoliu
	 */
	public void updateUserAndLogin(String ip, String token, String deviceType) {
		//log.debug("[updateUserAndLogin] ip {}, token {} ", ip, token);
		// 获取试玩配置==试玩金币
		List<UserTrialConfigEntity> list = userTrialConfigService
				.selectList(new EntityWrapper<UserTrialConfigEntity>(null));
		if (CollectionUtils.isEmpty(list)) {
			throw new RRException(ErrorCode.UserErrorCode.TRIAL_CONFIG_ISNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.TRIAL_CONFIG_ISNULL_ERRO.getCode());
		}
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = new UserEntity();
		entity.setId(userId);
		entity.setToken(token);
		entity.setRegisterIp(ip);
		entity.setCoin(list.get(0).getTrialAmount().longValue() * 100);
		baseMapper.updateById(entity);// 修改用户token
		// 为用户新增一条登陆记录
		userLoginService.insert(userId, ip, null, deviceType, token, SysConstant.SUCCESS);

	}

	/**
	 * 额度转换
	 */
	@Override
	public void changer(UserEntity user, BigDecimal money, Long coin) {
		//log.debug("[changer]  额度转换 user {} money {} coin {}", user, money, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		userUpdateParam.setMoney(BigDecimal.ZERO.subtract(money));
//		baseMapper.updateUserWalletByUserId(userUpdateParam);
		this.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.CHANGER.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(user.getMoney().subtract(money));
		transactionRecord.setAmount(money);
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(Constant.TransactionDetailType.QUOTACONVERSION.getValue());
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
	}

	/**
	 * 统计会员数量，有效会员数量(有充值)====非试玩账号
	 * 
	 */
	@Override
	public Map<String, Object> getStatisticsNumber() {
		return baseMapper.getStatisticsNumber();
	}

	/**
	 * 新增会员数近7日====非试玩账号
	 * 
	 */
	@Override
	public List<Map<String, Object>> getInsertUserNumber() {
		return baseMapper.getInsertUserNumber();
	}

	@Override
	public Map<String, Object> sumUserForDate(String startDate, String endDate) {
		return baseMapper.sumUserForDate(startDate, endDate);
	}

	/**
	 * 获取用户基本信息
	 */
	@Override
	public UserInfoResult getUserInformation(Long userId) {
		UserInfoResult userInfoResult = new UserInfoResult();
		UserEntity user = baseMapper.selectById(userId);
		userInfoResult.setAccount(user.getAccount());
		userInfoResult.setUserName(user.getUserName());
		userInfoResult.setBankAccountName(user.getUserName());
		List<UserInfoEntity> list = userInfoService
				.selectList(new EntityWrapper<UserInfoEntity>(null).eq("user_id", userId));
		if (!CollectionUtils.isEmpty(list)) {
			UserInfoEntity userInfo = list.get(0);
			userInfoResult.setAlipayAccount(userInfo.getAlipayAccount());
			userInfoResult.setBankCard(userInfo.getBankCard());
			userInfoResult.setBankName(userInfo.getBankName());
			userInfoResult.setBankAddress(userInfo.getBankAddress());

		}
		return userInfoResult;
	}

	// 修改用户基本信息，及新增完善信息奖励
	@Override
	public void updateUserInformation(UserInfoResult entity) throws Exception {
		UserEntity user = baseMapper.selectById(entity.getUserId());
		if (user == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setAlipayAccount(entity.getAlipayAccount());
		userInfo.setBankAccountName(entity.getUserName());
		userInfo.setBankCard(entity.getBankCard());
		userInfo.setBankName(entity.getBankName());
		userInfo.setBankAddress(entity.getBankAddress());
		userInfo.setUserId(entity.getUserId());
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(entity.getUserName());
		if (!CollectionUtils.isEmpty(this.selectList(new EntityWrapper<UserEntity>(userEntity)))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_NAME_REPEAT_ERRO.getCode());
		}
		user.setUserName(entity.getUserName());
		user.updateById();
		UserInfoEntity info = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(null).eq("user_id", user.getId()));
		if (info == null) {// 如果用户没有基本信息就新增
			userInfoService.insert(userInfo);
		} else {// 修改
			if (!StringUtils.isEmpty(info.getBankCard()) || !StringUtils.isEmpty(info.getBankName())
					|| !StringUtils.isEmpty(info.getBankAccountName())
					) {
				//|| !StringUtils.isEmpty(info.getAlipayAccount())
				throw new RRException(ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getCode());
			}
			// 校验银行卡是否已经被绑定
			UserInfoEntity userinfoEntity = userInfoService.selectOne(
					new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setBankCard(entity.getBankCard())));
			if (null != userinfoEntity) {
				throw new RRException(ErrorCode.UserErrorCode.BANKCARD_REPEAT_UPDATE.getErrMsg(),
						ErrorCode.UserErrorCode.BANKCARD_REPEAT_UPDATE.getCode());
			}
			userInfo.setId(info.getId());
			userInfoService.updateById(userInfo);
		}
		userRebate(user);
	}

	// 修改用户基本信息，及新增完善信息奖励
	@Override
	public void updateUserInfoAndRabate(UserInfoEntity userInfo, UserEntity user) throws Exception {
		userInfoService.updateById(userInfo);
		// 返利
		userRebate(user);
	}

	@Override
	public void updateUserFirstRecharge(Long id) {
		baseMapper.updateUserFirstRecharge(id);
	}

	// 资金归集
	@Override
	public void integrate(UserEntity user, BigDecimal money, Long coin, TransactionDetailType transactionDetailType) {
		//log.debug("[integrate] 资金归集  user {} money {} coin {}", user, money, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(0 - coin);
		userUpdateParam.setMoney(money);
		this.updateUserWalletByUserId(userUpdateParam);
//		baseMapper.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.INTEGRATE.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(user.getMoney().add(money));
		transactionRecord.setAmount(money);
		transactionRecord.setCoin(user.getCoin() - coin);
		transactionRecord.setCommission(user.getCommission());
		transactionRecord.setDetailType(transactionDetailType.getValue());
		userTransactionRecordService.insert(transactionRecord);
	}

	// 用户充值+额度直接转换
	@Override
	public void rechargeAndChanger(UserEntity user, BigDecimal money, Long coin) {
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		this.updateUserWalletByUserId(userUpdateParam);
//		baseMapper.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.CHANGER.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setAmount(money);
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(Constant.TransactionDetailType.QUOTACONVERSION.getValue());
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);

	}

	/**
	 * 佣金转金币
	 */
	@Override
	public void exchangeCommission(UserEntity user, BigDecimal commission, Long coin) {
		//log.debug("[exchangeCommission] 佣金转金币  user {} money {} coin {}", user, commission, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCommission(BigDecimal.ZERO.subtract(commission));
		userUpdateParam.setCoin(coin);
//		baseMapper.updateUserWalletByUserId(userUpdateParam);
		this.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.CHANGER.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setAmount(commission);
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(Constant.TransactionDetailType.EXCHANGE.getValue());
		transactionRecord.setCommission(user.getCommission().subtract(commission));
		userTransactionRecordService.insert(transactionRecord);
	}

	// 拉黑/取消拉黑
	@Override
	public void editForbidden(UserEntity user) {

		if (user.getForbiddenEnable() == true) {// 拉黑
			UserBlacklistEntity userBlacklist = new UserBlacklistEntity();
			userBlacklist.setType(SysConstant.ENABLE_FALSE);
			userBlacklist.setUserAccount(user.getUserOperater().getMemberAccount());
			userBlacklist.setUserId(user.getId());
			userBlacklistService.insert(userBlacklist);
		} else if (user.getForbiddenEnable() == false) {// 取消拉黑
			userBlacklistService.delete(new EntityWrapper<UserBlacklistEntity>().eq("user_id", user.getId()));
		}
		baseMapper.updateById(user);

	}

	@Override
	@Transactional
	public void revokeMoney(OrderRechargeEntity orderRecharge) throws Exception {
		UserEntity user = this.baseMapper.selectById(orderRecharge.getUserId());
		Long amount = orderRecharge.getAmount();
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(orderRecharge.getUserId());
		if (user.getMoney().subtract(MathUtil.getBigDecimal(amount)).compareTo(BigDecimal.ZERO) >= 0) {
			userUpdateParam.setMoney(MathUtil.getBigDecimal(-1 * amount));
			userUpdateParam.setCoin(0l);
		} else if (user.getCoin() - (amount * Constant.COIN_RATE) > 0) {
			userUpdateParam.setMoney(BigDecimal.ZERO);
			userUpdateParam.setCoin(-1 * amount * Constant.COIN_RATE);
		} else {
			BigDecimal subValue = MathUtil.getBigDecimal(amount).subtract(user.getMoney());
			Long subCoin = subValue.multiply(new BigDecimal(Constant.COIN_RATE)).longValue();
			userUpdateParam.setMoney(user.getMoney().multiply(new BigDecimal(-1)));
			Long unwithdrawCoin = user.getCoin() - subCoin;// 未撤回金币
			if (unwithdrawCoin >= 0) {
				userUpdateParam.setCoin(-1 * subCoin);

				userUpdateParam.setCoin(-1 * user.getCoin());
				orderRecharge.setUnwithdraw(MathUtil.getBigDecimal(Math.abs(unwithdrawCoin))
						.divide(MathUtil.getBigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		this.updateUserWalletByUserId(userUpdateParam);
		Integer updateNum=0;
//		Integer updateNum = this.baseMapper.updateUserWalletByUserId(userUpdateParam);
//		if (updateNum <= 0) {
//			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
//					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
//		}
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(Constant.TransactionType.RECHARGE_RETURN.getValue());
		transactionRecord.setUserId(orderRecharge.getUserId());
		transactionRecord.setUserAccount(orderRecharge.getUserAccount());
		transactionRecord.setOrderNo(orderRecharge.getOrderNo());
		transactionRecord.setMoney(user.getMoney().add(userUpdateParam.getMoney()));
		transactionRecord.setAmount(MathUtil.getBigDecimal(amount));
		transactionRecord.setCoin(user.getCoin() + userUpdateParam.getCoin());
		transactionRecord.setDetailType(Constant.TransactionDetailType.REVOKE_RECHARGE.getValue());
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
		// 代理商返佣撤销
		if (user.getSuperiorsId() != null) {
			UserEntity agent = this.baseMapper.selectById(user.getSuperiorsId());
			if (agent != null && agent.getAgentEnable()) {
				Map<String, Object> agentHierachy = userAgentHierarchyDao.getAgentHierarchyByUserId(agent.getId());
				if (agentHierachy == null) {
					log.error("[代理商返佣撤销失败] 代理商层级没有找到");
					return;
				}
				BigDecimal rate = MathUtil.getBigDecimal(agentHierachy.get("commission"));
				// 充值金额
				BigDecimal commission = MathUtil.getBigDecimal(orderRecharge.getAmount()).multiply(rate).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal cancelCommission = commission.multiply(new BigDecimal(-1));
				// 1.代理上加上佣金值
				UserEntity agentUpdateParam = new UserEntity();
				agentUpdateParam.setId(agent.getId());
				if (agent.getCommission().compareTo(commission) >= 0) {
					agentUpdateParam.setCommission(cancelCommission);
				} else {
					agentUpdateParam.setCommission(agent.getCommission().multiply(new BigDecimal(-1)));
				}
				this.updateUserWalletByUserId(agentUpdateParam);
//				baseMapper.updateUserWalletByUserId(agentUpdateParam);
				// 2.user_recommendation往总的佣金值加上当前佣金
				UserRecommendationEntity userRecommendation = new UserRecommendationEntity();
				userRecommendation.setUserId(agent.getId());
				userRecommendation.setAmount(cancelCommission);
				updateNum = userRecommendationDao.updateSuperiorsAmount(userRecommendation);
				if (updateNum == 0) {
					userRecommendationDao.updateSuperiorsAmountForZero(agent.getId());
				}
				// 3.user_recommendation_record往总的佣金值加上当前佣金（代理商下线贡献总值）
				UserRecommendationRecordEntity userRecommendationRecord = new UserRecommendationRecordEntity();
				userRecommendationRecord.setUserId(user.getId());
				userRecommendationRecord.setPromotingProfit(cancelCommission);
				updateNum = userRecommendationRecordDao.updateRecommendationRecordAmount(userRecommendationRecord);
				if (updateNum == 0) {
					userRecommendationRecordDao.updateRecommendationRecordAmountForZero(user.getId());
				}
				// 4.增加交易记录
				UserTransactionRecordEntity agentTransactionRecord = new UserTransactionRecordEntity();
				agentTransactionRecord.setType(Constant.TransactionType.RECHARGE_RETURN.getValue());
				agentTransactionRecord.setUserId(agent.getId());
				agentTransactionRecord.setUserAccount(agent.getAccount());
				agentTransactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
				agentTransactionRecord.setMoney(agent.getMoney());
				agentTransactionRecord.setAmount(commission);
				agentTransactionRecord.setCoin(agent.getCoin());
				agentTransactionRecord.setCommission(agent.getCommission().add(cancelCommission));
				if (agentTransactionRecord.getCommission().compareTo(BigDecimal.ZERO) < 0) {
					agentTransactionRecord.setCommission(BigDecimal.ZERO);
				}
				agentTransactionRecord
						.setDetailType(Constant.TransactionDetailType.AGENTSRECHARGECOMMISSIONCANCEL.getValue());
				userTransactionRecordService.insert(agentTransactionRecord);
			}
		}

	}

	@Override
	public UserDetail getUserInfoForH5(Long userId, Long hallId) {
		UserEntity user = baseMapper.selectById(userId);
		UserInfoEntity userInfo = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setUserId(userId)));
		UserRecommendationEntity recommendation = userRecommendationService.selectOne(
				new EntityWrapper<UserRecommendationEntity>(new UserRecommendationEntity().setUserId(userId)));
		//log.debug("[getUserInfoForH5] user {} ,userInfo {} ,recommendation {}", user, userInfo, recommendation);
		if (user == null) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		String recommendationCode = "";
		if (recommendation != null) {
			recommendationCode = recommendation.getRecommendationCode();
		}
		UserDetail result = new UserDetail(user, userInfo, ConfigConstant.OFFICIAL_URL + recommendationCode,
				ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, hallId);
		log.debug("[getUserInfoForH5] result {}", result);
		return result;
	}

	@Override
	public List<UserEntity> findUserListByAccount(String[] accountList) {
		return this.baseMapper.findUserListByAccount(accountList);
	}

	/**
	 * 用户完善资料返利操作
	 * 
	 * @throws Exception
	 */
	private void userRebate(UserEntity user) throws Exception {
		if (user.getFrozenEnable() || user.getForbiddenEnable()) {
			return;
		}
		Date FAIL_TIME = DateUtil.nextWeek(); // 邮件失效时间
		Date EFFECT_TIME = new Date(); // 邮件生效时间
		Long coin = 0L;// 奖励金币
		UserEntity userEntity = new UserEntity();
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		UserRebateEntity userRebateEntity = userRebateService.selectOne(new EntityWrapper<UserRebateEntity>(
				new UserRebateEntity().setType(Constant.RewardType.REAL_NAME.getValue())));
		if (userRebateEntity != null) {
			coin = userRebateEntity.getCoin();
			// =============================为用户赠送金币============================
			userEntity.setId(user.getId());
			userEntity.setCoin(coin);
			this.updateUserWalletByUserId(userEntity);// 修改用户金币余额
//			baseMapper.updateUserWalletByUserId(userEntity);// 修改用户金币余额
			// =============================为用户新增交易记录============================
			transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
			transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
			transactionRecord.setDetailType(Constant.TransactionDetailType.USERINFO.getValue());
			transactionRecord.setUserId(user.getId());
			transactionRecord.setUserAccount(user.getAccount());
			transactionRecord.setAmount(new BigDecimal(coin / (Constant.DB_COIN_RATE * Constant.CLIENT_COIN_RATE)));
			transactionRecord.setCoin(user.getCoin() + coin);
			userTransactionRecordService.insert(transactionRecord);
			orderCashExamineService.bindUserinfoGiftCashExamine(user, coin, userRebateEntity.getCodeMultiple());
			// 邮件推送
			MessageUserEntity messageManagement = new MessageUserEntity().setUserAccount(user.getAccount())
					.setContent(String.format(SUCCUSS_CONTENT, coin / Constant.DB_COIN_RATE))
					.setContentType(CONTENT_TYPE).setTitle(TITLE)
					.setEffectTime(EFFECT_TIME).setFailureTime(FAIL_TIME).setUserId(user.getId());
			messageUserService.save(messageManagement);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setCoin(coin);
			pushMessage.setId(user.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.debug("[用户完善信息返利-推送消息] message {}", message);
			pushService.pushExchange(message);
		}
		if (user.getSuperiorsId() != null) {// 如果该人员有上级
			// ===============================为上级新增金币===========================
			UserEntity superiorsUserEntity = baseMapper.selectById(user.getSuperiorsId());// 上级详情
			List<UserRecommendationEntity> list = userRecommendationService
					.selectList(new EntityWrapper<UserRecommendationEntity>(
							new UserRecommendationEntity().setUserId(user.getSuperiorsId())));
			if (CollectionUtils.isEmpty(list)) {
				log.error("[userRebate] 用户修改信息返利，上级id不为空，但是找不到上级的推荐记录 user {}", user);
				return;
			}
			coin=0l;
			if (list.get(0).getAgentHierarchyId() != 0 && list.get(0).getAgentHierarchyId() != null) {// 判断代理等级是否为空
				// 获取用户相应代理等级奖励规则
				UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService
						.selectById(list.get(0).getAgentHierarchyId());

//				if (userAgentHierarchy.getCoin() == null) {
//					throw new RRException(ErrorCode.AgentErrorCode.AGENT_COIN_ERRO.getErrMsg(),
//							ErrorCode.AgentErrorCode.AGENT_COIN_ERRO.getCode());
//				}
				if(userAgentHierarchy !=null) {
					coin =  userAgentHierarchy.getCoin() == null?0l:userAgentHierarchy.getCoin();
				}
				
			}
			if(null == coin || coin<=0) {
				return;
			}
			userEntity.setId(user.getSuperiorsId());
			userEntity.setCoin(coin);
			this.updateUserWalletByUserId(userEntity);// 修改上级金币余额
//			baseMapper.updateUserWalletByUserId(userEntity);// 修改上级金币余额
			orderCashExamineService.bindUserinfoGiftCashExamine(superiorsUserEntity, coin);
			// 邮件推送
			MessageUserEntity messageManagement = new MessageUserEntity()
					.setUserAccount(superiorsUserEntity.getAccount())
					.setContent("来自账号：'" + user.getAccount() + "'的实名认证"
							+ String.format(INVITE_SUCCUSS_CONTENT, coin / Constant.DB_COIN_RATE))
					.setContentType(CONTENT_TYPE).setTitle(INVITE_TITLE)
					.setEffectTime(EFFECT_TIME).setFailureTime(FAIL_TIME).setUserId(user.getSuperiorsId());
			messageUserService.save(messageManagement);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setCoin(coin);
			pushMessage.setId(superiorsUserEntity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.debug("[用户完善信息返利-推送消息] message {}", message);
			pushService.pushExchange(message);
			// =============================为上级新增交易记录========================
			transactionRecord.setType(SysConstant.TRANSACTION_REBATE);
			transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
			transactionRecord.setDetailType(SysConstant.TRANSACTION_DETAIL_RECOMMEND);
			transactionRecord.setUserId(superiorsUserEntity.getId());
			transactionRecord.setUserAccount(superiorsUserEntity.getAccount());
			transactionRecord.setAmount(new BigDecimal(coin / (Constant.DB_COIN_RATE * Constant.CLIENT_COIN_RATE)));
			transactionRecord.setCoin(superiorsUserEntity.getCoin() + coin);
			userTransactionRecordService.insert(transactionRecord);
			// ==============================修改上级邀请详情===============================
			UserRecommendationEntity userRecommendation = new UserRecommendationEntity();
			userRecommendation.setCoin(coin);
			userRecommendation.setUserId(user.getSuperiorsId());
			userRecommendationService.updateSuperiorsAmount(userRecommendation);
			// =============================修改用户的邀请记录盈利============================
			UserRecommendationRecordEntity userRecommendationRecord = new UserRecommendationRecordEntity();
			userRecommendationRecord.setCoin(coin);
			userRecommendationRecord.setUserId(user.getId());
			userRecommendationRecordService.updateRecordAmount(userRecommendationRecord);// 修改当前用户被邀请记录盈利
		}
	}

	@Override
	public UserDetail appTrialAccount(String ip, Long hallId, String deviceType) {
		String token = null;
		//log.debug("[APP获取试玩账号参数] ip {} ,hallId {}, deviceType {}", ip, hallId, deviceType);
		// 同一ip在规定时间内如果重复使用试玩，返回同一个账户
		List<UserEntity> ipList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL).eq("register_ip", ip));
		if (!CollectionUtils.isEmpty(ipList)) {
			for (UserEntity entity : ipList) {
				if (StringUtils.isEmpty(entity.getToken())) {
					continue;
				}
				//log.debug("[appTrialAccount] 该IP是否已有试玩 {}", localContentCache.getToken(entity.getToken()) != null);
				if (localContentCache.getToken(entity.getToken()) != null) {
					String officialUrl = domainManagementService.getOfficalDomain();
					token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
					entity.setToken(token);
					UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
					updateUserAndLogin(ip, token, deviceType);
					localContentCache.putToken(token, entity);// 将用户加入缓存
					setHallIp(result, hallId);
					log.debug("[appTrialAccount] result {}", result);
					return result;
				}

			}
		}
		List<UserEntity> list = baseMapper
				.selectList(new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL));
		if (!CollectionUtils.isEmpty(list)) {
			for (UserEntity entity : list) {
				//log.debug("[appTrialAccount] entity {}", entity);
				if (StringUtils.isEmpty(entity.getToken())) {// 如果这个试玩账号没登陆过
					token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
					localContentCache.putToken(token, entity);// 将用户加入缓存
					updateUserAndLogin(ip, token, deviceType);
					entity.setToken(token);
					String officialUrl = domainManagementService.getOfficalDomain();
					UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
					setHallIp(result, hallId);
					//log.debug("[appTrialAccount] result {}", result);
					return result;
				} else {// 如果登陆过，查看该用户是否在线
					//log.debug("[appTrialAccount] 是否是登陆状态 {}", localContentCache.getToken(entity.getToken()) == null);
					if (localContentCache.getToken(entity.getToken()) == null) {// 查看该用户是否在线
						token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
						localContentCache.putToken(token, entity);// 将用户加入缓存
						updateUserAndLogin(ip, token, deviceType);
						String officialUrl = domainManagementService.getOfficalDomain();
						entity.setToken(token);
						UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
						setHallIp(result, hallId);
						log.debug("[appTrialAccount] result {}", result);
						return result;
					}
				}
			}
		}
		throw new RRException(ErrorCode.UserErrorCode.TRIAL_ACCOUNT_ISNULL_ERRO.getErrMsg(),
				ErrorCode.UserErrorCode.TRIAL_ACCOUNT_ISNULL_ERRO.getCode());
	}

	@Override
	public void deleteUser(Long userId) {
		this.baseMapper.deleteUserById(userId);
		userInfoService.deleteUserInfoByUserId(userId);

	}

	@Override
	public void batchUpgradeUserAccount(Long userId,String account) {
		this.baseMapper.batchUpgradeUserAccount(userId,account);
	}

	@Override
	public void updateUserCoin(UserEntity user, Long coin, int type, int detailType) {
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		this.updateUserWalletByUserId(userUpdateParam);
//		if(baseMapper.updateUserWalletByUserId(userUpdateParam)<=0) {
//			if(coin>0) {
//				throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getErrMsg(),
//						ErrorCode.UserErrorCode.USER_ACCOUNT_INVALID.getCode());
//			}else {
//				throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
//						ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
//				
//			}
//		}
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(type);
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setAmount(
				new BigDecimal(Math.abs(coin)).divide(new BigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP));
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(detailType);
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
	}


	@Override
	public void freezeUserCoin(UserEntity user, int type, int detailType) {
		log.error("用户金币冻结 账号{} ，ID {}  ，金额 {} ", user.getAccount(),user.getId(),user.getCoin());
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setFreezeCoin(user.getCoin());
		userUpdateParam.setCoin(0L);

		this.freezeUserCoinByUserId(userUpdateParam);

	}

	@Override
	public Long statisticsRiskNum() {
		return baseMapper.statisticsRiskNum();
	}

	@Override
	public Integer upadateUserSubtractCoin(UserEntity user, SwitchParam param) {
		if (user.getCoin() < (param.getNumber())) { // 判断用户金币是否足够存入
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		UserEntity userParam = new UserEntity();
		userParam.setId(user.getId());
		userParam.setCoin(param.getNumber() * -1);
		this.updateUserWalletByUserId(userParam);
//		Integer i = this.baseMapper.updateUserWalletByUserId(userParam);
		return 1;

	}
	@Override
	public UserEntity getUser(String token) {
		// token转为userID,并通过其获取用户实体
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity user = this.selectById(userId);
		// 判断当前会员是否存在
		if (user == null || user.getId() <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		return user;
	}

	@Override
	public UserEntity getUserByTouristId(String touristId) {
		List<UserEntity> list =  this.baseMapper.findUserListByTouristId(touristId);
		if (list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public UserEntity getUserByPhone(String phone) {
		List<UserEntity> list =  this.baseMapper.findUserListByPhone(phone);
		if (list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	// 验证手机号是否重复(true:重复)
    public Boolean PhoneIsNotRepeat(String phone) {
    	//log.debug("[PhoneIsNotRepeat] phone {}", phone);
        Boolean result = false;
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        int num = 0;
        // 判断账号是不是手机号格式
        if (!VerificationUitl.phoneVerification(phone)) {
            num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity));
        } else {
            num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity).orNew("phone={0}", phone));
        }
        if (num > 0) {
            return result = true;
        }
        log.debug("[PhoneIsNotRepeat] result {}", result);
        return result;
    }

	// 验证账号和手机号是否重复(true:重复)
	public void validateAccountAndPhoneRepeat(String account, String phone) {
		//log.debug("[validatePhoneRepeat] account {} phone {}", account, phone);
		UserEntity userEntity = new UserEntity();
		userEntity.setAccount(account);
		List<UserEntity> result = baseMapper.findUserByAccountOrPhone(account, phone);
		if (CollectionUtils.isEmpty(result)) {
			return;
		}
		if (result.get(0).getAccount().equals(account)) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
		}
		if (result.get(0).getPhone().equals(phone)) {
			throw new RRException(ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getCode());
		}
	}
    
	@Override
	public void bindingPhone(BindingPhone bindingPhone, Long id) {
		//log.debug("[bindingPhone] bindingPhone {}", bindingPhone);
	        //验证账号格式有误
	        if (!VerificationUitl.phoneVerification(bindingPhone.getPhone())) {
	            throw new RRException(ErrorCode.UserErrorCode.USER_PHONE_ERRO.getErrMsg(),
	                    ErrorCode.UserErrorCode.USER_PHONE_ERRO.getCode());
	        }
	        // 验证账号是否重复
	        if (PhoneIsNotRepeat(bindingPhone.getPhone())) {
	            throw new RRException(ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getErrMsg(),
	                    ErrorCode.UserErrorCode.PHONE_ALREADY_BIND_ERRO.getCode());
	        }
	        // 验证码校验
	        if (!bindingPhone.getCode().equals(localContentCache.getPhoneVerificationCode(bindingPhone.getPhone()))) {
	            throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
	                    ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
	        }

	        // 给用户新增手机号
	        UserEntity user = this.selectById(id);
	        user.setPhone(bindingPhone.getPhone());
	        user.updateById();
		
	}

	@Override
	public Integer updateSuperiorsId(Long userId, Long superiorsId) {		
		return this.baseMapper.updateSuperiorsId(userId, superiorsId);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.GM_USER_CACHE, key = "'gmuserlist'", unless = "#result == null || #result.size() == 0")
	public List<Long> getGmUser() {
		return this.baseMapper.getGmUser();
	}

	@Override
	public boolean isGmUser(Long userId) {
		List<Long> gmList = this.getGmUser();
		if(null == gmList || gmList.isEmpty()) {
			return false;
		}
		//只要流中有一个元素满足该断言则返回true，否则返回false
		return gmList.stream().anyMatch(e -> e.equals(userId));
	}

	@Override
	public UserDetail phoneLogin(UserEntity user, PhoneLoginParams phoneLoginParams, String ip) {

		// 验证码校验
		if (!phoneLoginParams.getCode().equals(localContentCache.getPhoneVerificationCode(phoneLoginParams.getAccount()))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
		}
		// 根据账号获取人员信息
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);

		// token为空表示从未登陆过，如果不为空，那么就需要在缓存中查看是否有登陆
		Object oldToken = localContentCache.getToken(String.valueOf(user.getId()));
		if (null != oldToken) {
			localContentCache.remove(oldToken.toString());
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId);// 登录时生成一个token
		user.setToken(token);
		userEntity.setToken(token);
		userEntity.updateById();
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(phoneLoginParams.getCommonParam().getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(phoneLoginParams.getCommonParam().getJpushRegId()));
		}
		// 获取上一条登入记录
		UserLoginEntity lastLogin = new UserLoginEntity().setUserId(userEntity.getId());
		UserLoginEntity resultEntity = lastLogin.selectOne(new EntityWrapper<UserLoginEntity>(lastLogin)
				.ne("login_status", SysConstant.FAIL).orderBy("id", false).last("limit 1"));
		// 新增一条登陆成功记录
		UserLoginEntity userLogin = new UserLoginEntity();
		userLogin.setUserId(userId);
		userLogin.setIp(ip);
		String ipData[] = IPQueryUtil.queryIp(ip);
		String address=null;
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
				address=ipData[1] + ipData[2];
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(1L);
		userLogin.setEdition(phoneLoginParams.getCommonParam().getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(phoneLoginParams.getCommonParam().getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(phoneLoginParams.getCommonParam().getDeviceType());
		if (null != resultEntity) {
			userLogin.setLastDeviceCode(resultEntity.getDeviceCode());
			userLogin.setLastDeviceType(resultEntity.getDeviceType());
			userLogin.setLastEdition(resultEntity.getEdition());
			userLogin.setLastHallId(resultEntity.getHallId());
			userLogin.setLastIp(resultEntity.getIp());
			userLogin.setLastIpAddress(resultEntity.getIpAddress());
			userLogin.setLastNation(resultEntity.getNation());
			userLogin.setLastLoginTime(resultEntity.getCreateTime());
		}
		userLoginService.insert(userLogin);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
		result.setAddress(address);
		setHallIp(result, 1L);
		log.debug("[login] result {}", result);
		return result;
	}

	@Override
	public UserDetail phoneRegister(PhoneLoginParams phoneLoginParams) {


		// 验证码校验
		if (!phoneLoginParams.getCode().equals(localContentCache.getPhoneVerificationCode(phoneLoginParams.getAccount()))) {
			throw new RRException(ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_VALIDATION_CODE_ERRO.getCode());
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(phoneLoginParams.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(phoneLoginParams.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(phoneLoginParams.getCommonParam().getDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setRoomCard(0L);
		user.setChannelCode(phoneLoginParams.getCommonParam().getChannelCode());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(true));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		user.setPhone(phoneLoginParams.getAccount());
		user.setNickName(Base64Util.base64Encoder(phoneLoginParams.getAccount()));
//		user.setUserName((phoneLoginParams.getAccount()));
		//log.debug("[register] insertGetId {}", user);
		baseMapper.insertGetId(user);
		Long userId = user.getId();// 新增的用户ID
		/**
		 * 获取用户未读信息
		 */
		MessageRequestVo requestVo = new MessageRequestVo();
		requestVo.setContentType(Constant.MessageContentType.USER_CONTENT.getValue());
		String hierachyid = user.getHierarchyId() == null ? "" : user.getHierarchyId().toString().trim();
		requestVo.setHierarchyIds(hierachyid.split(","));
		requestVo.setUserId(user.getId());
		requestVo.setUserAccount(user.getAccount());
		requestVo.setRegisterDate(user.getCreateTime());
		int unreadNum = messageManagementService.countUnreadNumber(requestVo);
		user.setUnreadNum(unreadNum);
		// 新增用户信息
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(userId);// 用户ID
		if (!StringUtils.isEmpty(phoneLoginParams.getCommonParam().getJpushRegId())) {
			userInfo.setJpushRegId(phoneLoginParams.getCommonParam().getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 对密码进行加密并新增用户密码
		userPasswordService.insertPassword(userId, "", "");
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		//log.debug("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		String ipData[] = IPQueryUtil.queryIp(phoneLoginParams.getRegisterIp());
		String address =null;
		try {
			if (ipData != null) {

				address=ipData[1] + ipData[2];
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		result.setAddress(address);
		setHallIp(result, 1L);
		log.debug("[login] result {}", result);
		return result;
	}

	@Override
	public void unfreezeUserCoin(UserEntity entity) {
		log.error("用户金币解冻 账号{} ，ID {}  ，金额 {} ", entity.getAccount(),entity.getId(),entity.getCoin());
		this.baseMapper.unfreezeUserCoin(entity);
	}
}