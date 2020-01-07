package com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.annotation.SysLog;
import com.xmsy.server.zxyy.webhome.common.annotation.TakeOrderConfirmRepeat;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.SysConstant;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.entity.MessageManagementEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.messagemanagement.service.MessageManagementService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyLockingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.service.OrderTakeMoneyService;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 取款记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
@Slf4j
@RestController
@RequestMapping("ordertakemoney/ordertakemoney")
public class OrderTakeMoneyController extends AbstractController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderTakeMoneyService orderTakeMoneyService;
	@Autowired
	private OrderCashExamineService orderCashExamineService;
	@Autowired
	private MessageManagementService messageManagementService;
	@Autowired
	private PushService pushService;

	public static final String TITLE = "取款通知";
	public static final String CANCEL_CONTENT = "取款订单: %s, 金额 : %s, 已取消打码并确认,请联系管理员";
	public static final String FAIL_CONTENT = "取款订单: %s, 金额 : %s, 被取消,请联系管理员";
	public static final String SUCCUSS_CONTENT = "取款订单: %s, 金额 : %s, 已被确认,请注意查收";
	public static final int CONTENT_TYPE = 1; // 用户邮件类型
	public static final int TARGET_OBJECT = 1;// 指定用户

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ordertakemoney:ordertakemoney:list")
	public R list(OrderTakeMoneyEntity ordertakemoney, PageParam pageParam) {
		if (StringUtils.isEmpty(ordertakemoney.getTerm())) {
			// 如果没有指定排序，默认时间倒序
			ordertakemoney.setTerm("create_time");
			ordertakemoney.setEnable(false);
		}

		Page<OrderTakeMoneyEntity> result = new Page<OrderTakeMoneyEntity>(pageParam.getPage(), pageParam.getLimit());
		Wrapper<OrderTakeMoneyEntity> entityWrapper = new EntityWrapper<OrderTakeMoneyEntity>(ordertakemoney)
				.ge(!StringUtils.isEmpty(ordertakemoney.getQueryTime()), "create_time", // 取款时间查询
						ordertakemoney.getQueryTime() + SysConstant.START_TIME)
				.le(!StringUtils.isEmpty(ordertakemoney.getQueryTime()), "create_time",
						ordertakemoney.getQueryTime() + SysConstant.END_TIME)
				.ge(null != ordertakemoney.getAmountMin() && 0 < ordertakemoney.getAmountMin(), "take_amount", // 取款金额查询
						ordertakemoney.getAmountMin())
				.le(null != ordertakemoney.getAmountMax() && 0 < ordertakemoney.getAmountMax(), "take_amount",
						ordertakemoney.getAmountMax())
				.ge(null != ordertakemoney.getPoundageMin(), "poundage", ordertakemoney.getPoundageMin())
				.le(null != ordertakemoney.getPoundageMax(), "poundage", ordertakemoney.getPoundageMax())
				.like(!StringUtils.isEmpty(ordertakemoney.getUserBankName()), "bank_name",
						ordertakemoney.getUserBankName())
				.like(!StringUtils.isEmpty(ordertakemoney.getUserIncomeNo()), "income_no", // 入款账号
						ordertakemoney.getUserIncomeNo())
				.orderBy(ordertakemoney.getTerm(), ordertakemoney.getEnable());
		entityWrapper.orderBy(pageParam.getSort(), pageParam.getDirection());
		ordertakemoney.selectPage(result, entityWrapper);

		List<OrderTakeMoneyEntity> dataList = result.getRecords();
		if (dataList != null && !dataList.isEmpty()) {
			Set<Long> examineIdlist = new HashSet<>();
			for (OrderTakeMoneyEntity data : dataList) {
				if (data.getOrderExamineId() != null) {
					examineIdlist.add(data.getOrderExamineId());
				}

			}
			if (null != examineIdlist && !examineIdlist.isEmpty()) {
				Map<Long, OrderCashExamineEntity> examineMap = new HashMap<>();
				Wrapper<OrderCashExamineEntity> examineWrapper = new EntityWrapper<OrderCashExamineEntity>(null);
				examineWrapper.in("id", examineIdlist);
				List<OrderCashExamineEntity> examineList = orderCashExamineService.selectList(examineWrapper);
				if (null != examineList && !examineList.isEmpty()) {
					for (OrderCashExamineEntity examine : examineList) {
						examineMap.put(examine.getId(), examine);
					}
					for (OrderTakeMoneyEntity data : dataList) {
						if (data.getOrderExamineId() != null && examineMap.get(data.getOrderExamineId()) != null) {
							OrderCashExamineEntity edata = examineMap.get(data.getOrderExamineId());
							data.setUserNeedBet(MathUtil.getBigDecimal(edata.getUserNeedBet()));
							data.setUserValidBet(MathUtil.getBigDecimal(edata.getUserValidBet()));
						}
					}
				}
			}
		}
		return R.ok().put("page",
				new PageUtils(dataList, result.getTotal(), result.getSize(), result.getCurrent(), result.getPages()));
	}

	/**
	 * 余额取款数量
	 */
	@RequestMapping("/totalNumber")
	@RequiresPermissions("ordertakemoney:ordertakemoney:totalNumber")
	public R totalNumber(OrderTakeMoneyEntity ordertakemoney, PageParam pageParam) {
		OrderTakeMoneyEntity entity = new OrderTakeMoneyEntity();
		entity.setType(Constant.TakeMoneyType.TAKE_MONEY.getValue());
		entity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		int num = orderTakeMoneyService.selectCount(new EntityWrapper<OrderTakeMoneyEntity>(entity));
		return R.ok().put("num", num);
	}

	/**
	 * 取款订单批量锁定
	 * 
	 * @throws Exception
	 */
	@SysLog("取款订单批量锁定")
	@RequestMapping("/batchLocking")
	@RequiresPermissions("ordertakemoney:ordertakemoney:batchLocking")
	public R batchLocking(@RequestBody OrderTakeMoneyLockingEntity orderTakeMoneyEntity) {
		if (!orderTakeMoneyEntity.getSysUserId().equals(getUserId())) {
			throw new RRException("非法用户");
		}
		int i = orderTakeMoneyService.batchLocking(orderTakeMoneyEntity);
		System.out.println(i + "条记录已锁定成功");
		return R.ok().put("result", i + "条记录已锁定成功");
	}

	/**
	 * 佣金取款数量
	 */
	@RequestMapping("/commissionTotalNumber")
	@RequiresPermissions("ordertakemoney:ordertakemoney:commissionTotalNumber")
	public R commissionTotalNumber(OrderTakeMoneyEntity ordertakemoney, PageParam pageParam) {
		OrderTakeMoneyEntity entity = new OrderTakeMoneyEntity();
		entity.setType(Constant.TakeMoneyType.COMMISSION.getValue());
		entity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		int num = orderTakeMoneyService.selectCount(new EntityWrapper<OrderTakeMoneyEntity>(entity));
		return R.ok().put("num", num);
	}

	// 获取取款渠道
	@RequestMapping("/channel")
	@RequiresPermissions("ordertakemoney:ordertakemoney:channel")
	public R getTakeMoneyChannel() throws Exception {
		return R.ok().put("data", Constant.TakeMoneyChannel.getLookup());
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:info")
	public R info(@PathVariable("id") Long id) {
		OrderTakeMoneyEntity orderTakeMoney = orderTakeMoneyService.selectById(id);
		return R.ok().put("ordertakemoney", orderTakeMoney);
	}

	/**
	 * 取款订单锁定
	 * 
	 * @throws Exception
	 */
	@SysLog("取款订单锁定")
	@TakeOrderConfirmRepeat("取款订单重复锁定校验")
	@RequestMapping("/locking/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:locking")
	public R locking(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.UNCONFIRMED.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单已被他人锁定");
		}
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.LOCKING.getValue());
		orderTakeMoneyService.updateById(orderTakeMoneyEntity);
		return R.ok();
	}

	/**
	 * 取款订单解锁
	 * 
	 * @throws Exception
	 */
	@SysLog("取款订单解锁")
	@TakeOrderConfirmRepeat("取款订单重复解除锁定校验")
	@RequestMapping("/emancipateLocking/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:emancipateLocking")
	public R emancipateLocking(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单不是锁定状态");
		}
		orderTakeMoneyEntity.setSysUserId(null);
		orderTakeMoneyEntity.setSysUserAccount(null);
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		boolean result = orderTakeMoneyService.updateAll(orderTakeMoneyEntity);
		if (!result) {
			throw new RRException("订单解除锁定失败");
		}
		return R.ok();
	}

	/**
	 * 取款订单解锁---任务订单
	 * 
	 * @throws Exception
	 */
	@SysLog("取款订单锁定/任务订单")
	@TakeOrderConfirmRepeat("取款订单重复解除锁定校验")
	@RequestMapping("/dealWithEmancipateLocking/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:dealWithEmancipateLocking")
	public R dealWithEmancipateLocking(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单不是锁定状态");
		}
		if (!orderTakeMoneyEntity.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderTakeMoneyEntity.setSysUserId(null);
		orderTakeMoneyEntity.setSysUserAccount(null);
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.UNCONFIRMED.getValue());
		boolean result = orderTakeMoneyService.updateAll(orderTakeMoneyEntity);
		if (!result) {
			throw new RRException("订单解除锁定失败");
		}
		return R.ok();
	}

	/**
	 * 取款订单确认
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("取款订单重复确认校验")
	@SysLog("取款订单确认")
	@RequestMapping("/confirmed/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:confirmed")
	public R confirmed(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("请先锁定订单");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyConfirm(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(SUCCUSS_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单确认->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单确认异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 取款订单确认/任务订单
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("取款订单重复确认校验")
	@SysLog("取款订单确认/任务订单")
	@RequestMapping("/dealWithConfirmed/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:dealWithConfirmed")
	public R dealWithConfirmed(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("请先锁定订单");
		}
		if (!orderTakeMoneyEntity.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyConfirm(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(SUCCUSS_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单确认->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单确认异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 取款订单取消下注
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("订单重复取消下注校验")
	@SysLog("取款订单取消订单下注")
	@RequestMapping("/bet/canceled/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:betCanceled")
	public R betCanceled(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单已经被处理");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyBetCancel(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(CANCEL_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单取消订单下注->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单取消订单下注,异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 取款订单取消下注/任务订单
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("订单重复取消下注校验")
	@SysLog("取款订单取消订单下注/任务订单")
	@RequestMapping("/bet/dealWithBetCanceled/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:dealWithBetCanceled")
	public R dealWithBetCanceled(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单已经被处理");
		}
		if (!orderTakeMoneyEntity.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.COMPLETE.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyBetCancel(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(CANCEL_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单取消订单下注->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单取消订单下注,异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 线下充值取款取消
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("取款订单重复取消校验")
	@SysLog("取款订单取消")
	@RequestMapping("/canceled/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:canceled")
	public R canceled(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单已经被处理");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.CANCEL.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyCancel(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(FAIL_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			if (orderTakeMoneyEntity.getType() == Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
				pushMessage.setMoney(
						new BigDecimal(orderTakeMoneyEntity.getTakeAmount()).add(orderTakeMoneyEntity.getPoundage()));
			} else {
				pushMessage.setMoney(new BigDecimal(orderTakeMoneyEntity.getTakeAmount()));
			}
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单取消->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单取消,异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 线下充值取款取消/任务订单
	 * 
	 * @throws Exception
	 */
	@TakeOrderConfirmRepeat("取款订单重复取消校验")
	@SysLog("取款订单取消/任务订单")
	@RequestMapping("/dealWithCanceled/{id}")
	@RequiresPermissions("ordertakemoney:ordertakemoney:dealWithCanceled")
	public R dealWithCanceled(@PathVariable("id") Long id) throws Exception {
		OrderTakeMoneyEntity orderTakeMoneyEntity = orderTakeMoneyService.selectById(id);
		if (null == orderTakeMoneyEntity) {
			throw new RRException("订单不存在");
		}
		if (Constant.OrderStatus.LOCKING.getValue() != orderTakeMoneyEntity.getStatus()) {
			throw new RRException("订单已经被处理");
		}
		if (!orderTakeMoneyEntity.getSysUserId().equals(getUserId())) {
			throw new RRException("该订单已被他人锁定");
		}
		orderTakeMoneyEntity.setStatus(Constant.OrderStatus.CANCEL.getValue());
		orderTakeMoneyEntity.setSysUserId(getUser().getUserId());
		orderTakeMoneyEntity.setSysUserAccount(getUser().getUsername());
		orderTakeMoneyService.orderTakeMoneyCancel(orderTakeMoneyEntity);
		Date failTime = DateUtil.nextWeek(); // 邮件失效时间
		Date effectTime = new Date(); // 邮件生效时间
		try {
			// 邮件推送
			MessageManagementEntity messageManagement = new MessageManagementEntity()
					.setUserAccount(orderTakeMoneyEntity.getUserAccount())
					.setContent(String.format(FAIL_CONTENT, orderTakeMoneyEntity.getOrderNo(),
							orderTakeMoneyEntity.getTakeAmount()))
					.setContentType(CONTENT_TYPE).setTargetObject(TARGET_OBJECT).setEnable(true).setTitle(TITLE)
					.setEffectTime(effectTime).setFailureTime(failTime);
			messageManagementService.save(messageManagement);
			// 用户基本信息推送
			UserEntity entity = userService.selectById(orderTakeMoneyEntity.getUserId());
			UserEntity pushMessage = new UserEntity();
			pushMessage.setId(entity.getId());
			pushMessage.setUnreadNum(1);
			if (orderTakeMoneyEntity.getType() == Constant.TakeMoneyType.TAKE_MONEY.getValue()) {
				pushMessage.setMoney(
						new BigDecimal(orderTakeMoneyEntity.getTakeAmount()).add(orderTakeMoneyEntity.getPoundage()));
			} else {
				pushMessage.setMoney(new BigDecimal(orderTakeMoneyEntity.getTakeAmount()));
			}
			UserInfoMessage message = new UserInfoMessage(pushMessage, null);
			log.info("[取款订单取消->用户未读消息-推送消息] message {}", message);
			pushService.pushExchange(message);
		} catch (Exception e) {
			log.error("[取款订单取消,异常] Exception", e);
		}
		return R.ok();
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ordertakemoney:ordertakemoney:save")
	public R save(@RequestBody OrderTakeMoneyEntity ordertakemoney) {
		orderTakeMoneyService.insert(ordertakemoney);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ordertakemoney:ordertakemoney:update")
	public R update(@RequestBody OrderTakeMoneyEntity ordertakemoney) {
		orderTakeMoneyService.updateById(ordertakemoney);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ordertakemoney:ordertakemoney:delete")
	public R delete(@RequestBody Long[] ids) {
		for (Long id : ids) {
			orderTakeMoneyService.deleteById(id);
		}
		return R.ok();
	}

}
