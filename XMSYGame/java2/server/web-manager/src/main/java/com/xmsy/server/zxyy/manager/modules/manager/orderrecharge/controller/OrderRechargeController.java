package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.bean.message.FortuneMessage;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.annotation.RechargeOrderConfirmRepeat;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.constant.SysConstant;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.entity.MessageUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.messageuser.service.MessageUserService;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.mq.MqClient;
import com.xmsy.server.zxyy.manager.utils.DateUtils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 充值订单表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
@Slf4j
@RestController
@RequestMapping("orderrecharge/orderrecharge")
public class OrderRechargeController extends AbstractController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderRechargeService orderRechargeService;
	@Autowired
	private MessageUserService messageUserService;
	@Autowired
	private PushService pushService;
	@Autowired
	private RechargeChannelService rechargeChannelService;
	@Autowired
	private MqClient mqClient;

	public static final String TITLE = "充值结果通知";
	public static final String FAIL_CONTENT = "充值订单: %s , 金额: %s, 被取消, 请联系管理员";
	public static final String SUCCUSS_CONTENT = "充值订单: %s, 金额 : %s, 已被确认, 请注意查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户

	/**
	 * 列表
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/list")
	@RequiresPermissions("orderrecharge:orderrecharge:list")
	public R list(OrderRechargeEntity orderrecharge, PageParam pageParam) throws ParseException {
		if (StringUtils.isEmpty(orderrecharge.getTerm())) {
			// 如果没有指定排序，默认时间倒序
			orderrecharge.setTerm("id");
			orderrecharge.setEnable(false);
		}
		Page<OrderRechargeEntity> result = new Page<OrderRechargeEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<OrderRechargeEntity> entityWrapper = new EntityWrapper<OrderRechargeEntity>(orderrecharge)
//				.ge(!StringUtils.isEmpty(orderrecharge.getQueryTime()), "deposit_date", // 存款时间查询
//						orderrecharge.getQueryTime() + SysConstant.START_TIME)
//				.le(!StringUtils.isEmpty(orderrecharge.getQueryTime()), "deposit_date",
//						orderrecharge.getQueryTime() + SysConstant.END_TIME)
				.ge(!StringUtils.isEmpty(orderrecharge.getStartTime()), "deposit_date", // 存款时间查询
						orderrecharge.getStartTime())
						.le(!StringUtils.isEmpty(orderrecharge.getEndTime()), "deposit_date",
								orderrecharge.getEndTime())
				.ge(null != orderrecharge.getAmountMin() && 0 < orderrecharge.getAmountMin(), "amount", // 存款金额查询
						orderrecharge.getAmountMin())
				.le(null != orderrecharge.getAmountMax() && 0 < orderrecharge.getAmountMax(), "amount",
						orderrecharge.getAmountMax())
				.ge(null != orderrecharge.getDiscountAmountMin(), "discount_amount", // 优惠金额查询
						orderrecharge.getDiscountAmountMin())
				.le(null != orderrecharge.getDiscountAmountMax(), "discount_amount",
						orderrecharge.getDiscountAmountMax())
				.like(!StringUtils.isEmpty(orderrecharge.getBankAccount()), "income_bank_account",
						orderrecharge.getBankAccount())
				.orderBy(orderrecharge.getTerm(), orderrecharge.getEnable());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		orderrecharge.selectPage(result, entityWrapper);
		List<OrderRechargeEntity> list = result.getRecords();
		if (orderrecharge.getRechargeType() != null
				&& orderrecharge.getRechargeType() == Constant.RechargeType.THIRD.getValue()
				&& !CollectionUtil.isEmpty(list)) {
			for (OrderRechargeEntity entity : list) {
//				if (!StringUtils.isEmpty(entity.getRechargePlatform())) {
//					// Long.valueOf(entity.getRechargePlatform());
//					PayConfigEntity payentity = payConfigService.selectById(entity.getRechargePlatform());
//					if (payentity != null && !StringUtils.isEmpty(payentity.getName())) {
//						entity.setRechargePlatformName(payentity.getName());
//					}
//				}
				if (entity.getRechargeChannel() != null) {
					RechargeChannelEntity rechargeChannel = rechargeChannelService
							.selectById(entity.getRechargeChannel());
					if (rechargeChannel != null && !StringUtils.isEmpty(rechargeChannel.getName())) {
						entity.setRechargeChannelName(rechargeChannel.getName());
					}
				}

			}
		}
		// 获取昨日充值总额
		int yesterdayAmount = orderRechargeService.sumAmountForDate(DateUtils.format(DateUtils.getYesterday()),
				orderrecharge.getRechargeType());
		// 获取昨日充值总额
		int todayAmount = orderRechargeService.sumAmountForDate(DateUtils.format(DateUtils.getToday()),
				orderrecharge.getRechargeType());
		return R.ok()
				.put("page",
						new PageUtils(list, result.getTotal(), result.getSize(), result.getCurrent(),
								result.getPages()))
				.put("yesterdayAmount", yesterdayAmount).put("todayAmount", todayAmount);
	}
	
	/**
	 * 银行卡存款数量
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/totalNumber")
	@RequiresPermissions("orderrecharge:orderrecharge:totalNumber")
	public R totalNumber() {
		OrderRechargeEntity entity = new OrderRechargeEntity();
		entity.setRechargeType(Constant.RechargeType.BANK.getValue());
		entity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		int num = orderRechargeService.selectCount(new EntityWrapper<OrderRechargeEntity>(entity));
		return R.ok().put("num", num);
	}

	/**
	 * 线下充值订单确认
	 * 
	 * @throws Exception
	 */
	@SysLog("线下充值订单确认")
	@RechargeOrderConfirmRepeat("线下充值订单重复确认校验")
	@RequestMapping("/confirmed/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:confirmed")
	public R confirmed(@PathVariable("id") Long id) throws Exception {
		OrderRechargeEntity orderRecharge = orderRechargeService.selectById(id);
		if (null == orderRecharge) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderRecharge.getStatus()) {
			throw new RRException("请先锁定订单，再操作");
		}
		if (!orderRecharge.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderRecharge.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderRecharge.setRechargeTime(new Date());
		if (Constant.RechargeType.THIRD.getValue() != orderRecharge.getRechargeType()) {
			orderRecharge.setSysUserId(getUser().getUserId());
			orderRecharge.setSysUserAccount(getUser().getUsername());
		}
		// 添加代理商返佣
		RechargeRebateMessage rechargeRebateMessage = orderRechargeService.orderRechargeConfirm(orderRecharge);
		if(rechargeRebateMessage != null) {
			mqClient.userVipPush(rechargeRebateMessage);
			if(rechargeRebateMessage.getAgentId() != null) {
				mqClient.agentRebatePush(rechargeRebateMessage);
			}
		}
		//天降财神充值得红包(2.线下银行卡充值)
		FortuneMessage fortuneMessage=new FortuneMessage();
		fortuneMessage.setUserId(orderRecharge.getUserId());
		fortuneMessage.setRechargeAmount(orderRecharge.getAmount());
		fortuneMessage.setEventCode(SysConstant.FORTUNE_EVENT_RECHARGE);//充值事件 
		mqClient.fortunePush(fortuneMessage);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
//			MessageManagementEntity messageManagement = new MessageManagementEntity()
//					.setUserAccount(orderRecharge.getUserAccount())
//					.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
//					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
//					.setEffectTime(effectTime).setFailureTime(failTime);
//		messageManagementService.save(messageManagement);
			MessageUserEntity messageUserEntity = new MessageUserEntity();
			messageUserEntity
					.setUserAccount(orderRecharge.getUserAccount()).setUserId(orderRecharge.getUserId())
			    .setStatus(false).setDeleteMessage(false).setReceive(true)
				.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
					.setContentType(CONTENT_TYPE).setTitle(TITLE).setMessageId(Long.valueOf(0))
				.setEffectTime(effectTime).setFailureTime(failTime);
			messageUserService.insert(messageUserEntity);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(orderRecharge.getUserId());
			pushMessage.setCoin(new BigDecimal(orderRecharge.getAmount())
					.add(orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO
							: orderRecharge.getDiscountAmount())
					.multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
			UserEntity entity = userService.selectById(orderRecharge.getUserId());
			pushMessage.setUnreadNum(1);
			pushMessage.setId(entity.getId());
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[线下充值订单确认->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[线下充值订单确认,邮件和消息处理] Exception", e);
		}
		return R.ok();
	}
	
	@SysLog("第三方充值订单确认")
	@RechargeOrderConfirmRepeat("第三方订单重复确认校验")
	@RequestMapping("/confirmed-third/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:confirmed")
	public R confirmedThird(@PathVariable("id") Long id) throws Exception {
		OrderRechargeEntity orderRecharge = orderRechargeService.selectById(id);
		if (null == orderRecharge) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.UNCONFIRMED.getValue() != orderRecharge.getStatus()) {
			throw new RRException("订单已经确认");
		}
		orderRecharge.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderRecharge.setRechargeTime(new Date());
		orderRecharge.setSysUserId(getUser().getUserId());
		orderRecharge.setSysUserAccount(getUser().getUsername());
		if (Constant.RechargeType.THIRD.getValue() != orderRecharge.getRechargeType()) {
			orderRecharge.setSysUserId(getUser().getUserId());
			orderRecharge.setSysUserAccount(getUser().getUsername());
		}
		// 添加代理商返佣
		RechargeRebateMessage rechargeRebateMessage = orderRechargeService.orderRechargeConfirm(orderRecharge);
		if(rechargeRebateMessage != null) {
			mqClient.userVipPush(rechargeRebateMessage);
			if(rechargeRebateMessage.getAgentId() != null) {
				mqClient.agentRebatePush(rechargeRebateMessage);
			}
		}
		//天降财神充值得红包(3.第三方充值)
		FortuneMessage fortuneMessage = new FortuneMessage();
		fortuneMessage.setUserId(orderRecharge.getUserId());
		fortuneMessage.setRechargeAmount(orderRecharge.getAmount());
		fortuneMessage.setEventCode(SysConstant.FORTUNE_EVENT_RECHARGE);// 充值事件
		mqClient.fortunePush(fortuneMessage);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
//			MessageManagementEntity messageManagement = new MessageManagementEntity()
//					.setUserAccount(orderRecharge.getUserAccount())
//					.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
//					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
//					.setEffectTime(effectTime).setFailureTime(failTime);
//			messageManagementService.save(messageManagement);
			MessageUserEntity messageUserEntity = new MessageUserEntity();
			messageUserEntity
					.setUserAccount(orderRecharge.getUserAccount()).setUserId(orderRecharge.getUserId())
					.setStatus(false).setDeleteMessage(false).setReceive(true)
					.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
					.setContentType(CONTENT_TYPE).setTitle(TITLE).setMessageId(Long.valueOf(0))
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageUserService.insert(messageUserEntity);
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(orderRecharge.getUserId());
			pushMessage.setCoin(new BigDecimal(orderRecharge.getAmount())
					.add(orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO
							: orderRecharge.getDiscountAmount())
					.multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
			UserEntity entity = userService.selectById(orderRecharge.getUserId());
			pushMessage.setUnreadNum(1);
			pushMessage.setId(entity.getId());
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[第三方充值订单确认->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[第三方充值订单确认,邮件和消息处理] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 线下充值订单取消
	 */
	@SysLog("线下充值订单取消")
	@RechargeOrderConfirmRepeat("线下充值订单重复取消校验")
	@RequestMapping("/canceled/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:canceled")
	public R canceled(@PathVariable("id") Long id) {
		OrderRechargeEntity orderRecharge = orderRechargeService.selectById(id);
		if (null == orderRecharge) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderRecharge.getStatus()) {
			throw new RRException("请先锁定订单，再操作");
		}
		if (!orderRecharge.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderRecharge.setStatus(Constant.OrderStatus.CANCEL.getValue());
		orderRecharge.setRechargeTime(new Date());
		orderRecharge.setSysUserId(getUser().getUserId());
		orderRecharge.setSysUserAccount(getUser().getUsername());
		orderRechargeService.updateById(orderRecharge);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
//			MessageManagementEntity messageManagement = new MessageManagementEntity()
//					.setUserAccount(orderRecharge.getUserAccount())
//					.setContent(String.format(FAIL_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
//					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
//					.setEffectTime(effectTime).setFailureTime(failTime);
//			messageManagementService.save(messageManagement);
			MessageUserEntity messageUserEntity = new MessageUserEntity();
			messageUserEntity
					.setUserAccount(orderRecharge.getUserAccount()).setUserId(orderRecharge.getUserId())
					.setStatus(false).setDeleteMessage(false).setReceive(true)
					.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
					.setContentType(CONTENT_TYPE).setTitle(TITLE).setMessageId(Long.valueOf(0))
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageUserService.insert(messageUserEntity);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderRecharge.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[线下充值订单取消->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[线下充值订单取消,邮件和消息处理] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("orderrecharge:orderrecharge:info")
	public R info(@PathVariable("id") Long id) {
		OrderRechargeEntity orderRecharge = orderRechargeService.selectById(id);
		return R.ok().put("orderrecharge", orderRecharge);
	}
	
	/**
	 * 根据会员id统计总的取款金额 取款次数
	 */
	@RequestMapping("/queryRechargeNumByUserId")
	@RequiresPermissions("orderrecharge:orderrecharge:info")
	public R queryRechargeNumByUserId(Long userId) {
		OrderRechargeStatisticsTwo en = orderRechargeService.orderRechargeNum(userId);
		return R.ok().put("data", en);
	}
	
	/**
	 * 根据会员id统计总的取款金额 取款次数
	 */
	@RequestMapping("/getLastRechargeByUserId")
	@RequiresPermissions("orderrecharge:orderrecharge:info")
	public R getLastRechargeByUserId(Long userId) {
		OrderRechargeStatisticsThree en = orderRechargeService.getLastRechargeAmountByUserId(userId);
		return R.ok().put("data", en);
	}
	
	/**
	 * 线下充值订单锁定
	 * 
	 * @throws Exception
	 */
	@SysLog("线下充值订单锁定")
	@RechargeOrderConfirmRepeat("线下充值订单重复锁定校验")
	@RequestMapping("/locking/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:locking")
	public R locking(@PathVariable("id") Long id) throws Exception {
		OrderRechargeEntity orderRechargeEntity = orderRechargeService.selectById(id);
		if (null == orderRechargeEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.UNCONFIRMED.getValue() != orderRechargeEntity.getStatus()) {
			throw new RRException("订单已被他人锁定");
		}
		orderRechargeEntity.setSysUserId(getUser().getUserId());
		orderRechargeEntity.setSysUserAccount(getUser().getUsername());
		orderRechargeEntity.setStatus(Constant.OrderStatus.LOCKING.getValue());
		orderRechargeService.updateById(orderRechargeEntity);
		return R.ok();
	}

	/**
	 * 线下充值订单解锁
	 * 
	 * @throws Exception
	 */
	@SysLog("线下充值订单解锁")
	@RechargeOrderConfirmRepeat("线下充值订单重复解除锁定校验")
	@RequestMapping("/emancipateLocking/{id}")
	@RequiresPermissions("orderbankrecharge:orderbankrecharge:emancipateLocking")
	public R emancipateLocking(@PathVariable("id") Long id) throws Exception {
		OrderRechargeEntity orderRechargeEntity = orderRechargeService.selectById(id);
		if (null == orderRechargeEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderRechargeEntity.getStatus()) {
			throw new RRException("请先锁定订单，再操作");
		}
		orderRechargeEntity.setSysUserId(null);
		orderRechargeEntity.setSysUserAccount(null);
		orderRechargeEntity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		boolean result = orderRechargeService.updateAll(orderRechargeEntity);
		if (!result) {
			throw new RRException("订单解除锁定失败");
		}
		return R.ok();
	}

}
