package com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.MathUtil;
import com.xmsy.server.zxyy.webhome.common.utils.OrderNoUtil;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.dao.OrderCashExamineDao;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity.OrderCashExamineEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.service.OrderCashExamineService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.dao.OrderRechargeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderBankRechargeLockingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service.OrderRechargeService;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.service.UserTransactionRecordService;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserOrderRechargeExchangeParam;
import com.xmsy.server.zxyy.webhome.mq.MqClient;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .充值订单处理逻辑
 * 
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service("orderRechargeService")
public class OrderRechargeServiceImpl extends ServiceImpl<OrderRechargeDao, OrderRechargeEntity>
		implements OrderRechargeService {
	@Autowired
	private UserService userService;

	@Autowired
	private MqClient mqClient;

	@Autowired
	private OrderCashExamineService orderCashExamineService;

	@Autowired
	private UserTransactionRecordService userTransactionRecordService;

	@Autowired
	private OrderPreferentialService orderPreferentialService;
	@Autowired
	private OrderCashExamineDao orderCashExamineDao;

	@Override
	public Integer updateOrderRecharge(String merchantOrderNo, String orderNo, Integer completed, Integer uncomfirmed,
			String rechargeTime) {
		log.info("[updateOrderRecharge] merchantOrderNo {},orderNo {},completed {},uncomfirmed {},rechargeTime {}",
				merchantOrderNo, orderNo, completed, uncomfirmed, rechargeTime);
		return baseMapper.updateOrderRecharge(merchantOrderNo, orderNo, completed, uncomfirmed, rechargeTime);
	}

	@Override
	@Transactional
	public void saveAdministratorRecharge(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user)
			throws Exception {
		// 保存订单
		orderRecharge.setOrderNo(OrderNoUtil.getOrderNo());
		BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
		orderRecharge.setUserNeedBet(userNeedBet);
		this.baseMapper.insert(orderRecharge);
		saveOrderRechargeTransactionRecord(orderRecharge, transactionType, user);
		// 添加代理商返佣
		RechargeRebateMessage message = new RechargeRebateMessage();
		message.setUserId(user.getId());
		mqClient.userVipPush(message);
		if (null == user.getSuperiorsId()) {
			return;
		}
		message.setAgentId(user.getSuperiorsId());
		message.setOrderNo(orderRecharge.getOrderNo());
		mqClient.agentRebatePush(message);
	}

	@Override
	public void orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception {
		UserEntity user = userService.selectById(orderRecharge.getUserId());
		if (null == user) {
			throw new RRException(ErrorCode.UserErrorCode.USER_IS_NULL.getErrMsg(),
					ErrorCode.UserErrorCode.USER_IS_NULL.getCode());
		}
		// 充值优惠计算
		if (rechargePreferential(orderRecharge, user)) {
			// 如果是首冲,修改首冲状态
			user.setFirstRecharge(true);
			userService.updateUserFirstRecharge(user.getId());
		}
		// 创建取款稽查记录
		BigDecimal userNeedBet = orderCashExamineService.saveCashExamine(orderRecharge, user);
		orderRecharge.setUserNeedBet(userNeedBet);
		this.baseMapper.updateById(orderRecharge);
		// 添加用户交易记录
		saveOrderRechargeTransactionRecord(orderRecharge, Constant.TransactionType.RECHARGE.getValue(), user);
		
		// 添加代理商返佣
		RechargeRebateMessage message = new RechargeRebateMessage();
		message.setUserId(user.getId());
		mqClient.userVipPush(message);
		if (null == user.getSuperiorsId()) {
			return;
		}
		message.setAgentId(user.getSuperiorsId());
		message.setOrderNo(orderRecharge.getOrderNo());
		mqClient.agentRebatePush(message);
	}

	// 订单充值修改用户账号金额，生成交易记录
	private void saveOrderRechargeTransactionRecord(OrderRechargeEntity orderRecharge, int transactionType,
			UserEntity user) {
		UserTransactionRecordEntity transactionRecord = new UserTransactionRecordEntity();
		transactionRecord.setType(transactionType);
		transactionRecord.setOrderNo(orderRecharge.getOrderNo());
		transactionRecord.setDetailType(orderRecharge.getRechargeChannel().intValue());
		transactionRecord.setFristrecharge(orderRecharge.getFristrecharge());
		transactionRecord.setUserId(orderRecharge.getUserId());
		transactionRecord.setUserAccount(orderRecharge.getUserAccount());
		// 充值金额=充值金额+优惠金额
		transactionRecord.setAmount(MathUtil.getBigDecimal(orderRecharge.getAmount())
				.add(orderRecharge.getDiscountAmount() == null ? BigDecimal.ZERO : orderRecharge.getDiscountAmount()));
		// 更新用户余额(本来需要更新用户金额，再额度转换，两个动作直接合成一个额度转换直接把冲的金额转成冲金币，但是交易记录两个两条)
		// 充值之后的账户余额=账户余额+充值金额
		transactionRecord.setMoney(user.getMoney().add(transactionRecord.getAmount()));
		transactionRecord.setCommission(user.getCommission());
		transactionRecord.setRemake(orderRecharge.getRemark());
		transactionRecord.setCoin(user.getCoin());
		// 保存交易记录
		userTransactionRecordService.insert(transactionRecord);
		// 充值+额度转换两个动作一起做（只修改一次数据库）
		userService.rechargeAndChanger(user, transactionRecord.getAmount(),
				transactionRecord.getAmount().multiply(new BigDecimal(Constant.COIN_RATE)).longValue());
	}

	// 充值报表
	@Override
	public RechargeReport getRechargeReport(String startTime, String endTime) {
		return baseMapper.getRechargeReport(startTime, endTime);
	}

	// 充值报表
	@Override
	public List<RechargeTableReport> getRechargeTableReport(String startTime, String endTime) {
		return baseMapper.getRechargeTableReport(startTime, endTime);
	}

	// 优惠计算
	private boolean rechargePreferential(OrderRechargeEntity rechargeOrder, UserEntity user) {
		// 获取层级优惠
		List<OrderPreferentialEntity> preferentials = orderPreferentialService
				.getPreferentialsByHierarchyId(user.getHierarchyId());
		// 首冲优惠对象
		OrderPreferentialEntity firstRecharge = null;
		// 满送优惠对象
		OrderPreferentialEntity presentRecharge = null;
		for (OrderPreferentialEntity preferential : preferentials) {
			if (preferential.getFirstRecharge()) {
				firstRecharge = preferential;
			} else {
				presentRecharge = preferential;
			}
		}
		log.info("[优惠对象] firstRecharge:{} presentRecharge：{}", firstRecharge, presentRecharge);
		if (user.getFirstRecharge()) {
			// 非首冲（不是首冲判断是否符合满送）
			if (rechargeOrder.getAmount().longValue() > presentRecharge.getRechargeAmount().longValue()) {
				rechargeOrder.setDiscountAmount(
						presentRecharge.getGiftProportion().multiply(new BigDecimal(rechargeOrder.getAmount())));
				rechargeOrder.setDiscountId(presentRecharge.getId());
			}
			return false;
		} else {
			// 首冲
			rechargeOrder.setDiscountAmount(
					firstRecharge.getGiftProportion().multiply(new BigDecimal(rechargeOrder.getAmount())));
			rechargeOrder.setDiscountId(firstRecharge.getId());
			rechargeOrder.setFristrecharge(true);
			return true;
		}
	}

	@Override
	@Transactional
	public void revokeRecharge(Long rechargeId) {
		OrderRechargeEntity orderRecharge = this.baseMapper.selectById(rechargeId);
		if (orderRecharge == null || orderRecharge.getId() == null
				|| orderRecharge.getStatus() == OrderStatus.REVOKE.getValue()) {
			throw new RRException(ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getErrMsg(),
					ErrorCode.OrderErrorCode.RECHARGE_RECORD_ISNULL.getCode());
		}
		try {
			orderCancelUpdateCashExamine(orderRecharge.getOrderNo(), orderRecharge.getUserNeedBet());
			userService.revokeMoney(orderRecharge);
			orderRecharge.setStatus(OrderStatus.REVOKE.getValue());
			this.updateById(orderRecharge);
		} catch (Exception e) {
			log.error("[充值订单撤销保存失败] revokeMoney {}", orderRecharge, e);
		}
	}

	/**
	 * 取消人工充值订单，稽查订单修改重试
	 * 
	 * @param orderRechargeOrderNo
	 */
	private void orderCancelUpdateCashExamine(String orderRechargeOrderNo, BigDecimal userNeedBet) {
		int i = 0;
		while (i < 10) {
			OrderCashExamineEntity orderCashExamine = new OrderCashExamineEntity();
			orderCashExamine.setOrderNo(orderRechargeOrderNo);
			orderCashExamine = orderCashExamineDao.selectOne(orderCashExamine);
			if (orderCashExamine == null || orderCashExamine.getId() == null) {
				throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
						ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
			}
			// 是否结算（0：未审核，1：已审核）
			OrderCashExamineEntity newOrderCashExamine = new OrderCashExamineEntity();
			if (orderCashExamine.getStatus()) {// 1：已审核
				// 新的取款稽查需要扣除已经结算的 总需要打码数
				newOrderCashExamine = orderCashExamineDao.findRecentOrderCashExamine(orderCashExamine.getUserId());
				if (newOrderCashExamine == null || newOrderCashExamine.getId() == null) {
					throw new RRException(ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getErrMsg(),
							ErrorCode.OrderErrorCode.EXAMINE_IS_NULL.getCode());
				}
			} else {// 0：未审核
					// 需要把上一条取款稽查记录修改为未审核
				newOrderCashExamine = orderCashExamine;
			}
			newOrderCashExamine.setUserNeedBet(newOrderCashExamine.getUserNeedBet().subtract(userNeedBet));
			if (newOrderCashExamine.getUserNeedBet().compareTo(BigDecimal.ZERO) < 0) {
				newOrderCashExamine.setUserNeedBet(BigDecimal.ZERO);
			}
			if (orderCashExamineDao.updateById(newOrderCashExamine) > 0) {
				return;
			} else {
				i++;
			}
		}
		log.error("[revokeRecharge] ->orderCancelUpdateCashExamine 失败  orderRechargeOrderNo {}", orderRechargeOrderNo);
		throw new RRException("取消人工充值订单,稽查记录更新失败 ");

	}

	@Override
	public Page<UserOrderRechargeExchangeParam> rechargeExamineList(Long userId, PageParam pageParam, String startTime,
			String endTime) {
		log.info("[rechargeExamineList] userId:{} , startTime:{} , endTime:{} , pageParam:{}", userId, startTime,
				endTime, pageParam);
		Page<UserOrderRechargeExchangeParam> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造
		// page 对象
		return page.setRecords(baseMapper.rechargeExamineList(page, userId, startTime, endTime));
	}

	// 获取指定类型，指定日期充值总额
	@Override
	public int sumAmountForDate(String date, Integer rechargeType) {
		// TODO Auto-generated method stub
		return baseMapper.sumAmountForDate(date, rechargeType);
	}

	@Override
	public Map<String, Object> sumRechargeAmount(String startDate, String endDate, Integer rechargeType) {
		// TODO Auto-generated method stub
		return baseMapper.sumRechargeAmount(startDate, endDate, rechargeType);
	}

	/**
	 * 根据订单ID修改订单 目前只有修改状态，修改修改什么直接在sql中添
	 */
	@Override
	public boolean updateAll(OrderRechargeEntity orderRecharge) {
		// TODO Auto-generated method stub
		return baseMapper.updateAll(orderRecharge);
	}

	@Override
	public int batchLocking(OrderBankRechargeLockingEntity lockingEntity) {
		if (lockingEntity.getNum() <= 0) {
			throw new RRException("条数需大于0");
		}
		// 修改成功的订单集合
		List<OrderRechargeEntity> unconfirmedList = new ArrayList<OrderRechargeEntity>();
		while (true) {
			// 如果修改成功的订单数等于要求的数量，则跳出循环
			if (unconfirmedList.size() == lockingEntity.getNum()) {
				break;
			}
			// 获取所有状态为未确定的订单
			List<OrderRechargeEntity> list = selectList(new EntityWrapper<OrderRechargeEntity>(
					new OrderRechargeEntity().setStatus(Constant.OrderStatus.UNCONFIRMED.getValue())
							.setRechargeType(Constant.RechargeType.BANK.getValue())));
			// 如果没有未确认过订单，跳出循环
			if (CollectionUtil.isEmpty(list)) {
				if (unconfirmedList.size() == 0) {
					throw new RRException("无未确定订单");
				}
				break;
			} else {
				// 随机获取到订单，并进行修改
				Random random = new Random();
				int s = random.nextInt(list.size()) % (list.size() - 0 + 1) + 0;
				OrderRechargeEntity updateRecharge = list.get(s);
				updateRecharge.setStatus(Constant.OrderStatus.LOCKING.getValue());
				updateRecharge.setSysUserAccount(lockingEntity.getSysUserAccount());
				updateRecharge.setSysUserId(lockingEntity.getSysUserId());
				boolean result = updateById(updateRecharge);
				if (result) {
					// 如果修改成功
					unconfirmedList.add(updateRecharge);
				}
			}

		}
		return unconfirmedList.size();
	}

	@Override
	public Map<String, Object> countRechargePreferential(BigDecimal amount, boolean isFirst, Long hierarchyId) {
		Map<String, Object> resultMap = new HashMap<>();
		Long discountId = 0L;
		BigDecimal discountAmount = BigDecimal.ZERO;
		// 获取层级优惠
		List<OrderPreferentialEntity> preferentials = orderPreferentialService
				.getPreferentialsByHierarchyId(hierarchyId);
		if (preferentials == null || preferentials.isEmpty()) {
			throw new RRException("层级优惠未设置 ");
		}
		// 优惠对象
		OrderPreferentialEntity presentRecharge = null;
		for (OrderPreferentialEntity preferential : preferentials) {
			if (preferential.getFirstRecharge() != isFirst) {
				presentRecharge = preferential;
				break;
			}
		}
		if (presentRecharge == null || presentRecharge.getId() == null) {
			throw new RRException("层级优惠未设置 ");
		}
		log.info("[优惠对象] presentRecharge：{}", presentRecharge);
		if (isFirst) {// 非首冲（不是首冲判断是否符合满送）
			if (amount.longValue() > presentRecharge.getRechargeAmount().longValue()) {
				discountAmount = presentRecharge.getGiftProportion().multiply(amount);
				discountId = presentRecharge.getId();
			}
		} else {// 首冲
			discountAmount = presentRecharge.getGiftProportion().multiply(amount);
			discountId = presentRecharge.getId();
		}
		resultMap.put("discountAmount", discountAmount);
		resultMap.put("discountId", discountId);
		return resultMap;

	}

	@Override
	public Long selectAllAmount(Long userId) {
		return this.baseMapper.selectAllAmount(userId);
	}

}
