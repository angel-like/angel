package com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.controller;

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
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.RechargeOrderConfirmRepeat;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.service.PayConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

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
	private MessageManagementService messageManagementService;
	@Autowired
	private PushService pushService;
	@Autowired
	private PayConfigService payConfigService;
	@Autowired
	private RechargeChannelService rechargeChannelService;

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
				.ge(!StringUtils.isEmpty(orderrecharge.getQueryTime()), "deposit_date", // 存款时间查询
						orderrecharge.getQueryTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(orderrecharge.getQueryTime()), "deposit_date",
						orderrecharge.getQueryTime() + SysConstant.END_TIME)
				.ge(!StringUtils.isEmpty(orderrecharge.getQuerythirdTime()), "create_time", // 存款时间查询
						orderrecharge.getQuerythirdTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(orderrecharge.getQuerythirdTime()), "create_time",
						orderrecharge.getQuerythirdTime() + SysConstant.END_TIME)
				.ge(null != orderrecharge.getAmountMin() && 0 < orderrecharge.getAmountMin(), "amount", // 存款金额查询
						orderrecharge.getAmountMin())
				.le(null != orderrecharge.getAmountMax() && 0 < orderrecharge.getAmountMax(), "amount",
						orderrecharge.getAmountMax())
				.ge(null != orderrecharge.getDiscountAmountMin(), "discount_amount", // 优惠金额查询
						orderrecharge.getDiscountAmountMin())
				.le(null != orderrecharge.getDiscountAmountMax(), "discount_amount",
						orderrecharge.getDiscountAmountMax())
				.like(!StringUtils.isEmpty(orderrecharge.getBankAccount()), "deposit_bank_account",
						orderrecharge.getBankAccount())
				.orderBy(orderrecharge.getTerm(), orderrecharge.getEnable());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		orderrecharge.selectPage(result, entityWrapper);
		List<OrderRechargeEntity> list = result.getRecords();
		if (orderrecharge.getRechargeType() != null
				&& orderrecharge.getRechargeType() == Constant.RechargeType.THIRD.getValue()
				&& !CollectionUtil.isEmpty(list)) {
			for (OrderRechargeEntity entity : list) {
				if (!StringUtils.isEmpty(entity.getRechargePlatform())) {
					// Long.valueOf(entity.getRechargePlatform());
					PayConfigEntity payentity = payConfigService.selectById(entity.getRechargePlatform());
					if (payentity != null && !StringUtils.isEmpty(payentity.getName())) {
						entity.setRechargePlatformName(payentity.getName());
					}
				}
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
		if (Constant.OrderStatus.UNCONFIRMED.getValue() == orderRecharge.getStatus()) {
			throw new RRException("请先锁定订单");
		}
		orderRecharge.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderRecharge.setRechargeTime(new Date());
		if (Constant.RechargeType.THIRD.getValue() != orderRecharge.getRechargeType()) {
			orderRecharge.setSysUserId(getUser().getUserId());
			orderRecharge.setSysUserAccount(getUser().getUsername());
		}
		orderRechargeService.orderRechargeConfirm(orderRecharge);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderRecharge.getUserAccount())
					.setContent(String.format(SUCCUSS_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
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
		if (Constant.OrderStatus.UNCONFIRMED.getValue() != orderRecharge.getStatus()) {
			throw new RRException("订单已经被处理");
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
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderRecharge.getUserAccount())
					.setContent(String.format(FAIL_CONTENT, orderRecharge.getOrderNo(), orderRecharge.getAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
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

}
