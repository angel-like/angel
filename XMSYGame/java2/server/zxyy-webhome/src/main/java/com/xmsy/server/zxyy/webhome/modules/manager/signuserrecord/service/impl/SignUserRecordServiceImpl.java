package com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messageuser.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UpdateCoinParam;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.entity.SignRewardContinuousEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuous.service.SignRewardContinuousService;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.dao.SignRewardContinuousEveryDayDao;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.dao.SignUserRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.entity.SignUserRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signuserrecord.service.SignUserRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("signUserRecordService")
public class SignUserRecordServiceImpl extends ServiceImpl<SignUserRecordDao, SignUserRecordEntity>
		implements SignUserRecordService {
	@Autowired
	private SignRewardContinuousEveryDayDao signRewardContinuousEveryDayDao;
	@Autowired
	private UserTransactionRecordService userTransactionRecordService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private UserService userService;
	@Autowired
	private SignRewardContinuousService signRewardContinuousService;
	@Autowired
	private  MessageUserService messageUserService;
	@Autowired
	private PushService pushService;

	public static final String TITLE = "签到结果通知";
	public static final String SUCCUSS_CONTENT = "签到奖励: %s , 金币: %s, 已到账";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户

	// 用户签到
	@Override
	public JSONObject insertRecord(Long userId) throws Exception {
		log.info("[insertRecord===》用户签到] userId {}", userId);
		List<SignRewardContinuousEveryDayEntity> list = signRewardContinuousEveryDayDao
				.selectList(new EntityWrapper<SignRewardContinuousEveryDayEntity>().orderBy("id"));
		// 累计奖励列表
		List<SignRewardContinuousEntity> continuousList = signRewardContinuousService
				.selectList(new EntityWrapper<SignRewardContinuousEntity>(new SignRewardContinuousEntity()));
		if (CollectionUtil.isEmpty(list) || list.size() < 7) {
			log.info("[insertRecord===》用户签到] 配置数量小于7 {}", list.size());
			throw new RRException(ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getErrMsg(),
					ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getCode());
		}
		int i = DateUtils.dayOfWeek(DateUtils.getToday());// 当前星期几
		int continueSignDay = 1;// 累计登录天数，今天登录是一天，所以从1开始
		Long dayId = list.get(i - 1).getId();// 获取今天的盈利ID
		Long reward = list.get(i - 1).getReward() == null ? 0L : list.get(i - 1).getReward();
		log.info("[insertRecord===》用户签到] dayId {}, reward {}", dayId, reward);
		// 获取昨日签到时总次数
		SignUserRecordEntity record = this
				.selectOne(new EntityWrapper<SignUserRecordEntity>(new SignUserRecordEntity().setUserId(userId))
						.eq("sign_date", DateUtils.format(DateUtils.getYesterday())));
		if (null != record && null != record.getContinuousNum()) {
			continueSignDay += record.getContinuousNum();
		}
		// 通过累计天数，来给与对应累计奖励
		if (!CollectionUtil.isEmpty(continuousList)) {
			for (SignRewardContinuousEntity entity : continuousList) {
				if (null != entity && null != entity.getDayNum() && continueSignDay == entity.getDayNum()) {
					log.info("[insertRecord===》用户签到] 额外奖励 {}", entity.getReward());
					reward += entity.getReward();// 累计天数达标后的奖励
				}
			}
		}
		log.info("[insertRecord===》用户签到] continueSignDay {}", continueSignDay);
		// 新增签到记录
		SignUserRecordEntity entity = new SignUserRecordEntity();
		entity.setUserId(userId);
		entity.setDayId(dayId);
		entity.setReward(reward);
		entity.setContinuousNum(continueSignDay);
		entity.setSignDate(DateUtils.getToday());
		this.insert(entity);
		// 修改用户金币
		UpdateCoinParam param = new UpdateCoinParam();
		param.setCoin(reward);
		param.setUserId(userId);
		userService.updateUserCoin(param);
		UserEntity user = userService.selectById(userId);
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		// 新增交易记录
		transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
		transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
		transactionRecord.setDetailType(Constant.TransactionDetailType.CHECKINREBATE.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setAmount(new BigDecimal(reward / Constant.COIN_RATE));
		transactionRecord.setCoin(user.getCoin());// 上面已经修改完了用户金币
		userTransactionRecordService.insert(transactionRecord);
		// 新增稽查记录
		orderCashExamineService.bindUserinfoGiftCashExamine(user, reward);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageUserEntity messageUserEntity = new MessageUserEntity().setUserAccount(user.getAccount()).setUserId(user.getId())
					.setContent(String.format(SUCCUSS_CONTENT, transactionRecord.getOrderNo(),
							MathUtil.getBigDecimal(reward).divide(MathUtil.getBigDecimal(Constant.CLIENT_COIN_RATE),2,BigDecimal.ROUND_HALF_UP) ))
					.setContentType(CONTENT_TYPE).setTitle(TITLE).setStatus(false)
					.setEffectTime(effectTime).setFailureTime(failTime);
	       messageUserService.save(messageUserEntity);
//			MessageManagementEntity messageManagement = new MessageManagementEntity().setUserAccount(user.getAccount())
//					.setContent(String.format(SUCCUSS_CONTENT, transactionRecord.getOrderNo(),
//						MathUtil.getBigDecimal(reward).divide(MathUtil.getBigDecimal(Constant.CLIENT_COIN_RATE),2,BigDecimal.ROUND_HALF_UP) ))
//					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
//					.setEffectTime(effectTime).setFailureTime(failTime);
//			messageManagementService.save(messageManagement);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(userId);
			pushMessage.setCoin(reward);
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[签到->签到成功-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[签到->签到失败] Exception", e);
		}
		JSONObject obj = new JSONObject();
		obj.put("dayId", dayId);
		obj.put("continueSignDay", continueSignDay);
		log.info("[insertRecord===》用户签到] obj {}", obj);
		return obj;
	}

	@Override
	public JSONObject insertRecordTwo(UserEntity users) throws Exception {
		log.info("[insertRecord===》用户签到] userId {}", users.getId());
		Integer nowsignDay = this.continuousNum(users.getId(), DateUtils.getDateAddDay(0));
		if (nowsignDay != null && nowsignDay > 0) {
			throw new RRException(ErrorCode.SignErrorCode.SIGN_ED.getErrMsg(),
					ErrorCode.SignErrorCode.SIGN_ED.getCode());
		}

		Date startDate = DateUtils.getWeekStartAndEnd(0)[0];
		Integer signDay = this.continuousNum(users.getId(), startDate);
		if (signDay == null) {
			signDay = 0;
		}
		signDay++;
		SignRewardContinuousEveryDayEntity dayreward = signRewardContinuousEveryDayDao.selectByVipId(signDay,
				users.getVipId());
		if (dayreward == null) {
			throw new RRException(ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getErrMsg(),
					ErrorCode.SignErrorCode.SIGN_CONFIG_ERRO.getCode());
		}
		int continueSignDay = 1;// 累计登录天数，今天登录是一天，所以从1开始
		// 获取昨日签到时总次数
		SignUserRecordEntity record = this
				.selectOne(new EntityWrapper<SignUserRecordEntity>(new SignUserRecordEntity().setUserId(users.getId()))
						.eq("sign_date", DateUtils.format(DateUtils.getYesterday())));
		if (null != record && null != record.getContinuousNum()) {
			continueSignDay += record.getContinuousNum();
		}
		// 新增签到记录
		SignUserRecordEntity entity = new SignUserRecordEntity();
		entity.setUserId(users.getId());
		entity.setUserAccount(users.getAccount());
		entity.setDayId(dayreward.getId());
		entity.setReward(dayreward.getReward());
		entity.setContinuousNum(continueSignDay);
		entity.setSignDate(DateUtils.getToday());
		this.insert(entity);
		// 修改用户金币
		UpdateCoinParam param = new UpdateCoinParam();
		param.setCoin(dayreward.getReward());
		param.setUserId(users.getId());
		userService.updateUserCoin(param);
		UserEntity user = userService.selectById(users.getId());
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		// 新增交易记录
		transactionRecord.setType(Constant.TransactionType.REBATE.getValue());
		transactionRecord.setOrderNo(OrderNoUtil.getOrderNo());
		transactionRecord.setDetailType(Constant.TransactionDetailType.CHECKINREBATE.getValue());
		transactionRecord.setUserId(user.getId());
		transactionRecord.setUserAccount(user.getAccount());
		transactionRecord.setAmount(new BigDecimal(dayreward.getReward()).
				divide((new BigDecimal(Constant.COIN_RATE)),
						2,BigDecimal.ROUND_HALF_UP));
		transactionRecord.setCoin(user.getCoin());// 上面已经修改完了用户金币
		userTransactionRecordService.insert(transactionRecord);
		// 新增稽查记录
		orderCashExamineService.bindUserinfoGiftCashExamine(user, dayreward.getReward());
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {

			// 邮件推送
//			MessageManagementEntity messageManagement = new MessageManagementEntity().setUserAccount(user.getAccount())
//					.setContent(String.format(SUCCUSS_CONTENT, transactionRecord.getOrderNo(),
//							MathUtil.getBigDecimal(dayreward.getReward()).divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP)))
//					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
//					.setEffectTime(effectTime).setFailureTime(failTime).setReadonly(true);
//			messageManagementService.save(messageManagement);
			MessageUserEntity messageUserEntity = new MessageUserEntity().setUserAccount(user.getAccount()).setUserId(user.getId())
					.setContent(String.format(SUCCUSS_CONTENT, transactionRecord.getOrderNo(),
							MathUtil.getBigDecimal(dayreward.getReward()).divide(MathUtil.getBigDecimal(Constant.DB_COIN_RATE),2,BigDecimal.ROUND_HALF_UP)))
					.setContentType(CONTENT_TYPE).setTitle(TITLE).setStatus(false).setMessageId(Long.valueOf(0))
					.setReceive(true)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageUserService.save(messageUserEntity);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(users.getId());
			pushMessage.setCoin(dayreward.getReward());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[签到->签到成功-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[签到->签到失败] Exception", e);
		}
		JSONObject obj = new JSONObject();
		obj.put("dayId", dayreward.getId());
		obj.put("continueSignDay", signDay);
		log.info("[insertRecord===》用户签到] obj {}", obj);
		return obj;
	}

	/**
	 * 获取签到次数
	 *
	 * @param userId
	 * @param signDate
	 * @return
	 */
	@Override
	public Integer continuousNum(Long userId, Date signDate) {
		return baseMapper.continuousNum(userId, signDate);
	}

	@Override
	public Integer todaySign(Long userId, String signDate) {
		return baseMapper.todaySign(userId, signDate);
	}

}
