package com.xmsy.server.zxyy.manager.modules.manager.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Subordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.common.util.securitytools.MD5Util;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.cache.LocalContentCache;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ArrayUtil;
import com.xmsy.server.zxyy.manager.common.utils.ConfigConstant;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.Constant.TransactionDetailType;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.app.login.param.LoginParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.OAuth2Params;
import com.xmsy.server.zxyy.manager.modules.app.login.param.PhoneRegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.RegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.result.UserDetail;
import com.xmsy.server.zxyy.manager.modules.app.user.param.EnterGameParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.RecommenderParam;
import com.xmsy.server.zxyy.manager.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.manager.modules.manager.agent.param.TeamAgent;
import com.xmsy.server.zxyy.manager.modules.manager.domainmanagement.service.DomainManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.param.MessageRequestVo;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatistics;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.dao.OrderTakeMoneyDao;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatistics;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamThree;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamTwo;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.dao.UserAgentHierarchyDao;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.dao.UserBalanceDao;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.service.UserBalanceService;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.entity.UserBetRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.service.UserBetRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity.UserBlacklistEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.service.UserBlacklistService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.service.UserInfoService;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlogin.service.UserLoginService;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.entity.UserPasswordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userpassword.service.UserPasswordService;
import com.xmsy.server.zxyy.manager.modules.manager.userrebate.entity.UserRebateEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebate.service.UserRebateService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.dao.UserRecommendationDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.service.UserRecommendationService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.dao.UserRecommendationRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.entity.UserTrialConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.usertrialconfig.service.UserTrialConfigService;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.LoginEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.manager.modules.web.login.entity.RegisterEntity;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserAccountResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserInfoResult;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserPasswordResult;
import com.xmsy.server.zxyy.manager.oauth2.OAuth2Utils;
import com.xmsy.server.zxyy.manager.utils.ByteUtil;
import com.xmsy.server.zxyy.manager.utils.IPQueryUtil;
import com.xmsy.server.zxyy.manager.utils.InviteCode;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;
import com.xmsy.server.zxyy.manager.utils.PasswordVerification;
import com.xmsy.server.zxyy.manager.utils.UserStatusVerificationUtil;
import com.xmsy.server.zxyy.manager.utils.VerificationUitl;
import com.xmsy.server.zxyy.manager.common.utils.DateUtils;

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
	private MessageManagementService messageManagementService;
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
	private UserBalanceService userBalanceService;
	@Autowired
	private UserBalanceDao userBalanceDao;
	@Autowired
	private UserBetRecordService userBetRecordService;
	@Autowired
	private GameRecordDao gameRecordDao;
	public static final String TITLE = "实名返利通知";
	public static final String SUCCUSS_CONTENT = "用户实名返利; 金币 :%s, 请查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户
	@Override
	public Map<String,Object> statistics(UserParam userParam) {
		//start
		Map<String, Object> count = this.baseMapper.count(userParam.getId());
		Map<String, Object> count1 = orderTakeMoneyDao.count(userParam.getId());
		Map<String, Object> count2 = orderRechargeDao.count(userParam.getId());
		Map<String, Object> count3 = userBalanceDao.count(userParam.getId());

		if (count == null) {
			count = new HashMap<>();

		}
		if (count1 == null) {
			count1 = new HashMap<>();

		}
		if (count2 == null) {
			count2 = new HashMap<>();

		}
		if (count3 == null) {
			count3 = new HashMap<>();

		}
		count.putAll(count1);
		count.putAll(count2);
		count.putAll(count3);
		//end

		return count;
	}

	/**
	 * 树形列表
	 *
	 * @param userParam
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findUserTreePage(UserParam userParam) {
       /* PageUtils pageUtils = findUserList(userParam);
        List<Map<String, Object>> list = (List<Map<String, Object>>) pageUtils.getList();
        UserParam user = new UserParam();
        for (Map<String, Object> map : list) {
            map.put("open", false);
            map.put("list", new ArrayList<>());
            Object account = map.get("account");
            Object id = map.get("id");
            user.setSuperiorsId((Long)id);
            List<Map<String, Object>> userList = findUserList(user);
        }*/

		List<Map<String, Object>> list = findUserList(userParam);
		getUserOtherData(list, userParam.getTrial() != null && userParam.getTrial() == 1);
		// UserParam user = new UserParam();
		ArrayList<Map<String,Object>> tree =new ArrayList<>();
		tree.addAll(list);
		for (Map<String, Object> map : list) {
			map.put("open", false);
			map.put("list", new ArrayList<>());

			Object id = map.get("id");

			userParam.setSuperiorsId((Long) id);
			List<Map<String, Object>> mapList = findUserTreePage(userParam);
			tree.addAll(mapList);

		}
		return tree;
	}

	@Override
	public PageUtils findUserPage(UserParam userParam, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		//Long s=System.currentTimeMillis();
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
//		getUserOtherData(userList, user.getTrial() != null && user.getTrial() == 1);
		return userList;
	}
	@Override
	public List<Map<String, Object>> findUserListForCsv(UserParam user) {
		return this.baseMapper.findUserListForCsv(user);
	}
	private void getUserOtherData(List<Map<String, Object>> userList, boolean trial) {
		if(userList==null || userList.isEmpty()) {
			return;
		}
		Map<String, Object> superUserMap = new HashMap<>();
		List<Long> idList=new ArrayList<>();
		// 非试玩账号
		if (!trial) {
			Set<Long> superiorsIdList = new HashSet<>();
			Map<Long, String> gameMap = localContentCache.getGameMap();
			for (Map<String, Object> user : userList) {
				if (user.get("superiorsId") != null) {
					superiorsIdList.add(Long.parseLong(user.get("superiorsId").toString()));
				}
				if(user.get("gameInfoId")!=null) {
					Integer gameInfoId = Integer.valueOf(user.get("gameInfoId").toString());
					if (0 != gameInfoId) {
						user.put("roomId", ByteUtil.toInt(ByteUtil.highEight(gameInfoId)));
						user.put("gameId", ByteUtil.toInt(ByteUtil.lowFourteen(gameInfoId)));
						user.put("gradeId", ByteUtil.toInt(ByteUtil.middleEight(gameInfoId)));
//						user.put("gameName", GameInfoInterface.getGameName(Long.valueOf(user.get("gameId").toString())));
						user.put("gameName", gameMap.get(Long.valueOf(user.get("gameId").toString())));
					}
				}

				// 获取会员id集合     为了下面一个遍历获取余额宝金额
				Long id = (Long)user.get("id");
				user.put("yuEBaoMoney", 0L);//初始化余额宝金额
				idList.add(id);
				Integer sonPeople = this.baseMapper//获取下级人数
						.selectCount(new EntityWrapper<UserEntity>(new UserEntity().setSuperiorsId(id)));
				user.put("sonPeople", sonPeople);//初始化下级人数
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
		// 余额宝    通过id集合去查找每个用户的余额宝金额，再遍历放进用户集合里
		List<UserBalanceEntity> listUserBalance =null;
		if(idList!=null&&idList.size()>0) {
			listUserBalance= userBalanceService.findUserBalanceByUserId(idList);
		}
		for (Map<String, Object> user : userList) {
			if (user.get("coin") != null) {
				user.put("coin", MathUtil.getBigDecimal(user.get("coin")).
						divide(new BigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_DOWN));
			}
			if (MathUtil.getBigDecimal(user.get("gameInfoId")).longValue() > 0) {
				user.put("online", true);
			} else {
				user.put("online", false);
			}
			user.put("hierarchyName", localContentCache.getHierarchyName(MathUtil.toLong(user.get("hierarchyId"))));
			user.put("riskHierarchyName", localContentCache.getHierarchyName(MathUtil.toLong(user.get("riskHierarchyId"))));
			user.put("vipName", localContentCache.getVipName(MathUtil.toLong(user.get("vipId"))));
			Long userId = Long.parseLong(user.get("id").toString());
			if(user.get("token")!=null) {
				user.putAll(userLoginService.getUserLoginByUserId(userId, user.get("token").toString()));
			}

			// 非试玩账号
			if (!trial) {
				// 直接上级
				if (user.get("superiorsId") != null) {
					user.put("superiorsAccount", superUserMap.get(user.get("superiorsId").toString()));
				}
				// 取款
				OrderTakeMoneyStatistics orderTakeMoneyStatistics = orderTakeMoneyDao.orderTakeStatisticsV2(userId);
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
				OrderRechargeStatistics orderRechargeStatistics = orderRechargeDao.statisticsRechargeByUserIdV2(userId);
			      BigDecimal     discountAmount	 = orderRechargeDao.statisticsRechargeByUserIdV3(userId);
				if (orderRechargeStatistics != null) {
					user.put("rechargeAmount", orderRechargeStatistics.getAmount());
					user.put("rechargeNum", orderRechargeStatistics.getRechargeNum());
				} else {
					user.put("rechargeAmount", 0);

					user.put("rechargeNum", 0);
				}
				if(discountAmount!=null){
					user.put("discountAmount", discountAmount);
				}else {
					user.put("discountAmount", 0);
				}
				// 最后一次存款
				OrderRechargeEntity lastRecharge = orderRechargeDao.getLastRechargeByUserId(userId);
//		         BigDecimal lastRechargeByUser =   orderRechargeDao.getLastRechargeByUser(userId);
				if (lastRecharge != null) {
					user.put("lastRechargeAmount", MathUtil.getBigDecimal(lastRecharge.getAmount()));//当前存款
					user.put("lastRechargeAmountDate", lastRecharge.getCreateTime());
					user.put("lastDiscountAmount", MathUtil.getBigDecimal(lastRecharge.getDiscountAmount()));//当前优惠

				} else {
					user.put("lastRechargeAmount", 0);
					user.put("lastDiscountAmount", 0);
					user.put("lastRechargeAmountDate", "");
				}
//				if(lastRechargeByUser!=null){
//					user.put("lastDiscountAmount", lastRechargeByUser);//当前优惠
//				}

				// 打码量
				OrderCashExamineEntity orderCashExamine = orderCashExamineService.findRecentOrderCashExamine(userId);
				if (orderCashExamine != null && orderCashExamine.getUserNeedBet().compareTo(BigDecimal.ZERO)>0) {
						// 用户有效下注(稽查记录的最后一条  到现在   用户又增加了多少有效下注  去加入到有效下注里面 = 当前打码量)
						Long userValidBet = gameRecordDao.getUserValidBet(userId,
								DateUtils.formatTime(orderCashExamine.getUpdateTime()), DateUtils.formatTime(new Date()));
						BigDecimal userValidBetDecimal = new BigDecimal(userValidBet == null ? 0 : userValidBet)
								.add(orderCashExamine.getUserValidBet()); // 用户有效打码
						//如果 当前需要打码量为0  当前打码量也置0
						user.put("userNeedBet", orderCashExamine.getUserNeedBet());
						user.put("userValidBet", userValidBetDecimal);//当前打码量  (最后一条稽查记录+之后的用户有效下注值)
						user.put("hierarchyNormalMultiple", orderCashExamine.getHierarchyNormalMultiple());
						user.put("isSatisfy",
								orderCashExamine.getUserValidBet().compareTo(orderCashExamine.getUserNeedBet()) > 0);
					} else {
					// 用户有效下注(稽查记录的最后一条  到现在   用户又增加了多少有效下注  去加入到有效下注里面 = 当前打码量)
					//Long userValidBet = gameRecordDao.getUserValidBet(userId,null,null);无记录 就置0
					user.put("userNeedBet", 0);
					user.put("userValidBet", 0);
					user.put("isSatisfy", true);
				}
				//总累计打码量    userBetRecord表最新的一条数据
				UserBetRecordEntity userBetRecord = userBetRecordService
						.selectOne(new EntityWrapper<UserBetRecordEntity>(new UserBetRecordEntity().setUserId(userId))
								.orderBy("id", false));
				if(userBetRecord!=null) {
					user.put("totalUserValidBet", userBetRecord.getOldBet());
				}
				//余额宝
				for (UserBalanceEntity UserBalance : listUserBalance) {
					if (UserBalance.getUserId().equals(userId)) {
						user.put("yuEBaoMoney", UserBalance.getMoney());
						break;
					}
				}
			}
		}
	}

	/**
	 * web端注册
	 */
	@Override
	public UserEntity register(RegisterEntity registerEntity) {
		log.info("[register] registerEntity {}", registerEntity);
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
		// 一个ip限制注册5个账号
		List<UserEntity> userList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(new UserEntity().setRegisterIp(registerEntity.getRegisterIp())));
		if (!CollectionUtils.isEmpty(userList) && userList.size() > Constant.REGISTER_IP_LIMIT) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
		} // 新增用户并返回用户ID
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
		log.info("[register] insertGetId {}", user);
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
		log.info("[registerLogin] token {} user {}", token, user);
		return user;
	}

	/**
	 * web端手机注册
	 */
	@Override
	public UserEntity webPhoneRegister(PhoneRegisterEntity registerEntity) {
		log.info("[webPhoneRegister] PhoneRegisterEntity {}", registerEntity);
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
		List<UserEntity> userList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(new UserEntity().setRegisterIp(registerEntity.getRegisterIp())));
		if (!CollectionUtils.isEmpty(userList) && userList.size() > Constant.REGISTER_IP_LIMIT) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
		}
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
		log.info("[register] insertGetId {}", user);
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
		log.info("[registerLogin] token {} user {}", token, user);
		return user;
	}

	/**
	 * app端注册
	 */
	@Override
	public UserDetail appRegister(RegisterParams registerParams) {
		log.info("[register] registerParams {}", registerParams);
		// 验证账号及密码的合法性
		if (!registerParams.getLoginPassWord().equals(registerParams.getVerificationWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.PASSWORD_ISUSED_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ERRO.getCode());
		}
		if (!PasswordVerification.regularVerification(registerParams.getLoginPassWord())) {
			throw new RRException(ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getErrMsg(),
					ErrorCode.PasswordErrorCode.LOGIN_PASSWORD_ERRO.getCode());
		}
		// 验证账号是否重复
		if (validatePhoneRepeat(registerParams.getAccount())) {
			throw new RRException(ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_ACCOUNT_ISNOTNULL_ERRO.getCode());
		}
		// 获取默认层级ID
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		// 一个设备码只能注册2个账户
		if (!StringUtils.isEmpty(registerParams.getRegisterDeviceCode())) {
			int userCount = baseMapper.selectCount(new EntityWrapper<UserEntity>(
					new UserEntity().setRegisterDeviceCode(registerParams.getRegisterDeviceCode())));
			if (userCount > Constant.USER_DEVICE_CODE_LIMIT) {
				throw new RRException(ErrorCode.UserErrorCode.USER_DEVICE_CODE_LIMIT_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.USER_DEVICE_CODE_LIMIT_ERRO.getCode());
			}
		}
		// 一个ip限制注册5个账号
		List<UserEntity> userList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(new UserEntity().setRegisterIp(registerParams.getRegisterIp())));
		if (!CollectionUtils.isEmpty(userList) && userList.size() > Constant.REGISTER_IP_LIMIT) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IP_LIMIT_ERRO.getCode());
		} // 新增用户并返回用户ID
		UserEntity user = new UserEntity();// 用户
		user.setAccount(registerParams.getAccount());// 账号
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setRegisterIp(registerParams.getRegisterIp());// 注册IP
		user.setRegisterDeviceCode(registerParams.getRegisterDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setUserName(registerParams.getUserName() == null ? "" : registerParams.getUserName());
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		log.info("[register] insertGetId {}", user);
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
		if (!StringUtils.isEmpty(registerParams.getJpushRegId())) {
			userInfo.setJpushRegId(registerParams.getJpushRegId());
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
		log.info("[login] 用户是否登陆过 {}", StringUtils.isEmpty(user.getToken()));
		if (!StringUtils.isEmpty(user.getToken())) {
			// token不为空，根据token获取人员信息
			UserEntity cacheUser = (UserEntity) localContentCache.getToken(user.getToken());
			log.info("[login] 用户是否是登陆状态 {}", cacheUser != null);
			if (cacheUser != null) {
				// 如果不为空表示已登陆,将账号强制退出
				localContentCache.remove(user.getToken());
			}
		}
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		log.info("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, registerParams.getHallId());
		log.info("[login] result {}", result);
		return result;
	}

	/**
	 * app端手机注册
	 */
	@Override
	public UserDetail appPhoneRegister(PhoneRegisterParams registerParams) {
		log.info("[register] registerParams {}", registerParams);
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
		user.setRegisterDeviceCode(registerParams.getRegisterDeviceCode());// 注册设备码
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		user.setPhone(registerParams.getAccount());
		log.info("[register] insertGetId {}", user);
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
		if (!StringUtils.isEmpty(registerParams.getJpushRegId())) {
			userInfo.setJpushRegId(registerParams.getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 对密码进行加密并新增用户密码
		String passWord = MD5Util.generate(registerParams.getLoginPassWord());// 登陆密码
		String takePassWord = "";// 取款密码
		userPasswordService.insertPassword(userId, passWord, takePassWord);
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		log.info("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, registerParams.getHallId());
		log.info("[login] result {}", result);
		return result;
	}

	public UserDetail setHallIp(UserDetail result, Long hallId) {
		boolean isOk = false;
		try {
			if ("localhost".equals(HallUrlConstant.getHALL_URL())) {
				result.setHallIp("192.168.0.182");
				result.setHallPort(8500);
				return result;
			}
			String url = HallUrlConstant.getHALL_URL() + hallId;
			String body = HttpRequest.get(url).timeout(3000).execute().body();
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
//			log.info("大厅ip 缓存的map {}", SysConstant.hallAddressMap);
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
		user.setUserType(SysConstant.USER);
		UserHierarchyEntity hierarchy = userHierarchyService.getDefaultHierarchy();
		Long hierarchyId = hierarchy == null ? 0l : hierarchy.getId();// 获取默认层级ID
		user.setHierarchyId(hierarchyId);// 将用户加入默认层级
		// 新增用户信息,并获取到用户ID
		user.setCreateTime(new Date());
		user.setAgentEnable(SysConstant.ENABLE_TRUE);// 默认为正常代理
		user.setUserType(SysConstant.USER);// 用户类型(默认为普通用户)
		user.setHierarchyId(hierarchyId);// 用户所属层级
		user.setCoin(0L);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSex(MathUtil.getRandomSex());
		user.setCommission(BigDecimal.ZERO);
		user.setMoney(BigDecimal.ZERO);
		user.setPortrait(MathUtil.getRandomPortrait(user.getSex()));// 用户头像
		user.setForbiddenEnable(false);
		user.setFrozenEnable(false);
		log.info("[thirdPartyRegister] user {}", user);
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
		if (!StringUtils.isEmpty(oAuth2Params.getJpushRegId())) {
			userInfo.setJpushRegId(oAuth2Params.getJpushRegId());
		}
		userInfoService.insert(userInfo);// 新增用户信息
		// 生成一个新的token，并加入缓存中
		String token = JwtUtil.createJWT(userId.toString());// 登录时生成一个token
		user.setToken(token);
		log.info("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		String officialUrl = domainManagementService.getOfficalDomain();
		UserDetail result = new UserDetail(user, userInfo, officialUrl, ConfigConstant.SUPPORT_URL, null);
		setHallIp(result, oAuth2Params.getHallId());
		log.info("[login] result {}", result);
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
				log.info("[register] 上级代理状态是否正常 {}", superiorsEntity.getAgentEnable());
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
		this.baseMapper.updateUserWalletByUserId(updateUser);

	}

	/**
	 * 登陆
	 */
	@Override
	public String login(UserEntity user, LoginEntity loginEntity, String ip) {
		log.info("[login] user {} ,loginEntity {}, ip {}", user, loginEntity, ip);
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
		log.info("[login] token {} ", token);
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
		log.info("[login] entity {}", entity);
		return token;
	}

	/**
	 * app登陆
	 */
	@Override
	public UserDetail appLogin(UserEntity user, LoginParams loginParams, String Ip, boolean VerifyPassword) {
		log.info("[login] user {} ,loginParams {},ip {}", user, loginParams, Ip);
		// String account = loginParams.getAccount();
		String passWord = loginParams.getLoginPassWord();
		// 根据账号获取人员信息
		UserEntity userEntity = new UserEntity();
		Long userId = user.getId();
		userEntity.setId(userId);
		if (VerifyPassword) {// 验证密码
			// 验证输入密码错误是否超出默认次数
			int i = 0;
			if (localContentCache.get(SysConstant.PASSWORD_ERROR + userId) != null) {
				i = (int) localContentCache.get(SysConstant.PASSWORD_ERROR + userId);// 输入错误密码次数
				if (i >= SysConstant.PASSWORD_ERROR_NUM) {
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
		log.info("[login] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(loginParams.getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(loginParams.getJpushRegId()));
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
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(loginParams.getHallId());
		userLogin.setEdition(loginParams.getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(loginParams.getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(loginParams.getDeviceType());
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
		setHallIp(result, loginParams.getHallId());
		log.info("[login] result {}", result);
		return result;
	}

	/**
	 * app第三方登陆
	 */
	@Override
	public UserDetail appThirdPartyLogin(UserEntity user, OAuth2Params loginParams, String ip) {
		log.info("[appThirdPartyLogin] user {} ,loginParams {},ip {}", user, loginParams, ip);
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
		log.info("[appThirdPartyLogin] token {} ", token);
		localContentCache.putToken(token, user);
		localContentCache.put(String.valueOf(user.getId()), token);
		// 用户登入更新用户推送id
		if (!StringUtils.isEmpty(loginParams.getJpushRegId())) {
			userInfoService.updateByUserId(
					new UserInfoEntity().setUserId(user.getId()).setJpushRegId(loginParams.getJpushRegId()));
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
		try {
			if (ipData != null) {
				userLogin.setNation(ipData[0]);
				userLogin.setIpAddress(ipData[0] + ipData[1] + ipData[2]);
			}
		} catch (Exception e1) {
			log.error("[ip解析返回错误]", e1);
		}
		userLogin.setHallId(loginParams.getHallId());
		userLogin.setEdition(loginParams.getEdition());
		userLogin.setToken(token);
		userLogin.setDeviceCode(loginParams.getDeviceCode());
		userLogin.setLoginStatus(SysConstant.SUCCESS);
		userLogin.setDeviceType(loginParams.getDeviceType());
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
		setHallIp(result, loginParams.getHallId());
		log.info("[appThirdPartyLogin] result {}", result);
		return result;
	}

	/**
	 * 新增用户并返回ID
	 */
	@Override
	public UserEntity insertGetId(UserEntity userEntity) {
		log.info("[insertGetId] userEntity {}", userEntity);
		baseMapper.insertGetId(userEntity);
		return userEntity;
	}

	// 通过推荐码获取人员ID
	public List<UserRecommendationEntity> validateRecommender(String recommender) {
		log.info("[validateRecommender] recommender {}", recommender);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(recommender);
		List<UserRecommendationEntity> list = userRecommendationService
				.selectList(new EntityWrapper<UserRecommendationEntity>(entity));
		log.info("[validateRecommender] list {}", list);
		return list;
	}

	// 判断推荐码是否存在(大于0存在)
	public int validateRecommenderRepeat(String recommender) {
		log.info("[validateRecommenderRepeat] recommender {}", recommender);
		UserRecommendationEntity entity = new UserRecommendationEntity();
		entity.setRecommendationCode(recommender);
		int num = userRecommendationService.selectCount(new EntityWrapper<UserRecommendationEntity>(entity));
		log.info("[validateRecommenderRepeat] num {}", num);
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
		log.info("[validateAccountRepeat] account {}", account);
		Boolean result = false;
		UserEntity userEntity = new UserEntity();
		userEntity.setAccount(account);
		int num = baseMapper.selectCount(new EntityWrapper<UserEntity>(userEntity));
		if (num > 0) {
			return result = true;
		}
		log.info("[validateAccountRepeat] result {}", result);
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
		log.info("[validatePhoneRepeat] account {}", account);
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
		log.info("[validateAccountRepeat] result {}", result);
		return result;
	}

	/**
	 * 退出登陆
	 */
	@Override
	public void logout(String token) {
		log.info("[logout] token {}", token);
		localContentCache.remove(token);// 清除缓存，退出登陆
	}

	/**
	 * 根据token获取人员基本信息及账户余额
	 */
	@Override
	public UserEntity selectUserForToken(String token) {
		Long id = Long.valueOf(JwtUtil.getUserId(token));
		log.info("[selectUserForToken] token {} id {} ", token, id);
		UserEntity entity = baseMapper.selectById(id);
		UserPasswordEntity passwordEntity = userPasswordService
				.selectOne(new EntityWrapper<UserPasswordEntity>(new UserPasswordEntity().setUserId(id)));
		if (null == passwordEntity || StringUtils.isEmpty(passwordEntity.getBankPassword())) {
			entity.setTakemoney(false);
		} else {
			entity.setTakemoney(true);
		}
		log.info("[selectUserForToken] entity {}", entity);
		return entity;
	}

	/**
	 * 提供前缀自动生成账号 不重复
	 */
	public String autogenerationAccount(String name) {
		log.info("[autogenerationAccount] name {}", name);
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
		log.info("[autogenerationAccount] account {}", account);
		return account;
	}

	@Override
	public Integer deleteBankInfo(Long userId) {
		log.info("[deleteBankInfo] userId {}", userId);
		return this.baseMapper.deleteBankInfo(userId);
	}

	@Override
	public void updateUserBaseInfo(UserEntity userEntity) {
		log.info("[updateUserBaseInfo] userEntity {}", userEntity);
		this.baseMapper.updateUserBaseInfo(userEntity);
	}

	@Override
	public Page<Agent> getAgentList(UserEntity user, PageParam pageParam) {
		log.info("[getAgentList] user {} ,pageParam {}", user, pageParam);
		Page<Agent> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getAgentList(page, user));
	}

	/**
	 * 根据用户Id修改用户金币，余额，佣金
	 */
	@Override
	public void updateUserWalletByUserId(UserEntity user) {
		log.info("[updateUserWalletByUserId] user {} ", user);
		baseMapper.updateUserWalletByUserId(user);
	}

	@Override
	public Page<Subordinate> getAgentSubordinateList(Long userId, String account, PageParam pageParam) {
		log.info("[getAgentSubordinateList] userId {} ,account {} ,pageParam {}", userId, account, pageParam);
		Page<Subordinate> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getAgentSubordinateList(page, account, userId));
	}

	@Override
	public List<UserEntity> findUserListByIdList(Collection<Long> idList) {
		log.info("[findUserListByIdList] idList {} ", idList);
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
		log.info("[getUserPasswordInfo] id {} ", id);
		return this.baseMapper.getUserPasswordInfo(id);
	}

	@Override
	public UserAccountResult getUserBalance(String token) {
		log.info("[getUserBalance] token {} ", token);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		UserEntity entity = baseMapper.selectById(userId);
		UserAccountResult result = new UserAccountResult();
		result.setCoin(entity.getCoin());
		result.setId(entity.getId());
		result.setMoney(entity.getMoney());
		log.info("[getUserBalance] result {} ", result);
		return result;
	}

	/**
	 * 根据修改用户token 新增一条登陆记录 获取系统设置试玩账号金币====刷新金币 (用于试玩账号登陆) xiaoliu
	 */
	public void updateUserAndLogin(String ip, String token, String deviceType) {
		log.info("[updateUserAndLogin] ip {}, token {} ", ip, token);
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
		log.info("[changer]  额度转换 user {} money {} coin {}", user, money, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		userUpdateParam.setMoney(BigDecimal.ZERO.subtract(money));
		baseMapper.updateUserWalletByUserId(userUpdateParam);
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
		UserInfoEntity info = userInfoService
				.selectOne(new EntityWrapper<UserInfoEntity>(null).eq("user_id", user.getId()));
		if (info == null) {// 如果用户没有基本信息就新增
			userInfoService.insert(userInfo);
		} else {// 修改
			if (!StringUtils.isEmpty(info.getBankCard()) || !StringUtils.isEmpty(info.getBankName())
					|| !StringUtils.isEmpty(info.getBankAccountName())
					|| !StringUtils.isEmpty(info.getAlipayAccount())) {
				throw new RRException(ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getErrMsg(),
						ErrorCode.UserErrorCode.USER_INFO_ISNOTNULL_ERRO.getCode());
			}
			// 校验银行卡是否已经被绑定
			UserInfoEntity userEntity = userInfoService.selectOne(
					new EntityWrapper<UserInfoEntity>(new UserInfoEntity().setBankCard(entity.getBankCard())));
			if (null != userEntity) {
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
		log.info("[integrate] 资金归集  user {} money {} coin {}", user, money, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(0 - coin);
		userUpdateParam.setMoney(money);
		baseMapper.updateUserWalletByUserId(userUpdateParam);
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
		baseMapper.updateUserWalletByUserId(userUpdateParam);
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
		log.info("[exchangeCommission] 佣金转金币  user {} money {} coin {}", user, commission, coin);
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCommission(BigDecimal.ZERO.subtract(commission));
		userUpdateParam.setCoin(coin);
		baseMapper.updateUserWalletByUserId(userUpdateParam);
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
			//将游戏服id和在玩游戏信息id改为0
			UserEntity entity = new UserEntity();
        	entity.setId(user.getId());
        	entity.setGameInfoId(0l);
        	entity.setGameServerId(0l);
        	entity.updateById();
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
		Integer updateNum = this.baseMapper.updateUserWalletByUserId(userUpdateParam);
		if (updateNum <= 0) {
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
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
				baseMapper.updateUserWalletByUserId(agentUpdateParam);
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
		log.info("[getUserInfoForH5] user {} ,userInfo {} ,recommendation {}", user, userInfo, recommendation);
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
		log.info("[getUserInfoForH5] result {}", result);
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
			baseMapper.updateUserWalletByUserId(userEntity);// 修改用户金币余额
			// =============================为用户新增交易记录============================
			transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
			transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
			transactionRecord.setDetailType(Constant.TransactionDetailType.USERINFO.getValue());
			transactionRecord.setUserId(user.getId());
			transactionRecord.setUserAccount(user.getAccount());
			transactionRecord.setAmount(new BigDecimal(coin / Constant.COIN_RATE));
			transactionRecord.setCoin(user.getCoin() + coin);
			userTransactionRecordService.insert(transactionRecord);
			orderCashExamineService.bindUserinfoGiftCashExamine(user, coin, userRebateEntity.getCodeMultiple());
			Date FAIL_TIME = DateUtil.nextWeek(); // 邮件失效时间
			Date EFFECT_TIME = new Date(); // 邮件生效时间
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity().setUserAccount(user.getAccount())
					.setContent(String.format(SUCCUSS_CONTENT, coin / Constant.CLIENT_COIN_RATE))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(EFFECT_TIME).setFailureTime(FAIL_TIME);
			messageManagementService.save(messageManagement);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setCoin(coin);
			pushMessage.setId(user.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[用户完善信息返利-推送消息] message {}", message);
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
			if (list.get(0).getAgentHierarchyId() != 0 && list.get(0).getAgentHierarchyId() != null) {// 判断代理等级是否为空
				// 获取用户相应代理等级奖励规则
				UserAgentHierarchyEntity userAgentHierarchy = userAgentHierarchyService
						.selectById(list.get(0).getAgentHierarchyId());

				if (userAgentHierarchy.getCoin() == null) {
					throw new RRException(ErrorCode.AgentErrorCode.AGENT_COIN_ERRO.getErrMsg(),
							ErrorCode.AgentErrorCode.AGENT_COIN_ERRO.getCode());
				}
				coin = userAgentHierarchy.getCoin();
			}
			userEntity.setId(user.getSuperiorsId());
			userEntity.setCoin(coin);
			baseMapper.updateUserWalletByUserId(userEntity);// 修改上级金币余额
			orderCashExamineService.bindUserinfoGiftCashExamine(superiorsUserEntity, coin);
			// =============================为上级新增交易记录========================
			transactionRecord.setType(SysConstant.TRANSACTION_REBATE);
			transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
			transactionRecord.setDetailType(SysConstant.TRANSACTION_DETAIL_RECOMMEND);
			transactionRecord.setUserId(superiorsUserEntity.getId());
			transactionRecord.setUserAccount(superiorsUserEntity.getAccount());
			transactionRecord.setAmount(new BigDecimal(coin / Constant.COIN_RATE));
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
		log.info("[APP获取试玩账号参数] ip {} ,hallId {}, deviceType {}", ip, hallId, deviceType);
		// 同一ip在规定时间内如果重复使用试玩，返回同一个账户
		List<UserEntity> ipList = baseMapper.selectList(
				new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL).eq("register_ip", ip));
		if (!CollectionUtils.isEmpty(ipList)) {
			for (UserEntity entity : ipList) {
				if (StringUtils.isEmpty(entity.getToken())) {
					continue;
				}
				log.info("[appTrialAccount] 该IP是否已有试玩 {}", localContentCache.get(entity.getToken()) != null);
				if (localContentCache.get(entity.getToken()) != null) {
					String officialUrl = domainManagementService.getOfficalDomain();
					token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
					entity.setToken(token);
					UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
					updateUserAndLogin(ip, token, deviceType);
					localContentCache.put(token, entity);// 将用户加入缓存
					setHallIp(result, hallId);
					log.info("[appTrialAccount] result {}", result);
					return result;
				}

			}
		}
		List<UserEntity> list = baseMapper
				.selectList(new EntityWrapper<UserEntity>(null).eq("user_type", SysConstant.TRIAL));
		if (!CollectionUtils.isEmpty(list)) {
			for (UserEntity entity : list) {
				log.info("[appTrialAccount] entity {}", entity);
				if (StringUtils.isEmpty(entity.getToken())) {// 如果这个试玩账号没登陆过
					token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
					localContentCache.put(token, entity);// 将用户加入缓存
					updateUserAndLogin(ip, token, deviceType);
					entity.setToken(token);
					String officialUrl = domainManagementService.getOfficalDomain();
					UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
					setHallIp(result, hallId);
					log.info("[appTrialAccount] result {}", result);
					return result;
				} else {// 如果登陆过，查看该用户是否在线
					log.info("[appTrialAccount] 是否是登陆状态 {}", localContentCache.get(entity.getToken()) == null);
					if (localContentCache.get(entity.getToken()) == null) {// 查看该用户是否在线
						token = JwtUtil.createJWT(entity.getId().toString());// 登录时生成一个token
						localContentCache.put(token, entity);// 将用户加入缓存
						updateUserAndLogin(ip, token, deviceType);
						String officialUrl = domainManagementService.getOfficalDomain();
						entity.setToken(token);
						UserDetail result = new UserDetail(entity, null, officialUrl, ConfigConstant.SUPPORT_URL, null);
						setHallIp(result, hallId);
						log.info("[appTrialAccount] result {}", result);
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
	public void updateUserCoin(UserEntity user, Long coin, int type, int detailType) {
		UserEntity userUpdateParam = new UserEntity();
		userUpdateParam.setId(user.getId());
		userUpdateParam.setCoin(coin);
		baseMapper.updateUserWalletByUserId(userUpdateParam);
		// 保存交易记录
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(type);
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setOrderNo(System.nanoTime() + InviteCode.create());
		transactionRecord.setMoney(user.getMoney());
		transactionRecord.setAmount(
				new BigDecimal(coin).divide(new BigDecimal(Constant.COIN_RATE), 2, BigDecimal.ROUND_HALF_UP));
		transactionRecord.setCoin(user.getCoin() + coin);
		transactionRecord.setDetailType(detailType);
		transactionRecord.setCommission(user.getCommission());
		userTransactionRecordService.insert(transactionRecord);
	}

	@Override
	public List<Map<String, Object>> statisticsRiskNum() {
		return  this.baseMapper.statisticsRiskNum();
	}
	/**
	 * 获取用户风控人数总计
	 * @return
	 */
	@Override
	public Long statisticsRiskTotalNum() {
		return  this.baseMapper.statisticsRiskTotalNum();
	};

	@Override
	public Page<TeamAgent> getAgentTeamList(UserEntity user, PageParam pageParam) {
		log.info("[getAgentTeamList] user {} ,pageParam {}", user, pageParam);
		Page<TeamAgent> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.getAgentTeamList(page, user));
	}

	/**
	 * 根据账号查询
	 */
	@Override
	public UserParamTwo queryByAccount(String account) {
		return baseMapper.queryByAccount(account);
	}
	/**
	 * 通过UserEntity,以及代理商id
	 *     查询当前代理商下的所有会员信息
	 * @param user
	 * @return
	 */
	@Override
	public List<UserEntity> findProxyUser(Page<UserEntity> page, UserEntity user) {
		return this.baseMapper.findProxyUser(page, user);
	}

	@Override
	public void tickUserChangeGame(UserEntity user) {
		UserEntity entity = new UserEntity();
    	entity.setId(user.getId());
    	entity.setGameInfoId(0l);
    	entity.setGameServerId(0l);
    	entity.updateById();
	}

	@Override
	public UserParamThree findSingleUserPage(UserParamFour user) {
		return this.baseMapper.findSingleUserPage(user);
	}

	@Override
	public List<UserLoginEntity> logonLog(UserParamFour user) {
		return this.baseMapper.logonLog(user);
	}

	@Override
	public String selectAccountById(Long id) {
		return this.baseMapper.selectAccountById(id);
	}

	@Override
	public void batchUpdateHierarchy(Long hierarchyId,Long id) {
		this.baseMapper.batchUpdateHierarchy(hierarchyId,id);
	}

	@Override
	@CacheEvict(value = EhCacheName.GM_USER_CACHE, allEntries = true)
	@Transactional
	public void batchMarkTestUser(Long[] ids) {
		for(Long id :ids) {
			List<OrderRechargeEntity> rechargeList = 	orderRechargeDao.selectList(new EntityWrapper<OrderRechargeEntity>(
					new OrderRechargeEntity().setUserId(id)
					//.setStatus(Constant.OrderStatus.COMPLETE.getValue())
			));
			log.info("rechargeList {}",rechargeList.size());
			if(rechargeList!=null && !rechargeList.isEmpty()) {
				throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_GM_ERRO.getErrMsg(),
						ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNOTNULL_GM_ERRO.getCode());
			}
			this.baseMapper.batchMarkTestUser(id);
		}




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
	public String getSuperiorsName(Long userId) {
		return this.baseMapper.getSuperiorsName(userId);
	}
}
